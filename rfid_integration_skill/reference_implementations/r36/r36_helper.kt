

@SuppressLint("UnspecifiedRegisterReceiverFlag")
class R36Helper: RfidHelperExtDelegate,                             
    CoroutineScopeExtDelegate by BuiltInCoroutineScopeImpl()        
{
    override val theEventBus: KcEventBus by lazy { KcEventBus() }   

    internal var rfidManager: RfidManager? = null
    internal var tempRfidManager: RfidManager? = null   
    internal var readerManager: ReaderManager? = null
    internal var tempReaderManager: ReaderManager? = null   


    override fun hasRfidInstance(): Boolean {   
        val result = (null != rfidManager)
        Logger.getLogger("R36Helper").log(Level.INFO, "hasRfidInstance(): [$result]")
        return result
    }

    override fun createRfidInstanceIfNeeded(context: Context) {     
        if (null != rfidManager) {
            Logger.getLogger("R36Helper").log(Level.WARNING, "createRfidInstanceIfNeeded() - rfid Manager has existed!!")
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(rfidBroadcastReceiver, getRfidBroadcastReceiverIntentFilter(), RECEIVER_EXPORTED)
        } else {
            context.registerReceiver(rfidBroadcastReceiver, getRfidBroadcastReceiverIntentFilter())
        }


        tempRfidManager = try {
            RfidManager.InitInstance(context)
        }
        catch (cause: Throwable) {
            Logger.getLogger("R36Helper").log(Level.SEVERE, "createRfidInstanceIfNeeded() - error on RfidManager.InitInstance(context)", cause)
            null
        }
    }

    override fun freeRfidInstanceIfNeeded(context: Context) {   
        context.unregisterReceiver(rfidBroadcastReceiver)
        if (null != rfidManager) {
            try { rfidManager?.Release() } catch (_: Throwable) {}
        }
        rfidManager = null
    }

    private fun getDeviceInfo() {
        when (val deviceInfo: FmApiResult<DeviceInfoDelegate>? = rfidManager?.getDeviceInfoExt()) {
            is FmApiResult.Success -> {
                Logger.getLogger("R36Helper").log(Level.INFO, "getDeviceInfo() - deviceInfo: [${deviceInfo.data}]")
            }
            is FmApiResult.Error -> {
                Logger.getLogger("R36Helper").log(Level.SEVERE, "error on getDeviceInfo(): [${deviceInfo.cause}]")
            }
            else -> {
                Logger.getLogger("R36Helper").log(Level.SEVERE, "error on getDeviceInfo() - deviceInfo is null!!")
            }
        }
    }

    override suspend fun setTxPower(value: Int): FmApiResult<Int> =     // revision by elite_lin - 2025/08/30
        if (null == rfidManager) {
            Logger.getLogger("R36Helper").log(Level.WARNING, "setTxPower() - RfidManager is null!!")
            FmApiResult.Error(
                FmRuntimeException("R36Helper - setTxPower() - RfidManager is null!!", BaseErrorCodes.ILLEGAL_ARGUMENT_ERROR)
            )
        }
        else {
            rfidManager!!.setTxPowerExt(value)
                .suspendThen {
                    delay(1000L)
                    rfidManager!!.getTxPowerExt()
                }

        }

    override fun getTxPower(): FmApiResult<Int> =       
        if (null == rfidManager) {
            Logger.getLogger("R36Helper").log(Level.WARNING, "getTxPower() - RfidManager is null!!")
            FmApiResult.Error(
                FmRuntimeException("R36Helper - getTxPower() - RfidManager is null!!", BaseErrorCodes.ILLEGAL_ARGUMENT_ERROR)
            )
        }
        else {
            rfidManager!!.getTxPowerExt()
        }


    private suspend fun <T> compoundedWriteTagWrapperExt(
        writeTxPower: Int = 10,
        writeBlock: () -> FmApiResult<T>,
    ): FmApiResult<Int> =
        when(val txPowerResult: FmApiResult<Int> = getTxPower()) {
            is FmApiResult.Success -> {
                Logger.getLogger("R36Helper").log(Level.INFO, "compoundedWriteTagWrapperExt() - getTxPower() - success: [${txPowerResult.data}]")
                if(txPowerResult.data != writeTxPower) {
                    Logger.getLogger("R36Helper").log(Level.INFO, "compoundedWriteTagWrapperExt() [1]  --> setTxPower($writeTxPower)")
                    delay(1000L)
                    setTxPower(writeTxPower)
                }
                else {
                    FmApiResult.Success(writeTxPower)
                }
                .suspendThen { txPower: Int ->
                    Logger.getLogger("R36Helper").log(Level.INFO, "compoundedWriteTagWrapperExt() - txPower: [${txPower}]")
                    if(txPower != writeTxPower) {
                        Logger.getLogger("R36Helper").log(Level.WARNING, "compoundedWriteTagWrapperExt() [2] - txPower: [$txPower] != writeTxPower: [$writeTxPower]")
                        FmApiResult.Error(
                            FmRuntimeException("txPower: [$txPower] != writeTxPower: [$writeTxPower]", R36ErrorCodes.SET_TX_POWER_ERROR)
                        )
                    }
                    else {
                        delay(1000L)
                        writeBlock()
                    }
                }
                .suspendThen {
                    delay(1000L)
                    getRFIDMode()
                }
                .suspendThen { rfidMode: RFIDMode ->
                    if (rfidMode != RFIDMode.Inventory) {
                        Logger.getLogger("R36Helper").log(Level.INFO, "compoundedWriteTagWrapperExt() [1] - RfidMode != Inventory --> setRFIDMode(Inventory)")
                        delay(1000L)
                        setRFIDMode()
                    }
                    else {
                        Logger.getLogger("R36Helper").log(Level.INFO, "compoundedWriteTagWrapperExt() [1] - RfidMode == Inventory")
                        FmApiResult.Success(RFIDMode.Inventory)
                    }
                }
                .then { rfidMode: RFIDMode ->
                    if (rfidMode != RFIDMode.Inventory) {
                        Logger.getLogger("R36Helper").log(Level.WARNING, "compoundedWriteTagWrapperExt() [2] - RfidMode != Inventory")
                        FmApiResult.Error(
                            FmRuntimeException("RfidMode [$rfidMode] != [Inventory]", R36ErrorCodes.OPERATION_SET_ERROR)
                        )
                    }
                    else {
                        Logger.getLogger("R36Helper").log(Level.INFO, "compoundedWriteTagWrapperExt() [2] - RfidMode == Inventory")
                        getTxPower()
                    }
                }
                .suspendThen { txPower: Int ->
                    if(txPowerResult.data != txPower) {
                        Logger.getLogger("R36Helper").log(Level.INFO, "compoundedWriteTagWrapperExt() [3] --> setTxPower(${txPowerResult.data})")
                        delay(1000L)
                        setTxPower(txPowerResult.data)
                    }
                    else {
                        FmApiResult.Success(txPowerResult.data)
                    }
                }
                .then { txPower: Int ->
                    if (txPower == txPowerResult.data) {
                        Logger.getLogger("R36Helper").log(Level.INFO, "compoundedWriteTagWrapperExt() [4] - txPower [$txPower] == txPowerResult.data [${txPowerResult.data}]")
                        FmApiResult.Success(1)
                    }
                    else {
                        Logger.getLogger("R36Helper").log(Level.WARNING, "compoundedWriteTagWrapperExt() [4] - txPower [$txPower] != txPowerResult.data [${txPowerResult.data}]")
                        FmApiResult.Error(
                            FmRuntimeException("txPower [$txPower] != txPowerResult.data [${txPowerResult.data}]", R36ErrorCodes.SET_TX_POWER_ERROR)
                        )
                    }
                }
            }
            is FmApiResult.Error -> {
                Logger.getLogger("R36Helper").log(Level.SEVERE, "compoundedWriteTagWrapperExt() - getTxPower() - error", txPowerResult.cause)
                txPowerResult
            }
        }


    suspend fun compoundedDirectWriteTagByEpc(
        currentEpcInHexFormat: String,
        newEpcInHexFormat: String,
        pwdInHexFormat: String? = null,
        memoryBank: RFIDMemoryBank = RFIDMemoryBank.EPC,
        startIndex: Int = 2 * 2,
        retry: Int = 3,
        writeTxPower: Int = 10,
    ) : FmApiResult<Int> =
        compoundedWriteTagWrapperExt(writeTxPower = writeTxPower) {
            rfidManager!!.directWriteTagByEpcExt(
                currentEpcInHexFormat = currentEpcInHexFormat,
                newEpcInHexFormat = newEpcInHexFormat,
                pwdInHexFormat = pwdInHexFormat,
                memoryBank = memoryBank,
                startIndex = startIndex,
                retry = retry,
            )
        }


    private val rfidBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                com.cipherlab.rfid.GeneralString.Intent_RFIDSERVICE_CONNECTED -> {
                    // make sure this AP does already connect with RFID service (after call RfidManager.InitInstance(this)
                    // [start] revision by elite_lin - 2025/12/11
                    val serviceVersion = tempRfidManager?.GetServiceVersion() ?: ""
                    val apiVersion = tempRfidManager?.GetAPIVersion() ?: ""
                    rfidManager = tempRfidManager
                    tempRfidManager = null
                    // [end] revision by elite_lin - 2025/12/11
                    Logger.getLogger("R36Helper").log(Level.INFO, "rfidBroadcastReceiver - [Intent_RFIDSERVICE_CONNECTED] - serviceVersion: [$serviceVersion], apiVersion: [$apiVersion]")
                    val openResult = UhfReaderOpenResult(serviceVersion, apiVersion)
                    // [start] revision by elite_lin - 2024/08/23
                    //asyncTask.launch(Dispatchers.IO) {
                    launch(Dispatchers.IO) {
                    // [end] revision by elite_lin - 2024/08/23
                        getDeviceInfo()
                        theEventBus.publish(openResult)
                    }

                }
                com.cipherlab.rfid.GeneralString.Intent_RFIDSERVICE_TAG_DATA -> {
                    val uhfTagDelegate = intent.buildUhfTagDelegate()
                    if (null != uhfTagDelegate) {
                        Logger.getLogger("R36Helper").log(Level.INFO, "rfidBroadcastReceiver - [Intent_RFIDSERVICE_TAG_DATA] - [$uhfTagDelegate]")
                        // [start] revision by elite_lin - 2024/08/23
                        //asyncTask.launch { eventBus.publish(uhfTagDelegate) }
                        launch { theEventBus.publish(uhfTagDelegate) }
                        // [end] revision by elite_lin - 2024/08/23
                    }
                    // If type=8 ; Authenticate response data in ReadData
                    /*if(type==GeneralString.TYPE_AUTHENTICATE_TAG && response==GeneralString.RESPONSE_OPERATION_SUCCESS)
                    {
                        Log.i(TAG, "Authenticate response data=" + ReadData );
                    }*/
                }

                com.cipherlab.rfid.GeneralString.Intent_RFIDSERVICE_EVENT -> {
                    when (val event = intent.getIntExtra(com.cipherlab.rfid.GeneralString.EXTRA_EVENT_MASK, -1)) {
                        DeviceEvent.LowBattery.value -> {
                            Logger.getLogger("R36Helper").log(Level.INFO, "rfidBroadcastReceiver - [Intent_RFIDSERVICE_EVENT] - LowBattery")
                        }
                        DeviceEvent.PowerSavingMode.value -> {
                            Logger.getLogger("R36Helper").log(Level.INFO, "rfidBroadcastReceiver - [Intent_RFIDSERVICE_EVENT] - PowerSavingMode")
                        }
                        DeviceEvent.OverTemperature.value -> {
                            Logger.getLogger("R36Helper").log(Level.INFO, "rfidBroadcastReceiver - [Intent_RFIDSERVICE_EVENT] - OverTemperature")
                        }
                        DeviceEvent.ScannerFailure.value -> {
                            Logger.getLogger("R36Helper").log(Level.INFO, "rfidBroadcastReceiver - [Intent_RFIDSERVICE_EVENT] - ScannerFailure")
                        }
                        else -> {
                            Logger.getLogger("R36Helper").log(Level.INFO, "rfidBroadcastReceiver - [Intent_RFIDSERVICE_EVENT] - (else) DeviceEvent: [$event]")
                        }
                    }
                }
                com.cipherlab.rfid.GeneralString.Intent_FWUpdate_ErrorMessage -> {
                    Logger.getLogger("R36Helper").log(Level.INFO, "rfidBroadcastReceiver - [FWUpdate_ErrorMessage]")
                    /*String mse = "";
				mse = intent.getStringExtra(GeneralString.FWUpdate_ErrorMessage);
				int errorcode = intent.getIntExtra(GeneralString.FWUpdate_ErrorCode,-1);
				if(mse!=null)
				{
					Log.d(TAG,  "FWUpdate Error : " + mse  + "(" + errorcode+")");
					Toast.makeText(MainActivity.this,  mse, Toast.LENGTH_SHORT).show();

					if(errorcode==FWUpdateErrorCode.SameVersion.getValue())
					{
						Log.d(TAG,  "SameVersion");
					}
				}
				Log.d(TAG,  "Intent_FWUpdate_ErrorMessage" );*/
                }
                com.cipherlab.rfid.GeneralString.Intent_FWUpdate_Percent -> {
                    val progress = intent.getIntExtra(com.cipherlab.rfid.GeneralString.FWUpdate_Percent, 0)
                    Logger.getLogger("R36Helper").log(Level.INFO, "rfidBroadcastReceiver - [Intent_FWUpdate_Percent] - progress: [$progress]")
                }
                com.cipherlab.rfid.GeneralString.Intent_FWUpdate_Finish -> {
                    Logger.getLogger("R36Helper").log(Level.INFO, "rfidBroadcastReceiver - [Intent_FWUpdate_Finish]")
                }
                com.cipherlab.rfid.GeneralString.Intent_GUN_Attached -> {
                    Logger.getLogger("R36Helper").log(Level.INFO, "rfidBroadcastReceiver - [Intent_GUN_Attached]")
                }
                com.cipherlab.rfid.GeneralString.Intent_GUN_Unattached -> {
                    Logger.getLogger("R36Helper").log(Level.INFO, "rfidBroadcastReceiver - [Intent_GUN_Unattached]")
                }
                com.cipherlab.rfid.GeneralString.Intent_GUN_Power -> {
                    val ac = intent.getBooleanExtra(com.cipherlab.rfid.GeneralString.Data_GUN_ACPower, false)
                    val connect = intent.getBooleanExtra(com.cipherlab.rfid.GeneralString.Data_GUN_Connect, false)
                    Logger.getLogger("R36Helper").log(Level.INFO, "rfidBroadcastReceiver - [Intent_GUN_Power] - ac: [$ac], connect: [$connect]")
                }
            }
        }
    }

    ///

    fun hasBarcodeManager(): Boolean {
        val result = (null != readerManager)
        Logger.getLogger("R36Helper").log(Level.INFO, "hasBarcodeManager(): [$result]")
        return result
    }

    fun createBarcodeReaderManagerIfNeeded(context: Context) {
        if (null != readerManager) {
            Logger.getLogger("R36Helper").log(Level.WARNING, "createBarcodeReaderManagerIfNeeded() - readerManager has existed!!")
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(barcodeBroadcastReceiver, getBarcodeBroadcastReceiverIntentFilter(), RECEIVER_EXPORTED)
        } else {
            context.registerReceiver(barcodeBroadcastReceiver, getBarcodeBroadcastReceiverIntentFilter())
        }


        tempReaderManager = try {
            ReaderManager.InitInstance(context)
        }
        catch (cause: Throwable) {
            Logger.getLogger("R36Helper").log(Level.SEVERE, "createBarcodeReaderManagerIfNeeded() - error on ReaderManager.InitInstance(context)", cause)
            null
        }
    }

    fun freeBarcodeManagerIfNeeded(context: Context) {
        context.unregisterReceiver(barcodeBroadcastReceiver)
        if (null != readerManager) {
            try { readerManager?.Release() } catch (_: Throwable) {}
        }
        readerManager = null
    }

    private val barcodeBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                com.cipherlab.barcode.GeneralString.Intent_SOFTTRIGGER_DATA -> {
                    val data = intent.getStringExtra(GeneralString.BcReaderData)
                    Logger.getLogger("R36Helper").log(Level.INFO, "barcodeBroadcastReceiver - [Intent_SOFTTRIGGER_DATA] - data: [$data]")
                }
                com.cipherlab.barcode.GeneralString.Intent_PASS_TO_APP -> {
                    // If user disable KeyboardEmulation, barcode reader service will broadcast Intent_PASS_TO_APP
                    val data = intent.getStringExtra(GeneralString.BcReaderData)
                    Logger.getLogger("R36Helper").log(Level.INFO, "barcodeBroadcastReceiver - [Intent_PASS_TO_APP] - data: [$data]")
                }
                com.cipherlab.barcode.GeneralString.Intent_READERSERVICE_CONNECTED -> {
                    // Make sure this app bind to barcode reader service , then user can use APIs to get/set settings from barcode reader service
                    // [start] revision by elite_lin - 2024/08/23
                    //asyncTask.launch(Dispatchers.IO) {
                    launch(Dispatchers.IO) {
                    // [end] revision by elite_lin - 2024/08/23
                        // [start] revision by elite_lin - 2025/12/11
                        //when (val serviceResponse = readerManager?.getBarcodeServiceVerExt()) {
                        when (val serviceResponse = tempReaderManager?.getBarcodeServiceVerExt()) {
                            is FmApiResult.Success -> {
                                readerManager = tempReaderManager
                                tempReaderManager = null
                                Logger.getLogger("R36Helper").log(Level.SEVERE, "barcodeBroadcastReceiver - [Intent_READERSERVICE_CONNECTED] - get readerManager instance!!",)
                        // [end] revision by elite_lin - 2025/12/11
                                val readerType: BcReaderType? = readerManager?.getReaderTypeExt()
                                Logger.getLogger("R36Helper").log(Level.INFO, "barcodeBroadcastReceiver - [Intent_READERSERVICE_CONNECTED] - readerType: [$readerType]")
                                readerManager?.setDefaultReaderOutputConfigurationExt()
                                readerManager?.setActiveExt(true)
                                readerManager?.SetReaderCallback(MyReaderCallback())
                                val openResult = BarcodeDecoderOpenResult(serviceResponse.data)
                                theEventBus.publish(openResult)
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }


    private inner class MyReaderCallback: ReaderCallback {
        override fun asBinder(): IBinder? {
            return null
        }

        override fun onDecodeComplete(barcode: String?) {
            if (null == barcode) { return }
            Logger.getLogger("R36Helper").log(Level.INFO, "MyReaderCallback - onDecodeComplete(): [$barcode]")
            // [start] revision by elite_lin - 2024/08/23
            //asyncTask.launch(Dispatchers.IO) {
            launch(Dispatchers.IO) {
            // [end] revision by elite_lin - 2024/08/23
                val barcodeEntityDelegate: R36BarcodeEntityDelegate = BarcodeEntityDelegateImpl(barcode)
                theEventBus.publish(barcodeEntityDelegate)
            }
        }
    }
}


internal fun getRfidBroadcastReceiverIntentFilter(): IntentFilter =
    IntentFilter().apply {
        addAction(com.cipherlab.rfid.GeneralString.Intent_RFIDSERVICE_CONNECTED)
        addAction(com.cipherlab.rfid.GeneralString.Intent_RFIDSERVICE_TAG_DATA)
        addAction(com.cipherlab.rfid.GeneralString.Intent_RFIDSERVICE_EVENT)
        addAction(com.cipherlab.rfid.GeneralString.Intent_FWUpdate_ErrorMessage)
        addAction(com.cipherlab.rfid.GeneralString.Intent_FWUpdate_Percent)
        addAction(com.cipherlab.rfid.GeneralString.Intent_FWUpdate_Finish)
        addAction(com.cipherlab.rfid.GeneralString.Intent_GUN_Attached)
        addAction(com.cipherlab.rfid.GeneralString.Intent_GUN_Unattached)
        addAction(com.cipherlab.rfid.GeneralString.Intent_GUN_Power)
    }


internal fun getBarcodeBroadcastReceiverIntentFilter(): IntentFilter =
    IntentFilter().apply {
        addAction(com.cipherlab.barcode.GeneralString.Intent_SOFTTRIGGER_DATA)
        addAction(com.cipherlab.barcode.GeneralString.Intent_PASS_TO_APP)
        addAction(com.cipherlab.barcode.GeneralString.Intent_READERSERVICE_CONNECTED)
    }






object C72Helper : RfidHelperExtDelegate,                           
    CoroutineScopeExtDelegate by BuiltInCoroutineScopeImpl() 
{
    override val theEventBus: KcEventBus by lazy { KcEventBus() }   

    internal var rfidReader: RFIDWithUHFUART? = null

    private val rfidReaderLock = ByteArray(0)

    internal var barcodeDecoder: BarcodeDecoder? = null


    override fun hasRfidInstance(): Boolean {   
        val result = (null != rfidReader)
        Logger.getLogger("C72Helper").log(Level.INFO, "hasRfidInstance(): [$result]")
        return result
    }

    private var getRfidReaderTask: Job? = null

    private val getRfidReaderCallback: (FmApiResult<Pair<RFIDWithUHFUART, String>>) -> Unit = { result ->
        val openResult: UhfReaderOpenResult
        when(result) {
            is FmApiResult.Success -> {
                Logger.getLogger("C72Helper").log(Level.INFO, "getRfidReaderCallback - onSuccess")
                getRfidReaderTask?.cancelIfNeeded()
                getRfidReaderTask = null

                synchronized(rfidReaderLock) {
                    rfidReader = result.data.first
                }

                openResult = UhfReaderOpenResult(result.data.second, null)
            }
            is FmApiResult.Error -> {
                Logger.getLogger("C72Helper").log(Level.SEVERE, "getRfidReaderCallback - onError", result.cause)
                getRfidReaderTask?.cancelIfNeeded()
                getRfidReaderTask = null

                val error = if (result.cause is FmRuntimeException) {
                    result.cause as FmRuntimeException
                }
                else { FmRuntimeException(result.cause, BaseErrorCodes.UNKNOWN_ERROR) }
                openResult = UhfReaderOpenResult(null, error)
            }
        }

        launch { theEventBus.publish(openResult) }
    }

    override fun createRfidInstanceIfNeeded(context: Context) {
        if (null != rfidReader) {
            Logger.getLogger("C72Helper").log(Level.WARNING, "createRfidInstanceIfNeeded() - rfid reader has existed!!")
            return
        }
        if (null != getRfidReaderTask) { return }
        getRfidReaderTask = runTaskExt(GetRfidReaderRunnable(), getRfidReaderCallback)
    }

    @Throws(FmRuntimeException::class)
    internal fun createRfidReader(): RFIDWithUHFUART =
        try {
            val reader = RFIDWithUHFUART.getInstance()
            Logger.getLogger("C72Helper").log(Level.INFO, "createRfidReader() - On Thread: [" + Thread.currentThread().name + "]")
            reader
        } catch (cause: Throwable) {
            Logger.getLogger("C72Helper").log(Level.SEVERE, "error on createRfidReader() - On Thread: [" + Thread.currentThread().name + "]", cause)
            throw FmRuntimeException(cause, C72ErrorCodes.CREATION_FAILURE)
        }


    override fun freeRfidInstanceIfNeeded(context: Context) {   // revision by elite_lin - 2025/08/30
        if (null != rfidReader) {
            try {
                rfidReader?.free()
                Logger.getLogger(getLogTag()).log(Level.INFO, "freeRfidInstanceIfNeeded() - RFIDWithUHFUART.free() - done")
            }
            catch (cause: Throwable) {
                Logger.getLogger(getLogTag()).log(Level.SEVERE, "freeRfidInstanceIfNeeded() -error on RFIDWithUHFUART.free(): ", cause)
            }
        }

        synchronized(rfidReaderLock) { rfidReader = null }
    }

    private var readTagsTask: Job? = null
    private val readMultipleTagsRunnable = ReadMultipleTagsRunnable()

    fun startReadTags() {
        if (null != readTagsTask) {
            Logger.getLogger("C72Helper").log(Level.WARNING, "startReadTags() - readTagsTask is not null!!")
            return
        }
        if (null == rfidReader) {
            Logger.getLogger("C72Helper").log(Level.WARNING, "startReadTags() - rfid reader is null!!")
            return
        }

        when(val result: FmApiResult<Int> = rfidReader!!.startInventoryTagExt()) {
            is FmApiResult.Success -> {
                readMultipleTagsRunnable.setReadFlag(true)
                launch { theEventBus.publish(UhfTagReadState.START) }
                readTagsTask = runTaskExt(readMultipleTagsRunnable, {})
                Logger.getLogger("C72Helper").log(Level.INFO, "startReadTags() - done!!")
            }
            is FmApiResult.Error -> {
                Logger.getLogger("C72Helper").log(Level.SEVERE, "startReadTags() - error!!", result.cause)
            }
        }
    }

    fun stopReadTags() {
        if (null == readTagsTask) {
            Logger.getLogger("C72Helper").log(Level.WARNING, "stopReadTags() - readTagsTask is null!!")
            return
        }
        if (null == rfidReader) {
            Logger.getLogger("C72Helper").log(Level.WARNING, "stopReadTags() - rfid reader is null!!")
            return
        }

        when(val result: FmApiResult<Int> = rfidReader!!.stopInventoryExt()) {
            is FmApiResult.Success -> {
                Logger.getLogger("C72Helper").log(Level.INFO, "stopReadTags() - OK!!")
            }
            is FmApiResult.Error -> {
                Logger.getLogger("C72Helper").log(Level.SEVERE, "stopReadTags() - error!!", result.cause)
            }
        }

        readMultipleTagsRunnable.setReadFlag(false)

        launch { theEventBus.publish(UhfTagReadState.STOP) }

        readTagsTask!!.cancelIfNeeded()
        readTagsTask = null
        Logger.getLogger("C72Helper").log(Level.INFO, "stopReadTags() - done")
    }

    ///

    private val rfidLocationCallback = IUHFLocationCallback { locationValue, flag ->
        Logger.getLogger("C72Helper").log(Level.INFO, "rfidLocationCallback - locationValue: [$locationValue], flag: [$flag]")
        val locationResult = UhfLocationResultImpl(locationValue, flag)
        launch { theEventBus.publish(locationResult) }
    }

    fun startLocation(context: Context, epc: String, var3: Int = IUHF.Bank_EPC, var4: Int = 32): Boolean {
        if (null == rfidReader) {
            Logger.getLogger("C72Helper").log(Level.WARNING, "startLocation() - rfid reader is null!!")
            return false
        }

        return when(val result: FmApiResult<Int> = rfidReader!!.startLocationExt(context, epc, rfidLocationCallback, var3, var4,)) {
            is FmApiResult.Success -> {
                launch { theEventBus.publish(UhfTagReadState.START) }
                Logger.getLogger("C72Helper").log(Level.INFO, "startLocation() - done!!")
                true
            }
            is FmApiResult.Error -> {
                Logger.getLogger("C72Helper").log(Level.SEVERE, "startLocation() - error!!", result.cause)
                false
            }
        }
    }

    fun stopLocation() {
        if (null == rfidReader) {
            Logger.getLogger("C72Helper").log(Level.WARNING, "stopLocation() - rfid reader is null!!")
            return
        }

        when(val result: FmApiResult<Int> = rfidReader!!.stopLocationExt()) {
            is FmApiResult.Success -> {
                launch { theEventBus.publish(UhfTagReadState.STOP) }
                Logger.getLogger("C72Helper").log(Level.INFO, "stopLocation() - done!!")
            }
            is FmApiResult.Error -> {
                Logger.getLogger("C72Helper").log(Level.SEVERE, "stopLocation() - error!!", result.cause)
            }
        }
    }


    override suspend fun setTxPower(value: Int): FmApiResult<Int> =     // revision by elite_lin - 2025/08/30
        if (null == rfidReader) {
            Logger.getLogger("C72Helper").log(Level.WARNING, "setTxPower() - RfidManager is null!!")
            FmApiResult.Error(
                FmRuntimeException("C72Helper - setTxPower() - RfidManager is null!!", BaseErrorCodes.ILLEGAL_ARGUMENT_ERROR)
            )
        }
        else {
            rfidReader!!.setTxPowerExt(value)
                .suspendThen {
                    delay(1000L)
                    rfidReader!!.getTxPowerExt()
                }
        }


    override fun getTxPower(): FmApiResult<Int> =       // revision by elite_lin - 2025/08/30
        if (null == rfidReader) {
            Logger.getLogger("C72Helper").log(Level.WARNING, "getTxPower() - RfidManager is null!!")
            FmApiResult.Error(
                FmRuntimeException("C72Helper - getTxPower() - RfidManager is null!!", BaseErrorCodes.ILLEGAL_ARGUMENT_ERROR)
            )
        }
        else {
            rfidReader!!.getTxPowerExt()
        }


    /**
     * If The UhfBank.EPC is "EPC", the [startIndex] (offset) would be 2!!
     */
    fun writeDataWithIn32Bytes(
        dataInHexFormat: String,
        pwdInHexFormat: String = DEFAULT_UHF_WRITE_PWD,
        bank: UhfBank = UhfBank.EPC,
        startIndex: Int = 2,
    ) : FmApiResult<Int> =
        if (null == rfidReader) {
            Logger.getLogger("C72Helper").log(Level.WARNING, "writeDataWithIn32Bytes() - RfidManager is null!!")
            FmApiResult.Error(
                FmRuntimeException("C72Helper - writeDataWithIn32Bytes() - RfidManager is null!!", BaseErrorCodes.ILLEGAL_ARGUMENT_ERROR)
            )
        }
        else {
            rfidReader!!.writeDataWithIn32BytesExt(
                dataInHexFormat = dataInHexFormat,
                pwdInHexFormat = pwdInHexFormat,
                bank = bank,
                startIndex = startIndex,
            )
        }


    /**
     * If The UhfBank.EPC is "EPC", the [startIndex] (offset) would be 2!!
     */
    suspend fun compoundedWriteDataWithIn32Bytes(
        dataInHexFormat: String,
        pwdInHexFormat: String = DEFAULT_UHF_WRITE_PWD,
        bank: UhfBank = UhfBank.EPC,
        writeTxPower: Int = 10,
        startIndex: Int = 2,
    ) : FmApiResult<Int> =
        when(val txPowerResult: FmApiResult<Int> = getTxPower()) {
            is FmApiResult.Success -> {
                Logger.getLogger("C72Helper").log(Level.INFO, "compoundedWriteDataWithIn32Bytes() - getTxPower() - success: [${txPowerResult.data}]")
                if(txPowerResult.data != writeTxPower) {
                    Logger.getLogger("C72Helper").log(Level.INFO, "compoundedWriteDataWithIn32Bytes() [1] --> setTxPower($writeTxPower)")
                    delay(1000L)
                    setTxPower(writeTxPower)
                }
                else {
                    FmApiResult.Success(writeTxPower)
                }
                .suspendThen { txPower: Int ->
                    if(txPower != writeTxPower) {
                        Logger.getLogger("C72Helper").log(Level.WARNING, "compoundedWriteDataWithIn32Bytes() [2] - txPower: [$txPower] != writeTxPower: [$writeTxPower]")
                        FmApiResult.Error(
                            FmRuntimeException("txPower: [$txPower] != writeTxPower: [$writeTxPower]", C72ErrorCodes.SET_TX_POWER_ERROR)
                        )
                    }
                    else {
                        delay(1000L)
                        writeDataWithIn32Bytes(
                            dataInHexFormat = dataInHexFormat,
                            pwdInHexFormat = pwdInHexFormat,
                            bank = bank,
                            startIndex = startIndex,
                        )
                    }
                }
                .suspendThen {
                    delay(1000L)
                    getTxPower()
                }
                .suspendThen { txPower: Int ->
                    if(txPower != txPowerResult.data) {
                        Logger.getLogger("C72Helper").log(Level.INFO, "compoundedWriteDataWithIn32Bytes() [3] --> setTxPower(${txPowerResult.data})")
                        delay(1000L)
                        setTxPower(txPowerResult.data)
                    }
                    else {
                        FmApiResult.Success(txPowerResult.data)
                    }
                }
                .then { txPower: Int ->
                    if (txPower == txPowerResult.data) {
                        Logger.getLogger("C72Helper").log(Level.INFO, "compoundedWriteDataWithIn32Bytes() [4] - txPower [$txPower] == txPowerResult.data [${txPowerResult.data}]")
                        FmApiResult.Success(1)
                    }
                    else {
                        Logger.getLogger("C72Helper").log(Level.WARNING, "compoundedWriteDataWithIn32Bytes() [4] - txPower [$txPower] != txPowerResult.data [${txPowerResult.data}]")
                        FmApiResult.Error(
                            FmRuntimeException("txPower [$txPower] != txPowerResult.data [${txPowerResult.data}]", C72ErrorCodes.SET_TX_POWER_ERROR)
                        )
                    }
                }
            }
            is FmApiResult.Error -> {
                Logger.getLogger("C72Helper").log(Level.SEVERE, "compoundedWriteDataWithIn32Bytes() - getTxPower() - error", txPowerResult.cause)
                txPowerResult
            }
        }


    ///

    fun hasBarcodeDecoder(): Boolean {
        val result = (null != barcodeDecoder)
        Logger.getLogger("C72Helper").log(Level.INFO, "hasBarcodeDecoder: [$result]")
        return result
    }

    fun closeBarcodeDecoder() {
        if (null != barcodeDecoder) {
            try {
                barcodeDecoder?.close()
                Logger.getLogger("C72Helper").log(Level.INFO, "closeBarcodeDecoder() - done!!")
            }
            catch (cause: Throwable) {
                Logger.getLogger("C72Helper").log(Level.SEVERE, "closeBarcodeDecoder() - error on barcodeDecoder.close()", cause)
            }
        }

        barcodeDecoder = null
    }

    private var getBarcodeDecoderTask: Job? = null

    private val barDecodeCallback: (BarcodeEntity) -> Unit = { barcodeEntity ->
        Logger.getLogger("C72Helper").log(Level.INFO, "barDecodeCallback - barcodeEntity: [${barcodeEntity}]")
        launch { theEventBus.publish(barcodeEntity.buildBarcodeEntityDelegate()) }
    }

    private val getBarcodeDecoderCallback: (FmApiResult<BarcodeDecoder>) -> Unit = { result ->
        val openResult: BarcodeDecoderOpenResult
        when(result) {
            is FmApiResult.Success -> {
                Logger.getLogger("C72Helper").log(Level.INFO, "getBarcodeDecoderCallback - onSuccess")
                getBarcodeDecoderTask?.cancelIfNeeded()
                getBarcodeDecoderTask = null

                barcodeDecoder = result.data
                barcodeDecoder!!.setDecodeCallback(barDecodeCallback)
                openResult = BarcodeDecoderOpenResult(0, null)
            }
            is FmApiResult.Error -> {
                Logger.getLogger("C72Helper").log(Level.SEVERE, "getBarcodeDecoderCallback - onError", result.cause)
                getBarcodeDecoderTask?.cancelIfNeeded()
                getBarcodeDecoderTask = null

                val error = if (result.cause is FmRuntimeException) {
                    result.cause as FmRuntimeException
                }
                else { FmRuntimeException(result.cause, BaseErrorCodes.UNKNOWN_ERROR) }
                openResult = BarcodeDecoderOpenResult(null, error)
            }
        }

        launch { theEventBus.publish(openResult) }
    }

    fun createBarcodeDecoderIfNeeded(context: Context) {
        if (null != barcodeDecoder) {
            Logger.getLogger("C72Helper").log(Level.WARNING, "createBarcodeDecoderIfNeeded() - barcodeDecoder has existed!!")
            return
        }
        if (null != getBarcodeDecoderTask) { return }

        getBarcodeDecoderTask = runTaskExt(GetBarcodeDecoderRunnable(context.applicationContext), getBarcodeDecoderCallback)
    }

    fun startBarcodeDecoderScan() {
        if (null == barcodeDecoder) {
            Logger.getLogger("C72Helper").log(Level.WARNING, "startBarcodeDecoderScan() - barcodeDecoder is null!!")
            return
        }

        try {
            val startResult = barcodeDecoder!!.startScan()
            Logger.getLogger("C72Helper").log(Level.INFO, "startBarcodeDecoderScan() - startResult: [$startResult]")
        }
        catch (cause: Throwable) {
            Logger.getLogger("C72Helper").log(Level.SEVERE, "startBarcodeDecoderScan() - error on barcodeDecoder.startScan()", cause)
        }
    }

    fun stopBarcodeDecoderScan() {
        if (null == barcodeDecoder) {
            Logger.getLogger("C72Helper").log(Level.WARNING, "stopBarcodeDecoderScan() - barcodeDecoder is null!!")
            return
        }

        try {
            barcodeDecoder!!.stopScan()
        }
        catch (cause: Throwable) {
            Logger.getLogger("C72Helper").log(Level.SEVERE, "stopBarcodeDecoderScan() - error on barcodeDecoder.stopScan()", cause)
        }
    }
}


internal class GetRfidReaderRunnable : SuspendBlockSignature<Pair<RFIDWithUHFUART, String>> {
    override suspend fun invoke(): Pair<RFIDWithUHFUART, String> {
        Logger.getLogger("GetRfidReaderRunnable").log(Level.INFO, "GetRfidReaderRunnable - On Thread: [" + Thread.currentThread().name + "]")
        val reader = C72Helper.createRfidReader()
        val version = reader.initExt()
        return Pair(reader, version)
    }
}


internal class ReadMultipleTagsRunnable : SuspendBlockSignature<Unit> {

    private val lock = ByteArray(0)
    private var readFlag: Boolean = false

    override suspend fun invoke(): Unit {
        Logger.getLogger("ReadMultipleTagsRunnable").log(Level.INFO, "ReadMultipleTagsRunnable - On Thread: [" + Thread.currentThread().name + "]")
        while (readFlag) {
            val uhfTagInfo = C72Helper.rfidReader?.readTagFromBuffer()
            if (null != uhfTagInfo) {
                C72Helper.theEventBus.publish(uhfTagInfo.buildUhfTagDelegate())
            }
        }
    }

    fun setReadFlag(flag: Boolean) {
        synchronized(lock) { readFlag = flag }
    }
}


internal class GetBarcodeDecoderRunnable(
    private val context: Context
) : SuspendBlockSignature<BarcodeDecoder> {
    override suspend fun invoke(): BarcodeDecoder {
        Logger.getLogger("GetBarcodeDecoderRunnable").log(Level.INFO, "GetBarcodeDecoderRunnable - On Thread: [" + Thread.currentThread().name + "]")
        val decoder = BarcodeFactory.getInstance().barcodeDecoder
        if (decoder.open(context)) {
            return decoder
        }
        else {
            throw FmRuntimeException("Fail to open BarcodeDecoder", C72ErrorCodes.DECODER_OPEN_FAILURE)
        }
    }
}



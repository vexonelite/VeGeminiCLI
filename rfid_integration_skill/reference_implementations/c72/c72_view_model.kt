

class C72RfidViewModel : RfidReaderExtViewModel<C72UhfTagDelegate, C72BarcodeEntityDelegate>() {

    override val theRfidHelper: RfidHelperDelegate = C72Helper

    override val theEventBus: KcEventBus = C72Helper.theEventBus

    private var uhfTagReadStateMonitoringJob: Job? = null

    private var rfidTagLocationEventMonitoringJob: Job? = null

    private var barcodeReadEventMonitoringJob: Job? = null
    private var barcodeReadStateMonitoringJob: Job? = null

    private var barcodeDecoderOpenEventMonitoringJob: Job? = null

    private var isRfidReaderLocating = false

    val uhfTagReadStateStateFlow: MutableStateFlow<UhfTagReadState?> = MutableStateFlow<UhfTagReadState?>(null)

    val uhfLocationResultStateFlow: MutableStateFlow<UhfReaderTxPowerDelegate?> = MutableStateFlow<UhfReaderTxPowerDelegate?>(null)


    override fun onResumeHandler(context: Context) {
        if (C72Helper.hasRfidInstance()) {
            subscribeRfidTagReadEvent()
            subscribeUhfTagReadStateEvent()
            subscribeRfidTagLocationEvent()
            //subscribeUhfTxPowerEvent()
            C72Helper.getTxPower()
        }
        if (C72Helper.hasBarcodeDecoder()) {
            subscribeBarcodeReadEvent()
        }
    }

    override fun onPauseHandler(context: Context) {
        rfidTagReadEventMonitoringJob?.cancelIfNeeded()
        uhfTagReadStateMonitoringJob?.cancelIfNeeded()
        rfidTagLocationEventMonitoringJob?.cancelIfNeeded()
        barcodeReadEventMonitoringJob?.cancelIfNeeded()
        barcodeReadStateMonitoringJob?.cancelIfNeeded()
        //uhfTxPowerEventMonitoringJob?.cancelIfNeeded()
        rfidReaderStopReadTags()
        C72Helper.stopBarcodeDecoderScan()
    }

    override fun resetStateFlows() {
        uhfTagReadResultStateFlow.value = null
        uhfTagReadStateStateFlow.value = null
        barcodeReadResultStateFlow.value = null
        uhfLocationResultStateFlow.value = null
        uhfTxPowerStateFlow.value = null
        rfidInstanceCreationEventMonitoringJob?.cancelIfNeeded()
        barcodeDecoderOpenEventMonitoringJob?.cancelIfNeeded()
    }

    override fun hasRfidInstance(): Boolean = C72Helper.hasRfidInstance()

    ///

    fun subscribeRfidInstanceCreationEvent(context: Context) {
        //Logger.getLogger(getLogTag()).log(Level.WARNING, "subscribeRfidInstanceCreationEvent()")
        rfidInstanceCreationEventMonitoringJob?.cancelIfNeeded()
        rfidInstanceCreationEventMonitoringJob = launch {
            //Logger.getLogger(getLogTag()).log(Level.WARNING, "subscribeRfidInstanceCreationEvent() - launch")
            C72Helper.theEventBus.subscribe<UhfReaderOpenResult> { result ->
                if (null != result.version) {
                    Logger.getLogger(this@C72RfidViewModel.getLogTag()).log(Level.WARNING, "subscribeRfidInstanceCreationEvent() - success: [{${result.version}}]")
                    subscribeRfidTagReadEvent()
                    subscribeUhfTagReadStateEvent()
                    subscribeRfidTagLocationEvent()
                    subscribeUhfTxPowerEvent()
                    C72Helper.createBarcodeDecoderIfNeeded(context)
                }
                else {
                    Logger.getLogger(this@C72RfidViewModel.getLogTag()).log(Level.SEVERE, "subscribeRfidInstanceCreationEvent() - error: [{${result.error}}]")
                }
            }
        }
    }

    fun subscribeBarcodeDecoderOpenEvent() {
        //Logger.getLogger(getLogTag()).log(Level.WARNING, "subscribeBarcodeDecoderOpenEvent()")
        barcodeDecoderOpenEventMonitoringJob?.cancelIfNeeded()
        barcodeDecoderOpenEventMonitoringJob = launch {
            //Logger.getLogger(getLogTag()).log(Level.WARNING, "subscribeBarcodeDecoderOpenEvent() - launch")
            C72Helper.theEventBus.subscribe<BarcodeDecoderOpenResult> { result ->
                if (null != result.result) {
                    Logger.getLogger(this@C72RfidViewModel.getLogTag()).log(Level.WARNING, "subscribeBarcodeDecoderOpenEvent() - success: [${result.result}]")
                    subscribeBarcodeReadEvent()
                }
                else {
                    Logger.getLogger(this@C72RfidViewModel.getLogTag()).log(Level.SEVERE, "subscribeBarcodeDecoderOpenEvent() - error: [${result.error}]")
                }

                C72Helper.getTxPower()
            }
        }
    }

    ///


    fun subscribeRfidTagReadEvent() {
        rfidTagReadEventMonitoringJob?.cancelIfNeeded()
        rfidTagReadEventMonitoringJob = launch {
            C72Helper.theEventBus.subscribe<C72UhfTagDelegate> { uhfTagDelegate: C72UhfTagDelegate ->
                synchronized(tagMapLock) {
                    readTagMap[uhfTagDelegate.theEPC] = uhfTagDelegate
                    Logger.getLogger(this@C72RfidViewModel.getLogTag()).log(Level.INFO, "subscribeRfidTagReadEvent() - rfidTagReadEvent - put [{${uhfTagDelegate.theEPC}}]")
                }
                uhfTagReadResultStateFlow.update { uhfTagDelegate }
            }
        }
    }

    fun subscribeUhfTagReadStateEvent() {
        uhfTagReadStateMonitoringJob?.cancelIfNeeded()
        uhfTagReadStateMonitoringJob = launch {
            C72Helper.theEventBus.subscribe<UhfTagReadState> { readState: UhfTagReadState ->
                Logger.getLogger(this@C72RfidViewModel.getLogTag()).log(Level.INFO, "subscribeUhfTagReadStateEvent() - uhfTagReadState: [${readState}]")
                uhfTagReadStateStateFlow.update { readState }
            }
        }
    }

    fun rfidReaderReadTagsIfNeeded() {
        if (isRfidReaderScanning) {
            C72Helper.stopReadTags()
            isRfidReaderScanning = false
        }
        else {
            synchronized(tagMapLock) {
                readTagMap.clear()
            }
            C72Helper.startReadTags()
            isRfidReaderScanning = true
        }
    }

    fun rfidReaderStartReadTagsIfNeeded() {
        if (!isRfidReaderScanning) {
            synchronized(tagMapLock) {
                readTagMap.clear()
            }
            C72Helper.startReadTags()
            isRfidReaderScanning = true
        }
    }

    fun rfidReaderStopReadTagsIfNeeded() {
        if (isRfidReaderScanning) {
            C72Helper.stopReadTags()
            isRfidReaderScanning = false
        }
    }

    fun rfidReaderStopReadTags() {
        try { C72Helper.stopReadTags() }
        catch (error: Throwable) {
            Logger.getLogger(this@C72RfidViewModel.getLogTag()).log(Level.SEVERE, "rfidReaderStopReadTags() - error on C72Helper.stopReadTags()")
        }
        synchronized(tagMapLock) {
            readTagMap.clear()
        }
        isRfidReaderScanning = false
    }

    ///

    fun subscribeBarcodeReadEvent() {
        barcodeReadEventMonitoringJob?.cancelIfNeeded()
        barcodeReadEventMonitoringJob = launch {
            C72Helper.theEventBus.subscribe<C72BarcodeEntityDelegate> { barcodeEntityDelegate: C72BarcodeEntityDelegate ->
                Logger.getLogger(this@C72RfidViewModel.getLogTag()).log(Level.INFO, "subscribeBarcodeReadEvent() - barcodeReadEvent - [${barcodeEntityDelegate.showResultCode()}]")
                if (barcodeEntityDelegate.theResultCode == BarcodeStatusCodes.DECODE_SUCCESS) {
                    Logger.getLogger(this@C72RfidViewModel.getLogTag()).log(Level.INFO, "subscribeBarcodeReadEvent() - barcodeReadEvent: [${barcodeEntityDelegate.theRawBarcodeData}]")  
                    barcodeReadResultStateFlow.update { barcodeEntityDelegate }
                }
            }
        }
    }

    ///

    fun subscribeRfidTagLocationEvent() {
        rfidTagLocationEventMonitoringJob?.cancelIfNeeded()
        rfidTagLocationEventMonitoringJob = launch {
            C72Helper.theEventBus.subscribe<UhfReaderTxPowerDelegate> { locationResult: UhfReaderTxPowerDelegate ->
                Logger.getLogger(this@C72RfidViewModel.getLogTag()).log(Level.INFO, "subscribeRfidTagLocationEvent() - locationEvent - [$locationResult]")
                uhfLocationResultStateFlow.update { locationResult }
            }
        }
    }

    fun rfidReaderLocateTagIfNeeded(context: Context, epc: String) {
        if (isRfidReaderLocating) {
            C72Helper.stopLocation()
            isRfidReaderLocating = false
        }
        else {
            isRfidReaderLocating = C72Helper.startLocation(context, epc)
        }
    }


    /**
     * If The UhfBank.EPC is "EPC", the [startIndex] (offset) would be 2!!
     * 
     * Current Usage - remarked by elite_lin - 2026/03/06
     */
    fun compoundedWriteDataWithIn32Bytes(
        dataInHexFormat: String,
        pwdInHexFormat: String = DEFAULT_UHF_WRITE_PWD,
        bank: UhfBank = UhfBank.EPC,
        writeTxPower: Int = 10,
        startIndex: Int = 2,
    ) {
        uhfWriteTagStateFlow.value = null
        launch(Dispatchers.IO) {
            val writeTagResult: FmApiResult<Int> = C72Helper.compoundedWriteDataWithIn32Bytes(
                dataInHexFormat = dataInHexFormat,
                pwdInHexFormat = pwdInHexFormat,
                bank = bank,
                writeTxPower = writeTxPower,
                startIndex = startIndex,
            )
            when(writeTagResult) {
                is FmApiResult.Success -> {
                    Logger.getLogger(this@C72RfidViewModel.getLogTag()).log(Level.INFO, "compoundedWriteDataWithIn32Bytes() - success!!")
                }
                is FmApiResult.Error -> {
                    Logger.getLogger(this@C72RfidViewModel.getLogTag()).log(Level.SEVERE, "compoundedWriteDataWithIn32Bytes() - error", writeTagResult.cause)
                }
            }

            uhfWriteTagStateFlow.update { writeTagResult }
        }
    }

}



fun C72RfidViewModel.toRfidConfigExt(): RfidDeviceConfigDelegate = RfidDeviceConfigImpl(
    uhfTxPowerStateFlow = uhfTxPowerStateFlow,
    resetUhfTxPowerStateFlow = { uhfTxPowerStateFlow.value = null },
    uhfTagReadResultStateFlow = uhfTagReadResultStateFlow,
    resetUhfTagReadResultStateFlow = { uhfTagReadResultStateFlow.value = null },
    barcodeReadResultStateFlow = barcodeReadResultStateFlow,
    resetBarcodeReadResultStateFlow = { barcodeReadResultStateFlow.value = null },
    triggerEventFlow = MutableSharedFlow(), // Placeholder
    uhfWriteTagStateFlow = uhfWriteTagStateFlow,
    resetUhfWriteTagStateFlow = { uhfWriteTagStateFlow.value = null },
    compoundedWriteTagByEpc = { currentEpc, newEpc -> compoundedWriteDataWithIn32Bytes(dataInHexFormat = newEpc) },
    galleryCodeToEpcHexString = { it.theWtcGalleryCodeToEpcHexStringExt() },
    parseGalleryTagNo = { uhfTag: RfidUhfTagDelegate -> uhfTag.parseEPCtoGalleryTagNoExt() },
    getBarcodeData = { barcodeEntity: BarcodeEntityDelegate -> barcodeEntity.getBarcodeData() },
    onGetTxPower = { getTxPower() },
    onSetTxPower = { setTxPower(it) },
    hasRfidInstance = { hasRfidInstance() }
)



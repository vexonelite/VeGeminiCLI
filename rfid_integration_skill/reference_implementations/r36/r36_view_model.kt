

class R36RfidViewModel : RfidReaderExtViewModel<R36UhfTagDelegate, R36BarcodeEntityDelegate>() {

    private val r36Helper: R36Helper = R36Helper()

    override val theRfidHelper: RfidHelperDelegate = r36Helper

    override val theEventBus: KcEventBus = r36Helper.theEventBus

    private var barcodeDecoderOpenEventMonitoringJob: Job? = null

    private var barcodeReadEventMonitoringJob: Job? = null
    
    val uhfWriteTagStateFlow2: MutableStateFlow<FmApiResult<DeviceResponse>?> = MutableStateFlow<FmApiResult<DeviceResponse>?>(null)

    override fun onResumeHandler(context: Context) {
        subscribeRfidInstanceCreationEvent()
        subscribeBarcodeDecoderOpenEvent()
        r36Helper.createRfidInstanceIfNeeded(context)
        r36Helper.createBarcodeReaderManagerIfNeeded(context)
    }

    override fun onPauseHandler(context: Context) {
        rfidInstanceCreationEventMonitoringJob?.cancelIfNeeded()
        rfidTagReadEventMonitoringJob?.cancelIfNeeded()
        barcodeDecoderOpenEventMonitoringJob?.cancelIfNeeded()
        barcodeReadEventMonitoringJob?.cancelIfNeeded()
        uhfTxPowerEventMonitoringJob?.cancelIfNeeded()
        r36Helper.freeRfidInstanceIfNeeded(context)
        r36Helper.freeBarcodeManagerIfNeeded(context)
    }

    override fun resetStateFlows() {
        uhfTagReadResultStateFlow.value = null
        barcodeReadResultStateFlow.value = null
        uhfTxPowerStateFlow.value = null
    }

    ///

    private fun subscribeRfidInstanceCreationEvent() {
        rfidInstanceCreationEventMonitoringJob?.cancelIfNeeded()
        rfidInstanceCreationEventMonitoringJob = launch {
            r36Helper.theEventBus.subscribe<UhfReaderOpenResult> { result: UhfReaderOpenResult ->
                if (result.serviceVersion.isNotEmpty()) {
                    Logger.getLogger(this@R36RfidViewModel.getLogTag()).log(Level.WARNING, "subscribeRfidInstanceCreationEvent() - success: [{${result.serviceVersion}}]")
                    subscribeRfidTagReadEvent()
                    subscribeUhfTxPowerEvent()
                    getTxPower()
                }
                else {
                    Logger.getLogger(this@R36RfidViewModel.getLogTag()).log(Level.WARNING, "subscribeRfidInstanceCreationEvent() - result.serviceVersion is empty!!")
                }
            }
        }
    }

    private fun subscribeRfidTagReadEvent() {
        rfidTagReadEventMonitoringJob?.cancelIfNeeded()
        rfidTagReadEventMonitoringJob = launch {
            r36Helper.theEventBus.subscribe<R36UhfTagDelegate> { uhfTagDelegate: R36UhfTagDelegate ->
                val validSet = setOf<Int>(0, 1, 2, 3)
                if (uhfTagDelegate.theType in validSet) {
                    synchronized(tagMapLock) {
                        readTagMap[uhfTagDelegate.theEPC] = uhfTagDelegate
                        Logger.getLogger(this@R36RfidViewModel.getLogTag()).log(Level.INFO, "subscribeRfidTagReadEvent() - rfidTagReadEvent - put [{${uhfTagDelegate.theEPC}}]")
                    }
                    uhfTagReadResultStateFlow.update { uhfTagDelegate }
                }
            }
        }
    }

    ///

    private fun subscribeBarcodeDecoderOpenEvent() {
        barcodeDecoderOpenEventMonitoringJob?.cancelIfNeeded()
        barcodeDecoderOpenEventMonitoringJob = launch {
            r36Helper.theEventBus.subscribe<BarcodeDecoderOpenResult> { result: BarcodeDecoderOpenResult ->
                if (result.serviceVersion.isNotEmpty()) {
                    Logger.getLogger(this@R36RfidViewModel.getLogTag()).log(Level.WARNING, "subscribeBarcodeDecoderOpenEvent() - BarcodeDecoderOpenEvent - success: [${result.serviceVersion}]")
                    subscribeBarcodeReadEvent()
                }
                else {
                    Logger.getLogger(this@R36RfidViewModel.getLogTag()).log(Level.SEVERE, "subscribeBarcodeDecoderOpenEvent() - BarcodeDecoderOpenEvent - result.serviceVersion is empty!!")
                }
            }
        }
    }

    private fun subscribeBarcodeReadEvent() {
        barcodeReadEventMonitoringJob?.cancelIfNeeded()
        barcodeReadEventMonitoringJob = launch {
            r36Helper.theEventBus.subscribe<R36BarcodeEntityDelegate> { barcodeEntityDelegate: R36BarcodeEntityDelegate ->
                Logger.getLogger(this@R36RfidViewModel.getLogTag()).log(Level.INFO, "subscribeBarcodeReadEvent() - barcodeReadEvent - [${barcodeEntityDelegate.theRawBarcodeData}]")
                barcodeReadResultStateFlow.update { barcodeEntityDelegate }
            }
        }
    }


    override fun hasRfidInstance(): Boolean = r36Helper.hasRfidInstance()


    fun compoundedDirectWriteTagByEpc(
        currentEpcInHexFormat: String,
        newEpcInHexFormat: String,
        pwdInHexFormat: String? = null,
        memoryBank: RFIDMemoryBank = RFIDMemoryBank.EPC,
        startIndex: Int = 2 * 2,
        retry: Int = 3,
    ) {
        uhfWriteTagStateFlow.value = null
        launch(Dispatchers.IO) {
            val writeTagResult: FmApiResult<Int> = r36Helper.compoundedDirectWriteTagByEpc(
                currentEpcInHexFormat = currentEpcInHexFormat,
                newEpcInHexFormat = newEpcInHexFormat,
                pwdInHexFormat = pwdInHexFormat,
                memoryBank = memoryBank,
                startIndex = startIndex,
                retry = retry,
            )
            when(writeTagResult) {
                is FmApiResult.Success -> {
                    Logger.getLogger(this@R36RfidViewModel.getLogTag()).log(Level.INFO, "compoundedDirectWriteTagByEpc() - success: [${writeTagResult.data}]")
                }
                is FmApiResult.Error -> {
                    Logger.getLogger(this@R36RfidViewModel.getLogTag()).log(Level.SEVERE, "compoundedDirectWriteTagByEpc() - error", writeTagResult.cause)
                }
            }

            uhfWriteTagStateFlow.update {
                writeTagResult
            }
        }
    }

}



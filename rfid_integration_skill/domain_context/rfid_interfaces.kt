

interface UhfTagPcDelegate {
    val thePC: String
}


interface UhfTagEpcDelegate {
    val theEPC: String
}


interface UhfTagTidDelegate {
    val theTid: String
}


interface UhfTagRssiDelegate {
    val theRssiInDouble: Double
}


interface RfidUhfTagDelegate : UhfTagPcDelegate, UhfTagEpcDelegate, UhfTagTidDelegate,
    UhfTagRssiDelegate


interface RfidHelperDelegate : KcEventBusDelegate {

    fun hasRfidInstance() : Boolean

    suspend fun setTxPower(value: Int): FmApiResult<Int>

    fun getTxPower(): FmApiResult<Int>
}


interface RfidHelperExtDelegate : RfidHelperDelegate {

    fun createRfidInstanceIfNeeded(context: Context)

    fun freeRfidInstanceIfNeeded(context: Context)
}



interface UhfReaderTxPowerDelegate {
    val theTxPower: Int
    val theErrorMessage: String
    val theStatusCode: String
}


data class UhfReaderTxPowerImpl(
    override val theTxPower: Int = -1,
    override val theErrorMessage: String = "",
    override val theStatusCode: String = "",
) : UhfReaderTxPowerDelegate


interface BarcodeEntityDelegate {
    val theRawBarcodeData: String

    fun getBarcodeData(): String
}


interface RfidDeviceConfigDelegate {
    val uhfTxPowerStateFlow: StateFlow<UhfReaderTxPowerDelegate?>
    val resetUhfTxPowerStateFlow: () -> Unit
    val uhfTagReadResultStateFlow: StateFlow<RfidUhfTagDelegate?>
    val resetUhfTagReadResultStateFlow: () -> Unit
    val barcodeReadResultStateFlow: StateFlow<BarcodeEntityDelegate?>
    val resetBarcodeReadResultStateFlow: () -> Unit
    val parseGalleryTagNo: (RfidUhfTagDelegate) -> String
    val getBarcodeData: (BarcodeEntityDelegate) -> String
    val onGetTxPower: () -> Unit
    val onSetTxPower: (Int) -> Unit
    val hasRfidInstance: () -> Boolean
}


data class RfidDeviceConfigImpl(
    override val uhfTxPowerStateFlow: StateFlow<UhfReaderTxPowerDelegate?>,
    override val resetUhfTxPowerStateFlow: () -> Unit,
    override val uhfTagReadResultStateFlow: StateFlow<RfidUhfTagDelegate?>,
    override val resetUhfTagReadResultStateFlow: () -> Unit,
    override val barcodeReadResultStateFlow: StateFlow<BarcodeEntityDelegate?>,
    override val resetBarcodeReadResultStateFlow: () -> Unit,
    override val parseGalleryTagNo: (RfidUhfTagDelegate) -> String,
    override val getBarcodeData: (BarcodeEntityDelegate) -> String,
    override val onGetTxPower: () -> Unit,
    override val onSetTxPower: (Int) -> Unit,
    override val hasRfidInstance: () -> Boolean,
) : RfidDeviceConfigDelegate


///-----------------------------------------------------------------------------


interface RfidReaderExtDelegate<UHF : RfidUhfTagDelegate, BAR> : CoroutineScope, KcEventBusDelegate {

    val theRfidHelper: RfidHelperDelegate

    val uhfTagReadResultStateFlow: MutableStateFlow<UHF?>

    val barcodeReadResultStateFlow: MutableStateFlow<BAR?>

    val uhfTxPowerStateFlow: MutableStateFlow<UhfReaderTxPowerDelegate?>

    val uhfWriteTagStateFlow: MutableStateFlow<FmApiResult<Int>?>

    fun onResumeHandler(context: Context)

    fun onPauseHandler(context: Context)

    fun resetStateFlows()

    ///

    fun hasRfidInstance(): Boolean

    fun setTxPower(value: Int)

    fun getTxPower()

}



abstract class RfidReaderExtViewModel<UHF : RfidUhfTagDelegate, BAR>: RfidReaderExtDelegate<UHF, BAR>,
    ViewModel(), CoroutineScope by BuiltInCoroutineScopeImpl()
{

    protected var rfidInstanceCreationEventMonitoringJob: Job? = null

    protected var rfidTagReadEventMonitoringJob: Job? = null

    protected var uhfTxPowerEventMonitoringJob: Job? = null

    protected var isRfidReaderScanning: Boolean = false


    protected val tagMapLock: ByteArray = ByteArray(0)

    protected val readTagMap: MutableMap<String, UHF> = mutableMapOf()

    override val uhfTagReadResultStateFlow: MutableStateFlow<UHF?> = MutableStateFlow<UHF?>(null)

    override val barcodeReadResultStateFlow: MutableStateFlow<BAR?> = MutableStateFlow<BAR?>(null)

    override val uhfTxPowerStateFlow: MutableStateFlow<UhfReaderTxPowerDelegate?> = MutableStateFlow<UhfReaderTxPowerDelegate?>(null)

    override val uhfWriteTagStateFlow: MutableStateFlow<FmApiResult<Int>?> = MutableStateFlow<FmApiResult<Int>?>(null)

    ///

    override fun setTxPower(value: Int) {
        uhfTxPowerStateFlow.value = null
        launch(Dispatchers.IO) {
            when (val txPowerResult: FmApiResult<Int> = theRfidHelper.setTxPower(value)) {
                is FmApiResult.Success -> {
                    Logger.getLogger(this@RfidReaderExtViewModel.getLogTag()).log(Level.INFO, "setTxPower() - success: [${txPowerResult.data}]")
                    uhfTxPowerStateFlow.update {
                        UhfReaderTxPowerImpl(theTxPower = txPowerResult.data)
                    }
                }
                is FmApiResult.Error -> {
                    Logger.getLogger(this@RfidReaderExtViewModel.getLogTag()).log(Level.SEVERE, "setTxPower() - error", txPowerResult.cause)
                    val statusCode = if (txPowerResult.cause is FmRuntimeException) {
                        (txPowerResult.cause as FmRuntimeException).exceptionCode } else { "" }
                    val errorMessage = txPowerResult.cause.message ?: ""
                    uhfTxPowerStateFlow.update {
                        UhfReaderTxPowerImpl(theErrorMessage = errorMessage, theStatusCode = statusCode)
                    }
                }
            }
        }
    }

    override fun getTxPower() {
        uhfTxPowerStateFlow.value = null
        launch(Dispatchers.IO) {
            when(val txPowerResult: FmApiResult<Int> = theRfidHelper.getTxPower()) {
                is FmApiResult.Success -> {
                    Logger.getLogger(this@RfidReaderExtViewModel.getLogTag()).log(Level.INFO, "getTxPower() - success: [${txPowerResult.data}]")
                    uhfTxPowerStateFlow.update {
                        UhfReaderTxPowerImpl(theTxPower = txPowerResult.data)
                    }
                }
                is FmApiResult.Error -> {
                    Logger.getLogger(this@RfidReaderExtViewModel.getLogTag()).log(Level.SEVERE, "getTxPower() - error", txPowerResult.cause)
                    val statusCode = if (txPowerResult.cause is FmRuntimeException) {
                        (txPowerResult.cause as FmRuntimeException).exceptionCode } else { "" }
                    val errorMessage = txPowerResult.cause.message ?: ""
                    uhfTxPowerStateFlow.update {
                        UhfReaderTxPowerImpl(
                            theErrorMessage = errorMessage,
                            theStatusCode = statusCode
                        )
                    }
                }
            }
        }
    }

    protected fun subscribeUhfTxPowerEvent() {
        uhfTxPowerEventMonitoringJob?.cancelIfNeeded()
        uhfTxPowerEventMonitoringJob = launch {
            theEventBus.subscribe<UhfReaderTxPowerDelegate> { uhfReaderTxPower: UhfReaderTxPowerDelegate ->
                Logger.getLogger(this@RfidReaderExtViewModel.getLogTag()).log(Level.INFO, "subscribeUhfTxPowerEvent() - UhfTxPowerEvent - [${uhfReaderTxPower.theTxPower}, ${uhfReaderTxPower.theErrorMessage}]")
                uhfTxPowerStateFlow.update { uhfReaderTxPower }
            }
        }
    }
}





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


enum class ReaderTriggerState {
    TRIGGER_PRESSED,
    TRIGGER_RELEASED,
}


data class ReaderTriggerEvent(val triggerState: ReaderTriggerState)


interface RfidDeviceConfigDelegate {
    val uhfTxPowerStateFlow: StateFlow<UhfReaderTxPowerDelegate?>
    val resetUhfTxPowerStateFlow: () -> Unit
    val uhfTagReadResultStateFlow: StateFlow<RfidUhfTagDelegate?>
    val resetUhfTagReadResultStateFlow: () -> Unit
    val barcodeReadResultStateFlow: StateFlow<BarcodeEntityDelegate?>
    val triggerEventFlow: SharedFlow<ReaderTriggerEvent>
    val resetBarcodeReadResultStateFlow: () -> Unit
    // [start] added by gemini - 2026/03/17
    val uhfWriteTagStateFlow: StateFlow<FmApiResult<Int>?>
    val resetUhfWriteTagStateFlow: () -> Unit
    val compoundedWriteTagByEpc: (currentEpcInHexFormat: String, newEpcInHexFormat: String) -> Unit
    val galleryCodeToEpcHexString: (String) -> String
    // [end] added by gemini - 2026/03/17
    val parseGalleryTagNo: (RfidUhfTagDelegate) -> String
    val getBarcodeData: (BarcodeEntityDelegate) -> String
    val onGetTxPower: () -> Unit
    val onSetTxPower: (Int) -> Unit
    val hasRfidInstance: () -> Boolean
    val resetStateFlows: () -> Unit
}


data class RfidDeviceConfigImpl(
    override val uhfTxPowerStateFlow: StateFlow<UhfReaderTxPowerDelegate?>,
    override val resetUhfTxPowerStateFlow: () -> Unit,
    override val uhfTagReadResultStateFlow: StateFlow<RfidUhfTagDelegate?>,
    override val resetUhfTagReadResultStateFlow: () -> Unit,
    override val barcodeReadResultStateFlow: StateFlow<BarcodeEntityDelegate?>,
    override val triggerEventFlow: SharedFlow<ReaderTriggerEvent>,
    override val resetBarcodeReadResultStateFlow: () -> Unit,
    // [start] added by gemini - 2026/03/17
    override val uhfWriteTagStateFlow: StateFlow<FmApiResult<Int>?> = MutableStateFlow(null),
    override val resetUhfWriteTagStateFlow: () -> Unit = {},
    override val compoundedWriteTagByEpc: (String, String) -> Unit = { _, _ -> },
    override val galleryCodeToEpcHexString: (String) -> String = { it },
    // [end] added by gemini - 2026/03/17
    override val parseGalleryTagNo: (RfidUhfTagDelegate) -> String,
    override val getBarcodeData: (BarcodeEntityDelegate) -> String,
    override val onGetTxPower: () -> Unit,
    override val onSetTxPower: (Int) -> Unit,
    override val hasRfidInstance: () -> Boolean,
    override val resetStateFlows: () -> Unit,
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


/**
 * added by elite_lin - 2025/08/30
 */
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


///-----------------------------------------------------------------------------


interface UhfTagDataExtDelegate<V, I> where I : IdentifierDelegate {
    val uhfGalleryListStateFlow: MutableStateFlow<List<I>>

    fun putItemIntoUhfTagMap(scope: CoroutineScope, key: String, value: V, usageScenario: UsageScenarios)

    fun removeItemFromUhfTagMap(scope: CoroutineScope, key: String, usageScenario: UsageScenarios)

    fun clearUhfTagMap(scope: CoroutineScope, usageScenario: UsageScenarios)

    fun updateItemInUhfGalleryListStateFlow(scope: CoroutineScope, item: I)
}


class UhfTagDataExtImpl<V, I>(
    private val deviceGalleryFactory: DeviceGalleryFactoryDelegate<V, I>
) : UhfTagDataExtDelegate<V, I> where I : IdentifierDelegate {
    private val _uhfTagMapStateFlow: MutableStateFlow<Map<String, V>> = MutableStateFlow<Map<String, V>>(mutableMapOf())
    override val uhfGalleryListStateFlow: MutableStateFlow<List<I>> = MutableStateFlow<List<I>>(listOf())


    override fun putItemIntoUhfTagMap(scope: CoroutineScope, key: String, value: V, usageScenario: UsageScenarios) {
        _uhfTagMapStateFlow.update(key, value)
        Logger.getLogger(getLogTag()).log(Level.INFO, "putItemIntoUhfTagMap() -> size: [${_uhfTagMapStateFlow.value.size}]")
        reBuildUhfGalleryList(scope, usageScenario)
    }

    override fun removeItemFromUhfTagMap(scope: CoroutineScope, key: String, usageScenario: UsageScenarios) {
        _uhfTagMapStateFlow.remove(key)
        Logger.getLogger(getLogTag()).log(Level.INFO, "removeItemFromUhfTagMap() -> size: [${_uhfTagMapStateFlow.value.size}]")
        reBuildUhfGalleryList(scope, usageScenario)
    }

    override fun clearUhfTagMap(scope: CoroutineScope, usageScenario: UsageScenarios) {
        _uhfTagMapStateFlow.clear()
        Logger.getLogger(getLogTag()).log(Level.INFO, "clearUhfTagMap() -> size: [${_uhfTagMapStateFlow.value.size}]")
        reBuildUhfGalleryList(scope, usageScenario)
    }

    private fun reBuildUhfGalleryList(scope: CoroutineScope, usageScenario: UsageScenarios) {
        val currentMap: Map<String, V> = _uhfTagMapStateFlow.value
        Logger.getLogger(getLogTag()).log(Level.INFO, "reBuildUhfGalleryList() - currentMap.size: [${currentMap.size}] - on Thread [${Thread.currentThread().name}]")

        scope.launch(Dispatchers.Default) {
            val uhfGalleryList: List<I> =
                currentMap.map { entry: Map.Entry<String, V> ->
                    deviceGalleryFactory.create(entryValue = entry.value, usageScenario = usageScenario)
                }
                .sortedBy { delegate: I -> delegate.theIdentifier }

            uhfGalleryListStateFlow.update { uhfGalleryList }
        }
    }

    override fun updateItemInUhfGalleryListStateFlow(scope: CoroutineScope, item: I) {
        scope.launch(Dispatchers.Default) {
            uhfGalleryListStateFlow.update { currentList: List<I> ->
                currentList.updateItemGene(item)
            }
        }
    }
}


interface DeviceGalleryFactoryDelegate<V, I> {
    fun create(entryValue: V, usageScenario: UsageScenarios): I
}

///-----------------------------------------------------------------------------









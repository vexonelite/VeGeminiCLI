

/**
 * Template for Unified ViewModel Adapter
 * Orchestrates persistent state (StateFlow) and hardware events (SharedFlow).
 */
class ${DEVICE_NAME}ViewModel : RfidReaderExtViewModel<${TAG_DELEGATE}, ${BARCODE_DELEGATE}>() {

    override val theRfidHelper: RfidHelperDelegate = ${DEVICE_NAME}Helper
    override val theEventBus: KcEventBus = ${DEVICE_NAME}Helper.theEventBus

    // Reference to the Helper's flows
    private val helperState = ${DEVICE_NAME}Helper.state
    private val helperEvent = ${DEVICE_NAME}Helper.event

    // --- Lifecycle Management ---

    override fun onResumeHandler(context: Context) {
        // 1. Initialize hardware
        ${DEVICE_NAME}Helper.createRfidInstanceIfNeeded(context)
        
        // 2. Start collecting from flows
        subscribeToHardwareFlows()
        subscribeRfidTagReadEvent()
    }

    override fun onPauseHandler(context: Context) {
        // Standard cleanup pattern
        rfidTagReadEventMonitoringJob?.cancelIfNeeded()
        ${DEVICE_NAME}Helper.release()
    }

    // --- Flow Collection Logic ---

    private fun subscribeToHardwareFlows() {
        launch {
            // A. Collect Persistent State (UI Labels, Battery, Power)
            // Sticky behavior is desired here for UI consistency.
            launch {
                helperState.collect { state ->
                    uhfTxPowerStateFlow.update { 
                        UhfReaderTxPowerImpl(theTxPower = state.powerLevel) 
                    }
                    // Update other UI-bound StateFlows here
                }
            }

            // B. Collect Instantaneous Events (Trigger Anti-Sticky)
            // Ensures every trigger press starts a scan, even if state is unchanged.
            launch {
                helperEvent.collect { eventId ->
                    when (eventId) {
                        SDConsts.SDCmdMsg.TRIGGER_PRESSED -> {
                            rfidReaderStartReadTagsIfNeeded()
                        }
                        SDConsts.SDCmdMsg.TRIGGER_RELEASED -> {
                            rfidReaderStopReadTagsIfNeeded()
                        }
                    }
                }
            }
        }
    }

    // --- Writing Logic (Railway Pattern) ---

    fun requestWriteEpc(newEpc: String) {
        uhfWriteTagStateFlow.value = null
        launch(Dispatchers.IO) {
            val result = ${DEVICE_NAME}Helper.writeEpc(newEpc, "00000000")
            uhfWriteTagStateFlow.update { result }
        }
    }
}





/**
 * Template for Hardware Helper Implementation
 * This combines Railway-Oriented command execution with a Hybrid Flow Strategy 
 * (StateFlow for persistent data vs SharedFlow for hardware trigger events).
 */
object ${DEVICE_NAME}Helper : RfidHelperExtDelegate, 
    CoroutineScopeExtDelegate by BuiltInCoroutineScopeImpl() {

    override val theEventBus: KcEventBus by lazy { KcEventBus() }
    private var reader: ${SDK_READER_CLASS}? = null

    // --- State & Event Dispatchers ---

    // 1. STATEFLOW: For data that has a current value (Sticky is OK)
    // Used for: Battery level, connection status, current power level.
    private val _state = MutableStateFlow(${DEVICE_NAME}ReaderState())
    val state = _state.asStateFlow()

    // 2. SHAREDFLOW: For hardware actions (ANTI-STICKY required)
    // Used for: Trigger Pressed/Released to ensure every click triggers logic.
    private val _event = MutableSharedFlow<Int>(extraBufferCapacity = 1)
    val event = _event.asSharedFlow()

    // --- Initialization Pattern ---

    override fun createRfidInstanceIfNeeded(context: Context) {
        if (reader != null) return
        launch {
            // SDK specific open/connect logic
            val result = performOpen(context)
            theEventBus.publish(result)
        }
    }

    // --- Command Execution (Railway Pattern) ---

    override suspend fun setTxPower(value: Int): FmApiResult<Int> =
        if (reader == null) {
            FmApiResult.Error(FmRuntimeException("Reader null", "ERR_01"))
        } else {
            // SDK Call -> Success? -> Delay -> Verify Get (Standard WTC Pattern)
            executeSetPower(value)
                .suspendThen { 
                    delay(1000L) 
                    getTxPower() 
                }
        }

    suspend fun writeEpc(newEpc: String, password: String): FmApiResult<Int> {
        return try {
            val ret = reader?.${WRITE_EPC_METHOD}(1, newEpc, password, false) ?: -1
            if (ret == ${SUCCESS_CONST}) FmApiResult.Success(1) 
            else FmApiResult.Error(FmRuntimeException("Write Fail", ret.toString()))
        } catch (e: Throwable) {
            FmApiResult.Error(e)
        }
    }

    // --- Hardware Event Mapping ---

    private fun handleSledMessage(msg: Message) {
        when (msg.arg1) {
            // Update StateFlow for persistent info
            ${BATTERY_EVENT} -> _state.update { it.copy(batteryLevel = msg.arg2) }
            
            // Emit SharedFlow for trigger to solve the "Sticky Value" problem
            ${TRIGGER_PRESSED}, ${TRIGGER_RELEASED} -> {
                _event.tryEmit(msg.arg1)
            }
        }
    }
}



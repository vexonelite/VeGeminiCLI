
/**
 * [Event Bus Pattern in Android Using Kotlin Flows](https://dev.to/mohitrajput987/event-bus-pattern-in-android-using-kotlin-flows-la)
 */
class KcEventBus {
    private val internalEvents = MutableSharedFlow<Any>()
    val events = internalEvents.asSharedFlow()

    suspend fun publish(event: Any) {
        internalEvents.emit(event)
    }

    suspend inline fun <reified T> subscribe(crossinline onEvent: (T) -> Unit) {
        events.filterIsInstance<T>()
            .collectLatest { event ->
                coroutineContext.ensureActive()
                onEvent(event)
            }
    }
}


interface KcEventBusDelegate {
    val theEventBus: KcEventBus
}



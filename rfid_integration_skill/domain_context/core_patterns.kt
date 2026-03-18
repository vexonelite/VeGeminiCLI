
/**
 * * Ref: [Android Networking in 2019 — Retrofit with Kotlin’s Coroutines](https://android.jlelse.eu/android-networking-in-2019-retrofit-with-kotlins-coroutines-aefe82c4d777)
 */
sealed class FmApiResult<T> {
    data class Success<T>(val data: T) : FmApiResult<T>()
    data class Error<T>(val cause: Throwable) : FmApiResult<T>()
}


/**
 * * Ref: [Railway Oriented Programming in Kotlin](https://proandroiddev.com/railway-oriented-programming-in-kotlin-f1bceed399e5)
 */
infix fun <T, R> FmApiResult<T>.then(block: (T) -> FmApiResult<R>) =
    when (this) {
        is FmApiResult.Success -> block(this.data)
        is FmApiResult.Error -> FmApiResult.Error(this.cause)
    }


infix fun <T, R> FmApiResult<T>.then(converter: IMapFunction<T, FmApiResult<R>>) =
    when (this) {
        is FmApiResult.Success -> converter.convertIntoData(this.data)
        is FmApiResult.Error -> FmApiResult.Error(this.cause)
    }


suspend infix fun <T, R> FmApiResult<T>.suspendThen(block: suspend (T) -> FmApiResult<R>) =
    when (this) {
        is FmApiResult.Success -> block(this.data)
        is FmApiResult.Error -> FmApiResult.Error(this.cause)
    }


suspend infix fun <T, R> FmApiResult<T>.suspendThen(converter: ISuspendMapFunction<T, FmApiResult<R>>) =
    when (this) {
        is FmApiResult.Success -> converter.convertIntoData(this.data)
        is FmApiResult.Error -> FmApiResult.Error(this.cause)
    }


infix fun <T, R> T.to(block: (T) -> FmApiResult<R>) = FmApiResult.Success(this).then(block)


infix fun <T> FmApiResult<T>.otherwise(block: (Throwable) -> Unit) =
    if (this is FmApiResult.Error) { block(this.cause) } else { Unit }

///--------------------------------------------

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



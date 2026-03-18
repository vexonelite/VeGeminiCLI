

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


///-------------------------------------------

enum class ListDataStateType {
    Init,
    Loading,
    Available,
    Unavailable,
    None,
}


sealed interface ListDataState<T> {
    val theState: ListDataStateType
    val theList: List<T>

    data class Init<T>(
        override val theState: ListDataStateType = ListDataStateType.Loading,
        override val theList: List<T> = listOf(),
    ): ListDataState<T>

    data class Loading<T>(
        override val theState: ListDataStateType = ListDataStateType.Loading,
        override val theList: List<T> = listOf(),
    ): ListDataState<T>

    data class Available<T>(
        override val theState: ListDataStateType = ListDataStateType.Available,
        override val theList: List<T> = listOf(),
    ): ListDataState<T>

    data class Unavailable<T>(
        override val theState: ListDataStateType = ListDataStateType.Unavailable,
        override val theList: List<T> = listOf(),
    ): ListDataState<T>

    data class None<T>(
        override val theState: ListDataStateType = ListDataStateType.None,
        override val theList: List<T> = listOf(),
    ): ListDataState<T>
}


// Extension function to convert List<T> to Available state
fun <T> List<T>.toAvailableState(): ListDataState<T> =
    ListDataState.Available(theList = this)



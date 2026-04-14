

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



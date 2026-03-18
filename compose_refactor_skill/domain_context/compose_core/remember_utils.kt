

@Composable
fun rememberTextFieldValueFrom(text: String? = null, defaultText: String = ""): MutableState<TextFieldValue> =
    remember {
        val rememberedText = text ?: defaultText
        mutableStateOf(
            TextFieldValue(text = rememberedText, selection = TextRange(rememberedText.length))
        )
    }


@Composable
fun rememberIntValueFrom(defaultValue: Int = 0, initBlock: () -> Int?, ): MutableIntState =
    remember {
        val rememberedValue = initBlock() ?: defaultValue
        mutableIntStateOf(rememberedValue)
    }


@Composable
fun <T> rememberNonNullObjectFrom(initBlock: () -> T): MutableState<T> = remember { mutableStateOf<T>(initBlock()) }


@Composable
fun <T> rememberNullableObjectFrom(initBlock: () -> T?): MutableState<T?> = remember { mutableStateOf<T?>(initBlock()) }

///

/**
 * * [Ref](https://stackoverflow.com/questions/68885154/using-remembersaveable-with-mutablestatelistof)
 */
@Composable
fun <T : Any> rememberMutableStateListOf(vararg elements: T): SnapshotStateList<T> {
    return rememberSaveable(saver = builtInSnapshotStateListSaver01()) {
        elements.toList().toMutableStateList()
    }
}

fun <T : Any> builtInSnapshotStateListSaver01() = listSaver<SnapshotStateList<T>, T>(
    save = { stateList -> stateList.toList() },
    restore = { it.toMutableStateList() },
)


fun <T : Any> builtInListSaver01() = listSaver<List<T>, T>(
    save = { list -> list },
    restore = { it }
)


// SnapshotStateMap<String, UhfTagDelegate>

@Composable
fun <T> BuiltInNonNullTypeStateFlowCollector(
    stateFlow: MutableStateFlow<T>,
    tag: String = "",
    onSnapshotAvailable: (T) -> Unit = { },
) {
    Logger.getLogger("[$tag] BuiltInTypeStateFlowCollector").log(Level.INFO, "[$tag] BuiltInTypeStateFlowCollector")
    val snapshot: T? by stateFlow.collectAsState()
    if (null != snapshot) { onSnapshotAvailable(snapshot!!) }
}





enum class BottomSheetState {
    EXPANDED,
    COLLAPSED,
}


interface SheetDragDecorationDelegate {
    val theDragColor: Color
    val theIconImageVector: ImageVector
    val theIconTintColor: Color
}


data class SheetDragDecorationImpl(
    override val theDragColor: Color,
    override val theIconImageVector: ImageVector,
    override val theIconTintColor: Color,
): SheetDragDecorationDelegate


@OptIn(ExperimentalMaterial3Api::class)
interface TwoStateSheetDragDecorationDelegate {
    val theExpandedDragColor: Color
    val theExpandedIconImageVector: ImageVector
    val theExpandedIconTintColor: Color
    val theCollapsedDragColor: Color
    val theCollapsedIconImageVector: ImageVector
    val theCollapsedIconTintColor: Color
    fun getDecoration(bottomSheetScaffoldState: BottomSheetScaffoldState): SheetDragDecorationDelegate
}


@OptIn(ExperimentalMaterial3Api::class)
data class TwoStateSheetDragDecorationImpl(
    override val theExpandedDragColor: Color = Teal200,
    override val theExpandedIconImageVector: ImageVector = Icons.Filled.KeyboardDoubleArrowUp,
    override val theExpandedIconTintColor: Color = Color.White,
    override val theCollapsedDragColor: Color = Teal200,
    override val theCollapsedIconImageVector: ImageVector = Icons.Filled.KeyboardDoubleArrowUp,
    override val theCollapsedIconTintColor: Color = Color.White,
): TwoStateSheetDragDecorationDelegate {
    override fun getDecoration(bottomSheetScaffoldState: BottomSheetScaffoldState): SheetDragDecorationDelegate =
        when (bottomSheetScaffoldState.bottomSheetState.currentValue) {
            SheetValue.Expanded -> {
                SheetDragDecorationImpl(theExpandedDragColor, theExpandedIconImageVector, theExpandedIconTintColor,)
            }
            //SheetValue.PartiallyExpanded -> {
            else -> {
                SheetDragDecorationImpl(theCollapsedDragColor, theCollapsedIconImageVector, theCollapsedIconTintColor,)
            }
        }
}

///

interface ISearchButtonDelete {
    val theOnSearchButtonClick: () -> Unit
}


interface IBuiltInBottomSheetSearchParameters01 : ISearchButtonDelete {
    val theSearchTags: SnapshotStateList<String>
}


interface IBuiltInSearchItemsDelegate01 : ISearchButtonDelete {
    val theKeywordValue: TextFieldValue
    val theKeywordOnValueChange: (TextFieldValue) -> Unit
    val theKeywordHint: String
    val theKeywordSelectAllOnFocus: Boolean
    val theKeywordOnFocusChanged: (FocusState) -> Unit

    val theClearButtonVisibility: Boolean
    val theOnClearButtonClick: () -> Unit
}


interface IBuiltInTabScreenParameters01 {
    val theScrollableThreshold: Int
    val theSelectedTabIndex: Int
    val theTabItems: List<TabItemDelegate>
    val theOnTabItemClick: (Int) -> Unit
    val theTabTextFontSize: TextUnit
}


interface IBuiltInLaunchEffectParameters01 {
    val theHasScreenBeenInited: Boolean
    val theCoroutineScope: CoroutineScope
    val theLaunchedEffectBlock: suspend CoroutineScope.() -> Unit
}


interface IBuiltInReloadLaunchEffectParameters01 {
    val theDataUpdateEvent: Boolean
    val theCurrentBackStackEntry: NavBackStackEntry?
    val theReloadLaunchedEffectBlock: suspend CoroutineScope.() -> Unit
}


interface IBuiltInListDataParameters01<T> {
    val theItemClickCallback: HolderItemClickDelegate<T>
    val theListDataState: State<ListDataState<T>>
}


interface IBuiltInLaunchEffectTabScreenParameters01 :
    IBuiltInLaunchEffectParameters01, IBuiltInTabScreenParameters01


interface IBuiltInLaunchEffectTabListScreenParameters01<T> :
    IBuiltInLaunchEffectTabScreenParameters01, IBuiltInListDataParameters01<T>


interface IBuiltInListScreenParameters01<T> :
    IBuiltInListDataParameters01<T>, IBuiltInLaunchEffectParameters01


interface IBuiltInBottomSheetListScreenParameters01<T> :
    IBuiltInListScreenParameters01<T>, IBuiltInBottomSheetSearchParameters01


interface IBuiltInTabListScreenParameters01<T> :
    IBuiltInListScreenParameters01<T>, IBuiltInTabScreenParameters01


interface IBuiltInBottomSheetTabListScreenParameters01<T> :
    IBuiltInListScreenParameters01<T>, IBuiltInBottomSheetSearchParameters01,
    IBuiltInTabScreenParameters01

///


interface IBuiltInScreenInputParameters {
    val theNavController: NavHostController
    val theHeaderContent: @Composable () -> Unit
    val theBottomContent: @Composable () -> Unit
    val theTopAppBarTitle: String
    val theHomeIcon: ImageVector
    val theHomeIconOnClick: () -> Unit
    val theNavigationActionContent: @Composable RowScope.() -> Unit
    val theTag: String
}


interface IBuiltInBottomSheetInputParameters {
    val theSheetPeekHeight: Dp // The height of the visible part of the sheet when collapsed
    val theSheetDragDecoration: TwoStateSheetDragDecorationDelegate
    val theBottomSheetContentBlock: @Composable () -> Unit
}


interface IBuiltInListDataInputParameters<T> {
    val theRememberedParameters: IBuiltInListScreenParameters01<T>
    val theAvailableListContent: LazyListScope.() -> Unit
    val theUnavailableContent: @Composable () -> Unit
    val theListDataInitText: String
    val theTotalNumberVisibility: Boolean
    val theTextSize: TextUnit
}


interface IBuiltInScreenInputParameters01<T> :
    IBuiltInScreenInputParameters, IBuiltInBottomSheetInputParameters, IBuiltInListDataInputParameters<T>


data class BuiltInBottomSheetListScreenParameters01<T>(
    override val theNavController: NavHostController,
    override val theRememberedParameters: IBuiltInListScreenParameters01<T>,
    override val theSheetPeekHeight: Dp = 35.dp,
    override val theSheetDragDecoration: TwoStateSheetDragDecorationDelegate = TwoStateSheetDragDecorationImpl(
        theExpandedDragColor = Purple200,
        theExpandedIconImageVector = Icons.Filled.KeyboardDoubleArrowDown,
        theExpandedIconTintColor = Color.White,
        theCollapsedDragColor = Teal200,
        theCollapsedIconImageVector = Icons.Filled.KeyboardDoubleArrowUp ,
        theCollapsedIconTintColor = Color.White,
    ),
    override val theBottomSheetContentBlock: @Composable () -> Unit = {},
    override val theAvailableListContent: LazyListScope.() -> Unit,
    override val theUnavailableContent: @Composable () -> Unit,
    override val theHeaderContent: @Composable () -> Unit = {},
    override val theBottomContent: @Composable () -> Unit = { },
    override val theTopAppBarTitle: String = "",
    override val theHomeIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    override val theHomeIconOnClick: () -> Unit = {
        theNavController.popBackStack()
    },
    override val theNavigationActionContent: @Composable RowScope.() -> Unit = {},
    override val theListDataInitText: String = "",
    override val theTotalNumberVisibility: Boolean = true,
    override val theTextSize: TextUnit = 20.sp,
    override val theTag: String = "",
) : IBuiltInScreenInputParameters01<T>

///


// 2024/11/11
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> BuiltInBottomSheetListScreen01(
    inputParameters: IBuiltInScreenInputParameters01<T>,
    bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    // [start] added by elite_lin - 2025/12/03
    totalAmountPrefix: String = "共",
    totalAmountSuffix: String = "件",
    // [end] added by elite_lin - 2025/12/03
) {
    Logger.getLogger("[${inputParameters.theTag}] BuiltInBottomSheetListScreen01").log(Level.INFO, "[${inputParameters.theTag}] BuiltInBottomSheetListScreen01")

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            inputParameters.theBottomSheetContentBlock()
        },
        sheetShape = RoundedCornerShape(
            topStart = 15.dp, topEnd = 15.dp, bottomStart = 0.dp, bottomEnd = 0.dp
        ),
        sheetPeekHeight = inputParameters.theSheetPeekHeight, // The height of the visible part of the sheet when collapsed
        sheetDragHandle = {
            BuiltInBottomSheetDragHandle02(
                twoStateSheetDragDecoration = inputParameters.theSheetDragDecoration,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                onDragIconClick = {
                    bottomSheetScaffoldState.dragIconClickerExt01(inputParameters.theRememberedParameters.theCoroutineScope)
                },
            )
        },
        sheetContainerColor = Grey94,
        containerColor = Color.White,
    ) {
        BuiltInTopAppBarListScreen01(
            inputParameters = inputParameters,
            // [start] added by elite_lin - 2025/12/03
            totalAmountPrefix = totalAmountPrefix,
            totalAmountSuffix = totalAmountSuffix,
            // [end] added by elite_lin - 2025/12/03
        )

    }

}

///

@Composable
fun <T> BuiltInTopAppBarListScreen01(
    inputParameters: IBuiltInScreenInputParameters01<T>,
    // [start] added by elite_lin - 2025/12/03
    totalAmountPrefix: String = "共",
    totalAmountSuffix: String = "件",
    // [end] added by elite_lin - 2025/12/03
) {
    Logger.getLogger("[${inputParameters.theTag}] BuiltInTopAppBarListScreen01").log(Level.INFO, "[${inputParameters.theTag}] BuiltInTopAppBarListScreen01")

    BuiltInTopAppBarScreenContent(
        topAppBarTitle = inputParameters.theTopAppBarTitle,
        navigationIcon = {
            TopAppBarNavigation01(
                imageVector = inputParameters.theHomeIcon,
                onClick = inputParameters.theHomeIconOnClick,
            )
        },
        actions = inputParameters.theNavigationActionContent,
        bodyContent = {
            val bottomPaddingForBottomSheet: Dp =
                if (inputParameters.theRememberedParameters is IBuiltInBottomSheetSearchParameters01) { 35.dp }
                else { 0.dp }

            if (inputParameters.theRememberedParameters is IBuiltInTabScreenParameters01) {
                val tabScreenParameters: IBuiltInTabScreenParameters01 =
                    inputParameters.theRememberedParameters as IBuiltInTabScreenParameters01

                BuiltInTopAppBarTabListScreenContent02(
                    listDataState = inputParameters.theRememberedParameters.theListDataState.value,
                    itemClickCallback = inputParameters.theRememberedParameters.theItemClickCallback,
                    listContent = inputParameters.theAvailableListContent,
                    unavailableContent = inputParameters.theUnavailableContent,
                    listDataInitText = inputParameters.theListDataInitText,
                    // [start] added by elite_lin - 2025/12/03
                    totalAmountPrefix = totalAmountPrefix,
                    totalAmountSuffix = totalAmountSuffix,
                    // [end] added by elite_lin - 2025/12/03
                    scrollableThreshold = tabScreenParameters.theScrollableThreshold,
                    selectedTabIndex = tabScreenParameters.theSelectedTabIndex,
                    tabItems = tabScreenParameters.theTabItems,
                    onTabItemClick = tabScreenParameters.theOnTabItemClick,
                    tabTextFontSize = tabScreenParameters.theTabTextFontSize,
                    headerContent = inputParameters.theHeaderContent,
                    bottomContent = inputParameters.theBottomContent,
                    bottomPaddingForBottomSheet = bottomPaddingForBottomSheet,
                    totalNumberVisibility = inputParameters.theTotalNumberVisibility,
                    textSize = inputParameters.theTextSize,
                    tag = inputParameters.theTag,
                )
            }
            else {
                BuiltInTopAppBarListScreenContent02(
                    listDataState = inputParameters.theRememberedParameters.theListDataState.value,
                    itemClickCallback = inputParameters.theRememberedParameters.theItemClickCallback,
                    listContent = inputParameters.theAvailableListContent,
                    unavailableContent = inputParameters.theUnavailableContent,
                    listDataInitText = inputParameters.theListDataInitText,
                    // [start] added by elite_lin - 2025/12/03
                    totalAmountPrefix = totalAmountPrefix,
                    totalAmountSuffix = totalAmountSuffix,
                    // [end] added by elite_lin - 2025/12/03
                    headerContent = inputParameters.theHeaderContent,
                    bottomContent = inputParameters.theBottomContent,
                    bottomPaddingForBottomSheet = bottomPaddingForBottomSheet,
                    totalNumberVisibility = inputParameters.theTotalNumberVisibility,
                    textSize = inputParameters.theTextSize,
                    tag = inputParameters.theTag,
                )
            }
        }
    )

    LaunchedEffect(
        key1 = inputParameters.theRememberedParameters.theHasScreenBeenInited,
        block = inputParameters.theRememberedParameters.theLaunchedEffectBlock
    )
}

///

@Preview
@Composable
fun <T> BuiltInTopAppBarListScreenContent01(
    listDataState: ListDataState<T> = ListDataState.None(),
    itemClickCallback: HolderItemClickDelegate<T>? = null,
    listContent: LazyListScope.() -> Unit = {},
    unavailableContent: @Composable () -> Unit = { ListDataUnavailable01() },
    listDataInitText: String = "請設定查詢參數來進行查詢!",
    // [start] added by elite_lin - 2025/12/03
    totalAmountPrefix: String = "共",
    totalAmountSuffix: String = "件",
    // [end] added by elite_lin - 2025/12/03
    headerContent: @Composable () -> Unit = { },
    bottomPaddingForBottomSheet: Dp = 0.dp,
    totalNumberVisibility: Boolean = true,
    textSize: TextUnit = 20.sp,
    tag: String = "",
) {
    Logger.getLogger("[${tag}] BuiltInTopAppBarListScreenContent01").log(Level.INFO, "[${tag}] BuiltInTopAppBarListScreenContent01")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = bottomPaddingForBottomSheet),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        headerContent()

        ListDataContent01(
            listDataState = listDataState,
            itemClickCallback = itemClickCallback,
            initContent = {
                ListDataInitContent01(
                    initText = listDataInitText,
                    textFontSize = textSize,
                )
            },
            loadingContent = {
                ListDataLoading01()
            },
            availableContent = { listDataStateX: ListDataState<T>, itemClickCallbackX: HolderItemClickDelegate<T>? ->
                BuiltInTopAppBarListScreenContentDataStateAvailable01(
                    listDataState = listDataStateX,
                    // [start] added by elite_lin - 2025/12/03
                    totalAmountPrefix = totalAmountPrefix,
                    totalAmountSuffix = totalAmountSuffix,
                    // [end] added by elite_lin - 2025/12/03
                    listContent = listContent,
                    //listBottomPadding = availableListBottomPadding,
                    totalNumberVisibility = totalNumberVisibility,
                    textSize = textSize,
                    tag = tag,
                )
            },
            unavailableContent = unavailableContent,
        )
    }
}


@Preview
@Composable
fun <T> BuiltInTopAppBarListScreenContent02(
    listDataState: ListDataState<T> = ListDataState.None(),
    itemClickCallback: HolderItemClickDelegate<T>? = null,
    listContent: LazyListScope.() -> Unit = {},
    unavailableContent: @Composable () -> Unit = { ListDataUnavailable01() },
    listDataInitText: String = "請設定查詢參數來進行查詢!",
    // [start] added by elite_lin - 2025/12/03
    totalAmountPrefix: String = "共",
    totalAmountSuffix: String = "件",
    // [end] added by elite_lin - 2025/12/03
    headerContent: @Composable () -> Unit = { },
    bottomContent: @Composable () -> Unit = { },
    bottomPaddingForBottomSheet: Dp = 0.dp,
    totalNumberVisibility: Boolean = true,
    textSize: TextUnit = 20.sp,
    tag: String = "",
) {
    Logger.getLogger("[${tag}] BuiltInTopAppBarListScreenContent02").log(Level.INFO, "[${tag}] BuiltInTopAppBarListScreenContent02")

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = bottomPaddingForBottomSheet),
    ) {
        val (headerRef, contentRef, bottomRef) = createRefs()

        Box(modifier = Modifier
            .constrainAs(headerRef) {
                top.linkTo(parent.top)
//                start.linkTo(parent.start /*, margin = 10.dp */)
//                end.linkTo(parent.end)
                linkTo(start = parent.start, end = parent.end, bias = 0f)
                //linkTo(top = parent.top, bottom = parent.bottom, bias = 0f)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            },
        ) {
            headerContent()
        }

        Box(
            modifier = Modifier
                .constrainAs(contentRef) {
//                    top.linkTo(headerRef.bottom)
//                    start.linkTo(parent.start /*, margin = 10.dp */)
//                    end.linkTo(parent.end)
                    linkTo(start = parent.start, end = parent.end, bias = 0f)
                    linkTo(top = headerRef.bottom, bottom = bottomRef.top, bias = 0f)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
        ) {
            ListDataContent01(
                listDataState = listDataState,
                itemClickCallback = itemClickCallback,
                initContent = {
                    ListDataInitContent01(
                        initText = listDataInitText,
                        textFontSize = textSize,
                    )
                },
                loadingContent = {
                    ListDataLoading01()
                },
                availableContent = { listDataStateX: ListDataState<T>, itemClickCallbackX: HolderItemClickDelegate<T>? ->
                    BuiltInTopAppBarListScreenContentDataStateAvailable01(
                        listDataState = listDataStateX,
                        // [start] added by elite_lin - 2025/12/03
                        totalAmountPrefix = totalAmountPrefix,
                        totalAmountSuffix = totalAmountSuffix,
                        // [end] added by elite_lin - 2025/12/03
                        listContent = listContent,
                        //listBottomPadding = availableListBottomPadding,
                        totalNumberVisibility = totalNumberVisibility,
                        textSize = textSize,
                        tag = tag,
                    )
                },
                unavailableContent = unavailableContent,
            )
        }

        Box(modifier = Modifier
            .constrainAs(bottomRef) {
                bottom.linkTo(parent.bottom)
//                start.linkTo(parent.start /*, margin = 10.dp */)
//                end.linkTo(parent.end)
                linkTo(start = parent.start, end = parent.end, bias = 0f)
                //linkTo(top = parent.top, bottom = parent.bottom, bias = 0f)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            },
        ) {
            bottomContent()
        }
    }

}

///

@Preview
@Composable
fun <T> BuiltInTopAppBarTabListScreenContent01(
    listDataState: ListDataState<T> = ListDataState.None(),
    itemClickCallback: HolderItemClickDelegate<T>? = null,
    listContent: LazyListScope.() -> Unit = {},
    unavailableContent: @Composable () -> Unit = { ListDataUnavailable01() },
    listDataInitText: String = "請設定查詢參數來進行查詢!",
    // [start] added by elite_lin - 2025/12/03
    totalAmountPrefix: String = "共",
    totalAmountSuffix: String = "件",
    // [end] added by elite_lin - 2025/12/03
    scrollableThreshold: Int = 4,
    selectedTabIndex: Int = 0,
    tabItems: List<TabItemDelegate> = listOf<TabItemDelegate>(),
    onTabItemClick: (Int) -> Unit = {},
    tabTextFontSize: TextUnit = 20.sp,
    headerContent: @Composable () -> Unit = { },
    bottomPaddingForBottomSheet: Dp = 0.dp,
    totalNumberVisibility: Boolean = true,
    textSize: TextUnit = 20.sp,
    tag: String = "",
) {
    Logger.getLogger("[${tag}] BuiltInTopAppBarTabListScreenContent01").log(Level.INFO, "[${tag}] BuiltInTopAppBarTabListScreenContent01")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = bottomPaddingForBottomSheet),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        headerContent()

        Scaffold(
            topBar = {
//                BuiltInTabRow01(
//                    parameter = BuiltInTabRow01Parameter(
                BuiltInMixedTabRow01(
                    parameter = BuiltInMixedTabRow01Parameter(
                        scrollableThreshold = scrollableThreshold,
                        selectedTabIndex = selectedTabIndex,
                        tabItems = ImmutableObjectList(tabItems).objectList,
                        onTabItemClick = onTabItemClick,
                        tabTextFontSize = tabTextFontSize,
                    ),
                )
            }
        ) { paddingValues: PaddingValues ->
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .padding(paddingValues)
                    .fillMaxSize(),
            ) {
                ListDataContent01(
                    listDataState = listDataState,
                    itemClickCallback = itemClickCallback,
                    initContent = {
                        ListDataInitContent01(
                            initText = listDataInitText,
                            textFontSize = textSize,
                        )
                    },
                    loadingContent = {
                        ListDataLoading01()
                    },
                    availableContent = { listDataStateX: ListDataState<T>, itemClickCallbackX: HolderItemClickDelegate<T>? ->
                        BuiltInTopAppBarListScreenContentDataStateAvailable01(
                            listDataState = listDataStateX,
                            // [start] added by elite_lin - 2025/12/03
                            totalAmountPrefix = totalAmountPrefix,
                            totalAmountSuffix = totalAmountSuffix,
                            // [end] added by elite_lin - 2025/12/03
                            listContent = listContent,
                            totalNumberVisibility = totalNumberVisibility,
                            textSize = textSize,
                            tag = tag
                        )
                    },
                    unavailableContent = unavailableContent,
                )
            }
        }
    }
}


@Preview
@Composable
fun <T> BuiltInTopAppBarTabListScreenContent02(
    listDataState: ListDataState<T> = ListDataState.None(),
    itemClickCallback: HolderItemClickDelegate<T>? = null,
    listContent: LazyListScope.() -> Unit = {},
    unavailableContent: @Composable () -> Unit = { ListDataUnavailable01() },
    listDataInitText: String = "請設定查詢參數來進行查詢!",
    // [start] added by elite_lin - 2025/12/03
    totalAmountPrefix: String = "共",
    totalAmountSuffix: String = "件",
    // [end] added by elite_lin - 2025/12/03
    scrollableThreshold: Int = 4,
    selectedTabIndex: Int = 0,
    tabItems: List<TabItemDelegate> = listOf<TabItemDelegate>(),
    onTabItemClick: (Int) -> Unit = {},
    tabTextFontSize: TextUnit = 20.sp,
    headerContent: @Composable () -> Unit = { },
    bottomContent: @Composable () -> Unit = { },
    bottomPaddingForBottomSheet: Dp = 0.dp,
    totalNumberVisibility: Boolean = true,
    textSize: TextUnit = 20.sp,
    tag: String = "",
) {
    Logger.getLogger("[${tag}] BuiltInTopAppBarTabListScreenContent02").log(Level.INFO, "[${tag}] BuiltInTopAppBarTabListScreenContent02")

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = bottomPaddingForBottomSheet),
    ) {
        val (headerRef, contentRef, bottomRef) = createRefs()

        Box(modifier = Modifier
            .constrainAs(headerRef) {
                top.linkTo(parent.top)
//                start.linkTo(parent.start /*, margin = 10.dp */)
//                end.linkTo(parent.end)
                linkTo(start = parent.start, end = parent.end, bias = 0f)
                //linkTo(top = parent.top, bottom = parent.bottom, bias = 0f)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            },
        ) {
            headerContent()
        }

        Scaffold(
            modifier = Modifier
                .constrainAs(contentRef) {
//                    top.linkTo(headerRef.bottom)
//                    start.linkTo(parent.start /*, margin = 10.dp */)
//                    end.linkTo(parent.end)
                    linkTo(start = parent.start, end = parent.end, bias = 0f)
                    linkTo(top = headerRef.bottom, bottom = bottomRef.top, bias = 0f)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            topBar = {
//                BuiltInTabRow01(
//                    parameter = BuiltInTabRow01Parameter(
                BuiltInMixedTabRow01(
                    parameter = BuiltInMixedTabRow01Parameter(
                        scrollableThreshold = scrollableThreshold,
                        selectedTabIndex = selectedTabIndex,
                        tabItems = ImmutableObjectList(tabItems).objectList,
                        onTabItemClick = onTabItemClick,
                        tabTextFontSize = tabTextFontSize,
                    ),
                )
            }
        ) { paddingValues: PaddingValues ->
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .padding(paddingValues)
                    .fillMaxSize(),
            ) {
                ListDataContent01(
                    listDataState = listDataState,
                    itemClickCallback = itemClickCallback,
                    initContent = {
                        ListDataInitContent01(
                            initText = listDataInitText,
                            textFontSize = textSize,
                        )
                    },
                    loadingContent = {
                        ListDataLoading01()
                    },
                    availableContent = { listDataStateX: ListDataState<T>, itemClickCallbackX: HolderItemClickDelegate<T>? ->
                        BuiltInTopAppBarListScreenContentDataStateAvailable01(
                            listDataState = listDataStateX,
                            // [start] added by elite_lin - 2025/12/03
                            totalAmountPrefix = totalAmountPrefix,
                            totalAmountSuffix = totalAmountSuffix,
                            // [end] added by elite_lin - 2025/12/03
                            listContent = listContent,
                            //listBottomPadding = availableListBottomPadding,
                            totalNumberVisibility = totalNumberVisibility,
                            textSize = textSize,
                            tag = tag,
                        )
                    },
                    unavailableContent = unavailableContent,
                )
            }
        }

        Box(modifier = Modifier
            .constrainAs(bottomRef) {
                bottom.linkTo(parent.bottom)
//                start.linkTo(parent.start /*, margin = 10.dp */)
//                end.linkTo(parent.end)
                linkTo(start = parent.start, end = parent.end, bias = 0f)
                //linkTo(top = parent.top, bottom = parent.bottom, bias = 0f)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            },
        ) {
            bottomContent()
        }
    }
}

///

@Preview
@Composable
fun <T> BuiltInTopAppBarListScreenContentDataStateAvailable01(
    listDataState: ListDataState<T> = ListDataState.Available(),
    listContent: LazyListScope.() -> Unit = {},
    // [start] added by elite_lin - 2025/12/03
    totalAmountPrefix: String = "共",
    totalAmountSuffix: String = "件",
    // [end] added by elite_lin - 2025/12/03
    totalNumberVisibility: Boolean = true,
    textSize: TextUnit = 20.sp,
    tag: String = "",
) {
    Logger.getLogger("[${tag}] BuiltInTopAppBarListScreenContentDataStateAvailable01").log(Level.INFO, "[${tag}] BuiltInTopAppBarListScreenContentDataStateAvailable01")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp,),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (totalNumberVisibility) {
            Text(
                text = "$totalAmountPrefix ${listDataState.theList.size} $totalAmountSuffix",   // revision by elite_lin - 2025/12/03
                fontSize = textSize,
                color = Color.Black,
                textAlign = TextAlign.End,
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
                //.background(Grey80)
                //.padding(horizontal = 10.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.padding(vertical = 5.dp))
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            //contentPadding = PaddingValues(start = 0.dp, top = 16.dp, end = 0.dp, bottom = 16.dp),
        ) {
            listContent()
        }
    }
}

///



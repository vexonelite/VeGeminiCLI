

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WtcInventoryScreen(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
    fontSize: TextUnit = 16.sp,
    chipColors: ChipColors = theBuiltInChipColors01(containerColor = Color.White),
    containerPaddingValues: PaddingValues = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
    containerBackground: Color = Grey94,
) {
    Logger.getLogger("WtcInventoryScreen").log(Level.INFO, "WtcInventoryScreen - time: [${System.currentTimeMillis()}]")

    val bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    val inventoryQueryParameters: InventoryQueryParameters = rememberInventoryQueryParameters(
        navController = navController,
        apiViewModel = apiViewModel,
        bottomSheetScaffoldState = bottomSheetScaffoldState,
    )

    BuiltInBottomSheetListScreen01(
        inputParameters = BuiltInBottomSheetListScreenParameters01<InventoryItemDelegate>(
            theNavController = navController,
            theRememberedParameters = inventoryQueryParameters,
            theBottomSheetContentBlock = {
                //WtcInventoryQueryScreenBottomSheetContent(
                WtcGalleryQueryScreenBottomSheetContent(
                    theParameters = inventoryQueryParameters,
                )
            },
            theAvailableListContent = {
                val itemList: List<InventoryItemDelegate> = inventoryQueryParameters.theListDataState.value.theList
                items(
                    items = ImmutableObjectList<InventoryItemDelegate>(itemList).objectList,
                    key = { inventoryItem: InventoryItemDelegate -> inventoryItem.theIdentifier },
                ) { inventoryItem: InventoryItemDelegate ->
                    InventoryListItem01(
                        inventoryItem = inventoryItem,
                        itemClickCallback = inventoryQueryParameters.theItemClickCallback,
                    )
                }
            },
            theUnavailableContent = {
                val itemList: List<InventoryItemDelegate> = inventoryQueryParameters.theListDataState.value.theList
                val unavailableItem: InventoryItemDelegate? = if (itemList.isNotEmpty()) { itemList[0] } else { null }
                ListDataUnavailable01(
                    unavailableText = unavailableItem?.theDescription ?: "",
                    onClick = {
                        if (null != unavailableItem) {
                            inventoryQueryParameters.theItemClickCallback.onHolderItemClicked(unavailableItem, BaseActions.REFRESH, -1)
                        }
                    },
                )
            },
            theHeaderContent = {
                val suggestionChipGroupParameter = BuiltInSuggestionChipGroup01Parameter(
                    items = ImmutableObjectList(inventoryQueryParameters.theSearchTags),
                    fontSize = fontSize,
                    chipColors = chipColors,
                    containerPaddingValues = containerPaddingValues,
                    containerBackground = containerBackground,
                )
                BuiltInSuggestionChipGroup01(
                    modifier = builtInChipGroupModifier01(
                        modifier = Modifier
                            .fillMaxWidth(),
                        //.padding(horizontal = 10.dp),
                        containerPaddingValues = suggestionChipGroupParameter.containerPaddingValues,
                        containerBackground = suggestionChipGroupParameter.containerBackground,
                    ),
                    //.then(Modifier).padding(horizontal = 10.dp),
                    parameter = suggestionChipGroupParameter,
                )
            },
//            theBottomContent = {
//                WtcAppSaveButton01 {
//
//                }
//            },
            theTopAppBarTitle = stringResource(R.string.menu_inventory),
            theListDataInitText = "請設定查詢參數來進行查詢!",
            theTotalNumberVisibility = false,
            theTag = "WtcInventory",
        ),
        bottomSheetScaffoldState = bottomSheetScaffoldState,
    )

    LaunchedEffect(
        key1 = inventoryQueryParameters.theDataUpdateEvent,
        key2 = inventoryQueryParameters.theCurrentBackStackEntry?.destination?.route,
        block = inventoryQueryParameters.theReloadLaunchedEffectBlock
    )
}


@Preview
@Composable
fun WtcInventoryQueryScreenBottomSheetContent(
    theParameters: InventoryQueryParameters = InventoryQueryParameters(),
) {
    Logger.getLogger("WtcInventoryQueryScreenBottomSheetContent").log(Level.INFO, "WtcInventoryQueryScreenBottomSheetContent - time: [${System.currentTimeMillis()}]")

    val keywordFocusRequester: FocusRequester = remember { FocusRequester() }
    val galleryCodeFocusRequester: FocusRequester = remember { FocusRequester() }
    val nameFocusRequester: FocusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(all = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val keywordParameter = BuiltInTextField02Parameter(
//            modifier = Modifier
//                .padding(horizontal = 20.dp),
            textValue = theParameters.theKeywordValue,
            onValueChange = theParameters.theKeywordOnValueChange,
            cornerShape = RoundedCornerShape(4.dp),
            //backgroundColor = Yellow002,
            hint = theParameters.theKeywordHint,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { galleryCodeFocusRequester.requestFocus() }),
            selectAllOnFocus = theParameters.theKeywordSelectAllOnFocus,
            onFocusChanged = theParameters.theKeywordOnFocusChanged,
            focusRequester = keywordFocusRequester,
        )

        BuiltInTextField02(
            modifier = createBuiltInTextField02Modifier01(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 10.dp),
                parameter = keywordParameter,
            ),
            parameter = keywordParameter,
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        val galleryCodeParameter = BuiltInTextField02Parameter(
//            modifier = Modifier
//                .padding(horizontal = 20.dp),
            textValue = theParameters.theGalleryCodeValue,
            onValueChange = theParameters.theGalleryCodeOnValueChange,
            cornerShape = RoundedCornerShape(4.dp),
            //backgroundColor = Yellow002,
            hint = theParameters.theGalleryCodeHint,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { nameFocusRequester.requestFocus() }),
            selectAllOnFocus = theParameters.theGalleryCodeSelectAllOnFocus,
            onFocusChanged = theParameters.theGalleryCodeOnFocusChanged,
            focusRequester = galleryCodeFocusRequester,
        )

        BuiltInTextField02(
            modifier = createBuiltInTextField02Modifier01(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 10.dp),
                parameter = galleryCodeParameter,
            ),
            parameter = galleryCodeParameter,
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        val nameParameter = BuiltInTextField02Parameter(
//            modifier = Modifier
//                .padding(horizontal = 20.dp),
            textValue = theParameters.theNameValue,
            onValueChange = theParameters.theNameOnValueChange,
            cornerShape = RoundedCornerShape(4.dp),
            //backgroundColor = Yellow002,
            hint = theParameters.theNameHint,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done),
            //keyboardActions = KeyboardActions(onNext = { keyboardFocusRequester.requestFocus() }),
            selectAllOnFocus = theParameters.theNameSelectAllOnFocus,
            onFocusChanged = theParameters.theNameOnFocusChanged,
            focusRequester = nameFocusRequester,
        )

        BuiltInTextField02(
            modifier = createBuiltInTextField02Modifier01(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 10.dp),
                parameter = nameParameter,
            ),
            parameter = nameParameter,
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Button(
            modifier = Modifier.wrapContentHeight(),
            onClick = theParameters.theOnSearchButtonClick,
            enabled = true,
            shape = ButtonDefaults.shape, // | elevatedShape | outlinedShape | textShape
            colors = theBuiltInButtonColor01(),
            contentPadding = PaddingValues(horizontal = 30.dp, vertical = 6.dp),

            ) {
            Icon(imageVector = Icons.Filled.Search, contentDescription = null, tint = Color.White)

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            Text(
                text = stringResource(tw.gov.wtc.rfid.R.string.b_query),
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                //modifier = Modifier.padding(vertical = 0.dp, horizontal = 10.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberInventoryQueryParameters(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
): InventoryQueryParameters {

    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    val updateInventoryDetailsEvent: Boolean by apiViewModel.inventoryEntryReloadEventStateFlow.collectAsState()
    val currentBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()

    // <editor-fold desc="TextFieldValues">
    val initText = ""
    var keywordValue: TextFieldValue by rememberTextFieldValueFrom(initText)
    var galleryCodeValue: TextFieldValue by rememberTextFieldValueFrom(initText)
    var nameValue: TextFieldValue by rememberTextFieldValueFrom(initText)
    // </editor-fold desc="TextFieldValues">

    // <editor-fold desc="FocusIndicators">
    var keywordFocusIndicator by remember { mutableIntStateOf(0) }
    var galleryCodeFocusIndicator by remember { mutableIntStateOf(0) }
    var nameFocusIndicator by remember { mutableIntStateOf(0) }
    // </editor-fold desc="FocusIndicators">

    val searchTags: SnapshotStateList<String> = rememberMutableStateListOf<String>()

    // <editor-fold desc="selectedTabIndex">
    var selectedTabIndex: Int by rememberIntValueFrom {
        apiViewModel.inventorySelectedTabIndexStateFlow.value
    }
    // </editor-fold desc="selectedTabIndex">

    // <editor-fold desc="tabItems">
    val tabItems: MutableState<List<TabItemDelegate>> = remember {
        mutableStateOf(
            // [start] revision by elite_lin - 2025/07/31
            //mutableListOf<WtcBadgetTabItemImpl>().apply { addAll(apiViewModel.inventoryTabList) }
            mutableListOf<MuseumBadgetTabItemImpl>().apply { addAll(WtcRfidTabListFactoryImpl().theInventoryEntryTabList) }
            // [end] revision by elite_lin - 2025/07/31
        )
    }
    // </editor-fold desc="tabItems">

    // <editor-fold desc="listDataState">
    val listDataState: MutableState<ListDataState<InventoryItemDelegate>> = remember {
        mutableStateOf(ListDataState.None())
    }
    // </editor-fold desc="listDataState">

    var hasScreenBeenInited: Boolean by rememberSaveable { mutableStateOf(false) }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    // <editor-fold desc="GalleryInventoryListResultStateFlowCollector">
    apiViewModel.galleryInventoryListResultStateFlow.NullableCollectorExt { queryInventoryListResult: FmApiResult<List<WtcRfInventoryItem>> ->
        coroutineScope.launch {
            when(queryInventoryListResult) {
                is FmApiResult.Success -> {
                    Logger.getLogger("rememberInventoryQueryParameters").log(Level.INFO, "rememberInventoryQueryParameters - GalleryInventoryListResultStateFlowCollector: [Success]")
                    // [start] revision by elite_lin - 2025/04/25
                    apiViewModel.apply {
                        updateCachedInventoryItemList(queryInventoryListResult.data)    // added by elite_lin - 2025/04/25
                        reBuildInventoryTabListMap(
                            scope = apiViewModel,
                            inventoryList = queryInventoryListResult.data,
                            index = selectedTabIndex
                        )
                        //selectedTabIndexToInventoryList(selectedTabIndex)
                    }
                    // [end] revision by elite_lin - 2025/04/25
                }
                is FmApiResult.Error -> {
                    Logger.getLogger("rememberInventoryQueryParameters").log(Level.SEVERE, "rememberInventoryQueryParameters - GalleryInventoryListResultStateFlowCollector： [Error]", queryInventoryListResult.cause)
                    apiViewModel.accountReAuthIfNeeded(scope = apiViewModel, cause = queryInventoryListResult.cause)
                    listDataState.value = defaultInventoryQueryUnavailableResult01()
                }
            }
        }
    }
    // </editor-fold desc="GalleryInventoryListResultStateFlowCollector">

    // <editor-fold desc="InventoryItemListStateFlowCollector">
    apiViewModel.inventoryItemListStateFlow.NullableCollectorExt { inventoryList: List<InventoryItemDelegate> ->
        Logger.getLogger("rememberInventoryQueryParameters").log(Level.INFO, "rememberInventoryQueryParameters - InventoryItemListStateFlowCollector - inventoryList.size: [${inventoryList.size}]")
        listDataState.value = ListDataState.Available<InventoryItemDelegate>(theList = inventoryList)
    }
    // </editor-fold desc="InventoryItemListStateFlowCollector">

    // <editor-fold desc="InventoryTabListStateFlowCollector">
    apiViewModel.inventoryTabListStateFlow.NullableCollectorExt { inventoryTabList: List<TabItemDelegate> ->
        Logger.getLogger("rememberInventoryQueryParameters").log(Level.INFO, "rememberInventoryQueryParameters - Inventory [Tab] ListStateFlowCollector")
        tabItems.value = inventoryTabList
    }
    // </editor-fold desc="InventoryTabListStateFlowCollector">

    // <editor-fold desc="reloadData">
    fun reloadData() {
        val authData: AuthData? = apiViewModel.theAuthData()
        if (null == authData) {
            Logger.getLogger("rememberInventoryQueryParameters").log(Level.INFO, "rememberInventoryQueryParameters - reloadData() - authData is null!")
            return
        }

        Logger.getLogger("rememberInventoryQueryParameters").log(Level.INFO, "rememberInventoryQueryParameters - reloadData() -  authData: [${authData.userCode}, ${authData.userName}]")

        val newSearchTags = apiViewModel.getCachedSearchParameters2()
            .filter { tag: String -> tag.isNotEmpty() }
        searchTags.apply {
            clear()
            addAll(newSearchTags)
        }

        val dateEndStr = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Calendar.getInstance().time)
        val startCalendar = Calendar.getInstance().apply {
            this.add(Calendar.YEAR,-5)
        }
        val dateStartStr = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(startCalendar.time)

        val queryModel = if (apiViewModel.cachedKeyword.isNotEmpty()) {
            WtcRfInventoryQueryModel(keyword = apiViewModel.cachedKeyword,)
        }
        else {
            WtcRfInventoryQueryModel(
                //inventoryDateStart = "2021/01/01", inventoryDateEnd = "2024/01/01"
                inventoryDateStart = dateStartStr, inventoryDateEnd = dateEndStr
            )
        }

        apiViewModel.inventoryEntryReloadEventStateFlow.update { false }

        listDataState.value = ListDataState.Loading()

        apiViewModel.apply {
            clearInventoryTabListMap()
            inventoryItemListStateFlow.value = null
            callGalleryInventoryListApi(
                scope = apiViewModel,
                apiDomain = getApiDomain(),
                authToken = authData.authToken,
                queryModel = queryModel,
            )
        }

        coroutineScope.launch {

        }
    }
    // </editor-fold desc="reloadData">

    return InventoryQueryParameters(
        // <editor-fold desc="InventoryQueryParameters - Keyword">
        theKeywordValue = keywordValue,
        theKeywordOnValueChange = { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, keywordFocusIndicator)
//            keywordFocusIndicator = pair.second
//            keywordValue = pair.first
            keywordValue = newValue
            Logger.getLogger("rememberInventoryQueryParameters").log(Level.INFO, "rememberInventoryQueryParameters() - onValueChange() - keywordFocusIndicator: [$keywordFocusIndicator], keywordValue： [${keywordValue.text}]")
        },
        theKeywordOnFocusChanged = {
            val pair: Pair<TextFieldValue, Int> = keywordValue.builtInTextFieldFocusChangedHandler01(true, it)
            keywordFocusIndicator = pair.second
            keywordValue = pair.first
            Logger.getLogger("rememberInventoryQueryParameters").log(Level.INFO, "rememberInventoryQueryParameters() - onFocusChanged() - keywordFocusIndicator: [$keywordFocusIndicator], keywordValue： [${keywordValue.text}]")
        },
        // </editor-fold desc="InventoryQueryParameters - Keyword">

        // <editor-fold desc="InventoryQueryParameters - GalleryCode">
        theGalleryCodeValue = galleryCodeValue,
        theGalleryCodeOnValueChange = { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, galleryCodeFocusIndicator)
//            galleryCodeFocusIndicator = pair.second
//            galleryCodeValue = pair.first
            galleryCodeValue = newValue
            Logger.getLogger("rememberInventoryQueryParameters").log(Level.INFO, "rememberInventoryQueryParameters() - onValueChange() - galleryCodeFocusIndicator: [$galleryCodeFocusIndicator], galleryCodeValue： [${galleryCodeValue.text}]")
        },
        theGalleryCodeOnFocusChanged = {
            val pair: Pair<TextFieldValue, Int> = galleryCodeValue.builtInTextFieldFocusChangedHandler01(true, it)
            galleryCodeFocusIndicator = pair.second
            galleryCodeValue = pair.first
            Logger.getLogger("rememberInventoryQueryParameters").log(Level.INFO, "rememberInventoryQueryParameters() - onFocusChanged() - galleryCodeFocusIndicator: [$galleryCodeFocusIndicator], galleryCodeValue： [${galleryCodeValue.text}]")
        },
        // </editor-fold desc="InventoryQueryParameters - GalleryCode">

        // <editor-fold desc="InventoryQueryParameters - Name">
        theNameValue = nameValue,
        theNameOnValueChange = { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, nameFocusIndicator)
//            nameFocusIndicator = pair.second
//            nameValue = pair.first
            nameValue = newValue
            Logger.getLogger("rememberInventoryQueryParameters").log(Level.INFO, "rememberInventoryQueryParameters() - onValueChange() - nameFocusIndicator: [$nameFocusIndicator], nameValue： [${nameValue.text}]")
        },
        theNameOnFocusChanged = {
            val pair: Pair<TextFieldValue, Int> = nameValue.builtInTextFieldFocusChangedHandler01(true, it)
            nameFocusIndicator = pair.second
            nameValue = pair.first
            Logger.getLogger("rememberInventoryQueryParameters").log(Level.INFO, "rememberInventoryQueryParameters() - onFocusChanged() - nameFocusIndicator: [$nameFocusIndicator], nameValue： [${nameValue.text}]")
        },
        // </editor-fold desc="InventoryQueryParameters - Name">

        // <editor-fold desc="InventoryQueryParameters - theItemClickCallback">
        theItemClickCallback = HolderItemClickDelegate<InventoryItemDelegate> { dataObject: InventoryItemDelegate, action: String, position: Int ->
            Logger.getLogger("rememberInventoryQueryParameters").log(Level.INFO, "rememberInventoryQueryParameters - itemClickCallback - delegate: [${dataObject.theDescription}], action: [$action], position: [$position]")
            coroutineScope.launch {
                apiViewModel.apply {
                    selectedInventory = dataObject.theDataObject
//                    galleryInventoryRoomSummaryListResultLiveData.value = null
//                    inventoryRoomSummarySelectedTabIndexLiveData.value = null
//                    inventoryRoomSummaryTabListLiveData.value = null
//                    inventoryRoomSummaryListLiveData.value = null
                    galleryInventoryRoomSummaryListResultStateFlow.value = null
                    inventoryRoomSummarySelectedTabIndexStateFlow.value = null
                    inventoryRoomSummaryTabListStateFlow.value = null
                    inventoryRoomSummaryListStateFlow.value = null
                    clearCachedInventoryRoomSummaryList()   // added by elite_lin - 2025/04/25
                    clearInventoryRoomSummaryTabListMap()
                }
                navController.navigateToExt(WtcRouteDestination.InventoryRoomSummary.theRoute)
            }
        },
        // </editor-fold desc="InventoryQueryParameters - theItemClickCallback">

        theListDataState = listDataState,

        theSelectedTabIndex = selectedTabIndex,
        theTabItems = tabItems.value,

        // <editor-fold desc="InventoryQueryParameters - theOnTabItemClick">
        theOnTabItemClick = { index: Int ->
            selectedTabIndex = index
            // [start] revision by elite_lin - 2025/04/25
            // apiViewModel.inventorySelectedTabIndexStateFlow.update { selectedTabIndex }
            // bug fixes: display empty content when clicking tabs
            apiViewModel.apply {
                inventorySelectedTabIndexStateFlow.update { selectedTabIndex }
                // [start] added by elite_lin - 2025/04/25
                reBuildInventoryTabListMap(
                    scope = apiViewModel,
                    inventoryList = apiViewModel.cachedInventoryItemList,
                    index = selectedTabIndex
                )
                // [end] added by elite_lin - 2025/04/25
            }
            // [end] revision by elite_lin - 2025/04/25
            Logger.getLogger("rememberInventoryQueryParameters").log(Level.INFO, "rememberInventoryQueryParameters - theOnTabItemClick - selectedTabIndex: [$selectedTabIndex]!")
        },
        // </editor-fold desc="InventoryQueryParameters - theOnTabItemClick">

        theHasScreenBeenInited = hasScreenBeenInited,
        theCoroutineScope = coroutineScope,

        // <editor-fold desc="InventoryQueryParameters - theLaunchedEffectBlock">
        theLaunchedEffectBlock = {
            Logger.getLogger("rememberInventoryQueryParameters").log(Level.INFO, "rememberInventoryQueryParameters - launchedEffectBlock -  hasScreenBeenInited: [$hasScreenBeenInited]!")
            if (!hasScreenBeenInited) {
                Logger.getLogger("rememberInventoryQueryParameters").log(Level.INFO, "rememberInventoryQueryParameters - launchedEffectBlock - run init!!")
                listDataState.value = ListDataState.Init()
                delay(1000)
                bottomSheetScaffoldState.bottomSheetState.expand()
                hasScreenBeenInited = true
            }
            else { Logger.getLogger("rememberInventoryQueryParameters").log(Level.INFO, "rememberInventoryQueryParameters - launchedEffectBlock - no action!!") }
        },
        // </editor-fold desc="InventoryQueryParameters - theLaunchedEffectBlock">

        theSearchTags = searchTags,

        // <editor-fold desc="CeDocumentQueryParameters - theReloadLaunchedEffectBlock">
        theDataUpdateEvent = updateInventoryDetailsEvent,
        theCurrentBackStackEntry = currentBackStackEntry,
        theReloadLaunchedEffectBlock = {
            Logger.getLogger("rememberCeDocumentQueryParameters").log(Level.INFO, "rememberCeDocumentQueryParameters - theReloadLaunchedEffectBlock - [$updateInventoryDetailsEvent], currentBackStackEntry: [${currentBackStackEntry?.destination?.route}]!")
            if ((updateInventoryDetailsEvent) && (currentBackStackEntry?.destination?.route == WtcRoute.INVENTORY)) {
                Logger.getLogger("rememberCeDocumentQueryParameters").log(Level.INFO, "rememberCeDocumentQueryParameters - theReloadLaunchedEffectBlock[***] - cachedKeyword.value: [${apiViewModel.cachedKeyword}]!")
                reloadData()
            }
        },
        // </editor-fold desc="CeDocumentQueryParameters - theReloadLaunchedEffectBlock">

        // <editor-fold desc="InventoryQueryParameters - theOnSearchButtonClick">
        theOnSearchButtonClick = {
            keyboardController?.hide()
            bottomSheetScaffoldState.dragIconClickerExt01(coroutineScope)
            apiViewModel.apply {
                cachedKeyword = keywordValue.text
                cachedGalleryCode = galleryCodeValue.text
                cachedName = nameValue.text
            }
            reloadData()
        },
        // </editor-fold desc="InventoryQueryParameters - theOnSearchButtonClick">
    )
}


data class InventoryQueryParameters(
    override val theKeywordValue: TextFieldValue = TextFieldValue(),
    override val theKeywordOnValueChange: (TextFieldValue) -> Unit = {},
    override val theKeywordHint: String = "全欄位檢索詞",
    override val theKeywordSelectAllOnFocus: Boolean = false,
    override val theKeywordOnFocusChanged: (FocusState) -> Unit = {},

    override val theGalleryCodeValue: TextFieldValue = TextFieldValue(),
    override val theGalleryCodeOnValueChange: (TextFieldValue) -> Unit = {},
    override val theGalleryCodeHint: String = "登錄號",
    override val theGalleryCodeSelectAllOnFocus: Boolean = false,
    override val theGalleryCodeOnFocusChanged: (FocusState) -> Unit = {},
    override val theGalleryCodeVisibility: Boolean = false,

    override val theNameValue: TextFieldValue = TextFieldValue(),
    override val theNameOnValueChange: (TextFieldValue) -> Unit = {},
    override val theNameHint: String = "名稱",
    override val theNameSelectAllOnFocus: Boolean = false,
    override val theNameOnFocusChanged: (FocusState) -> Unit = {},
    override val theNameVisibility: Boolean = false,

    override val theClearButtonVisibility: Boolean = false,
    override val theOnClearButtonClick: () -> Unit = {},

    override val theItemClickCallback: HolderItemClickDelegate<InventoryItemDelegate> = DefaultHolderCellClickHandler<InventoryItemDelegate>(),
    override val theListDataState: State<ListDataState<InventoryItemDelegate>> = mutableStateOf<ListDataState<InventoryItemDelegate>>(ListDataState.None()),

    override val theSearchTags: SnapshotStateList<String> = mutableStateListOf(),
    override val theOnSearchButtonClick: () -> Unit = {},

    override val theSelectedTabIndex: Int = 0,
    override val theTabItems: List<TabItemDelegate> = listOf<TabItemDelegate>(),
    override val theOnTabItemClick: (Int) -> Unit = {},
    override val theTabTextFontSize: TextUnit = 20.sp,

    override val theDataUpdateEvent: Boolean = false,
    override val theCurrentBackStackEntry: NavBackStackEntry? = null,
    override val theReloadLaunchedEffectBlock: suspend CoroutineScope.() -> Unit = {},

    override val theHasScreenBeenInited: Boolean = false,
    override val theCoroutineScope: CoroutineScope = BuiltInCoroutineScopeImpl(),
    override val theLaunchedEffectBlock: suspend CoroutineScope.() -> Unit = {},
    override val theScrollableThreshold: Int = 3,

) : MuseumSearchItemsDelegate,
    IBuiltInBottomSheetTabListScreenParameters01<InventoryItemDelegate>,
    IBuiltInReloadLaunchEffectParameters01


fun defaultInventoryQueryUnavailableResult01(
    description: String = "無查詢結果!\n請使用其它查詢設定，\n重新進行查詢。",
    action: String = "Unavailable"
) = ListDataState.Unavailable<InventoryItemDelegate>(
    theList = listOf(
        InventoryItemImpl(
            theIdentifier = generateRandomStringViaUuid(),
            theCellType = BaseCellTypes.UNAVAILABLE,
            theDescription = description,
            theAction = action
        )
    )
)



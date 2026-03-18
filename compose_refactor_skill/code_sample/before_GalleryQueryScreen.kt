

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WtcGalleryQueryScreen(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
    c72RfidViewModel: C72RfidViewModel,
    r36RfidViewModel: R36RfidViewModel,
    rG768RfidViewModel: RG768RfidViewModel,     // added by elite_lin - 2025/08/31
    fontSize: TextUnit = 16.sp,
    chipColors: ChipColors = theBuiltInChipColors01(containerColor = Color.White),
    containerPaddingValues: PaddingValues = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
    containerBackground: Color = Grey94,
) {
    Logger.getLogger("WtcGalleryQueryScreen").log(Level.INFO, "WtcGalleryQueryScreen - time: [${System.currentTimeMillis()}]")

    val context: Context = LocalContext.current

    val bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    val galleryQueryParameters: GalleryQueryParameters = rememberGalleryQueryParameters(
        navController = navController,
        apiViewModel = apiViewModel,
        c72RfidViewModel = c72RfidViewModel,
        r36RfidViewModel = r36RfidViewModel,
        rG768RfidViewModel = rG768RfidViewModel,    // added by elite_lin - 2025/08/31
        bottomSheetScaffoldState = bottomSheetScaffoldState,
    )

    BuiltInBottomSheetListScreen01(
        inputParameters = BuiltInBottomSheetListScreenParameters01<GalleryItemDelegate>(
            theNavController = navController,
            theRememberedParameters = galleryQueryParameters,
            theBottomSheetContentBlock = {
                WtcGalleryQueryScreenBottomSheetContent(
                    theParameters = galleryQueryParameters,
                )
            },
            theAvailableListContent = {
                val itemList: List<GalleryItemDelegate> = galleryQueryParameters.theListDataState.value.theList
                items(
                    items = ImmutableObjectList<GalleryItemDelegate>(itemList).objectList,
                    key = { galleryItem: GalleryItemDelegate -> galleryItem.theIdentifier },
                ) { galleryItem: GalleryItemDelegate ->
                    GalleryListItem01(
                        gallery = galleryItem,
                        itemClickCallback = galleryQueryParameters.theItemClickCallback,
                        inOutBoundIconVisibility = false,
                        //removeButtonVisibility = false
                    )
                }
            },
            theUnavailableContent = {
                val itemList: List<GalleryItemDelegate> = galleryQueryParameters.theListDataState.value.theList
                val unavailableItem: GalleryItemDelegate? = if (itemList.isNotEmpty()) { itemList[0] } else { null }
                ListDataUnavailable01(
                    unavailableText = unavailableItem?.theDescription ?: "",
                    onClick = {
                        if (null != unavailableItem) {
                            galleryQueryParameters.theItemClickCallback.onHolderItemClicked(unavailableItem, BaseActions.REFRESH, -1)
                        }
                    },
                )
            },
            theHeaderContent = {
                val suggestionChipGroupParameter = BuiltInSuggestionChipGroup01Parameter(
                    items = ImmutableObjectList(galleryQueryParameters.theSearchTags),
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
            theTopAppBarTitle = stringResource(R.string.menu_query),
            theListDataInitText = "1. 拉出下方抽屉來設定查詢參數進行查詢!\n2. 使用手持機讀取藏品UHF標籤或一維條碼!",
            theTag = "WtcGalleryQuery",
        ),
        bottomSheetScaffoldState = bottomSheetScaffoldState,
    )

    BuiltInDialogSet01(
        dialogState = galleryQueryParameters.galleryQueryDialogState.value
    )
}


// Keep
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberGalleryQueryParameters(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
    c72RfidViewModel: C72RfidViewModel,
    r36RfidViewModel: R36RfidViewModel,
    rG768RfidViewModel: RG768RfidViewModel,     // added by elite_lin - 2025/08/31
    bottomSheetScaffoldState: BottomSheetScaffoldState,
): GalleryQueryParameters {

    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    var hasScreenBeenInited: Boolean by rememberSaveable { mutableStateOf(false) }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

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

    // <editor-fold desc="searchTags">
    val searchTags: SnapshotStateList<String> = rememberMutableStateListOf<String>()
    // </editor-fold desc="searchTags">

    // <editor-fold desc="listDataState">
    val listDataState: MutableState<ListDataState<GalleryItemDelegate>> = remember {
        mutableStateOf(ListDataState.None())
    }
    // </editor-fold desc="listDataState">

    // <editor-fold desc="galleryQueryDialogState">
    val galleryQueryDialogState: MutableState<BuiltInDialogStateDelegate> = remember { mutableStateOf(
        BuiltInDialogStateImpl()
    ) }
    // </editor-fold desc="galleryQueryDialogState">

    // <editor-fold desc="progressDialogState">
    val progressDialogState = BuiltInDialogStateImpl(
        theDialogType = DialogType.Progress,
        theDialogState = true,
        theMessage = "載入中...",
        onDismiss = { galleryQueryDialogState.value = BuiltInDialogStateImpl() }
    )
    // </editor-fold desc="progressDialogState">

    // <editor-fold desc="singleActionDialogState">
    val singleActionDialogState = MutableBuiltInDialogStateImpl(
        theDialogType = DialogType.SingleAction,
        theDialogState = true,
        theTitle = "提醒",
        theConfirmTitle = "確定",
        theCancelTitle = "取消",
        onDismiss = { galleryQueryDialogState.value = BuiltInDialogStateImpl() }
    )
    // </editor-fold desc="singleActionDialogState">

    // [start] added by elite_lin - 2025/12/11
    val c72RfidConfig: RfidDeviceConfigDelegate = remember(c72RfidViewModel) { c72RfidViewModel.toRfidConfigExt() }
    val r36RfidConfig: RfidDeviceConfigDelegate = remember(r36RfidViewModel) { r36RfidViewModel.toRfidConfigExt() }

//    val rfidConfig = remember(deviceType, c72RfidViewModel, r36RfidViewModel) {
//        when (deviceType) {
//            DeviceType.R36 -> r36ViewModel?.toRfidConfig()
//            DeviceType.R50 -> r50ViewModel?.toRfidConfig()
//            DeviceType.R60 -> r60ViewModel?.toRfidConfig()
//        } ?: error("No ViewModel provided for device type: $deviceType")
//    }
    // [end] added by elite_lin - 2025/12/11

    // <editor-fold desc="QueryGalleryListResultStateFlowCollector">
    // [start] revision by elite_lin - 2025/12/11
    val queryGalleryListResultX: FmApiResult<List<GalleryItemDelegate>>? by apiViewModel.queryGalleryListResultStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(queryGalleryListResultX) {
        val queryGalleryListResult = queryGalleryListResultX ?: return@LaunchedEffect

        listDataState.value = when(queryGalleryListResult) {
            is FmApiResult.Success -> {
                Logger.getLogger("rememberGalleryQueryParameters").log(Level.INFO, "rememberGalleryQueryParameters - QueryGalleryListResultStateFlowCollector: [Success]")
                val resultList: List<GalleryItemDelegate> = queryGalleryListResult.data
                if (resultList.isNotEmpty()) {
                    apiViewModel.stringUhfTagDataExtDelegate.clearUhfTagMap(scope = apiViewModel, usageScenario = UsageScenarios.GALLERY_QUERY)
                    ListDataState.Available<GalleryItemDelegate>(theList = queryGalleryListResult.data)
                }
                else { defaultGalleryQueryUnavailableResult01() }
            }
            is FmApiResult.Error -> {
                Logger.getLogger("rememberGalleryQueryParameters").log(Level.SEVERE, "rememberGalleryQueryParameters - QueryGalleryListResultStateFlowCollector： .Error]", queryGalleryListResult.cause)

                apiViewModel.accountReAuthIfNeeded(scope = apiViewModel, cause = queryGalleryListResult.cause)

                defaultGalleryQueryUnavailableResult01()
            }
        }
    }
    // [end] revision by elite_lin - 2025/12/11
    // </editor-fold desc="QueryGalleryListResultStateFlowCollector">

    // <editor-fold desc="StringUhfGalleryListStateFlowCollector">
    // [start] revision by elite_lin - 2025/12/11
    val uhfGalleryListX: List<GalleryItemDelegate>? by apiViewModel.stringUhfTagDataExtDelegate.uhfGalleryListStateFlow.collectAsState()
    LaunchedEffect(uhfGalleryListX) {
        val uhfGalleryList = uhfGalleryListX ?: return@LaunchedEffect

        Logger.getLogger("rememberGalleryQueryParameters").log(Level.INFO, "rememberGalleryQueryParameters() - stringUhfTagDataExtDelegate.uhfGalleryList StateFlowCollector")
        if (uhfGalleryList.isNotEmpty()) {
            if (null != apiViewModel.galleryListResultStateFlow.value) {
                apiViewModel.galleryListResultStateFlow.value = null
            }
            listDataState.value = ListDataState.Available<GalleryItemDelegate>(theList = uhfGalleryList)
        }
        else {
            if (null == apiViewModel.galleryListResultStateFlow.value) {
                listDataState.value = ListDataState.Init()
            }
        }
    }
    // [end] revision by elite_lin - 2025/12/11
    // </editor-fold desc="StringUhfGalleryListStateFlowCollector">

    // <editor-fold desc="C72 StateFlowCollector">
    // [start] revision by elite_lin - 2025/12/11
    val c72UhfTagX: RfidUhfTagDelegate? by c72RfidConfig.uhfTagReadResultStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(c72UhfTagX) {
        val uhfTag = c72UhfTagX ?: return@LaunchedEffect

        val parsedTag = c72RfidConfig.parseGalleryTagNo(uhfTag)
        apiViewModel.stringUhfTagDataExtDelegate.putItemIntoUhfTagMap(scope = apiViewModel, key = parsedTag, value = parsedTag, usageScenario = UsageScenarios.GALLERY_QUERY)
        c72RfidConfig.resetUhfTagReadResultStateFlow()
    }
    
    //==============================================================================================

    val c72BarcodeX: BarcodeEntityDelegate? by c72RfidConfig.barcodeReadResultStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(c72BarcodeX) {
        val barcodeResult = c72BarcodeX ?: return@LaunchedEffect

        val readBarCode = c72RfidConfig.getBarcodeData(barcodeResult)
        apiViewModel.stringUhfTagDataExtDelegate.putItemIntoUhfTagMap(scope = apiViewModel, key = readBarCode, value = readBarCode, usageScenario = UsageScenarios.GALLERY_QUERY)
        c72RfidConfig.resetBarcodeReadResultStateFlow()
    }
    // </editor-fold desc="C72 StateFlowCollector">

    // <editor-fold desc="R36 StateFlowCollector">
    // [start] revision by elite_lin - 2025/12/11
    val r36UhfTagX: RfidUhfTagDelegate? by r36RfidConfig.uhfTagReadResultStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(r36UhfTagX) {
        val uhfTag = r36UhfTagX ?: return@LaunchedEffect

        val parsedTag = r36RfidConfig.parseGalleryTagNo(uhfTag)
        apiViewModel.stringUhfTagDataExtDelegate.putItemIntoUhfTagMap(scope = apiViewModel, key = parsedTag, value = parsedTag, usageScenario = UsageScenarios.GALLERY_QUERY)
        r36RfidConfig.resetUhfTagReadResultStateFlow()
    }

    //==============================================================================================

    val r36BarcodeX: BarcodeEntityDelegate? by r36RfidConfig.barcodeReadResultStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(r36BarcodeX) {
        val barcodeResult = r36BarcodeX ?: return@LaunchedEffect

        val readBarCode = r36RfidConfig.getBarcodeData(barcodeResult)
        apiViewModel.stringUhfTagDataExtDelegate.putItemIntoUhfTagMap(scope = apiViewModel, key = readBarCode, value = readBarCode, usageScenario = UsageScenarios.GALLERY_QUERY)
        r36RfidConfig.resetBarcodeReadResultStateFlow()
    }
    // [end] revision by elite_lin - 2025/12/11
    // </editor-fold desc="R36 StateFlowCollector">

    // [start] added by elite_lin - 2025/08/31
    // <editor-fold desc="RG768 StateFlowCollector">
    RG768UhfGalleryTagReadResultStateFlowCollector(rG768RfidViewModel) { parsedTag: String, uhfTag: RG768UhfTagDelegate ->
        //Logger.getLogger("rememberGalleryQueryParameters").log(Level.INFO, "rememberGalleryQueryParameters - RG768UhfGalleryTagReadResultStateFlowCollector: parsedTag: [$parsedTag]")
        apiViewModel.stringUhfTagDataExtDelegate.putItemIntoUhfTagMap(scope = apiViewModel, key = parsedTag, value = parsedTag, usageScenario = UsageScenarios.GALLERY_QUERY)
    }
    // [end] added by elite_lin - 2025/08/31


    return GalleryQueryParameters(
        // <editor-fold desc="GalleryQueryParameters - Keyword">
        theKeywordValue = keywordValue,
        theKeywordOnValueChange = { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, keywordFocusIndicator)
//            keywordFocusIndicator = pair.second
//            keywordValue = pair.first
            keywordValue = newValue
            Logger.getLogger("rememberGalleryQueryParameters").log(Level.INFO, "rememberGalleryQueryParameters() - onValueChange() - keywordFocusIndicator: [$keywordFocusIndicator], keywordValue： [${keywordValue.text}]")
        },
        theKeywordOnFocusChanged = {
            val pair: Pair<TextFieldValue, Int> = keywordValue.builtInTextFieldFocusChangedHandler01(true, it)
            keywordFocusIndicator = pair.second
            keywordValue = pair.first
            Logger.getLogger("rememberGalleryQueryParameters").log(Level.INFO, "rememberGalleryQueryParameters() - onFocusChanged() - keywordFocusIndicator: [$keywordFocusIndicator], keywordValue： [${keywordValue.text}]")
        },
        // </editor-fold desc="GalleryQueryParameters - Keyword">

        // <editor-fold desc="GalleryQueryParameters - GalleryCode">
        theGalleryCodeValue = galleryCodeValue,
        theGalleryCodeOnValueChange = { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, galleryCodeFocusIndicator)
//            galleryCodeFocusIndicator = pair.second
//            galleryCodeValue = pair.first
            galleryCodeValue = newValue
            Logger.getLogger("rememberGalleryQueryParameters").log(Level.INFO, "rememberGalleryQueryParameters() - onValueChange() - galleryCodeFocusIndicator: [$galleryCodeFocusIndicator], galleryCodeValue： [${galleryCodeValue.text}]")
        },
        theGalleryCodeOnFocusChanged = {
            val pair: Pair<TextFieldValue, Int> = galleryCodeValue.builtInTextFieldFocusChangedHandler01(true, it)
            galleryCodeFocusIndicator = pair.second
            galleryCodeValue = pair.first
            Logger.getLogger("rememberGalleryQueryParameters").log(Level.INFO, "rememberGalleryQueryParameters() - onFocusChanged() - galleryCodeFocusIndicator: [$galleryCodeFocusIndicator], galleryCodeValue： [${galleryCodeValue.text}]")
        },
        // </editor-fold desc="GalleryQueryParameters - GalleryCode">

        // <editor-fold desc="GalleryQueryParameters - Name">
        theNameValue = nameValue,
        theNameOnValueChange = { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, nameFocusIndicator)
//            nameFocusIndicator = pair.second
//            nameValue = pair.first
            nameValue = newValue
            Logger.getLogger("rememberGalleryQueryParameters").log(Level.INFO, "rememberGalleryQueryParameters() - onValueChange() - nameFocusIndicator: [$nameFocusIndicator], nameValue： [${nameValue.text}]")
        },
        theNameOnFocusChanged = {
            val pair: Pair<TextFieldValue, Int> = nameValue.builtInTextFieldFocusChangedHandler01(true, it)
            nameFocusIndicator = pair.second
            nameValue = pair.first
            Logger.getLogger("rememberGalleryQueryParameters").log(Level.INFO, "rememberGalleryQueryParameters() - onFocusChanged() - nameFocusIndicator: [$nameFocusIndicator], nameValue： [${nameValue.text}]")
        },
        // </editor-fold desc="GalleryQueryParameters - Name">

        // <editor-fold desc="GalleryQueryParameters - theItemClickCallback">
        theItemClickCallback = HolderItemClickDelegate<GalleryItemDelegate> { dataObject: GalleryItemDelegate, action: String, position: Int ->
            Logger.getLogger("rememberGalleryQueryParameters").log(Level.INFO, "rememberGalleryQueryParameters - itemClickCallback - delegate: [${dataObject.theDescription}], action: [$action], position: [$position]")
            coroutineScope.launch {
                when(action) {
                    BaseActions.REMOVE -> {
                        if (dataObject is DeviceGalleryItemDelegate) {
                            apiViewModel.stringUhfTagDataExtDelegate.removeItemFromUhfTagMap(scope = apiViewModel, key = dataObject.theIdentifier, usageScenario = UsageScenarios.GALLERY_QUERY)
                        }
                    }
                    BaseActions.TAP -> {
                        apiViewModel.apply {
                            galleryDetailResultStateFlow.value = null
                            galleryUpdateResultStateFlow.value = null
                            selectedGallery = dataObject.theDataObject
                        }
                        navController.navigateToExt(WtcRouteDestination.GalleryDetails.theRoute)
                    }
                }
            }
        },
        // </editor-fold desc="GalleryQueryParameters - theItemClickCallback">

        theSearchTags = searchTags,
        theListDataState = listDataState,

        theHasScreenBeenInited = hasScreenBeenInited,
        theCoroutineScope = coroutineScope,

        // <editor-fold desc="GalleryQueryParameters - theLaunchedEffectBlock">
        theLaunchedEffectBlock = {
            Logger.getLogger("rememberGalleryQueryParameters").log(Level.INFO, "rememberGalleryQueryParameters - launchedEffectBlock -  hasScreenBeenInited: [$hasScreenBeenInited]!")
            if (!hasScreenBeenInited) {
                Logger.getLogger("rememberGalleryQueryParameters").log(Level.INFO, "rememberGalleryQueryParameters - launchedEffectBlock - run init!!")
                listDataState.value = ListDataState.Init()
                // [start] revision by elite_lin - 2024/08/07
                // disable auto expansion when entering the Screen for the first time
//                delay(1000)
//                coroutineScope.launch {
//                    bottomSheetScaffoldState.bottomSheetState.expand()
//                }
                // [end] revision by elite_lin - 2024/08/07
                hasScreenBeenInited = true
            }
            else { Logger.getLogger("rememberGalleryQueryParameters").log(Level.INFO, "rememberGalleryQueryParameters - launchedEffectBlock - no action!!") }
        },
        // </editor-fold desc="GalleryQueryParameters - theLaunchedEffectBlock">

        // <editor-fold desc="GalleryQueryParameters - theOnClearButtonClick">
        theOnClearButtonClick = {
            //apiViewModel.clearUhfTagMapLiveData()
            //apiViewModel.galleryListResultLiveData.value = null
            //listDataState.value = ListDataState.Init()
            keywordValue = TextFieldValue(text = initText, selection = TextRange(initText.length))
            galleryCodeValue = TextFieldValue(text = initText, selection = TextRange(initText.length))
            nameValue = TextFieldValue(text = initText, selection = TextRange(initText.length))
        },
        // </editor-fold desc="GalleryQueryParameters - theOnClearButtonClick">

        // <editor-fold desc="GalleryQueryParameters - theOnSearchButtonClick">
        theOnSearchButtonClick = {
            Logger.getLogger("rememberGalleryQueryParameters").log(Level.INFO, "rememberGalleryQueryParameters - onSearchButtonClick - [${keywordValue.text.isEmpty()}]")

            if (keywordValue.text.isEmpty()) {
                coroutineScope.launch {
                    galleryQueryDialogState.value = singleActionDialogState.apply {
                        theMessage = "請輸入全欄位檢索詞!!"
                        onConfirm = {
                            galleryQueryDialogState.value = BuiltInDialogStateImpl()
                        }
                    }
                }
                return@GalleryQueryParameters
            }

            val authData: AuthData? = apiViewModel.theAuthData()
            if (null == authData) {
                Logger.getLogger("rememberGalleryQueryParameters").log(Level.INFO, "rememberGalleryQueryParameters - onSearchButtonClick - authData is null!")
                return@GalleryQueryParameters
            }

            coroutineScope.launch {
                keyboardController?.hide()
                bottomSheetScaffoldState.partialExpand(coroutineScope)
                Logger.getLogger("rememberGalleryQueryParameters").log(Level.INFO, "rememberGalleryQueryParameters - onSearchButtonClick -  authData: [${authData.userCode}, ${authData.userName}]")
                listDataState.value = ListDataState.Loading()
                val newSearchTags = listOf<String>(
                        keywordValue.text,
                        nameValue.text,
                        galleryCodeValue.text,
                    )
                    .filter { tag: String -> tag.isNotEmpty() }

                searchTags.apply {
                    clear()
                    addAll(newSearchTags)
                }

                apiViewModel.runQueryGalleryListTask(
                    queryModel = WtcRfGalleryQueryModel(
                        theKeyword = keywordValue.text,
                        theNameCht = nameValue.text,
                        theCode = galleryCodeValue.text,
                    ),
                    authToken = authData.authToken
                )
            }
        },
        // <editor-fold desc="GalleryQueryParameters - theOnSearchButtonClick">
        galleryQueryDialogState = galleryQueryDialogState,
    )

}


data class GalleryQueryParameters(
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
    override val theNameHint: String = "作品名稱",
    override val theNameSelectAllOnFocus: Boolean = false,
    override val theNameOnFocusChanged: (FocusState) -> Unit = {},
    override val theNameVisibility: Boolean = false,

    override val theClearButtonVisibility: Boolean = false,
    override val theOnClearButtonClick: () -> Unit = {},

    override val theSearchTags: SnapshotStateList<String> = mutableStateListOf(),
    override val theOnSearchButtonClick: () -> Unit = {},

    override val theItemClickCallback: HolderItemClickDelegate<GalleryItemDelegate> = DefaultHolderCellClickHandler<GalleryItemDelegate>(),

    override val theListDataState: State<ListDataState<GalleryItemDelegate>> = mutableStateOf<ListDataState<GalleryItemDelegate>>(ListDataState.None()),

    override val theHasScreenBeenInited: Boolean = false,
    override val theCoroutineScope: CoroutineScope = BuiltInCoroutineScopeImpl(),
    override val theLaunchedEffectBlock: suspend CoroutineScope.() -> Unit = {},

    val galleryQueryDialogState: State<BuiltInDialogStateDelegate> = mutableStateOf<BuiltInDialogStateDelegate>(
        BuiltInDialogStateImpl()
    ),
) : MuseumSearchItemsDelegate,
    IBuiltInBottomSheetListScreenParameters01<GalleryItemDelegate>


fun defaultGalleryQueryUnavailableResult01(
    description: String = "無查詢結果!\n請使用其它查詢設定，\n重新進行查詢。",
    action: String = "Unavailable"
) = ListDataState.Unavailable<GalleryItemDelegate>(
        theList = listOf(
            GalleryItemImpl(
                theIdentifier = generateRandomStringViaUuid(),
                theCellType = BaseCellTypes.UNAVAILABLE,
                theDescription = description,
                theAction = action
            )
        )
    )





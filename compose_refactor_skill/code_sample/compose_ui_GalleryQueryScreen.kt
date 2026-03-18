

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MuseumGalleryQueryScreen(
    navController: NavHostController,
    galleryQueryParameters: GalleryQueryParameterDelegate = BuiltInGalleryQueryParameter01(),
    fontSize: TextUnit = 16.sp,
    chipColors: ChipColors = theBuiltInChipColors01(containerColor = Color.White),
    containerPaddingValues: PaddingValues = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
    containerBackground: Color = Grey94,
    tag: String = "MuseumGalleryQuery",
) {
    Logger.getLogger("MuseumGalleryQueryScreen").log(Level.INFO, "MuseumGalleryQueryScreen - time: [${System.currentTimeMillis()}]")

    val bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    BuiltInBottomSheetListScreen01(
        inputParameters = BuiltInBottomSheetListScreenParameters01<GalleryItemDelegate>(
            theNavController = navController,
            theRememberedParameters = galleryQueryParameters,
            theBottomSheetContentBlock = {
                MuseumGalleryQueryScreenBottomSheetContent(
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
            theListDataInitText = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_query_caption),
            theTag = tag,
        ),
        bottomSheetScaffoldState = bottomSheetScaffoldState,
    )

    BuiltInDialogSet01(
        dialogState = galleryQueryParameters.theGalleryQueryDialogState.value
    )
}


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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <Parameters> rememberGalleryQueryScreenParameters(
    navController: NavHostController,
    apiViewModel: MuseumApiModelDelegate,
    uiUtility: MuseumUiUtilityDelegate,
    rfidConfig: RfidDeviceConfigDelegate,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    parametersFactory: (
        // 1-4
        theKeywordValue: TextFieldValue,
        theKeywordOnValueChange: (TextFieldValue) -> Unit,
        theKeywordHint: String,
        theKeywordOnFocusChanged: (FocusState) -> Unit,
        // 5-9
        theGalleryCodeValue: TextFieldValue,
        theGalleryCodeOnValueChange: (TextFieldValue) -> Unit,
        theGalleryCodeHint: String,
        theGalleryCodeOnFocusChanged: (FocusState) -> Unit,
        theGalleryCodeVisibility: Boolean,
        // 10-14
        theNameValue: TextFieldValue,
        theNameOnValueChange: (TextFieldValue) -> Unit,
        theNameHint: String,
        theNameOnFocusChanged: (FocusState) -> Unit,
        theNameVisibility: Boolean,
        // 15-16
        theClearButtonVisibility: Boolean,
        theOnClearButtonClick: () -> Unit,
        // 17-18
        theSearchTags: SnapshotStateList<String>,
        theOnSearchButtonClick: () -> Unit,
        // 19
        theItemClickCallback: HolderItemClickDelegate<GalleryItemDelegate>,
        // 20
        theListDataState: State<ListDataState<GalleryItemDelegate>>,
        // 21-23
        theHasScreenBeenInited: Boolean,
        theCoroutineScope: CoroutineScope,
        theLaunchedEffectBlock: suspend CoroutineScope.() -> Unit,
        //14
        theGalleryQueryDialogState: State<BuiltInDialogStateDelegate>,
    ) -> Parameters
): Parameters where Parameters : GalleryQueryParameterDelegate {

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

    val remindTitle = stringResource(tw.com.wtc.mis.android.compose.R.string.base_message_remind_title)
    val confirmTitle = stringResource(tw.com.wtc.mis.android.compose.R.string.base_action_confirm)
    val cancelTitle = stringResource(tw.com.wtc.mis.android.compose.R.string.base_action_cancel)

    val beingProcessing = stringResource(tw.com.wtc.mis.android.compose.R.string.base_being_processing)
    val queryUnavailableResult = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_query_unavailable_result)
    val pleaseInputSearchKeyword = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_please_input_search_keyword)

    // <editor-fold desc="progressDialogState">
    val progressDialogState = BuiltInDialogStateImpl(
        theDialogType = DialogType.Progress,
        theDialogState = true,
        theMessage = beingProcessing,
        onDismiss = { galleryQueryDialogState.value = BuiltInDialogStateImpl() }
    )
    // </editor-fold desc="progressDialogState">

    // <editor-fold desc="singleActionDialogState">
    val singleActionDialogState = MutableBuiltInDialogStateImpl(
        theDialogType = DialogType.SingleAction,
        theDialogState = true,
        theTitle = remindTitle,
        theConfirmTitle = confirmTitle,
        theCancelTitle = cancelTitle,
        onDismiss = { galleryQueryDialogState.value = BuiltInDialogStateImpl() }
    )
    // </editor-fold desc="singleActionDialogState">

    // [start] added by elite_lin - 2025/12/11
//    val c72RfidConfig: RfidDeviceConfigDelegate = remember(c72RfidViewModel) { c72RfidViewModel.toRfidConfigExt() }
//    val r36RfidConfig: RfidDeviceConfigDelegate = remember(r36RfidViewModel) { r36RfidViewModel.toRfidConfigExt() }

//    val rfidConfig = remember(deviceType, c72RfidViewModel, r36RfidViewModel) {
//        when (deviceType) {
//            DeviceType.R36 -> r36ViewModel?.toRfidConfig()
//            DeviceType.R50 -> r50ViewModel?.toRfidConfig()
//            DeviceType.R60 -> r60ViewModel?.toRfidConfig()
//        } ?: error("No ViewModel provided for device type: $deviceType")
//    }
    // [end] added by elite_lin - 2025/12/11

    // <editor-fold desc="QueryGalleryListResultStateFlowCollector">
    val queryGalleryListResultX: FmApiResult<List<GalleryItemDelegate>>? by apiViewModel.queryGalleryListResultStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(queryGalleryListResultX) {
        val queryGalleryListResult = queryGalleryListResultX ?: return@LaunchedEffect

        listDataState.value = when(queryGalleryListResult) {
            is FmApiResult.Success -> {
                Logger.getLogger("rememberGalleryQueryScreenParameters").log(Level.INFO, "rememberGalleryQueryScreenParameters - QueryGalleryListResultStateFlowCollector: [Success]")
                val resultList: List<GalleryItemDelegate> = queryGalleryListResult.data
                if (resultList.isNotEmpty()) {
                    apiViewModel.stringUhfTagDataExtDelegate.clearUhfTagMap(scope = apiViewModel, usageScenario = UsageScenarios.GALLERY_QUERY)
                    ListDataState.Available<GalleryItemDelegate>(theList = queryGalleryListResult.data)
                }
                else {
                    defaultGalleryQueryUnavailableResult01(description = queryUnavailableResult)
                }
            }
            is FmApiResult.Error -> {
                Logger.getLogger("rememberGalleryQueryScreenParameters").log(Level.SEVERE, "rememberGalleryQueryScreenParameters - QueryGalleryListResultStateFlowCollector： .Error]", queryGalleryListResult.cause)

                apiViewModel.accountReAuthIfNeeded(scope = apiViewModel, cause = queryGalleryListResult.cause)

                defaultGalleryQueryUnavailableResult01(description = queryUnavailableResult)
            }
        }
    }
    // </editor-fold desc="QueryGalleryListResultStateFlowCollector">

    // <editor-fold desc="StringUhfGalleryListStateFlowCollector">
    val uhfGalleryListX: List<GalleryItemDelegate>? by apiViewModel.stringUhfTagDataExtDelegate.uhfGalleryListStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(uhfGalleryListX) {
        val uhfGalleryList = uhfGalleryListX ?: return@LaunchedEffect

        Logger.getLogger("rememberGalleryQueryScreenParameters").log(Level.INFO, "rememberGalleryQueryScreenParameters() - stringUhfTagDataExtDelegate.uhfGalleryList StateFlowCollector")
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
    // </editor-fold desc="StringUhfGalleryListStateFlowCollector">

    // <editor-fold desc="UhfTagReadResult StateFlowCollector">
    val uhfTagX: RfidUhfTagDelegate? by rfidConfig.uhfTagReadResultStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(uhfTagX) {
        val uhfTag = uhfTagX ?: return@LaunchedEffect

        val parsedTag = rfidConfig.parseGalleryTagNo(uhfTag)
        apiViewModel.stringUhfTagDataExtDelegate.putItemIntoUhfTagMap(scope = apiViewModel, key = parsedTag, value = parsedTag, usageScenario = UsageScenarios.GALLERY_QUERY)
        rfidConfig.resetUhfTagReadResultStateFlow()
    }
    // </editor-fold desc="UhfTagReadResult StateFlowCollector">

    // <editor-fold desc="BarcodeReadResult StateFlowCollector">
    val barcodeX: BarcodeEntityDelegate? by rfidConfig.barcodeReadResultStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(barcodeX) {
        val barcodeResult = barcodeX ?: return@LaunchedEffect

        val readBarCode = rfidConfig.getBarcodeData(barcodeResult)
        apiViewModel.stringUhfTagDataExtDelegate.putItemIntoUhfTagMap(scope = apiViewModel, key = readBarCode, value = readBarCode, usageScenario = UsageScenarios.GALLERY_QUERY)
        rfidConfig.resetBarcodeReadResultStateFlow()
    }
    // </editor-fold desc="BarcodeReadResult StateFlowCollector">

    return parametersFactory(
        // <editor-fold desc="GalleryQueryParameters - Keyword">
        keywordValue,
        { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, keywordFocusIndicator)
//            keywordFocusIndicator = pair.second
//            keywordValue = pair.first
            keywordValue = newValue
            Logger.getLogger("rememberGalleryQueryScreenParameters").log(Level.INFO, "rememberGalleryQueryScreenParameters() - onValueChange() - keywordFocusIndicator: [$keywordFocusIndicator], keywordValue： [${keywordValue.text}]")
        },
        stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_search_keyword),
        {
            val pair: Pair<TextFieldValue, Int> = keywordValue.builtInTextFieldFocusChangedHandler01(true, it)
            keywordFocusIndicator = pair.second
            keywordValue = pair.first
            Logger.getLogger("rememberGalleryQueryScreenParameters").log(Level.INFO, "rememberGalleryQueryScreenParameters() - onFocusChanged() - keywordFocusIndicator: [$keywordFocusIndicator], keywordValue： [${keywordValue.text}]")
        },
        // </editor-fold desc="GalleryQueryParameters - Keyword">

        // <editor-fold desc="GalleryQueryParameters - GalleryCode">
        galleryCodeValue,
        { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, galleryCodeFocusIndicator)
//            galleryCodeFocusIndicator = pair.second
//            galleryCodeValue = pair.first
            galleryCodeValue = newValue
            Logger.getLogger("rememberGalleryQueryScreenParameters").log(Level.INFO, "rememberGalleryQueryScreenParameters() - onValueChange() - galleryCodeFocusIndicator: [$galleryCodeFocusIndicator], galleryCodeValue： [${galleryCodeValue.text}]")
        },
        stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_gallery_code),
        {
            val pair: Pair<TextFieldValue, Int> = galleryCodeValue.builtInTextFieldFocusChangedHandler01(true, it)
            galleryCodeFocusIndicator = pair.second
            galleryCodeValue = pair.first
            Logger.getLogger("rememberGalleryQueryScreenParameters").log(Level.INFO, "rememberGalleryQueryScreenParameters() - onFocusChanged() - galleryCodeFocusIndicator: [$galleryCodeFocusIndicator], galleryCodeValue： [${galleryCodeValue.text}]")
        },
        false,
        // </editor-fold desc="GalleryQueryParameters - GalleryCode">

        // <editor-fold desc="GalleryQueryParameters - Name">
        nameValue,
        { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, nameFocusIndicator)
//            nameFocusIndicator = pair.second
//            nameValue = pair.first
            nameValue = newValue
            Logger.getLogger("rememberGalleryQueryScreenParameters").log(Level.INFO, "rememberGalleryQueryScreenParameters() - onValueChange() - nameFocusIndicator: [$nameFocusIndicator], nameValue： [${nameValue.text}]")
        },
        stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_gallery_name),
         {
            val pair: Pair<TextFieldValue, Int> = nameValue.builtInTextFieldFocusChangedHandler01(true, it)
            nameFocusIndicator = pair.second
            nameValue = pair.first
            Logger.getLogger("rememberGalleryQueryScreenParameters").log(Level.INFO, "rememberGalleryQueryScreenParameters() - onFocusChanged() - nameFocusIndicator: [$nameFocusIndicator], nameValue： [${nameValue.text}]")
        },
        false,
        // </editor-fold desc="GalleryQueryParameters - Name">

        // <editor-fold desc="GalleryQueryParameters - theOnClearButtonClick">
        false,
        {
            //apiViewModel.clearUhfTagMapLiveData()
            //apiViewModel.galleryListResultLiveData.value = null
            //listDataState.value = ListDataState.Init()
            keywordValue = TextFieldValue(text = initText, selection = TextRange(initText.length))
            galleryCodeValue = TextFieldValue(text = initText, selection = TextRange(initText.length))
            nameValue = TextFieldValue(text = initText, selection = TextRange(initText.length))
        },
        // </editor-fold desc="GalleryQueryParameters - theOnClearButtonClick">
        searchTags,
        // <editor-fold desc="GalleryQueryParameters - theOnSearchButtonClick">
        {
            Logger.getLogger("rememberGalleryQueryScreenParameters").log(Level.INFO, "rememberGalleryQueryScreenParameters - onSearchButtonClick - [${keywordValue.text.isEmpty()}]")

            if (keywordValue.text.isEmpty()) {
                coroutineScope.launch {
                    galleryQueryDialogState.value = singleActionDialogState.apply {
                        theMessage = pleaseInputSearchKeyword
                        onConfirm = {
                            galleryQueryDialogState.value = BuiltInDialogStateImpl()
                        }
                    }
                }
                return@parametersFactory
            }

            val authData: AuthData? = apiViewModel.theAuthData()
            if (null == authData) {
                Logger.getLogger("rememberGalleryQueryScreenParameters").log(Level.INFO, "rememberGalleryQueryScreenParameters - onSearchButtonClick - authData is null!")
                return@parametersFactory
            }

            coroutineScope.launch {
                keyboardController?.hide()
                bottomSheetScaffoldState.partialExpand(coroutineScope)
                Logger.getLogger("rememberGalleryQueryScreenParameters").log(Level.INFO, "rememberGalleryQueryScreenParameters - onSearchButtonClick -  authData: [${authData.userCode}, ${authData.userName}]")
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

                apiViewModel.callGalleryListApi(
                    scope = apiViewModel,
                    apiDomain = uiUtility.getApiDomain(),
                    authToken = authData.authToken,
                    queryModel = uiUtility.buildGalleryQueryModel(
                        keyword = keywordValue.text,
                        nameCht = nameValue.text,
                        galleryCode = galleryCodeValue.text,
                    ),
                )
            }
        },
        // <editor-fold desc="GalleryQueryParameters - theItemClickCallback">
        HolderItemClickDelegate<GalleryItemDelegate> { dataObject: GalleryItemDelegate, action: String, position: Int ->
            Logger.getLogger("rememberGalleryQueryScreenParameters").log(Level.INFO, "rememberGalleryQueryScreenParameters - itemClickCallback - delegate: [${dataObject.theDescription}], action: [$action], position: [$position]")
            coroutineScope.launch {
                when(action) {
                    BaseActions.REMOVE -> {
                        if (dataObject is DeviceGalleryItemDelegate) {
                            apiViewModel.stringUhfTagDataExtDelegate.removeItemFromUhfTagMap(scope = apiViewModel, key = dataObject.theIdentifier, usageScenario = UsageScenarios.GALLERY_QUERY)
                        }
                    }
                    BaseActions.TAP -> {
                        val route: String = uiUtility.navigateToGalleryDetailsScreen(dataObject.theDataObject)
                        navController.navigateToExt(route)
                    }
                }
            }
        },
        // </editor-fold desc="GalleryQueryParameters - theItemClickCallback">
        listDataState,
        hasScreenBeenInited,
        coroutineScope,

        // <editor-fold desc="GalleryQueryParameters - theLaunchedEffectBlock">
        {
            Logger.getLogger("rememberGalleryQueryScreenParameters").log(Level.INFO, "rememberGalleryQueryScreenParameters - launchedEffectBlock -  hasScreenBeenInited: [$hasScreenBeenInited]!")
            if (!hasScreenBeenInited) {
                Logger.getLogger("rememberGalleryQueryScreenParameters").log(Level.INFO, "rememberGalleryQueryScreenParameters - launchedEffectBlock - run init!!")
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
            else { Logger.getLogger("rememberGalleryQueryScreenParameters").log(Level.INFO, "rememberGalleryQueryScreenParameters - launchedEffectBlock - no action!!") }
        },
        // </editor-fold desc="GalleryQueryParameters - theLaunchedEffectBlock">

        // <editor-fold desc="GalleryQueryParameters - theOnSearchButtonClick">
        galleryQueryDialogState,
    )

}

///

interface GalleryQueryParameterDelegate :
    MuseumSearchItemsDelegate,
    IBuiltInBottomSheetListScreenParameters01<GalleryItemDelegate>
{
    val theGalleryQueryDialogState: State<BuiltInDialogStateDelegate>
}


data class BuiltInGalleryQueryParameter01(
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

    override val theGalleryQueryDialogState: State<BuiltInDialogStateDelegate> = mutableStateOf<BuiltInDialogStateDelegate>(
        BuiltInDialogStateImpl()
    ),
) : GalleryQueryParameterDelegate



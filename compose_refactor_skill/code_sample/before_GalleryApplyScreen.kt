


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WtcGalleryApplyScreen(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
    fontSize: TextUnit = 16.sp,
    chipColors: ChipColors = theBuiltInChipColors01(containerColor = Color.White),
    containerPaddingValues: PaddingValues = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
    containerBackground: Color = Grey94,
) {
    Logger.getLogger("WtcGalleryApplyScreen").log(Level.INFO, "WtcGalleryApplyScreen - time: [${System.currentTimeMillis()}]")

    val context = LocalContext.current

    val bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    val galleryApplyQueryParameters: GalleryApplyQueryParameters = rememberGalleryApplyQueryParameters(
        navController = navController,
        apiViewModel = apiViewModel,
        bottomSheetScaffoldState = bottomSheetScaffoldState
    )

    BuiltInBottomSheetListScreen01(
        inputParameters = BuiltInBottomSheetListScreenParameters01<GalleryApplyItemDelegate>(
            theNavController = navController,
            theRememberedParameters = galleryApplyQueryParameters,
            theBottomSheetContentBlock = {
                //WtcGalleryApplyScreenBottomSheetContent(
                WtcGalleryQueryScreenBottomSheetContent(
                    theParameters = galleryApplyQueryParameters,
                )
            },
            theAvailableListContent = {
                val itemList: List<GalleryApplyItemDelegate> = galleryApplyQueryParameters.theListDataState.value.theList
                items(
                    items = ImmutableObjectList<GalleryApplyItemDelegate>(itemList).objectList,
                    key = { galleryApplyItem: GalleryApplyItemDelegate -> galleryApplyItem.theIdentifier },
                ) { galleryApplyItem: GalleryApplyItemDelegate ->
                    GalleryApplyListItem01(
                        galleryApply = galleryApplyItem,
                        itemClickCallback = galleryApplyQueryParameters.theItemClickCallback,
                    )
                }
            },
            theUnavailableContent = {
                val itemList: List<GalleryApplyItemDelegate> = galleryApplyQueryParameters.theListDataState.value.theList
                val unavailableItem: GalleryApplyItemDelegate? = if (itemList.isNotEmpty()) { itemList[0] } else { null }
                ListDataUnavailable01(
                    unavailableText = unavailableItem?.theDescription ?: "",
                    onClick = {
                        if (null != unavailableItem) {
                            galleryApplyQueryParameters.theItemClickCallback.onHolderItemClicked(unavailableItem, BaseActions.REFRESH, -1)
                        }
                    },
                )
            },
            theHeaderContent = {
                val suggestionChipGroupParameter = BuiltInSuggestionChipGroup01Parameter(
                    items = ImmutableObjectList(galleryApplyQueryParameters.theSearchTags),
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
            theTopAppBarTitle = stringResource(R.string.menu_in_out_bound),
            theListDataInitText = "請設定查詢參數來進行查詢!",
            theTag = "WtcGalleryApply",
        ),
        bottomSheetScaffoldState = bottomSheetScaffoldState,
    )
}


@Preview
@Composable
fun WtcGalleryApplyScreenBottomSheetContent(
    theParameters: GalleryApplyQueryParameters = GalleryApplyQueryParameters(),
) {
    Logger.getLogger("WtcGalleryApplyScreenBottomSheetContent").log(Level.INFO, "WtcGalleryApplyScreenBottomSheetContent - time: [${System.currentTimeMillis()}]")
    
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
                text = stringResource(tw.com.wtc.rfid.R.string.b_query),
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
fun rememberGalleryApplyQueryParameters(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
): GalleryApplyQueryParameters {

    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    // <editor-fold desc="TextFieldValues">
    val initText = ""
    var keywordValue: TextFieldValue by rememberTextFieldValueFrom(initText)
    var galleryCodeValue: TextFieldValue by rememberTextFieldValueFrom(initText)
    var nameValue: TextFieldValue by rememberTextFieldValueFrom(initText)
    var optionValue: TextFieldValue by rememberTextFieldValueFrom(initText)
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
    val listDataState: MutableState<ListDataState<GalleryApplyItemDelegate>> = remember {
        mutableStateOf(ListDataState.None())
    }
    // </editor-fold desc="listDataState">

    var hasScreenBeenInited: Boolean by rememberSaveable { mutableStateOf(false) }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    // <editor-fold desc="QueryGalleryApplyListResultStateFlowCollector">
    apiViewModel.queryGalleryApplyListResultStateFlow.NullableCollectorExt { queryGalleryApplyListResult: FmApiResult<List<GalleryApplyItemDelegate>> ->
        coroutineScope.launch {
            listDataState.value = when(queryGalleryApplyListResult) {
                is FmApiResult.Success -> {
                    Logger.getLogger("rememberGalleryApplyQueryParameters").log(Level.INFO, "rememberGalleryApplyQueryParameters - QueryGalleryApplyListResultStateFlowCollector: [Success]")
                    val resultList: List<GalleryApplyItemDelegate> = queryGalleryApplyListResult.data
                    if (resultList.isNotEmpty()) {
                        ListDataState.Available<GalleryApplyItemDelegate>(theList = queryGalleryApplyListResult.data)
                    }
                    else { defaultGalleryApplyQueryUnavailableResult01() }
                }
                is FmApiResult.Error -> {
                    Logger.getLogger("rememberGalleryApplyQueryParameters").log(Level.SEVERE, "rememberGalleryApplyQueryParameters - QueryGalleryApplyListResultStateFlowCollector： .Error]", queryGalleryApplyListResult.cause)
                    apiViewModel.accountReAuthIfNeeded(scope = apiViewModel, cause = queryGalleryApplyListResult.cause)
                    defaultGalleryApplyQueryUnavailableResult01()
                }
            }
        }
    }
    // </editor-fold desc="QueryGalleryApplyListResultStateFlowCollector">

    return GalleryApplyQueryParameters(
        // <editor-fold desc="GalleryApplyQueryParameters - Keyword">
        theKeywordValue = keywordValue,
        theKeywordOnValueChange = { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, keywordFocusIndicator)
//            keywordFocusIndicator = pair.second
//            keywordValue = pair.first
            keywordValue = newValue
            Logger.getLogger("rememberGalleryApplyQueryParameters").log(Level.INFO, "rememberGalleryApplyQueryParameters() - onValueChange() - keywordFocusIndicator: [$keywordFocusIndicator], keywordValue： [${keywordValue.text}]")
        },
        theKeywordOnFocusChanged = {
            val pair: Pair<TextFieldValue, Int> = keywordValue.builtInTextFieldFocusChangedHandler01(true, it)
            keywordFocusIndicator = pair.second
            keywordValue = pair.first
            Logger.getLogger("rememberGalleryApplyQueryParameters").log(Level.INFO, "rememberGalleryApplyQueryParameters() - onFocusChanged() - keywordFocusIndicator: [$keywordFocusIndicator], keywordValue： [${keywordValue.text}]")
        },
        // </editor-fold desc="GalleryApplyQueryParameters - Keyword">

        // <editor-fold desc="GalleryApplyQueryParameters - GalleryCode">
        theGalleryCodeValue = galleryCodeValue,
        theGalleryCodeOnValueChange = { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, galleryCodeFocusIndicator)
//            galleryCodeFocusIndicator = pair.second
//            galleryCodeValue = pair.first
            galleryCodeValue = newValue
            Logger.getLogger("rememberGalleryApplyQueryParameters").log(Level.INFO, "rememberGalleryApplyQueryParameters() - onValueChange() - galleryCodeFocusIndicator: [$galleryCodeFocusIndicator], galleryCodeValue： [${galleryCodeValue.text}]")
        },
        theGalleryCodeOnFocusChanged = {
            val pair: Pair<TextFieldValue, Int> = galleryCodeValue.builtInTextFieldFocusChangedHandler01(true, it)
            galleryCodeFocusIndicator = pair.second
            galleryCodeValue = pair.first
            Logger.getLogger("rememberGalleryApplyQueryParameters").log(Level.INFO, "rememberGalleryApplyQueryParameters() - onFocusChanged() - galleryCodeFocusIndicator: [$galleryCodeFocusIndicator], galleryCodeValue： [${galleryCodeValue.text}]")
        },
        // </editor-fold desc="GalleryApplyQueryParameters - GalleryCode">

        // <editor-fold desc="GalleryApplyQueryParameters - Name">
        theNameValue = nameValue,
        theNameOnValueChange = { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, nameFocusIndicator)
//            nameFocusIndicator = pair.second
//            nameValue = pair.first
            nameValue = newValue
            Logger.getLogger("rememberGalleryApplyQueryParameters").log(Level.INFO, "rememberGalleryApplyQueryParameters() - onValueChange() - nameFocusIndicator: [$nameFocusIndicator], nameValue： [${nameValue.text}]")
        },
        theNameOnFocusChanged = {
            val pair: Pair<TextFieldValue, Int> = nameValue.builtInTextFieldFocusChangedHandler01(true, it)
            nameFocusIndicator = pair.second
            nameValue = pair.first
            Logger.getLogger("rememberGalleryApplyQueryParameters").log(Level.INFO, "rememberGalleryApplyQueryParameters() - onFocusChanged() - nameFocusIndicator: [$nameFocusIndicator], nameValue： [${nameValue.text}]")
        },
        // </editor-fold desc="GalleryApplyQueryParameters - Name">

        // <editor-fold desc="GalleryApplyQueryParameters - option">
        optionValue = optionValue,
        optionOnValueChange = { newValue: TextFieldValue -> optionValue = newValue },
        // </editor-fold desc="GalleryApplyQueryParameters - option">

        // <editor-fold desc="GalleryApplyQueryParameters - theItemClickCallback">
        theItemClickCallback = HolderItemClickDelegate<GalleryApplyItemDelegate> { dataObject: GalleryApplyItemDelegate, action: String, position: Int ->
            Logger.getLogger("rememberGalleryApplyQueryParameters").log(Level.INFO, "rememberGalleryApplyQueryParameters - itemClickCallback - delegate: [${dataObject.theDescription}], action: [$action], position: [$position]")
            coroutineScope.launch {
                apiViewModel.apply {
                    selectedGalleryApply = dataObject.theDataObject
                    galleryApplyDetailListResultStateFlow.value = null
                    galleryApplyDetailSelectedTabIndexStateFlow.value = null
                    galleryApplyDetailTabListStateFlow.value = null
                    galleryApplyDetailListStateFlow.value = null
                    galleryApplyDetailUpdateBatchResultStateFlow.value = null
                    clearCachedGalleryApplyDetailList()     // added by elite_lin - 2025/04/25
                    clearGalleryApplyDetailMaps()
                }
                navController.navigateToExt(WtcRouteDestination.ApplyDetails.theRoute)
            }
        },
        // </editor-fold desc="GalleryApplyQueryParameters - theItemClickCallback">

        theSearchTags = searchTags,
        theListDataState = listDataState,

        theHasScreenBeenInited = hasScreenBeenInited,
        theCoroutineScope = coroutineScope,
        // <editor-fold desc="GalleryApplyQueryParameters - theLaunchedEffectBlock">
        theLaunchedEffectBlock = {
            Logger.getLogger("rememberGalleryApplyQueryParameters").log(Level.INFO, "rememberGalleryApplyQueryParameters - launchedEffectBlock -  hasScreenBeenInited: [$hasScreenBeenInited]!")
            if (!hasScreenBeenInited) {
                Logger.getLogger("rememberGalleryApplyQueryParameters").log(Level.INFO, "rememberGalleryApplyQueryParameters - launchedEffectBlock - run init!!")
                listDataState.value = ListDataState.Init()
                //apiViewModel.galleryApplyListResultLiveData.value = null
                apiViewModel.galleryApplyListResultStateFlow.value = null
                delay(1000)
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                }
                hasScreenBeenInited = true
            }
            else { Logger.getLogger("rememberGalleryApplyQueryParameters").log(Level.INFO, "rememberGalleryApplyQueryParameters - launchedEffectBlock - no action!!") }
        },
        // </editor-fold desc="GalleryApplyQueryParameters - theLaunchedEffectBlock">

        // <editor-fold desc="GalleryApplyQueryParameters - theOnSearchButtonClick">
        theOnSearchButtonClick = {
            val authData: AuthData? = apiViewModel.theAuthData()
            if (null == authData) {
                Logger.getLogger("rememberGallerySearchParameters").log(Level.INFO, "rememberGallerySearchParameters - onSearchButtonClick - authData is null!")
                return@GalleryApplyQueryParameters
            }

            coroutineScope.launch {
                keyboardController?.hide()
                bottomSheetScaffoldState.dragIconClickerExt01(coroutineScope)

                Logger.getLogger("rememberGalleryApplyQueryParameters").log(Level.INFO, "rememberGalleryApplyQueryParameters - onSearchButtonClick -  authData: [${authData.userCode}, ${authData.userName}]")
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

                val dateEndStr = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Calendar.getInstance().time)
                val startCalendar = Calendar.getInstance().apply {
                    this.add(Calendar.YEAR,-3)
                }
                val dateStartStr = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(startCalendar.time)

                val queryModel = if (keywordValue.text.isNotEmpty()) {
                    WtcRfGalleryApplyQueryModel(keyword = keywordValue.text,)
                }
                else {
                    WtcRfGalleryApplyQueryModel(
                        //applyDateStart = "2023/01/01"
                        applyDateStart = dateStartStr, applyDateEnd = dateEndStr,
                    )
                }

                apiViewModel.runQueryGalleryApplyListTask(
                    queryModel = queryModel, authToken = authData.authToken
                )
            }
        },
        // </editor-fold desc="GalleryApplyQueryParameters - theOnSearchButtonClick">
    )
}


data class GalleryApplyQueryParameters(
    override val theKeywordValue: TextFieldValue = TextFieldValue(),
    override val theKeywordOnValueChange: (TextFieldValue) -> Unit = {},
    override val theKeywordHint: String = "全欄位檢索詞",
    override val theKeywordSelectAllOnFocus: Boolean = true,
    override val theKeywordOnFocusChanged: (FocusState) -> Unit = {},

    override val theGalleryCodeValue: TextFieldValue = TextFieldValue(),
    override val theGalleryCodeOnValueChange: (TextFieldValue) -> Unit = {},
    override val theGalleryCodeHint: String = "登錄號",
    override val theGalleryCodeSelectAllOnFocus: Boolean = true,
    override val theGalleryCodeOnFocusChanged: (FocusState) -> Unit = {},
    override val theGalleryCodeVisibility: Boolean = false,

    override val theNameValue: TextFieldValue = TextFieldValue(),
    override val theNameOnValueChange: (TextFieldValue) -> Unit = {},
    override val theNameHint: String = "名稱",
    override val theNameSelectAllOnFocus: Boolean = true,
    override val theNameOnFocusChanged: (FocusState) -> Unit = {},
    override val theNameVisibility: Boolean = false,

    override val theClearButtonVisibility: Boolean = false,
    override val theOnClearButtonClick: () -> Unit = {},

    val optionValue: TextFieldValue = TextFieldValue(),
    val optionOnValueChange: (TextFieldValue) -> Unit = {},
    val optionHint: String = "選項",

    override val theItemClickCallback: HolderItemClickDelegate<GalleryApplyItemDelegate> = DefaultHolderCellClickHandler<GalleryApplyItemDelegate>(),

    override val theSearchTags: SnapshotStateList<String> = mutableStateListOf(),
    override val theOnSearchButtonClick: () -> Unit = {},

    override val theListDataState: State<ListDataState<GalleryApplyItemDelegate>> = mutableStateOf<ListDataState<GalleryApplyItemDelegate>>(ListDataState.None()),

    override val theHasScreenBeenInited: Boolean = false,
    override val theCoroutineScope: CoroutineScope = BuiltInCoroutineScopeImpl(),
    override val theLaunchedEffectBlock: suspend CoroutineScope.() -> Unit = {},
) : MuseumSearchItemsDelegate,
    IBuiltInBottomSheetListScreenParameters01<GalleryApplyItemDelegate>


fun defaultGalleryApplyQueryUnavailableResult01(
    description: String = "無查詢結果!\n請使用其它查詢設定，\n重新進行查詢。",
    action: String = "Unavailable"
) = ListDataState.Unavailable<GalleryApplyItemDelegate>(
    theList = listOf(
        GalleryApplyItemImpl(
            theIdentifier = generateRandomStringViaUuid(),
            theCellType = BaseCellTypes.UNAVAILABLE,
            theDescription = description,
            theAction = action
        )
    )
)


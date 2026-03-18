

@Composable
fun WtcGalleryDetailScreen(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
) {
    Logger.getLogger("fun WtcGalleryDetailScreen(\n").log(Level.INFO, "fun WtcGalleryDetailScreen(\n")
    
    val galleryDetailParameters: GalleryDetailParameters = rememberGalleryDetailParameters(apiViewModel= apiViewModel)

    BuiltInTopAppBarTabBodyScreen01(
        inputParameters = BuiltInTopAppBarTabBodyScreenParameters01(
            theNavController = navController,
            theRememberedParameters = galleryDetailParameters,
            theTopAppBarTitle = stringResource(R.string.nav_header_CollectionDetail),
            theTag = "WtcGalleryDetail",
        ),
    ) {
        when(galleryDetailParameters.theSelectedTabIndex) {
            0 -> { WtcGalleryDetailTabContent(theParameters = galleryDetailParameters) }
            1 -> { WtcGalleryReadLogTabContent(theParameters = galleryDetailParameters) }
            else -> {
                TextCenterScreenContent(galleryDetailParameters.theTabItems[galleryDetailParameters.theSelectedTabIndex].theDescription)
            }
        }

    }

    BuiltInDialogSet01(
        dialogState = galleryDetailParameters.galleryDetailDialogState.value
    )

    WtcStoreRoomPickerDialog01(
        dialogState = galleryDetailParameters.theStoreRoomPickerDialogState.value,
        theParameters = galleryDetailParameters
    )
}


@Preview
@Composable
fun WtcGalleryDetailTabContent(
    theParameters: GalleryDetailParameters = GalleryDetailParameters(),
) {
    Logger.getLogger("WtcGalleryDetailTabContent").log(Level.INFO, "WtcGalleryDetailTabContent - [${theParameters.galleryItem}]")

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp),
    ) {

        val (galleryDetailRef, bottomRowRef) = createRefs()

        Column(
            modifier = Modifier
                .verticalScroll(theParameters.scrollState)
                .constrainAs(galleryDetailRef) {
                    linkTo(start = parent.start, end = parent.end)
                    linkTo(top = parent.top, bottom = bottomRowRef.top, topMargin = 10.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .padding(horizontal = 10.dp),
        ) {
            // <editor-fold desc="Banner Image">
            Box( // Child
                modifier = Modifier
                    //.aspectRatio(ratio = 16f/9f)
                    .aspectRatioReference(
                        ratioWidth = 16f,
                        ratioHeight = 9f,
                        AspectRatioReference.MIN_PARENT_WIDTH_PARENT_HEIGHT
                    )
                    .background(Grey94),
                //.align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(theParameters.galleryItem.imageUrl)
                        .crossfade(durationMillis = 300)
                        .build(),
                    // java.lang.IllegalArgumentException: Only VectorDrawables and rasterized asset types are supported ex. PNG, JPG, WEBP
                    //cannot use xml drawable!!
                    //placeholder = painterResource(R.drawable.logo2),
                    contentDescription = theParameters.galleryItem.theNameCht,      // revision by elite_lin - 2025/12/08
                    contentScale = ContentScale.Crop, // added by elite_lin - 2025/06/16
                    //modifier = Modifier.clip(CircleShape)
                    modifier = Modifier.fillMaxSize(),
                    // [start] debug by elite_lin - 2025/06/16
                    onError = { result: AsyncImagePainter.State.Error ->
                        Logger.getLogger("WtcGalleryDetailTabContent").log(Level.SEVERE, "WtcGalleryDetailTabContent - Coil3 error: [${result.result.throwable.message}]")
                    },
                    // [end] debug by elite_lin - 2025/06/16
                )
            }
            // </editor-fold desc="Banner Image">

            // [start] added by elite_lin - 2025/04/17
            Row(
                modifier = Modifier
                    .padding(all = 10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // <editor-fold desc="在庫/出庫">
                val (statusText, statusColor, statusIcon) = if (theParameters.galleryItem.theStatus == "I")    // revision by elite_lin - 2025/12/08
                    { Triple<String, Color, ImageVector>("在庫", Green002, Icons.Filled.CheckCircle) }
                    else { Triple<String, Color, ImageVector>("出庫", Pink001, Icons.Filled.RemoveCircle) }

                BuiltInCircleIcon01(
                    modifier = createBuiltInCircleIcon01Modifier02(
                        circleColor = Color.Transparent,
                        size = 36.dp
                    ),
                    size = 36.dp,
                    iconImageVector = statusIcon,
                    iconTint = statusColor
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = statusText,
                    fontSize = 20.sp,
                    color = statusColor,
                )
                // </editor-fold desc="在庫/出庫">

                Spacer(modifier = Modifier.width(30.dp))

                // <editor-fold desc="在位/離位">

                val (onSiteText, onSiteColor, onSiteIcon) = if (theParameters.galleryItem.onSite == "Y")
                    { Triple<String, Color, ImageVector>("在位", Green002, Icons.Filled.RadioButtonChecked) }
                    else { Triple<String, Color, ImageVector>("離位", Pink001, Icons.Filled.RadioButtonUnchecked) }

                BuiltInCircleIcon02(
                    modifier = Modifier
                        .size(36.dp),
                    arcColor = Color.White,
                    iconImageVector = onSiteIcon,
                    iconTint = onSiteColor,
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = onSiteText,
                    fontSize = 20.sp,
                    color = onSiteColor,
                )
                // </editor-fold desc="在位/離位">
            }
            // [end] added by elite_lin - 2025/04/17
            
            // <editor-fold desc="登錄號, 作品名稱, 作者, 尺寸, 實體組件數">
            theParameters.galleryItem.buildGalleryDetailItemListExt().forEach { theItem: GalleryDetailItem ->   // revision by elite_lin - 2025/12/08
                val omp = BuiltInOutlinedTextFieldParameter(
                    textValue = TextFieldValue(theItem.value),
                    cornerShape = RoundedCornerShape(4.dp),
                    //backgroundColor = Yellow002,
                    label = theItem.title,      // revision by elite_lin - 2025/12/08
                    singleLine = false,
                    enabled = theItem.type == GalleryDetailItemType.TextField,
                    readOnly = theItem.type == GalleryDetailItemType.Text,
                    textColor = Grey85,
                    backgroundColor = Color.Transparent
                )

                BuiltInOutlinedTextField01(
                    modifier = createBuiltInOutlinedTextField01Modifier01(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                        parameter = omp,
                    ),
                    parameter = omp,
                )

                Spacer(modifier = Modifier.padding(vertical = 6.dp))
            }
            // </editor-fold desc="登錄號, 作品名稱, 作者, 尺寸, 實體組件數">

            val location1Requester: FocusRequester = remember { FocusRequester() }
            val location2Requester: FocusRequester = remember { FocusRequester() }
            val location3Requester: FocusRequester = remember { FocusRequester() }
            val placeRequester: FocusRequester = remember { FocusRequester() }
            val remarkRequester: FocusRequester = remember { FocusRequester() }

            // <editor-fold desc="庫位">
            //val location1Parameter = BuiltInTextField02Parameter(
            val location1Parameter = BuiltInOutlinedTextFieldParameter(
                textValue = theParameters.theLocation1Value,
                onValueChange = theParameters.theLocation1OnValueChange,
                cornerShape = RoundedCornerShape(4.dp),
                //backgroundColor = Yellow002,
                label = theParameters.theLocation1Hint,     // revision by elite_lin - 2025/12/08
//            height = 120.dp,
                singleLine = false,
//                maxLines = 3,
//                minLines = 3,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { location2Requester.requestFocus() }),
                selectAllOnFocus = theParameters.theLocation1SelectAllOnFocus,
                //onFocusChanged = theParameters.theLocation1OnFocusChanged,
                focusRequester = location1Requester,
            )

//            BuiltInTextField02(
//                modifier = createBuiltInTextField02Modifier02(parameter = location1Parameter,),
            BuiltInOutlinedTextField01(
                modifier = createBuiltInOutlinedTextField01Modifier01(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    parameter = location1Parameter,
                ),
                parameter = location1Parameter,
            )
            // </editor-fold desc="庫位">

            ///

            Spacer(modifier = Modifier.padding(vertical = 6.dp))

            // <editor-fold desc="櫃位">
            //val location2Parameter = BuiltInTextField02Parameter(
            val location2Parameter = BuiltInOutlinedTextFieldParameter(
                textValue = theParameters.theLocation2Value,
                onValueChange = theParameters.theLocation2OnValueChange,
                cornerShape = RoundedCornerShape(4.dp),
                //backgroundColor = Yellow002,
                label = theParameters.theLocation2Hint,     // revision by elite_lin - 2025/12/08
//            height = 120.dp,
                singleLine = false,
//                maxLines = 3,
//                minLines = 3,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { location3Requester.requestFocus() }),
                selectAllOnFocus = theParameters.theLocation2SelectAllOnFocus,
                //onFocusChanged = theParameters.theLocation2OnFocusChanged,
                focusRequester = location2Requester,
            )

//            BuiltInTextField02(
//                modifier = createBuiltInTextField02Modifier02(parameter = location2Parameter,),
            BuiltInOutlinedTextField01(
                modifier = createBuiltInOutlinedTextField01Modifier01(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    parameter = location2Parameter,
                ),
                parameter = location2Parameter,
            )
            // </editor-fold desc="櫃位">

            ///

            Spacer(modifier = Modifier.padding(vertical = 6.dp))

            // <editor-fold desc="網架">
            //val location3Parameter = BuiltInTextField02Parameter(
            val location3Parameter = BuiltInOutlinedTextFieldParameter(
                textValue = theParameters.theLocation3Value,
                onValueChange = theParameters.theLocation3OnValueChange,
                cornerShape = RoundedCornerShape(4.dp),
                //backgroundColor = Yellow002,
                label = theParameters.theLocation3Hint,     // revision by elite_lin - 2025/12/08
//            height = 120.dp,
                singleLine = false,
//                maxLines = 3,
//                minLines = 3,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { placeRequester.requestFocus() }),
                selectAllOnFocus = theParameters.theLocation3SelectAllOnFocus,
                //onFocusChanged = theParameters.theLocation3OnFocusChanged,
                focusRequester = location3Requester,
            )

//            BuiltInTextField02(
//                modifier = createBuiltInTextField02Modifier02(parameter = location3Parameter,),
            BuiltInOutlinedTextField01(
                modifier = createBuiltInOutlinedTextField01Modifier01(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    parameter = location3Parameter,
                ),
                parameter = location3Parameter,
            )
            // </editor-fold desc="網架">

            ///

            Spacer(modifier = Modifier.padding(vertical = 6.dp))

            // <editor-fold desc="暫存位置">
            //val placeParameter = BuiltInTextField02Parameter(
            val placeParameter = BuiltInOutlinedTextFieldParameter(
                textValue = theParameters.thePlaceValue,
                onValueChange = theParameters.thePlaceOnValueChange,
                cornerShape = RoundedCornerShape(4.dp),
                //backgroundColor = Yellow002,
                label = theParameters.thePlaceHint,     // revision by elite_lin - 2025/12/08
//            height = 120.dp,
                singleLine = false,
//                maxLines = 3,
//                minLines = 3,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { remarkRequester.requestFocus() }),
                selectAllOnFocus = theParameters.thePlaceSelectAllOnFocus,
                //onFocusChanged = theParameters.thePlaceOnFocusChanged,
                focusRequester = placeRequester,
            )

            Row(
                modifier = Modifier
                    //.padding(all = 10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BuiltInOutlinedTextField01(
                    modifier = createBuiltInOutlinedTextField01Modifier01(
                        modifier = Modifier.weight(1f).height(120.dp),
                        parameter = placeParameter,
                    ),
                    parameter = placeParameter,
                )

                // [start] added by elite_lin - 2025/04/18

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                BuiltInRoundedCornerIcon01(
                    modifier = createBuiltInRoundedCornerIcon01Modifier02(
                        rectColor = Blue002,
                        size = 40.dp,
                        onClick = theParameters.theStoreRoomIconClick,
                    ),
                    size = 40.dp,
                    iconImageVector = Icons.AutoMirrored.Filled.ListAlt,
                )

                // [end] added by elite_lin - 2025/04/18
            }

            // <editor-fold desc="暫存位置">

            ///

            Spacer(modifier = Modifier.padding(vertical = 6.dp))

            // <editor-fold desc="備註">
            //val remarkParameter = BuiltInTextField02Parameter(
            val remarkParameter = BuiltInOutlinedTextFieldParameter(
                textValue = theParameters.theRemarkValue,
                onValueChange = theParameters.theRemarkOnValueChange,
                cornerShape = RoundedCornerShape(4.dp),
                //backgroundColor = Yellow002,
                label = theParameters.theRemarkHint,    // revision by elite_lin - 2025/12/08
//            height = 120.dp,
                singleLine = false,
//                maxLines = 3,
//                minLines = 3,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Default),
                //keyboardActions = KeyboardActions(onNext = { remarkRequester.requestFocus() }),
                selectAllOnFocus = theParameters.theRemarkSelectAllOnFocus,
                //onFocusChanged = theParameters.theRemarkOnFocusChanged,
                focusRequester = remarkRequester,
            )

            BuiltInOutlinedTextField01(
                modifier = createBuiltInOutlinedTextField01Modifier01(
                    modifier = Modifier.fillMaxWidth().height(180.dp),
                    parameter = remarkParameter,
                ),
                parameter = remarkParameter,
            )
            // </editor-fold desc="備註">
        }

        BuiltInSaveButton01(
            buttonModifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                .constrainAs(bottomRowRef) {
                    linkTo(start = parent.start, end = parent.end)
                    linkTo(top = galleryDetailRef.bottom, bottom = parent.bottom, topMargin = 10.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
                .padding(horizontal = 50.dp),
            buttonOnClick = theParameters.theOnSaveButtonClick,
        )
    }
}


@Preview
@Composable
fun WtcGalleryReadLogTabContent(
    theParameters: GalleryDetailParameters = GalleryDetailParameters(),
) {
    BuiltInTopAppBarListScreenContent02(
        listDataState = theParameters.theListDataState.value,
        itemClickCallback = theParameters.theItemClickCallback,
        listContent = {
            val itemList: List<ThreeTextsDelegate> = theParameters.theListDataState.value.theList
            Logger.getLogger("WtcGalleryReadLogTabContent").log(Level.INFO, "WtcGalleryReadLogTabContent - listContent - itemList.size: [${itemList.size}]")
            items(
                items = ImmutableObjectList<ThreeTextsDelegate>(itemList).objectList,
                key = { readLogItem: ThreeTextsDelegate -> readLogItem.theThird },
            ) { readLogItem: ThreeTextsDelegate ->
                ThreeTextsItem01(
                    threeTextsLabel = theParameters.threeTextsLabel,
                    threeTextsValue = readLogItem,
                    itemClickCallback = theParameters.theItemClickCallback,
                    text3Visibility = false,
                )
            }
        },
        unavailableContent = {
            Logger.getLogger("WtcGalleryReadLogTabContent").log(Level.INFO, "WtcGalleryReadLogTabContent - unavailableContent")

            val itemList: List<ThreeTextsDelegate> = theParameters.theListDataState.value.theList
            val unavailableItem: ThreeTextsDelegate? = if (itemList.isNotEmpty()) {
                Logger.getLogger("WtcGalleryReadLogTabContent").log(Level.INFO, "WtcGalleryReadLogTabContent - unavailableContent - itemList[0]: [${itemList[0].theFirst}]")

                itemList[0]
            } else { null }
            ListDataUnavailable01(
                unavailableText = unavailableItem?.theFirst ?: "",
                onClick = {
                    if (null != unavailableItem) {
                        theParameters.theItemClickCallback.onHolderItemClicked(unavailableItem, BaseActions.REFRESH, -1)
                    }
                },
            )
        },
//        listDataInitText = "",
        headerContent = {
            ThreeTextsItem01(
                threeTextsLabel = ThreeTextsImpl(
                    theFirst = "登錄號：",
                    theSecond = "作品名稱：",
                ),
                threeTextsValue = ThreeTextsImpl(
                    // [start] revision by elite_lin - 2025/12/08
                    theFirst = theParameters.galleryItem.theCode,
                    theSecond = theParameters.galleryItem.theNameCht,
                    // [end] revision by elite_lin - 2025/12/08
                ),
                backgroundColor = Grey94,
                text3Visibility = false,
            )
        },
//        bottomContent = {},
//        bottomPaddingForBottomSheet = 10.dp,
        tag = "WtcGalleryReadLog",
    )
}


// [start] added by elite_lin - 2025/04/18

@Preview
@Composable
fun WtcStoreRoomPickerDialog01(
    dialogState: BuiltInDialogStateDelegate = BuiltInDialogStateImpl(
        theDialogType = DialogType.TwinActions,
        theDialogState = true,
        theConfirmTitle = "確定",
        theCancelTitle = "取消",
    ),
    textFontSize: TextUnit = 20.sp,
    textColor: Color = Color.Black,
    backgroundColor: Color = Blue012,
    backgroundShape: Shape = RoundedCornerShape(8.dp),
    dialogBackgroundColor: Color = Color.White,
    theParameters: GalleryDetailsScreenStormRoomDelegate = WtcButtonTestParameters(),
) {
    Logger.getLogger("WtcStoreRoomPickerDialog01").log(Level.INFO, "WtcStoreRoomPickerDialog01 [-1]")
    if (dialogState.theDialogType != DialogType.TwinActions) { return }
    if (!dialogState.theDialogState) { return }
    Logger.getLogger("WtcStoreRoomPickerDialog01").log(Level.INFO, "WtcStoreRoomPickerDialog01 [-1]")

    val horizontalSpace = 10.dp
    val paddingHorizontal = 4.dp
    val paddingVertical = 4.dp

    theParameters.theAreaItems.forEach {
        Logger.getLogger("WtcStoreRoomPickerDialog01").log(Level.INFO, "WtcStoreRoomPickerDialog01 - area: [${it}]")
    }


    BuiltInCustomDialog01(
        onDismissRequest = dialogState.onDismiss,
        dialogContent = {
            Column(
                modifier = Modifier.wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                BuiltInTitleOnlyContent02(dialogState)

                // <editor-fold desc="scaleNo">
                BuiltInDropDownMenu01(
                    modifier = Modifier
                        .padding(horizontal = paddingHorizontal),
                    parameter = BuiltInDropDownMenu01Parameter(
                        items = theParameters.theAreaItems,
                        hint = theParameters.theAreaHint,
                        fontSize = dialogState.theMessageFontSize,
                        paddingHorizontal = paddingHorizontal,
                        paddingVertical = paddingVertical,
                        textFieldValue = theParameters.theAreaDropDownValue,
                        onDropDownMenuItemChange = theParameters.onAreaDropDownMenuItemChange,
                        focusRequester = theParameters.theAreaFocusRequester
                    ),
                )
                // </editor-fold desc="scaleNo">

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    TextLabelListDataContent01(
                        lazyColumnContentPadding = PaddingValues(horizontal = 8.dp,),
                        listDataState = theParameters.theStoreRoomListDataState,
                        itemClickCallback = theParameters.theStoreRoomItemClickCallback,
                        textFontSize = textFontSize,
                        textColor = textColor,
                        backgroundColor = backgroundColor,
                        backgroundShape = backgroundShape,
                    )
                }

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                BuiltInSingleActionBottom01(
                    dialogState = dialogState,
                    onDismiss = dialogState.onDismiss,
                    onConfirm = dialogState.onConfirm,
                )
            }
        },
        isFillMaxSize = false
    )
}

// [end] added by elite_lin - 2025/04/18


@Composable
fun rememberGalleryDetailParameters(
    apiViewModel: ApiViewModel,
    fontSize: TextUnit = 20.sp,
): GalleryDetailParameters {

    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    var hasScreenBeenInited: Boolean by rememberSaveable { mutableStateOf(false) }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    val scrollState = rememberScrollState()

    val referencedWidth = builtInMeasureTextWidth("實體組件數：", style = TextStyle(fontSize = fontSize))

    var selectedTabIndex: Int by remember { mutableIntStateOf(0) }

    val galleryDetails = stringResource(tw.com.wtc.rfid.R.string.gallery_details)
    val galleryReadLog = stringResource(tw.com.wtc.rfid.R.string.gallery_readLog)

    // <editor-fold desc="tabItems">
    val tabItems = remember {
        mutableStateListOf<TabItemDelegate>(
            SimpleTabDelegateImpl(theIdentifier = "001", theDescription = galleryDetails),
            SimpleTabDelegateImpl(theIdentifier = "002", theDescription = galleryReadLog),
        )
    }
    // </editor-fold desc="tabItems">

    // <editor-fold desc="listDataState">
    val readLogListDataState: MutableState<ListDataState<ThreeTextsDelegate>> = remember {
        mutableStateOf(ListDataState.None())
    }
    // </editor-fold desc="listDataState">

    // <editor-fold desc="galleryItemState">
    // updated version:
    val galleryItemState: MutableState<WtcRfGalleryItem> = remember {
        mutableStateOf(WtcRfGalleryItem())
//        val galleryItemX: WtcRfGalleryItem? = apiViewModel.selectedGallery
//        if (null != galleryItemX) { mutableStateOf(galleryItemX) }
//        else { mutableStateOf(WtcRfGalleryItem()) }
    }
    // </editor-fold desc="galleryItemState">

    // <editor-fold desc="TextFieldValues">
    val initText = ""   // added by elite_lin - 2025/04/18
    // [start] revision by elite_lin - 2025/12/08
    var location1Value: TextFieldValue by rememberTextFieldValueFrom(galleryItemState.value.theLocation1)
    var location2Value: TextFieldValue by rememberTextFieldValueFrom(galleryItemState.value.theLocation2)
    var location3Value: TextFieldValue by rememberTextFieldValueFrom(galleryItemState.value.theLocation3)
    var placeValue: TextFieldValue by rememberTextFieldValueFrom(galleryItemState.value.thePlace)
    var remarkValue: TextFieldValue by rememberTextFieldValueFrom(galleryItemState.value.theRemark)
    // [end] revision by elite_lin - 2025/12/08
    var areaValue: TextFieldValue by rememberTextFieldValueFrom(initText)    // added by elite_lin - 2025/04/18
    // </editor-fold desc="TextFieldValues">

    // <editor-fold desc="FocusIndicators">
    var location1FocusIndicator by remember { mutableIntStateOf(0) }
    var location2FocusIndicator by remember { mutableIntStateOf(0) }
    var location3FocusIndicator by remember { mutableIntStateOf(0) }
    var placeFocusIndicator by remember { mutableIntStateOf(0) }
    var remarkFocusIndicator by remember { mutableIntStateOf(0) }
    var areaFocusIndicator by remember { mutableIntStateOf(0) }     // added by elite_lin - 2025/04/18
    // </editor-fold desc="FocusIndicators">

    // [start] added by elite_lin - 2025/04/18

    // <editor-fold desc="FocusRequesters">
    val areaFocusRequester: FocusRequester = remember { FocusRequester() }
    // </editor-fold desc="FocusRequesters">

    // <editor-fold desc="areaItems">
    val areaMap: MutableState<Map<String, WtcRfStoreRoomSummary>> = remember { mutableStateOf(emptyMap()) }
    val areaItems: MutableState<List<String>> = remember { mutableStateOf(emptyList()) }
    val selectedAreaItemsItem: MutableState<WtcRfStoreRoomSummary?> = rememberNullableObjectFrom { null }
    // </editor-fold desc="areaItems">

    // <editor-fold desc="storeRoomItems">
    val storeRoomItems: MutableState<List<StoreRoomItemDelegate>> = remember { mutableStateOf(emptyList()) }
    val selectedStoreRoomItem: MutableState<StoreRoomItemDelegate?> = remember { mutableStateOf(null) }
    // </editor-fold desc="storeRoomItems">

    // <editor-fold desc="updateArea">
    fun updateArea(area: String) {
        val summary: WtcRfStoreRoomSummary? = areaMap.value[area]
        selectedAreaItemsItem.value = summary
        val display = summary?.theArea ?: ""
        areaValue = TextFieldValue(text = display, selection = TextRange(display.length))
        //selectedStoreRoomItem.value = null
        storeRoomItems.value = summary?.items ?: listOf()
    }
    // </editor-fold desc="updateArea">

    // <editor-fold desc="storeRoomPickerDialogState">
    val storeRoomPickerDialogState: MutableState<BuiltInDialogStateDelegate> = remember {
        mutableStateOf(BuiltInDialogStateImpl())
    }
    // </editor-fold desc="storeRoomPickerDialogState">

    // <editor-fold desc="storeRoomPickerTwinActionsDialogState">
    val storeRoomPickerTwinActionsDialogState = MutableBuiltInDialogStateImpl(
        theDialogType = DialogType.TwinActions,
        theDialogState = true,
        theTitle = "挑選現況位置",
        theConfirmTitle = "取消",//"確定",
        theCancelTitle = "取消",
//        theTitleFontSize = dialogTextFieldFontSize,
//        theMessageFontSize = dialogTextFieldFontSize,
//        theConfirmTitleFontSize = dialogTextFieldFontSize,
//        theCancelTitleFontSize = dialogTextFieldFontSize,
        onDismiss = {
            coroutineScope.launch {
                storeRoomPickerDialogState.value = BuiltInDialogStateImpl()
                updateArea("")
            }
        },
        onConfirm = {
            coroutineScope.launch {
                storeRoomPickerDialogState.value = BuiltInDialogStateImpl()
                updateArea("")
            }
        },
    )
    // </editor-fold desc="addSaGradeWeightDetailTwinActionsDialogState">

    // [end] added by elite_lin - 2025/04/18

    // <editor-fold desc="galleryDetailDialogState">
    val galleryDetailDialogState: MutableState<BuiltInDialogStateDelegate> = remember {
        mutableStateOf(BuiltInDialogStateImpl())
    }
    // </editor-fold desc="galleryDetailDialogState">

    // <editor-fold desc="progressDialogState">
    val progressDialogState = BuiltInDialogStateImpl(
        theDialogType = DialogType.Progress,
        theDialogState = true,
        theMessage = "更新中...",
        onDismiss = { galleryDetailDialogState.value = BuiltInDialogStateImpl() }
    )
    // </editor-fold desc="progressDialogState">

    // <editor-fold desc="singleActionDialogState">
    val singleActionDialogState = MutableBuiltInDialogStateImpl(
        theDialogType = DialogType.SingleAction,
        theDialogState = true,
        theTitle = "提醒",
        theConfirmTitle = "確定",
        theCancelTitle = "取消",
        onDismiss = { galleryDetailDialogState.value = BuiltInDialogStateImpl() }
    )
    // </editor-fold desc="singleActionDialogState">

    // <editor-fold desc="reloadBlock">
    val reloadBlock: () -> Unit = {
        //apiViewModel.galleryDetailResultLiveData.value = null
        apiViewModel.galleryDetailResultStateFlow.value = null

        val authData: AuthData? = apiViewModel.theAuthData()
        val selectedGallery: RfGalleryItemDelegate? = apiViewModel.selectedGallery   // revision by elite_lin - 2025/12/08
        if ((null != authData) && (null != selectedGallery)) {
            galleryDetailDialogState.value = progressDialogState
            readLogListDataState.value = ListDataState.Loading()
            apiViewModel.callGalleryDetailApi(
                scope = apiViewModel,
                apiDomain = getApiDomain(),
                authToken = authData.authToken,
                queryModel = WtcRfGalleryQueryModel(theCode = selectedGallery.theCode),
            )

            apiViewModel.callReadLogPassiveListApi(
                scope = apiViewModel,
                apiDomain = getApiDomain(),
                authToken = authData.authToken,
                queryModel = WtcRfReadLogPassiveQueryModel(tagId = galleryItemState.value.theCode)
            )
        }
        else {
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - authData is null: [${null == authData}], selectedGallery is null: [${null == selectedGallery}]")
        }
    }
    // </editor-fold desc="reloadBlock">

    // <editor-fold desc="StoreRoomListResultStateFlowCollector">
    // [start] added by elite_lin - 2025/04/18
    apiViewModel.storeRoomListResultStateFlow.NullableCollectorExt { apiResult: FmApiResult<List<WtcRfStoreRoomSummary>> ->
        when(apiResult) {
            is FmApiResult.Success -> {
                Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - StoreRoomListResultStateFlowCollector： [Success]")

                val areaListX = mutableListOf<String>()
                val areaMapX = mutableMapOf<String, WtcRfStoreRoomSummary>()

                apiResult.data.forEach { item: WtcRfStoreRoomSummary ->
                    Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - StoreRoomListResultStateFlowCollector - Area: [${item.theArea}], item.count: [${item.itemCount}]")
//                    item.items.forEach { storeRoomItem: WtcRfStoreRoomItem ->
//                        Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - StoreRoomListResultStateFlowCollector - storeRoomItem: [${storeRoomItem}]")
//                    }
                    areaListX.add(item.theArea)
                    areaMapX[item.theArea] = item
                }
                areaItems.value = areaListX
                areaMap.value = areaMapX
            }
            is FmApiResult.Error -> {
                Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - StoreRoomListResultStateFlowCollector： [Error]", apiResult.cause)
            }
        }
    }
    // [end] added by elite_lin - 2025/04/18
    // </editor-fold desc="StoreRoomListResultStateFlowCollector">

    // <editor-fold desc="QueryGalleryDetailResultStateFlowCollector">
    apiViewModel.galleryDetailResultStateFlow.NullableCollectorExt { queryGalleryDetailResult: FmApiResult<List<WtcRfGalleryItem>> ->
        coroutineScope.launch {
            galleryDetailDialogState.value = BuiltInDialogStateImpl()

            when(queryGalleryDetailResult) {
                is FmApiResult.Success -> {
                    Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - QueryGalleryDetailResultStateFlowCollector: [Success]")
                    val resultList: List<WtcRfGalleryItem> = queryGalleryDetailResult.data
                    if (resultList.isNotEmpty()) {
                        apiViewModel.selectedGallery = resultList[0]
                        galleryItemState.value = resultList[0]
                        // [start] revision by elite_lin - 2025/12/08
                        location1Value = TextFieldValue(galleryItemState.value.theLocation1)
                        location2Value = TextFieldValue(galleryItemState.value.theLocation2)
                        location3Value = TextFieldValue(galleryItemState.value.theLocation3)
                        placeValue = TextFieldValue(galleryItemState.value.thePlace)
                        remarkValue = TextFieldValue(galleryItemState.value.theRemark)
                        // [end] revision by elite_lin - 2025/12/08
                        //Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - [${galleryItem}]")
                    }
                }
                is FmApiResult.Error -> {
                    val errorObject = queryGalleryDetailResult.cause
                    Logger.getLogger("rememberGalleryDetailParameters").log(Level.SEVERE, "rememberGalleryDetailParameters - QueryGalleryDetailResultStateFlowCollector： .Error]", errorObject)

                    apiViewModel.accountReAuthIfNeeded(scope = apiViewModel, cause = errorObject)
                }
            }

            apiViewModel.galleryDetailResultStateFlow.value = null
        }
    }
    // </editor-fold desc="QueryGalleryDetailResultStateFlowCollector">

    // <editor-fold desc="QueryReadLogPassiveListResultStateFlowCollector">
    apiViewModel.queryReadLogPassiveListResultStateFlow.NullableCollectorExt { queryReadLogListResult: FmApiResult<List<WtcRfReadLogPassiveItem>> ->
        coroutineScope.launch {
            readLogListDataState.value = when(queryReadLogListResult) {
                is FmApiResult.Success -> {
                    Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - QueryReadLogPassiveListResultStateFlowCollector: [Success]")
                    val resultList: List<ThreeTextsDelegate> = withContext(Dispatchers.Default) {
                        queryReadLogListResult.data.map { readLogItem: WtcRfReadLogPassiveItem ->
                            ThreeTextsImpl(
                                theFirst = readLogItem.readTime.replace("T", " "),
                                theSecond = readLogItem.deviceDescription,
                                theThird = readLogItem.readLogPassiveId,)
                        }
                    }
                    Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - QueryReadLogPassiveListResultStateFlowCollector - resultList.size: [${resultList.size}]")
                    if (resultList.isNotEmpty()) {
                        ListDataState.Available<ThreeTextsDelegate>(theList = resultList)
                    }
                    else { defaultReadLogUnavailableResult01() }
                }
                is FmApiResult.Error -> {
                    val errorObject = queryReadLogListResult.cause
                    Logger.getLogger("rememberGalleryDetailParameters").log(Level.SEVERE, "rememberGalleryDetailParameters - QueryReadLogPassiveListResultStateFlowCollector： [Error]", errorObject)
                    apiViewModel.accountReAuthIfNeeded(scope = apiViewModel, cause = errorObject)
                    defaultReadLogUnavailableResult01()
                }
            }

            apiViewModel.queryReadLogPassiveListResultStateFlow.value = null
        }
    }
    // </editor-fold desc="QueryReadLogPassiveListResultStateFlowCollector">

    // <editor-fold desc="GalleryUpdateResultStateFlowCollector">
    apiViewModel.galleryUpdateResultStateFlow.NullableCollectorExt { apiResult: FmApiResult<CommonApiResponse> ->
        coroutineScope.launch {
            galleryDetailDialogState.value = BuiltInDialogStateImpl()

            when(apiResult) {
                is FmApiResult.Success -> {
                    Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - GalleryUpdateResultStateFlowCollector - [Success]")
                    galleryDetailDialogState.value = singleActionDialogState.apply {
                        theMessage = "儲存成功"
                        onConfirm =  {
                            coroutineScope.launch {
                                reloadBlock()
                            }
                        }
                    }
                }
                is FmApiResult.Error -> {
                    Logger.getLogger("rememberGalleryDetailParameters").log(Level.SEVERE, "rememberGalleryDetailParameters - GalleryUpdateResultStateFlowCollector - [Error]", apiResult.cause)
                    apiViewModel.accountReAuthIfNeeded(scope = apiViewModel, cause = apiResult.cause)

                    galleryDetailDialogState.value = singleActionDialogState.apply {
                        theMessage = "儲存失敗!!"
                        onConfirm =  {
                            coroutineScope.launch {
                                galleryDetailDialogState.value = BuiltInDialogStateImpl()
                            }
                        }
                    }
                }
            }

            apiViewModel.galleryUpdateResultStateFlow.value = null
        }
    }
    // </editor-fold desc="GalleryUpdateResultStateFlowCollector">

    return GalleryDetailParameters(
        galleryItem = galleryItemState.value,

        scrollState = scrollState,

        fontSize = fontSize,
        referencedWidth = referencedWidth,

        theSelectedTabIndex = selectedTabIndex,
        theTabItems = tabItems,
        theOnTabItemClick = { index: Int -> selectedTabIndex = index },

        galleryDetailDialogState = galleryDetailDialogState,

        threeTextsLabel = ThreeTextsImpl(theFirst = "行政區：", theSecond = "地號：",),
        theListDataState = readLogListDataState,

        theHasScreenBeenInited = hasScreenBeenInited,
        theCoroutineScope = coroutineScope,
        // <editor-fold desc="GalleryDetailParameters - theLaunchedEffectBlock">
        theLaunchedEffectBlock = {
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - launchedEffectBlock -  hasScreenBeenInited: [$hasScreenBeenInited]!")
            if (!hasScreenBeenInited) {
                Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - launchedEffectBlock - run init!!")
                delay(1000)
                reloadBlock()
                hasScreenBeenInited = true
            }
            else { Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - launchedEffectBlock - no action!!") }
        },
        // </editor-fold desc="GalleryDetailParameters - theLaunchedEffectBlock">

        // [start] added by elite_lin - 2025/04/18

        // <editor-fold desc="GalleryDetailParameters - theArea">
        theAreaDropDownValue = areaValue,
        onAreaDropDownMenuItemChange = { item: String ->
            coroutineScope.launch {
//                areaValue = TextFieldValue(text = item)
//                selectedScaleNoItem.value = item
//                Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - onScaleNoDropDownMenuItemChange - selectedScaleNoItem: [${selectedScaleNoItem.value}]")
                updateArea(item)
            }
        },
        theAreaValue = areaValue,
        theAreaOnValueChange = { newValue: TextFieldValue ->
            areaValue = newValue
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters() - theAreaOnValueChange() - areaFocusIndicator: [$areaFocusIndicator], areaValue： [${areaValue.text}]")
        },
        theAreaOnFocusChanged = {
            val pair: Pair<TextFieldValue, Int> = areaValue.builtInTextFieldFocusChangedHandler01(true, it)
            areaFocusIndicator = pair.second
            areaValue = pair.first
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters() - theAreaOnFocusChanged() - areaFocusIndicator: [$areaFocusIndicator], areaValue： [${areaValue.text}]")
        },
        theAreaFocusRequester = areaFocusRequester,
        theAreaOnDone = {
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters() - theAreaOnDone")

        },
        theAreaItems = areaItems.value,
        // </editor-fold desc="GalleryDetailParameters - theArea">
        // [start] revision by elite_lin - 2025/07/29
        //theStoreRoomListDataState = ListDataState<StoreRoomItemDelegate>.Available(
        //    theList = storeRoomItems.value,
        //),
        theStoreRoomListDataState = storeRoomItems.value.toAvailableState(),
        // [end] revision by elite_lin - 2025/07/29
        // <editor-fold desc="storeRoomItemClickCallback">
        theStoreRoomItemClickCallback = HolderItemClickDelegate<TextLabelDelegate> { dataObject: TextLabelDelegate, action: String, position: Int ->
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - theStoreRoomItemClickCallback - delegate: [${dataObject.theDescription}], action: [$action], position: [$position]")
            storeRoomPickerDialogState.value = BuiltInItemPickerDialogStateImpl()
            if (dataObject !is StoreRoomItemDelegate) { return@HolderItemClickDelegate }
            coroutineScope.launch {
                selectedStoreRoomItem.value = dataObject
                placeValue = TextFieldValue(text = dataObject.theDataObject.name, selection = TextRange(dataObject.theDataObject.name.length))
                updateArea("")
            }
        },
        // </editor-fold desc="storeRoomItemClickCallback">
        theStoreRoomPickerDialogState = storeRoomPickerDialogState,
        theStoreRoomIconClick = {
            coroutineScope.launch {
                storeRoomPickerDialogState.value = storeRoomPickerTwinActionsDialogState
            }
        },

        // [end] added by elite_lin - 2025/04/18

        // <editor-fold desc="GalleryDetailParameters - Location1">
        theLocation1Value = location1Value,
        theLocation1OnValueChange = { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, location1FocusIndicator)
//            location1FocusIndicator = pair.second
//            location1Value = pair.first
            location1Value = newValue
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters() - onValueChange() - location1FocusIndicator: [$location1FocusIndicator], location1Value： [${location1Value.text}]")
        },
        theLocation1OnFocusChanged = {
            val pair: Pair<TextFieldValue, Int> = location1Value.builtInTextFieldFocusChangedHandler01(true, it)
            location1FocusIndicator = pair.second
            location1Value = pair.first
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters() - onFocusChanged() - location1FocusIndicator: [$location1FocusIndicator], location1Value： [${location1Value.text}]")
        },
        // </editor-fold desc="GalleryDetailParameters - Location1">

        // <editor-fold desc="GalleryDetailParameters - Location2">
        theLocation2Value = location2Value,
        theLocation2OnValueChange = { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, location2FocusIndicator)
//            location2FocusIndicator = pair.second
//            location2Value = pair.first
            location2Value = newValue
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters() - onValueChange() - location2FocusIndicator: [$location2FocusIndicator], location2Value： [${location2Value.text}]")
        },
        theLocation2OnFocusChanged = {
            val pair: Pair<TextFieldValue, Int> = location2Value.builtInTextFieldFocusChangedHandler01(true, it)
            location2FocusIndicator = pair.second
            location2Value = pair.first
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters() - onFocusChanged() - location2FocusIndicator: [$location2FocusIndicator], location2Value： [${location2Value.text}]")
        },
        // </editor-fold desc="GalleryDetailParameters - Location2">

        // <editor-fold desc="GalleryDetailParameters - Location3">
        theLocation3Value = location3Value,
        theLocation3OnValueChange = { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, location3FocusIndicator)
//            location3FocusIndicator = pair.second
//            location3Value = pair.first
            location3Value = newValue
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters() - onValueChange() - location3FocusIndicator: [$location3FocusIndicator], location3Value： [${location3Value.text}]")
        },
        theLocation3OnFocusChanged = {
            val pair: Pair<TextFieldValue, Int> = location3Value.builtInTextFieldFocusChangedHandler01(true, it)
            location3FocusIndicator = pair.second
            location3Value = pair.first
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters() - onFocusChanged() - location3FocusIndicator: [$location3FocusIndicator], location3Value： [${location3Value.text}]")
        },
        // </editor-fold desc="GalleryDetailParameters - Location3">

        // <editor-fold desc="GalleryDetailParameters - Place">
        thePlaceValue = placeValue,
        thePlaceOnValueChange = { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, placeFocusIndicator)
//            placeFocusIndicator = pair.second
//            placeValue = pair.first
            placeValue = newValue
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters() - onValueChange() - placeFocusIndicator: [$placeFocusIndicator], placeValue： [${placeValue.text}]")
        },
        thePlaceOnFocusChanged = {
            val pair: Pair<TextFieldValue, Int> = placeValue.builtInTextFieldFocusChangedHandler01(true, it)
            placeFocusIndicator = pair.second
            placeValue = pair.first
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters() - onFocusChanged() - placeFocusIndicator: [$placeFocusIndicator], placeValue： [${placeValue.text}]")
        },
        // </editor-fold desc="GalleryDetailParameters - Place">

        // <editor-fold desc="GalleryDetailParameters - Remark">
        theRemarkValue = remarkValue,
        theRemarkOnValueChange = { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, remarkFocusIndicator)
//            remarkFocusIndicator = pair.second
//            remarkValue = pair.first
            remarkValue = newValue
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters() - onValueChange() - remarkFocusIndicator: [$remarkFocusIndicator], remarkValue： [${remarkValue.text}]")
        },
        theRemarkOnFocusChanged = {
            val pair: Pair<TextFieldValue, Int> = remarkValue.builtInTextFieldFocusChangedHandler01(true, it)
            remarkFocusIndicator = pair.second
            remarkValue = pair.first
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters() - onFocusChanged() - remarkFocusIndicator: [$remarkFocusIndicator], remarkValue： [${remarkValue.text}]")
        },
        // </editor-fold desc="GalleryDetailParameters - Remark">

        // <editor-fold desc="GalleryDetailParameters - theOnSaveButtonClick">
        theOnSaveButtonClick = {
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters() - theOnSaveButtonClick() - location1: [${location1Value.text}], location2: [${location2Value.text}], location3: [${location3Value.text}], place: [${placeValue.text}], remark: [${remarkValue.text}]")

            val authData: AuthData? = apiViewModel.theAuthData()
            if (null == authData) {
                Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - theOnSaveButtonClick - authData is null!")
                return@GalleryDetailParameters
            }

            keyboardController?.hide()

            coroutineScope.launch {
                galleryDetailDialogState.value = progressDialogState
                val clonedGalleryItem = galleryItemState.value.copy (
                    // [start] revision by elite_lin - 2025/12/08
                    theLocation1 = location1Value.text,
                    theLocation2 = location2Value.text,
                    theLocation3 = location3Value.text,
                    thePlace = placeValue.text,
                    theRemark = remarkValue.text,
                    // [end] revision by elite_lin - 2025/12/08
                )
                apiViewModel.runGalleryUpdateTask(galleryItem = clonedGalleryItem, authToken = authData.authToken)
            }
        },
        // </editor-fold desc="GalleryDetailParameters - theOnSaveButtonClick">
    )
}


interface GalleryDetailsScreenItemsDelegate {
    val theLocation1Value: TextFieldValue
    val theLocation1OnValueChange: (TextFieldValue) -> Unit
    val theLocation1Hint: String
    val theLocation1SelectAllOnFocus: Boolean
    val theLocation1OnFocusChanged: (FocusState) -> Unit

    val theLocation2Value: TextFieldValue
    val theLocation2OnValueChange: (TextFieldValue) -> Unit
    val theLocation2Hint: String
    val theLocation2SelectAllOnFocus: Boolean
    val theLocation2OnFocusChanged: (FocusState) -> Unit

    val theLocation3Value: TextFieldValue
    val theLocation3OnValueChange: (TextFieldValue) -> Unit
    val theLocation3Hint: String
    val theLocation3SelectAllOnFocus: Boolean
    val theLocation3OnFocusChanged: (FocusState) -> Unit

    val thePlaceValue: TextFieldValue
    val thePlaceOnValueChange: (TextFieldValue) -> Unit
    val thePlaceHint: String
    val thePlaceSelectAllOnFocus: Boolean
    val thePlaceOnFocusChanged: (FocusState) -> Unit

    val theRemarkValue: TextFieldValue
    val theRemarkOnValueChange: (TextFieldValue) -> Unit
    val theRemarkHint: String
    val theRemarkSelectAllOnFocus: Boolean
    val theRemarkOnFocusChanged: (FocusState) -> Unit

    val theOnSaveButtonClick: () -> Unit
}


interface GalleryDetailsScreenStormRoomDelegate {
    val theAreaHint: String
    val theAreaDropDownValue: TextFieldValue
    val onAreaDropDownMenuItemChange: (String) -> Unit
    val theAreaValue: TextFieldValue
    val theAreaOnValueChange: (TextFieldValue) -> Unit
    val theAreaSelectAllOnFocus: Boolean
    val theAreaOnFocusChanged: (FocusState) -> Unit
    val theAreaFocusRequester: FocusRequester
    val theAreaOnDone: KeyboardActionScope.() -> Unit
    val theAreaItems: List<String>
    //val theStoreRoomItems: List<WtcRfStoreRoomItem>
    val theStoreRoomListDataState: ListDataState<TextLabelDelegate>
    val theStoreRoomItemClickCallback: HolderItemClickDelegate<TextLabelDelegate>
    val theStoreRoomPickerDialogState: MutableState<BuiltInDialogStateDelegate>
    val theStoreRoomIconClick: () -> Unit
}


data class GalleryDetailParameters(
    val galleryItem: WtcRfGalleryItem = WtcRfGalleryItem(),

    val scrollState: ScrollState = ScrollState(initial = 0),

    val fontSize: TextUnit = 20.sp,
    val referencedWidth: Dp = 0.dp,

    val pairList: List<Pair<String, String>> = listOf(),

    val galleryDetailDialogState: State<BuiltInDialogStateDelegate> = mutableStateOf<BuiltInDialogStateDelegate>(
        BuiltInDialogStateImpl()
    ),

    override val theLocation1Value: TextFieldValue = TextFieldValue(),
    override val theLocation1OnValueChange: (TextFieldValue) -> Unit = {},
    override val theLocation1Hint: String = "庫位",
    override val theLocation1SelectAllOnFocus: Boolean = false,
    override val theLocation1OnFocusChanged: (FocusState) -> Unit = {},

    override val theLocation2Value: TextFieldValue = TextFieldValue(),
    override val theLocation2OnValueChange: (TextFieldValue) -> Unit = {},
    override val theLocation2Hint: String = "櫃位",
    override val theLocation2SelectAllOnFocus: Boolean = false,
    override val theLocation2OnFocusChanged: (FocusState) -> Unit = {},

    override val theLocation3Value: TextFieldValue = TextFieldValue(),
    override val theLocation3OnValueChange: (TextFieldValue) -> Unit = {},
    override val theLocation3Hint: String = "網架",
    override val theLocation3SelectAllOnFocus: Boolean = false,
    override val theLocation3OnFocusChanged: (FocusState) -> Unit = {},

    override val thePlaceValue: TextFieldValue = TextFieldValue(),
    override val thePlaceOnValueChange: (TextFieldValue) -> Unit = {},
    override val thePlaceHint: String = "暫存位置",
    override val thePlaceSelectAllOnFocus: Boolean = false,
    override val thePlaceOnFocusChanged: (FocusState) -> Unit = {},

    override val theRemarkValue: TextFieldValue = TextFieldValue(),
    override val theRemarkOnValueChange: (TextFieldValue) -> Unit = {},
    override val theRemarkHint: String = "備註",
    override val theRemarkSelectAllOnFocus: Boolean = false,
    override val theRemarkOnFocusChanged: (FocusState) -> Unit = {},

    // [start] added by elite_lin - 2025/04/18

    override val theAreaHint: String = "區域",
    override val theAreaDropDownValue: TextFieldValue = TextFieldValue(),
    override val onAreaDropDownMenuItemChange: (String) -> Unit= {},
    override val theAreaValue: TextFieldValue = TextFieldValue(),
    override val theAreaOnValueChange: (TextFieldValue) -> Unit= {},
    override val theAreaSelectAllOnFocus: Boolean = false,
    override val theAreaOnFocusChanged: (FocusState) -> Unit= {},
    override val theAreaFocusRequester: FocusRequester = FocusRequester(),
    override val theAreaOnDone: KeyboardActionScope.() -> Unit= {},
    override val theAreaItems: List<String> = listOf(),
    //override val theStoreRoomItems: List<WtcRfStoreRoomItem> = listOf<WtcRfStoreRoomItem>(),
    override val theStoreRoomListDataState: ListDataState<TextLabelDelegate> = ListDataState.Available(),
    override val theStoreRoomItemClickCallback: HolderItemClickDelegate<TextLabelDelegate> = DefaultHolderCellClickHandler<TextLabelDelegate>(),
    override val theStoreRoomPickerDialogState: MutableState<BuiltInDialogStateDelegate> =
        mutableStateOf(BuiltInDialogStateImpl()),
    override val theStoreRoomIconClick: () -> Unit = {},

    // [end] added by elite_lin - 2025/04/18

    override val theOnSaveButtonClick: () -> Unit = {},

    override val theScrollableThreshold: Int = 3,
    override val theSelectedTabIndex: Int = 0,
    override val theTabItems: List<TabItemDelegate> = listOf<TabItemDelegate>(),
    override val theOnTabItemClick: (Int) -> Unit = {},
    override val theTabTextFontSize: TextUnit = 20.sp,

    val threeTextsLabel: ThreeTextsDelegate = ThreeTextsImpl(),
    override val theItemClickCallback: HolderItemClickDelegate<ThreeTextsDelegate> = DefaultHolderCellClickHandler<ThreeTextsDelegate>(),
    override val theListDataState: State<ListDataState<ThreeTextsDelegate>> = mutableStateOf<ListDataState<ThreeTextsDelegate>>(ListDataState.None()),

    override val theHasScreenBeenInited: Boolean = false,
    override val theCoroutineScope: CoroutineScope = BuiltInCoroutineScopeImpl(),
    override val theLaunchedEffectBlock: suspend CoroutineScope.() -> Unit = {},

) : IBuiltInLaunchEffectTabListScreenParameters01<ThreeTextsDelegate>,
    GalleryDetailsScreenItemsDelegate,
    GalleryDetailsScreenStormRoomDelegate


fun defaultReadLogUnavailableResult01(
    description: String = "尚無藏品讀取紀錄",
    action: String = "Unavailable"
) = ListDataState.Unavailable<ThreeTextsDelegate>(
    theList = listOf(
        ThreeTextsImpl(
            theFirst = description,
            theSecond = generateRandomStringViaUuid(),
            theThird = action,
        )
    )
)



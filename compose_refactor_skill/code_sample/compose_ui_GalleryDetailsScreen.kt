

@Composable
fun MuseumGalleryDetailScreen(
    navController: NavHostController,
    galleryDetailParameters: GalleryDetailParameterDelegate = BuiltInGalleryDetailParameter01(),
    tag: String = "MuseumGalleryDetail",
) {
    Logger.getLogger("MuseumGalleryDetailScreen").log(Level.INFO, "MuseumGalleryDetailScreen - time: [${System.currentTimeMillis()}]")

    BuiltInTopAppBarTabBodyScreen01(
        inputParameters = BuiltInTopAppBarTabBodyScreenParameters01(
            theNavController = navController,
            theRememberedParameters = galleryDetailParameters,
            theTopAppBarTitle = galleryDetailParameters.theTopAppBarTitle,
            theTag = tag,
        ),
    ) {
        when(galleryDetailParameters.theSelectedTabIndex) {
            0 -> { MuseumGalleryDetailTabContent(theParameters = galleryDetailParameters) }
            1 -> { MuseumGalleryReadLogTabContent(theParameters = galleryDetailParameters) }
            else -> {
                TextCenterScreenContent(galleryDetailParameters.theTabItems[galleryDetailParameters.theSelectedTabIndex].theDescription)
            }
        }
    }

    BuiltInDialogSet01(
        dialogState = galleryDetailParameters.theGalleryDetailDialogState.value
    )

    MuseumStoreRoomPickerDialog01(
        dialogState = galleryDetailParameters.theStoreRoomPickerDialogState.value,
        theParameters = galleryDetailParameters,
    )
}


@Preview
@Composable
fun MuseumGalleryDetailTabContent(
    theParameters: GalleryDetailParameterDelegate = BuiltInGalleryDetailParameter01(),
) {
    Logger.getLogger("MuseumGalleryDetailTabContent").log(Level.INFO, "MuseumGalleryDetailTabContent - [${theParameters.theGalleryItem}]")

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp),
    ) {

        val (galleryDetailRef, bottomRowRef) = createRefs()

        Column(
            modifier = Modifier
                .verticalScroll(theParameters.theScrollState)
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
                    .aspectRatioReference(
                        ratioWidth = 16f,
                        ratioHeight = 9f,
                        AspectRatioReference.MIN_PARENT_WIDTH_PARENT_HEIGHT
                    )
                    .background(Grey94),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(theParameters.theGalleryItem.theImageUrl)
                        .crossfade(durationMillis = 300)
                        .build(),
                    // java.lang.IllegalArgumentException: Only VectorDrawables and rasterized asset types are supported ex. PNG, JPG, WEBP
                    //cannot use xml drawable!!
                    //placeholder = painterResource(R.drawable.logo2),
                    contentDescription = theParameters.theGalleryItem.theNameCht,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    onError = { result: AsyncImagePainter.State.Error ->
                        Logger.getLogger("MuseumGalleryDetailTabContent").log(Level.SEVERE, "MuseumGalleryDetailTabContent - Coil3 error: [${result.result.throwable.message}]")
                    },
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
                val inbound = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_inbound)
                val outbound = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_outbound)
                val (statusText, statusColor, statusIcon) = if (theParameters.theGalleryItem.theStatus == "I")
                    { Triple<String, Color, ImageVector>(inbound, Green002, Icons.Filled.CheckCircle) }
                    else { Triple<String, Color, ImageVector>(outbound, Pink001, Icons.Filled.RemoveCircle) }

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
                val inStock = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_in_stock)
                val outOfStock = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_out_of_stock)
                val (onSiteText, onSiteColor, onSiteIcon) =
                    if (theParameters.theGalleryItem.theOnSite == "Y")
                    { Triple<String, Color, ImageVector>(inStock, Green002, Icons.Filled.RadioButtonChecked) }
                    else { Triple<String, Color, ImageVector>(outOfStock, Pink001, Icons.Filled.RadioButtonUnchecked) }

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
            theParameters.theGalleryItem.buildGalleryDetailItemListExt().forEach { theItem: GalleryDetailItem ->
                val omp = BuiltInOutlinedTextFieldParameter(
                    textValue = TextFieldValue(theItem.value),
                    cornerShape = RoundedCornerShape(4.dp),
                    label = theItem.title,
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
            val location1Parameter = BuiltInOutlinedTextFieldParameter(
                textValue = theParameters.theLocation1Value,
                onValueChange = theParameters.theLocation1OnValueChange,
                cornerShape = RoundedCornerShape(4.dp),
                label = theParameters.theLocation1Hint,
                singleLine = false,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { location2Requester.requestFocus() }),
                selectAllOnFocus = theParameters.theLocation1SelectAllOnFocus,
                focusRequester = location1Requester,
            )

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
            val location2Parameter = BuiltInOutlinedTextFieldParameter(
                textValue = theParameters.theLocation2Value,
                onValueChange = theParameters.theLocation2OnValueChange,
                cornerShape = RoundedCornerShape(4.dp),
                label = theParameters.theLocation2Hint,
                singleLine = false,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { location3Requester.requestFocus() }),
                selectAllOnFocus = theParameters.theLocation2SelectAllOnFocus,
                focusRequester = location2Requester,
            )

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
            val location3Parameter = BuiltInOutlinedTextFieldParameter(
                textValue = theParameters.theLocation3Value,
                onValueChange = theParameters.theLocation3OnValueChange,
                cornerShape = RoundedCornerShape(4.dp),
                label = theParameters.theLocation3Hint,
                singleLine = false,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { placeRequester.requestFocus() }),
                selectAllOnFocus = theParameters.theLocation3SelectAllOnFocus,
                focusRequester = location3Requester,
            )

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
            val placeParameter = BuiltInOutlinedTextFieldParameter(
                textValue = theParameters.thePlaceValue,
                onValueChange = theParameters.thePlaceOnValueChange,
                cornerShape = RoundedCornerShape(4.dp),
                label = theParameters.thePlaceHint,
                // height = 120.dp,
                singleLine = false,
                // maxLines = 3,
                // minLines = 3,
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
            val remarkParameter = BuiltInOutlinedTextFieldParameter(
                textValue = theParameters.theRemarkValue,
                onValueChange = theParameters.theRemarkOnValueChange,
                cornerShape = RoundedCornerShape(4.dp),
                //backgroundColor = Yellow002,
                label = theParameters.theRemarkHint,
                // height = 120.dp,
                singleLine = false,
                // maxLines = 3,
                // minLines = 3,
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
fun MuseumGalleryReadLogTabContent(
    theParameters: GalleryDetailParameterDelegate = BuiltInGalleryDetailParameter01(),
    tag: String = "MuseumGalleryReadLog",
) {
    BuiltInTopAppBarListScreenContent02(
        listDataState = theParameters.theListDataState.value,
        itemClickCallback = theParameters.theItemClickCallback,
        listContent = {
            val itemList: List<ThreeTextsDelegate> = theParameters.theListDataState.value.theList
            Logger.getLogger("MuseumGalleryReadLogTabContent").log(Level.INFO, "MuseumGalleryReadLogTabContent - listContent - itemList.size: [${itemList.size}]")
            items(
                items = ImmutableObjectList<ThreeTextsDelegate>(itemList).objectList,
                key = { readLogItem: ThreeTextsDelegate -> readLogItem.theThird },
            ) { readLogItem: ThreeTextsDelegate ->
                ThreeTextsItem01(
                    threeTextsLabel = theParameters.theThreeTextsLabel,
                    threeTextsValue = readLogItem,
                    itemClickCallback = theParameters.theItemClickCallback,
                    text3Visibility = false,
                )
            }
        },
        unavailableContent = {
            Logger.getLogger("MuseumGalleryReadLogTabContent").log(Level.INFO, "MuseumGalleryReadLogTabContent - unavailableContent")

            val itemList: List<ThreeTextsDelegate> = theParameters.theListDataState.value.theList
            val unavailableItem: ThreeTextsDelegate? = if (itemList.isNotEmpty()) {
                Logger.getLogger("MuseumGalleryReadLogTabContent").log(Level.INFO, "MuseumGalleryReadLogTabContent - unavailableContent - itemList[0]: [${itemList[0].theFirst}]")

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
        headerContent = {
            ThreeTextsItem01(
                threeTextsLabel = ThreeTextsImpl(
                    theFirst = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_gallery_code),
                    theSecond = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_gallery_name),
                ),
                threeTextsValue = ThreeTextsImpl(
                    theFirst = theParameters.theGalleryItem.theCode,
                    theSecond = theParameters.theGalleryItem.theNameCht,
                ),
                backgroundColor = Grey94,
                text3Visibility = false,
            )
        },
        tag = tag,
    )
}


// [start] added by elite_lin - 2025/04/18

@Preview
@Composable
fun MuseumStoreRoomPickerDialog01(
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
    theParameters: GalleryDetailParameterDelegate = BuiltInGalleryDetailParameter01(),
) {
    Logger.getLogger("MuseumStoreRoomPickerDialog01").log(Level.INFO, "MuseumStoreRoomPickerDialog01 [-1]")
    if (dialogState.theDialogType != DialogType.TwinActions) { return }
    if (!dialogState.theDialogState) { return }
    Logger.getLogger("MuseumStoreRoomPickerDialog01").log(Level.INFO, "MuseumStoreRoomPickerDialog01 [-1]")

    val horizontalSpace = 10.dp
    val paddingHorizontal = 4.dp
    val paddingVertical = 4.dp

    theParameters.theAreaItems.forEach {
        Logger.getLogger("MuseumStoreRoomPickerDialog01").log(Level.INFO, "MuseumStoreRoomPickerDialog01 - area: [${it}]")
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
fun <Parameters> rememberGalleryDetailScreenParameters(
    navController: NavHostController,
    apiViewModel: MuseumApiModelDelegate,
    uiUtility: MuseumUiUtilityDelegate,
    fontSize: TextUnit = 20.sp,
    parametersFactory: (
        // 1-5
        theLocation1Value: TextFieldValue,
        theLocation1OnValueChange: (TextFieldValue) -> Unit,
        theLocation1Hint: String,
        theLocation1SelectAllOnFocus: Boolean,
        theLocation1OnFocusChanged: (FocusState) -> Unit,
        // 6-10
        theLocation2Value: TextFieldValue,
        theLocation2OnValueChange: (TextFieldValue) -> Unit,
        theLocation2Hint: String,
        theLocation2SelectAllOnFocus: Boolean,
        theLocation2OnFocusChanged: (FocusState) -> Unit,
        // 11-15
        theLocation3Value: TextFieldValue,
        theLocation3OnValueChange: (TextFieldValue) -> Unit,
        theLocation3Hint: String,
        theLocation3SelectAllOnFocus: Boolean,
        theLocation3OnFocusChanged: (FocusState) -> Unit,
        // 16-20
        thePlaceValue: TextFieldValue,
        thePlaceOnValueChange: (TextFieldValue) -> Unit,
        thePlaceHint: String,
        thePlaceSelectAllOnFocus: Boolean,
        thePlaceOnFocusChanged: (FocusState) -> Unit,
        // 21-25
        theRemarkValue: TextFieldValue,
        theRemarkOnValueChange: (TextFieldValue) -> Unit,
        theRemarkHint: String,
        theRemarkSelectAllOnFocus: Boolean,
        theRemarkOnFocusChanged: (FocusState) -> Unit,
        // 26
        theOnSaveButtonClick: () -> Unit,
        // 27-30
        theAreaHint: String,
        theAreaDropDownValue: TextFieldValue,
        onAreaDropDownMenuItemChange: (String) -> Unit,
        theAreaValue: TextFieldValue,
        // 31-36
        theAreaOnValueChange: (TextFieldValue) -> Unit,
        theAreaSelectAllOnFocus: Boolean,
        theAreaOnFocusChanged: (FocusState) -> Unit,
        theAreaFocusRequester: FocusRequester,
        theAreaOnDone: KeyboardActionScope.() -> Unit,
        theAreaItems: List<String>,
        // 37-40
        theStoreRoomListDataState: ListDataState<TextLabelDelegate>,
        theStoreRoomItemClickCallback: HolderItemClickDelegate<TextLabelDelegate>,
        theStoreRoomPickerDialogState: State<BuiltInDialogStateDelegate>,
        theStoreRoomIconClick: () -> Unit,
        // 41-45
        theScrollableThreshold: Int,
        theSelectedTabIndex: Int,
        theTabItems: List<TabItemDelegate>,
        theOnTabItemClick: (Int) -> Unit,
        theTabTextFontSize: TextUnit,
        // 46-47
        theItemClickCallback: HolderItemClickDelegate<ThreeTextsDelegate>,
        theListDataState: State<ListDataState<ThreeTextsDelegate>>,
        // 48-50
        theHasScreenBeenInited: Boolean,
        theCoroutineScope: CoroutineScope,
        theLaunchedEffectBlock: suspend CoroutineScope.() -> Unit,
        // 51-55
        theGalleryItem: RfGalleryItemDelegate,
        theScrollState: ScrollState,
        theFontSize: TextUnit,
        theReferencedWidth: Dp,
        theTopAppBarTitle: String,
        // 56-57
        theThreeTextsLabel: ThreeTextsDelegate,
        theGalleryDetailDialogState: State<BuiltInDialogStateDelegate>,
    ) -> Parameters
): Parameters where Parameters : GalleryDetailParameterDelegate {

    val context = LocalContext.current

    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    var hasScreenBeenInited: Boolean by rememberSaveable { mutableStateOf(false) }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    val scrollState = rememberScrollState()

    val referencedWidth = builtInMeasureTextWidth("實體組件數：", style = TextStyle(fontSize = fontSize))

    var selectedTabIndex: Int by remember { mutableIntStateOf(0) }


    val galleryDetails = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_gallery_details)
    val galleryProperties = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_gallery_properties)
    val galleryReadLog = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_gallery_readLog)

    val pickACurrentLocation = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_pick_a_current_location)

    val remindTitle = stringResource(tw.com.wtc.mis.android.compose.R.string.base_message_remind_title)
    val confirmTitle = stringResource(tw.com.wtc.mis.android.compose.R.string.base_action_confirm)
    val cancelTitle = stringResource(tw.com.wtc.mis.android.compose.R.string.base_action_cancel)

    val beingProcessing = stringResource(tw.com.wtc.mis.android.compose.R.string.base_being_processing)
    val beingUpdating = stringResource(tw.com.wtc.mis.android.compose.R.string.base_being_updating)
    val saveSuccessfully = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_save_successfully)
    val failToSave = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_fail_to_save)

    // <editor-fold desc="tabItems">
    val tabItems = remember {
        mutableStateListOf<TabItemDelegate>(
            SimpleTabDelegateImpl(theIdentifier = "001", theDescription = galleryProperties),
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
    val galleryItemState: MutableState<RfGalleryItemDelegate> = remember {
        mutableStateOf(RfGalleryItemImpl())
    }
    // </editor-fold desc="galleryItemState">

    // <editor-fold desc="TextFieldValues">
    val initText = ""
    var location1Value: TextFieldValue by rememberTextFieldValueFrom(galleryItemState.value.theLocation1)
    var location2Value: TextFieldValue by rememberTextFieldValueFrom(galleryItemState.value.theLocation2)
    var location3Value: TextFieldValue by rememberTextFieldValueFrom(galleryItemState.value.theLocation3)
    var placeValue: TextFieldValue by rememberTextFieldValueFrom(galleryItemState.value.thePlace)
    var remarkValue: TextFieldValue by rememberTextFieldValueFrom(galleryItemState.value.theRemark)
    var areaValue: TextFieldValue by rememberTextFieldValueFrom(initText)
    // </editor-fold desc="TextFieldValues">

    // <editor-fold desc="FocusIndicators">
    var location1FocusIndicator by remember { mutableIntStateOf(0) }
    var location2FocusIndicator by remember { mutableIntStateOf(0) }
    var location3FocusIndicator by remember { mutableIntStateOf(0) }
    var placeFocusIndicator by remember { mutableIntStateOf(0) }
    var remarkFocusIndicator by remember { mutableIntStateOf(0) }
    var areaFocusIndicator by remember { mutableIntStateOf(0) }
    // </editor-fold desc="FocusIndicators">

    // [start] added by elite_lin - 2025/04/18

    // <editor-fold desc="FocusRequesters">
    val areaFocusRequester: FocusRequester = remember { FocusRequester() }
    // </editor-fold desc="FocusRequesters">

    // <editor-fold desc="areaItems">
    val areaMap: MutableState<Map<String, MuseumRfStoreRoomSummary>> = remember { mutableStateOf(emptyMap()) }
    val areaItems: MutableState<List<String>> = remember { mutableStateOf(emptyList()) }
    val selectedAreaItemsItem: MutableState<MuseumRfStoreRoomSummary?> = rememberNullableObjectFrom { null }
    // </editor-fold desc="areaItems">

    // <editor-fold desc="storeRoomItems">
    val storeRoomItems: MutableState<List<StoreRoomItemDelegate>> = remember { mutableStateOf(emptyList()) }
    val selectedStoreRoomItem: MutableState<StoreRoomItemDelegate?> = remember { mutableStateOf(null) }
    // </editor-fold desc="storeRoomItems">

    // <editor-fold desc="updateArea">
    fun updateArea(area: String) {
        val summary: MuseumRfStoreRoomSummary? = areaMap.value[area]
        selectedAreaItemsItem.value = summary
        val display = summary?.theArea ?: ""
        areaValue = TextFieldValue(text = display, selection = TextRange(display.length))
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
        theTitle = pickACurrentLocation,
        theConfirmTitle = confirmTitle,
        theCancelTitle = cancelTitle,
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
        theMessage = beingUpdating,
        onDismiss = { galleryDetailDialogState.value = BuiltInDialogStateImpl() }
    )
    // </editor-fold desc="progressDialogState">

    // <editor-fold desc="singleActionDialogState">
    val singleActionDialogState = MutableBuiltInDialogStateImpl(
        theDialogType = DialogType.SingleAction,
        theDialogState = true,
        theTitle = remindTitle,
        theConfirmTitle = confirmTitle,
        theCancelTitle = cancelTitle,
        onDismiss = { galleryDetailDialogState.value = BuiltInDialogStateImpl() }
    )
    // </editor-fold desc="singleActionDialogState">

    // <editor-fold desc="reloadBlock">
    val reloadBlock: () -> Unit = {
        apiViewModel.galleryDetailResultStateFlow.value = null

        val authData: AuthData? = apiViewModel.theAuthData()
        val selectedGallery: RfGalleryItemDelegate? = apiViewModel.selectedGallery
        if ((null != authData) && (null != selectedGallery)) {
            galleryDetailDialogState.value = progressDialogState
            readLogListDataState.value = ListDataState.Loading()
            apiViewModel.callGalleryDetailApi(
                scope = apiViewModel,
                apiDomain = uiUtility.getApiDomain(),
                authToken = authData.authToken,
                queryModel = uiUtility.buildGalleryQueryModel(
                    keyword = "",
                    nameCht = "",
                    galleryCode = selectedGallery.theCode
                ),
            )

            apiViewModel.callReadLogPassiveListApi(
                scope = apiViewModel,
                apiDomain = uiUtility.getApiDomain(),
                authToken = authData.authToken,
                queryModel = uiUtility.buildReadLogPassiveQueryModel(tagId = galleryItemState.value.theCode)
            )
        }
        else {
            Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters - authData is null: [${null == authData}], selectedGallery is null: [${null == selectedGallery}]")
        }
    }
    // </editor-fold desc="reloadBlock">

    // <editor-fold desc="StoreRoomListResultStateFlowCollector">
    val storeRoomListResultX: FmApiResult<List<MuseumRfStoreRoomSummary>>? by apiViewModel.storeRoomListResultStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(storeRoomListResultX) {
        val storeRoomListResult = storeRoomListResultX ?: return@LaunchedEffect

        when(storeRoomListResult) {
            is FmApiResult.Success -> {
                Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters - StoreRoomListResultStateFlowCollector： [Success]")

                val areaListX = mutableListOf<String>()
                val areaMapX = mutableMapOf<String, MuseumRfStoreRoomSummary>()

                storeRoomListResult.data.forEach { item: MuseumRfStoreRoomSummary ->
                    Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters - StoreRoomListResultStateFlowCollector - Area: [${item.theArea}], item.count: [${item.itemCount}]")
//                    item.items.forEach { storeRoomItem: StoreRoomItemDelegate ->
//                        Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - StoreRoomListResultStateFlowCollector - storeRoomItem: [${storeRoomItem}]")
//                    }
                    areaListX.add(item.theArea)
                    areaMapX[item.theArea] = item
                }
                areaItems.value = areaListX
                areaMap.value = areaMapX
            }
            is FmApiResult.Error -> {
                Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters - StoreRoomListResultStateFlowCollector： [Error]", storeRoomListResult.cause)
            }
        }
    }
    // </editor-fold desc="StoreRoomListResultStateFlowCollector">

    // <editor-fold desc="QueryGalleryDetailResultStateFlowCollector">
    val queryGalleryDetailResultX: FmApiResult<List<RfGalleryItemDelegate>>? by apiViewModel.galleryDetailResultStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(queryGalleryDetailResultX) {
        val queryGalleryDetailResult = queryGalleryDetailResultX ?: return@LaunchedEffect

        galleryDetailDialogState.value = BuiltInDialogStateImpl()

        when(queryGalleryDetailResult) {
            is FmApiResult.Success -> {
                Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters - QueryGalleryDetailResultStateFlowCollector: [Success]")
                val resultList: List<RfGalleryItemDelegate> = queryGalleryDetailResult.data
                if (resultList.isNotEmpty()) {
                    apiViewModel.selectedGallery = resultList[0]
                    galleryItemState.value = resultList[0]
                    location1Value = TextFieldValue(galleryItemState.value.theLocation1)
                    location2Value = TextFieldValue(galleryItemState.value.theLocation2)
                    location3Value = TextFieldValue(galleryItemState.value.theLocation3)
                    placeValue = TextFieldValue(galleryItemState.value.thePlace)
                    remarkValue = TextFieldValue(galleryItemState.value.theRemark)
                    //Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - [${galleryItem}]")
                }
            }
            is FmApiResult.Error -> {
                val errorObject = queryGalleryDetailResult.cause
                Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.SEVERE, "rememberGalleryDetailScreenParameters - QueryGalleryDetailResultStateFlowCollector： .Error]", errorObject)

                apiViewModel.accountReAuthIfNeeded(scope = apiViewModel, cause = errorObject)
            }
        }

        apiViewModel.galleryDetailResultStateFlow.value = null
    }
    // </editor-fold desc="QueryGalleryDetailResultStateFlowCollector">

    // <editor-fold desc="QueryReadLogPassiveListResultStateFlowCollector">
    val queryReadLogListResultX: FmApiResult<List<RfReadLogPassiveDelegate>>? by apiViewModel.queryReadLogPassiveListResultStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(queryReadLogListResultX) {
        val queryReadLogListResult = queryReadLogListResultX ?: return@LaunchedEffect

        readLogListDataState.value = when(queryReadLogListResult) {
            is FmApiResult.Success -> {
                Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters - QueryReadLogPassiveListResultStateFlowCollector: [Success]")
                val resultList: List<ThreeTextsDelegate> = withContext(Dispatchers.Default) {
                    queryReadLogListResult.data.map { readLogItem: RfReadLogPassiveDelegate ->
                        ThreeTextsImpl(
                            theFirst = readLogItem.theReadTime.replace("T", " "),
                            theSecond = readLogItem.theDeviceDescription,
                            theThird = readLogItem.theReadLogPassiveId,)
                    }
                }
                Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters - QueryReadLogPassiveListResultStateFlowCollector - resultList.size: [${resultList.size}]")
                if (resultList.isNotEmpty()) {
                    ListDataState.Available<ThreeTextsDelegate>(theList = resultList)
                }
                else {
                    defaultReadLogUnavailableResult01()
                }
            }
            is FmApiResult.Error -> {
                val errorObject = queryReadLogListResult.cause
                Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.SEVERE, "rememberGalleryDetailScreenParameters - QueryReadLogPassiveListResultStateFlowCollector： [Error]", errorObject)
                apiViewModel.accountReAuthIfNeeded(scope = apiViewModel, cause = errorObject)
                defaultReadLogUnavailableResult01()
            }
        }

        apiViewModel.queryReadLogPassiveListResultStateFlow.value = null
    }
    // </editor-fold desc="QueryReadLogPassiveListResultStateFlowCollector">

    // <editor-fold desc="GalleryUpdateResultStateFlowCollector">
    val galleryUpdateResultResultX: FmApiResult<CommonApiResponse>? by apiViewModel.galleryUpdateResultStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(galleryUpdateResultResultX) {
        val galleryUpdateResult = galleryUpdateResultResultX ?: return@LaunchedEffect

        when(galleryUpdateResult) {
            is FmApiResult.Success -> {
                Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters - GalleryUpdateResultStateFlowCollector - [Success]")
                galleryDetailDialogState.value = singleActionDialogState.apply {
                    theMessage = saveSuccessfully
                    onConfirm =  {
                        coroutineScope.launch {
                            reloadBlock()
                        }
                    }
                }
            }
            is FmApiResult.Error -> {
                Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.SEVERE, "rememberGalleryDetailScreenParameters - GalleryUpdateResultStateFlowCollector - [Error]", galleryUpdateResult.cause)
                apiViewModel.accountReAuthIfNeeded(scope = apiViewModel, cause = galleryUpdateResult.cause)

                galleryDetailDialogState.value = singleActionDialogState.apply {
                    theMessage = failToSave
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
    // </editor-fold desc="GalleryUpdateResultStateFlowCollector">

    return parametersFactory(
        // 1-5
        location1Value,
        { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, location1FocusIndicator)
            location1Value = newValue
            Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters() - onValueChange() - location1FocusIndicator: [$location1FocusIndicator], location1Value： [${location1Value.text}]")
        },
        "庫位",
        false,
        {
            val pair: Pair<TextFieldValue, Int> = location1Value.builtInTextFieldFocusChangedHandler01(true, it)
            location1FocusIndicator = pair.second
            location1Value = pair.first
            Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters() - onFocusChanged() - location1FocusIndicator: [$location1FocusIndicator], location1Value： [${location1Value.text}]")
        },
        // 6-10
        location2Value,
        { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, location2FocusIndicator)
            location2Value = newValue
            Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters() - onValueChange() - location2FocusIndicator: [$location2FocusIndicator], location2Value： [${location2Value.text}]")
        },
        "櫃位",
        false,
        {
            val pair: Pair<TextFieldValue, Int> = location2Value.builtInTextFieldFocusChangedHandler01(true, it)
            location2FocusIndicator = pair.second
            location2Value = pair.first
            Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters() - onFocusChanged() - location2FocusIndicator: [$location2FocusIndicator], location2Value： [${location2Value.text}]")
        },
        // 11-15
        location3Value,
        { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, location3FocusIndicator)
            location3Value = newValue
            Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters() - onValueChange() - location3FocusIndicator: [$location3FocusIndicator], location3Value： [${location3Value.text}]")
        },
        "網架",
        false,
        {
            val pair: Pair<TextFieldValue, Int> = location3Value.builtInTextFieldFocusChangedHandler01(true, it)
            location3FocusIndicator = pair.second
            location3Value = pair.first
            Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters() - onFocusChanged() - location3FocusIndicator: [$location3FocusIndicator], location3Value： [${location3Value.text}]")
        },
        // 16-20
        placeValue,
        { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, placeFocusIndicator)
            placeValue = newValue
            Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters() - onValueChange() - placeFocusIndicator: [$placeFocusIndicator], placeValue： [${placeValue.text}]")
        },
        "暫存位置",
        false,
        {
            val pair: Pair<TextFieldValue, Int> = placeValue.builtInTextFieldFocusChangedHandler01(true, it)
            placeFocusIndicator = pair.second
            placeValue = pair.first
            Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters() - onFocusChanged() - placeFocusIndicator: [$placeFocusIndicator], placeValue： [${placeValue.text}]")
        },
        // 21-25
        remarkValue,
        { newValue: TextFieldValue ->
            val pair: Pair<TextFieldValue, Int> = newValue.builtInTextFieldValueChangeHandler01(true, remarkFocusIndicator)
            remarkValue = newValue
            Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters() - onValueChange() - remarkFocusIndicator: [$remarkFocusIndicator], remarkValue： [${remarkValue.text}]")
        },
        "備註",
        false,
        {
            val pair: Pair<TextFieldValue, Int> = remarkValue.builtInTextFieldFocusChangedHandler01(true, it)
            remarkFocusIndicator = pair.second
            remarkValue = pair.first
            Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters() - onFocusChanged() - remarkFocusIndicator: [$remarkFocusIndicator], remarkValue： [${remarkValue.text}]")
        },
        // 26
        {
            Logger.getLogger("rememberGalleryDetailScreenParameters").log(Level.INFO, "rememberGalleryDetailScreenParameters() - theOnSaveButtonClick() - location1: [${location1Value.text}], location2: [${location2Value.text}], location3: [${location3Value.text}], place: [${placeValue.text}], remark: [${remarkValue.text}]")
            reloadBlock()
        },
        // 27-30
        "區域",
        areaValue,
        { item: String ->
            coroutineScope.launch {
                updateArea(item)
            }
        },
        areaValue,
        // 31-36
        { newValue: TextFieldValue ->
            areaValue = newValue
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters() - theAreaOnValueChange() - areaFocusIndicator: [$areaFocusIndicator], areaValue： [${areaValue.text}]")
        },
        false,
        {
            val pair: Pair<TextFieldValue, Int> = areaValue.builtInTextFieldFocusChangedHandler01(true, it)
            areaFocusIndicator = pair.second
            areaValue = pair.first
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters() - theAreaOnFocusChanged() - areaFocusIndicator: [$areaFocusIndicator], areaValue： [${areaValue.text}]")
        },
        areaFocusRequester,
        {
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters() - theAreaOnDone")
        },
        areaItems.value,
        // 37-40
        storeRoomItems.value.toAvailableState(),
        HolderItemClickDelegate<TextLabelDelegate> { dataObject: TextLabelDelegate, action: String, position: Int ->
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - theStoreRoomItemClickCallback - delegate: [${dataObject.theDescription}], action: [$action], position: [$position]")
            storeRoomPickerDialogState.value = BuiltInItemPickerDialogStateImpl()
            if (dataObject !is StoreRoomItemDelegate) { return@HolderItemClickDelegate }
            coroutineScope.launch {
                selectedStoreRoomItem.value = dataObject
                placeValue = TextFieldValue(text = dataObject.theDataObject.theName, selection = TextRange(dataObject.theDataObject.theName.length))
                updateArea("")
            }
        },
        storeRoomPickerDialogState,
        {
            coroutineScope.launch {
                storeRoomPickerDialogState.value = storeRoomPickerTwinActionsDialogState
            }
        },
        // 41-45
        3,
        selectedTabIndex,
        tabItems,
        { index: Int -> selectedTabIndex = index },
        20.sp,
        // 46-47
        DefaultHolderCellClickHandler<ThreeTextsDelegate>(),
        readLogListDataState,
        // 48-50
        hasScreenBeenInited,
        coroutineScope,
        {
            Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - launchedEffectBlock -  hasScreenBeenInited: [$hasScreenBeenInited]!")
            if (!hasScreenBeenInited) {
                Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - launchedEffectBlock - run init!!")
                delay(1000)
                reloadBlock()
                hasScreenBeenInited = true
            }
            else { Logger.getLogger("rememberGalleryDetailParameters").log(Level.INFO, "rememberGalleryDetailParameters - launchedEffectBlock - no action!!") }
        },
        // 51-55
        galleryItemState.value,
        scrollState,
        fontSize,
        referencedWidth,
        galleryDetails,
        // 56-57
        ThreeTextsImpl(theFirst = "行政區：", theSecond = "地號：",),
        galleryDetailDialogState,
    )
}

///


interface GalleryDetailsItemsDelegate {
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



interface GalleryDetailsStormRoomDelegate {
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
    val theStoreRoomListDataState: ListDataState<TextLabelDelegate>
    val theStoreRoomItemClickCallback: HolderItemClickDelegate<TextLabelDelegate>
    val theStoreRoomPickerDialogState: State<BuiltInDialogStateDelegate>
    val theStoreRoomIconClick: () -> Unit
}


interface GalleryDetailParameterDelegate :
    IBuiltInLaunchEffectTabListScreenParameters01<ThreeTextsDelegate>,
    GalleryDetailsItemsDelegate,
    GalleryDetailsStormRoomDelegate
{
    val theGalleryItem: RfGalleryItemDelegate
    val theScrollState: ScrollState
    val theFontSize: TextUnit
    val theReferencedWidth: Dp
    val theTopAppBarTitle: String
    val theThreeTextsLabel: ThreeTextsDelegate
    val theGalleryDetailDialogState: State<BuiltInDialogStateDelegate>
}


data class BuiltInGalleryDetailParameter01(
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

    override val theOnSaveButtonClick: () -> Unit = {},

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
    override val theStoreRoomListDataState: ListDataState<TextLabelDelegate> = ListDataState.Available(),
    override val theStoreRoomItemClickCallback: HolderItemClickDelegate<TextLabelDelegate> = DefaultHolderCellClickHandler<TextLabelDelegate>(),
    override val theStoreRoomPickerDialogState: State<BuiltInDialogStateDelegate> =
        mutableStateOf(BuiltInDialogStateImpl()),
    override val theStoreRoomIconClick: () -> Unit = {},

    override val theScrollableThreshold: Int = 3,
    override val theSelectedTabIndex: Int = 0,
    override val theTabItems: List<TabItemDelegate> = listOf<TabItemDelegate>(),
    override val theOnTabItemClick: (Int) -> Unit = {},
    override val theTabTextFontSize: TextUnit = 20.sp,

    override val theItemClickCallback: HolderItemClickDelegate<ThreeTextsDelegate> = DefaultHolderCellClickHandler<ThreeTextsDelegate>(),
    override val theListDataState: State<ListDataState<ThreeTextsDelegate>> = mutableStateOf<ListDataState<ThreeTextsDelegate>>(ListDataState.None()),

    override val theHasScreenBeenInited: Boolean = false,
    override val theCoroutineScope: CoroutineScope = BuiltInCoroutineScopeImpl(),
    override val theLaunchedEffectBlock: suspend CoroutineScope.() -> Unit = {},

    override val theGalleryItem: RfGalleryItemDelegate = RfGalleryItemImpl(),
    override val theScrollState: ScrollState = ScrollState(initial = 0),
    override val theFontSize: TextUnit = 20.sp,
    override val theReferencedWidth: Dp = 0.dp,
    override val theTopAppBarTitle: String = "藏品詳情",

    override val theThreeTextsLabel: ThreeTextsDelegate = ThreeTextsImpl(),
    override val theGalleryDetailDialogState: State<BuiltInDialogStateDelegate> = mutableStateOf<BuiltInDialogStateDelegate>(
        BuiltInDialogStateImpl()
    ),
) : GalleryDetailParameterDelegate


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


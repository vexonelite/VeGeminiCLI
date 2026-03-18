

@Composable
fun MuseumGalleryApplyDetailsScreen(
    navController: NavHostController,
    galleryApplyDetailQueryParameters: GalleryApplyDetailQueryParameterDelegate = BuiltInGalleryApplyDetailQueryParameter01(),
    tag: String = "MuseumGalleryApplyDetails",
) {
    Logger.getLogger("MuseumGalleryApplyDetailsScreen").log(Level.INFO, "MuseumGalleryApplyDetailsScreen - time: [${System.currentTimeMillis()}]")

    BuiltInTopAppBarListScreen01(
        inputParameters = BuiltInBottomSheetListScreenParameters01<GalleryItemDelegate>(
            theNavController = navController,
            theRememberedParameters = galleryApplyDetailQueryParameters,
            theAvailableListContent = {
                val itemList: List<GalleryItemDelegate> = galleryApplyDetailQueryParameters.theListDataState.value.theList
                items(
                    items = ImmutableObjectList<GalleryItemDelegate>(itemList).objectList,
                    key = { galleryApplyDetailItem: GalleryItemDelegate -> galleryApplyDetailItem.theIdentifier },
                ) { galleryApplyDetailItem: GalleryItemDelegate ->
                    GalleryListItem01(
                        gallery = galleryApplyDetailItem,
                        itemClickCallback = galleryApplyDetailQueryParameters.theItemClickCallback,
                    )
                }
            },
            theUnavailableContent = {
                val itemList: List<GalleryItemDelegate> = galleryApplyDetailQueryParameters.theListDataState.value.theList
                val unavailableItem: GalleryItemDelegate? = if (itemList.isNotEmpty()) { itemList[0] } else { null }
                ListDataUnavailable01(
                    unavailableText = unavailableItem?.theDescription ?: "",
                    onClick = {
                        if (null != unavailableItem) {
                            galleryApplyDetailQueryParameters.theItemClickCallback.onHolderItemClicked(unavailableItem, BaseActions.REFRESH, -1)
                        }
                    },
                )
            },
            theHeaderContent = {
                Text(
                    text = "${galleryApplyDetailQueryParameters.theSelectedGalleryApply.theDestination}\n${galleryApplyDetailQueryParameters.theSelectedGalleryApply.theApplyNo}",
                    fontSize = 20.sp,
                    color = Green002,
                    textAlign = TextAlign.Start,
                    //maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        //.background(Grey80)
                        .padding(horizontal = 4.dp, vertical = 6.dp)
                )
            },
            theBottomContent = {
                BuiltInSaveButton01(
                    buttonOnClick = galleryApplyDetailQueryParameters.theOnSaveButtonClick
                )
            },
            theTopAppBarTitle = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.menu_in_out_bound),
            theNavigationActionContent = {
                IconButton(
                    onClick = galleryApplyDetailQueryParameters.theOnReloadButtonClick
                ) {
                    Icon(Icons.Filled.Repeat, null, tint = Color.White)
                }
            },
            theListDataInitText = "",
            theTotalNumberVisibility = false,
            theTag = tag,
        ),
    )

    BuiltInDialogSet01(dialogState = galleryApplyDetailQueryParameters.theGalleryApplyDetailDialogState.value)
}


@Composable
fun <Parameters> rememberGalleryApplyDetailQueryScreenParameters(
    navController: NavHostController,
    apiViewModel: MuseumApiModelDelegate,
    uiUtility: MuseumUiUtilityDelegate,
    rfidConfig: RfidDeviceConfigDelegate,
    tabListFactory: MuseumRfidTabListFactoryDelegate,
    parametersFactory: (
        // 1-4
        theSelectedGalleryApply: RfGalleryApplyExt02Delegate,
        theGalleryApplyDetailDialogState: State<BuiltInDialogStateDelegate>,
        theOnReloadButtonClick: () -> Unit,
        theOnSaveButtonClick: () -> Unit,
        // 5-9
        theScrollableThreshold: Int,
        theSelectedTabIndex: Int,
        theTabItems: List<TabItemDelegate>,
        theOnTabItemClick: (Int) -> Unit,
        theTabTextFontSize: TextUnit,
        // 10-11
        theItemClickCallback: HolderItemClickDelegate<GalleryItemDelegate>,
        theListDataState: State<ListDataState<GalleryItemDelegate>>,
        // 12-14
        theHasScreenBeenInited: Boolean,
        theCoroutineScope: CoroutineScope,
        theLaunchedEffectBlock: suspend CoroutineScope.() -> Unit,
    ) -> Parameters
): Parameters where Parameters : GalleryApplyDetailQueryParameterDelegate {

    val context = LocalContext.current

    // <editor-fold desc="selectedTabIndex">
    var selectedTabIndex: Int by rememberIntValueFrom {
        apiViewModel.galleryApplyDetailSelectedTabIndexStateFlow.value
    }
    // </editor-fold desc="selectedTabIndex">

    // <editor-fold desc="tabItems">
    val tabItems: MutableState<List<TabItemDelegate>> = remember {
        mutableStateOf(
            mutableListOf<MuseumBadgetTabItemImpl>().apply { addAll(tabListFactory.theGalleryApplyDetailTabList) }
        )
    }
    // </editor-fold desc="tabItems">

    // <editor-fold desc="selectedGalleryApply">
    val selectedGalleryApply: RfGalleryApplyExt02Delegate by rememberNonNullObjectFrom {
        apiViewModel.selectedGalleryApply ?: RfGalleryApplyExtImpl()
    }
    // </editor-fold desc="selectedGalleryApply">

    // <editor-fold desc="listDataState">
    val listDataState: MutableState<ListDataState<GalleryItemDelegate>> = remember {
        mutableStateOf(ListDataState.None())
    }
    // </editor-fold desc="listDataState">

    var hasScreenBeenInited: Boolean by rememberSaveable { mutableStateOf(false) }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    // <editor-fold desc="galleryApplyDetailDialogState">
    val galleryApplyDetailDialogState: MutableState<BuiltInDialogStateDelegate> = remember {
        mutableStateOf(BuiltInDialogStateImpl())
    }
    // </editor-fold desc="galleryApplyDetailDialogState">

    val remindTitle = stringResource(tw.com.wtc.mis.android.compose.R.string.base_message_remind_title)
    val confirmTitle = stringResource(tw.com.wtc.mis.android.compose.R.string.base_action_confirm)
    val cancelTitle = stringResource(tw.com.wtc.mis.android.compose.R.string.base_action_cancel)

    val beingProcessing = stringResource(tw.com.wtc.mis.android.compose.R.string.base_being_processing)
    val beingUpdating = stringResource(tw.com.wtc.mis.android.compose.R.string.base_being_updating)
    val updateSuccessfully = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_update_successfully)
    val failToUpdate = stringResource(tw.com.wtc.mis.museum_rfid.compose_ui.R.string.prompt_fail_to_update)

    // <editor-fold desc="progressDialogState">
    val progressDialogState = BuiltInDialogStateImpl(
        theDialogType = DialogType.Progress,
        theDialogState = true,
        theMessage = beingUpdating,
        onDismiss = { galleryApplyDetailDialogState.value = BuiltInDialogStateImpl() }
    )
    // </editor-fold desc="progressDialogState">

    // <editor-fold desc="singleActionDialogState">
    val singleActionDialogState = MutableBuiltInDialogStateImpl(
        theDialogType = DialogType.SingleAction,
        theDialogState = true,
        theTitle = remindTitle,
        theConfirmTitle = confirmTitle,
        theCancelTitle = cancelTitle,
        onDismiss = { galleryApplyDetailDialogState.value = BuiltInDialogStateImpl() }
    )
    // </editor-fold desc="singleActionDialogState">

    // <editor-fold desc="reloadData">
    val reloadData: () -> Unit = {
        val authData: AuthData? = apiViewModel.theAuthData()
        val selectedGalleryApplyX: RfGalleryApplyExt02Delegate? = apiViewModel.selectedGalleryApply
        if ((null != authData) && (null != selectedGalleryApplyX)) {
            listDataState.value = ListDataState.Loading()
            apiViewModel.apply {
                galleryApplyDetailListStateFlow.value = null
                clearGalleryApplyDetailMaps()
                apiViewModel.callGalleryApplyDetailListApi(
                    scope = apiViewModel,
                    apiDomain = uiUtility.getApiDomain(),
                    authToken = authData.authToken,
                    queryModel = uiUtility.buildRfGalleryApplyDetailQueryModel(
                        galleryApplyId = selectedGalleryApplyX.theGalleryApplyId
                    ),
                )
            }
        }
        else {
            Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryScreenParameters - authData is null: [${null == authData}], selectedGalleryApply is null: [${null == selectedGalleryApplyX}]")
        }
    }
    // </editor-fold desc="reloadData">

    // <editor-fold desc="GalleryApplyDetailListResultStateFlowCollector">
    val queryGalleryApplyDetailListResultX: FmApiResult<List<RfGalleryApplyDetailDelegate>>? by apiViewModel.galleryApplyDetailListResultStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(queryGalleryApplyDetailListResultX) {
        val queryGalleryApplyDetailListResult = queryGalleryApplyDetailListResultX ?: return@LaunchedEffect

        when(queryGalleryApplyDetailListResult) {
            is FmApiResult.Success -> {
                Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryScreenParameters - QueryGalleryApplyDetailListResultStateFlowCollector: [Success]")
                apiViewModel.apply {
                    updateCachedGalleryApplyDetailList(queryGalleryApplyDetailListResult.data)
                    reBuildGalleryApplyDetailDataSet(
                        scope = apiViewModel,
                        galleryApplyDetailList = queryGalleryApplyDetailListResult.data,
                        index = selectedTabIndex
                    )
                }
            }
            is FmApiResult.Error -> {
                Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.SEVERE, "rememberGalleryApplyDetailQueryScreenParameters - QueryGalleryApplyDetailListResultStateFlowCollector： .Error]", queryGalleryApplyDetailListResult.cause)
                apiViewModel.accountReAuthIfNeeded(scope = apiViewModel, cause = queryGalleryApplyDetailListResult.cause)
                listDataState.value = defaultGalleryApplyDetailQueryUnavailableResult01()
            }
        }
    }
    // </editor-fold desc="GalleryApplyDetailListResultStateFlowCollector">

    // <editor-fold desc="GalleryApplyDetailListStateFlowCollector">
    val galleryApplyDetailListX: List<GalleryApplyDetailItemDelegate>? by apiViewModel.galleryApplyDetailListStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(galleryApplyDetailListX) {
        galleryApplyDetailListX?.let { galleryApplyDetailList ->
            Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryScreenParameters - GalleryApplyDetailListStateFlowCollector - galleryApplyDetailList.size: [${galleryApplyDetailList.size}]")
            listDataState.value = ListDataState.Available<GalleryItemDelegate>(theList = galleryApplyDetailList)
        }
    }
    // </editor-fold desc="GalleryApplyDetailListStateFlowCollector">

    // <editor-fold desc="GalleryApplyDetailTabListStateFlowCollector">
    val galleryApplyTabListX: List<TabItemDelegate>? by apiViewModel.galleryApplyDetailTabListStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(galleryApplyTabListX) {
        galleryApplyTabListX?.let { galleryApplyTabList ->
            Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryScreenParameters - GalleryApplyDetail [Tab] ListStateFlowCollector")
            tabItems.value = galleryApplyTabList
        }
    }
    // </editor-fold desc="GalleryApplyDetailTabListStateFlowCollector">

    // <editor-fold desc="GalleryApplyDetailUpdateBatchResultStateFlowCollector">
    val galleryApplyDetailUpdateBatchResultX: FmApiResult<CommonApiResponse>? by apiViewModel.galleryApplyDetailUpdateBatchResultStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(galleryApplyDetailUpdateBatchResultX) {
        val apiResult = galleryApplyDetailUpdateBatchResultX ?: return@LaunchedEffect

        Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryScreenParameters - InventoryDetailUpdateBatchResultStateFlowCollector")

        when(apiResult) {
            is FmApiResult.Success -> {
                Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryScreenParameters - GalleryApplyDetailUpdateBatchResultStateFlowCollector: [Success]")
                reloadData()

                val dialogState: BuiltInDialogStateDelegate =
                    singleActionDialogState.apply {
                        theMessage = updateSuccessfully
                        onConfirm = {
                            galleryApplyDetailDialogState.value = BuiltInDialogStateImpl()
                        }
                    }
                if (galleryApplyDetailDialogState.value.theDialogType != DialogType.SingleAction) {
                    galleryApplyDetailDialogState.value = dialogState
                }
            }
            is FmApiResult.Error -> {
                Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.SEVERE, "rememberGalleryApplyDetailQueryScreenParameters - GalleryApplyDetailUpdateBatchResultStateFlowCollector： [Error]", apiResult.cause)

                apiViewModel.accountReAuthIfNeeded(scope = apiViewModel, cause = apiResult.cause)

                val dialogState: BuiltInDialogStateDelegate =
                    apiResult.cause.appApiExceptionHandlerExt(
                        context = context,
                        title = remindTitle,
                        message = failToUpdate,
                        onDismiss = {
                            galleryApplyDetailDialogState.value = BuiltInDialogStateImpl()
                        },
                        onConfirm = {
                            galleryApplyDetailDialogState.value = BuiltInDialogStateImpl()
                        },
                    )
                if (galleryApplyDetailDialogState.value.theDialogType != DialogType.SingleAction) {
                    galleryApplyDetailDialogState.value = dialogState
                }
            }
        }

        // must do this since we need to involve ``reloadData()``
        apiViewModel.galleryApplyDetailUpdateBatchResultStateFlow.value = null
    }
    // </editor-fold desc="GalleryApplyDetailUpdateBatchResultStateFlowCollector">

    // <editor-fold desc="RFID StateFlowCollectors">
    // UHF Tag StateFlow
    val uhfTagX: RfidUhfTagDelegate? by rfidConfig.uhfTagReadResultStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(uhfTagX) {
        val uhfTag = uhfTagX ?: return@LaunchedEffect

        val parsedTag = rfidConfig.parseGalleryTagNo(uhfTag)
        apiViewModel.updateGalleryApplyDetailItemByDevice(apiViewModel, parsedTag, selectedTabIndex)
        rfidConfig.resetUhfTagReadResultStateFlow()
    }

    // Barcode StateFlow
    val barcodeX: BarcodeEntityDelegate? by rfidConfig.barcodeReadResultStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(barcodeX) {
        val barcodeResult = barcodeX ?: return@LaunchedEffect

        val readBarCode = rfidConfig.getBarcodeData(barcodeResult)
        apiViewModel.updateGalleryApplyDetailItemByDevice(apiViewModel, readBarCode, selectedTabIndex)
        rfidConfig.resetBarcodeReadResultStateFlow()
    }
    // </editor-fold desc="RFID StateFlowCollectors">

    return parametersFactory(
        // 1-4
        selectedGalleryApply,
        galleryApplyDetailDialogState,
        // <editor-fold desc="GalleryApplyDetailQueryParameters - onReloadButtonClick">
        {
            coroutineScope.launch {
                reloadData()
            }
        },
        // </editor-fold desc="GalleryApplyDetailQueryParameters - onReloadButtonClick">

        // <editor-fold desc="GalleryApplyDetailQueryParameters - onSaveButtonClick">
        {
            Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryScreenParameters - onSaveButtonClick")

            val authData: AuthData? = apiViewModel.theAuthData()
            if (null == authData) {
                Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.WARNING, "rememberGalleryApplyDetailQueryScreenParameters - onSaveButtonClick - AuthData is null!!")
                return@parametersFactory
            }

            val galleryApplyDetailList: List<GalleryApplyDetailItemDelegate>? = apiViewModel.galleryApplyDetailListStateFlow.value
            if (galleryApplyDetailList.isNullOrEmpty()) {
                Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.WARNING, "rememberGalleryApplyDetailQueryScreenParameters - onSaveButtonClick - galleryApplyDetailList is null or empty!!")
                return@parametersFactory
            }

            Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryScreenParameters - onSaveButtonClick - galleryApplyDetailList.size: [${galleryApplyDetailList.size}]")
            val statusSet = setOf<String>("I", "O")
            val newList: List<RfGalleryApplyDetailDelegate> = galleryApplyDetailList
                .filter { delegate: GalleryApplyDetailItemDelegate ->
                    (delegate.theGalleryApplyDetailItem.theStatus in statusSet) &&
                            (delegate.theHumanChecked || delegate.theRfidChecked)
                }
                .map { delegate: GalleryApplyDetailItemDelegate ->
                    uiUtility.cloneGalleryApplyDetailByStatus(
                        srcGalleryApplyDetail = delegate.theGalleryApplyDetailItem,
                        status = if (delegate.theGalleryApplyDetailItem.theStatus == "I") { "O" } else { "I" }
                    )
                }

            Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryScreenParameters - onSaveButtonClick - newList.size: [${newList.size}]")
            newList.forEach { applyDetailItem: RfGalleryApplyDetailDelegate ->
                Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryScreenParameters - applyDetailItem: [${applyDetailItem.theGalleryCode}, ${applyDetailItem.theStatus}]")
            }

            if (newList.isEmpty()) {
                Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.WARNING, "rememberGalleryApplyDetailQueryScreenParameters - onSaveButtonClick - galleryApplyDetailList is null or empty!!")
                coroutineScope.launch {
                    galleryApplyDetailDialogState.value = singleActionDialogState.apply {
                        theMessage = "尚未異動任何藏品出/在庫狀態!!"
                        onConfirm = {
                            galleryApplyDetailDialogState.value = BuiltInDialogStateImpl()
                        }
                    }
                }
                return@parametersFactory
            }

            coroutineScope.launch {
                galleryApplyDetailDialogState.value = progressDialogState
                apiViewModel.callGalleryApplyDetailUpdateBatchApi(
                    scope = apiViewModel,
                    apiDomain = uiUtility.getApiDomain(),
                    authToken = authData.authToken,
                    applyDetailList = newList,
                )
            }
        },
        // </editor-fold desc="GalleryApplyDetailQueryParameters - onSaveButtonClick">

        // 5-9
        3,
        selectedTabIndex,
        tabItems.value,
        // <editor-fold desc="GalleryApplyDetailQueryParameters - theOnTabItemClick">
        { index: Int ->
            selectedTabIndex = index
            apiViewModel.apply {
                galleryApplyDetailSelectedTabIndexStateFlow.update { selectedTabIndex }
                reBuildGalleryApplyDetailDataSet(
                    scope = apiViewModel,
                    galleryApplyDetailList = apiViewModel.cachedGalleryApplyDetailList,
                    index = selectedTabIndex
                )
            }
            Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryScreenParameters - theOnTabItemClick - selectedTabIndex: [$selectedTabIndex]!")
        },
        // </editor-fold desc="GalleryApplyDetailQueryParameters - theOnTabItemClick">
        20.sp,

        // 10-11
        // <editor-fold desc="GalleryApplyDetailQueryParameters - theItemClickCallback">
        HolderItemClickDelegate<GalleryItemDelegate> { dataObject: GalleryItemDelegate, action: String, position: Int ->
            Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryScreenParameters - itemClickCallback - delegate: [${dataObject.theDescription}], action: [$action], position: [$position]")
            if (dataObject !is GalleryApplyDetailItemImpl) {
                Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.WARNING, "rememberGalleryApplyDetailQueryScreenParameters - itemClickCallback - delegate: is not an instance of [GalleryApplyDetailItemDelegate]!!")
                return@HolderItemClickDelegate
            }

            coroutineScope.launch {
                when(action) {
                    BaseActions.TAP -> {
                        apiViewModel.selectedGallery = RfGalleryItemImpl(
                            theGalleryId = dataObject.theGalleryApplyDetailItem.theGalleryId,
                            theCode = dataObject.theGalleryApplyDetailItem.theGalleryCode,
                            theNameCht = dataObject.theGalleryApplyDetailItem.theGalleryNameCht,
                        )
                        navController.navigateToExt(uiUtility.navigateToGalleryDetailsScreen(apiViewModel.selectedGallery!!))
                    }
                    MuseumRfidActions.HUMAN_CHECK -> {
                        val clonedOne: GalleryApplyDetailItemImpl = dataObject.copy(theHumanChecked = !dataObject.theHumanChecked)
                        apiViewModel.updateGalleryApplyDetailItem(apiViewModel, clonedOne, selectedTabIndex)
                    }
                    MuseumRfidActions.RESET_STATE -> {
                        val clonedOne: GalleryApplyDetailItemImpl = dataObject.copy(theHumanChecked = false, theRfidChecked = false)
                        apiViewModel.updateGalleryApplyDetailItem(apiViewModel, clonedOne, selectedTabIndex)
                    }
                }
            }
        },
        // </editor-fold desc="GalleryApplyDetailQueryParameters - theItemClickCallback">
        listDataState,

        // 12-14
        hasScreenBeenInited,
        coroutineScope,
        // <editor-fold desc="GalleryApplyDetailQueryParameters - theLaunchedEffectBlock">
        {
            Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryScreenParameters - launchedEffectBlock -  hasScreenBeenInited: [$hasScreenBeenInited]!")
            if (!hasScreenBeenInited) {
                Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryScreenParameters - launchedEffectBlock - run init!!")
                listDataState.value = ListDataState.Init()

                delay(1000)
                reloadData()
                hasScreenBeenInited = true
            }
            else { Logger.getLogger("rememberGalleryApplyDetailQueryScreenParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryScreenParameters - launchedEffectBlock - no action!!") }
        },
        // </editor-fold desc="GalleryApplyDetailQueryParameters - theLaunchedEffectBlock">
    )
}


interface GalleryApplyDetailQueryParameterDelegate : IBuiltInTabListScreenParameters01<GalleryItemDelegate> {
    val theSelectedGalleryApply: RfGalleryApplyExt02Delegate
    val theGalleryApplyDetailDialogState: State<BuiltInDialogStateDelegate>
    val theOnReloadButtonClick: () -> Unit
    val theOnSaveButtonClick: () -> Unit
}


data class BuiltInGalleryApplyDetailQueryParameter01(
    override val theSelectedGalleryApply: RfGalleryApplyExt02Delegate = RfGalleryApplyExtImpl(),
    override val theGalleryApplyDetailDialogState: State<BuiltInDialogStateDelegate> = mutableStateOf<BuiltInDialogStateDelegate>(BuiltInDialogStateImpl()),
    override val theOnReloadButtonClick: () -> Unit = {},
    override val theOnSaveButtonClick: () -> Unit = {},

    override val theScrollableThreshold: Int = 3,
    override val theSelectedTabIndex: Int = 0,
    override val theTabItems: List<TabItemDelegate> = emptyList() ,
    override val theOnTabItemClick: (Int) -> Unit = {index: Int -> },
    override val theTabTextFontSize: TextUnit = 20.sp,

    override val theItemClickCallback: HolderItemClickDelegate<GalleryItemDelegate> = DefaultHolderCellClickHandler<GalleryItemDelegate>(),
    override val theListDataState: State<ListDataState<GalleryItemDelegate>> = mutableStateOf<ListDataState<GalleryItemDelegate>>(ListDataState.None()),

    override val theHasScreenBeenInited: Boolean = false,
    override val theCoroutineScope: CoroutineScope = BuiltInCoroutineScopeImpl(),
    override val theLaunchedEffectBlock: suspend CoroutineScope.() -> Unit = {},

) : GalleryApplyDetailQueryParameterDelegate


fun defaultGalleryApplyDetailQueryUnavailableResult01(
    description: String = "無查詢結果!",
    action: String = "Unavailable"
) = ListDataState.Unavailable<GalleryItemDelegate>(
    theList = listOf(
        GalleryApplyDetailItemImpl(
            theIdentifier = generateRandomStringViaUuid(),
            theCellType = BaseCellTypes.UNAVAILABLE,
            theDescription = description,
            theAction = action
        )
    )
)



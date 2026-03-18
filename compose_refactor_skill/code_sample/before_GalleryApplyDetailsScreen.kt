

@Composable
fun WtcGalleryApplyDetailsScreen(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
    c72RfidViewModel: C72RfidViewModel,
    r36RfidViewModel: R36RfidViewModel,
    rG768RfidViewModel: RG768RfidViewModel,     // added by elite_lin - 2025/08/31
) {
    Logger.getLogger("WtcGalleryApplyDetailsScreen").log(Level.INFO, "WtcGalleryApplyDetailsScreen")
    
    val galleryApplyDetailQueryParameters: GalleryApplyDetailQueryParameters = rememberGalleryApplyDetailQueryParameters(
        navController = navController,
        apiViewModel = apiViewModel,
        c72RfidViewModel = c72RfidViewModel,
        r36RfidViewModel = r36RfidViewModel,
        rG768RfidViewModel = rG768RfidViewModel,    // added by elite_lin - 2025/08/31
    )

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
                        //removeButtonVisibility = false,
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
                    text = "${galleryApplyDetailQueryParameters.selectedGalleryApply.theDestination}\n${galleryApplyDetailQueryParameters.selectedGalleryApply.theApplyNo}",
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
                    buttonOnClick = galleryApplyDetailQueryParameters.onSaveButtonClick
                )
            },
            theTopAppBarTitle = stringResource(R.string.menu_in_out_bound),
            theNavigationActionContent = {
                IconButton(
                    onClick = galleryApplyDetailQueryParameters.onReloadButtonClick
                ) {
                    Icon(Icons.Filled.Repeat, null, tint = Color.White)
                }
            },
            theListDataInitText = "",
            theTotalNumberVisibility = false,
            theTag = "WtcGalleryApplyDetails",
        ),
    )

    BuiltInDialogSet01(dialogState = galleryApplyDetailQueryParameters.galleryApplyDetailDialogState.value)
}


@Composable
fun rememberGalleryApplyDetailQueryParameters(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
    c72RfidViewModel: C72RfidViewModel,
    r36RfidViewModel: R36RfidViewModel,
    rG768RfidViewModel: RG768RfidViewModel,     // added by elite_lin - 2025/08/31
): GalleryApplyDetailQueryParameters {

    val context = LocalContext.current

    // <editor-fold desc="selectedTabIndex">
    var selectedTabIndex: Int by rememberIntValueFrom {
        apiViewModel.galleryApplyDetailSelectedTabIndexStateFlow.value
    }
    // </editor-fold desc="selectedTabIndex">

    // <editor-fold desc="tabItems">
    val tabItems: MutableState<List<TabItemDelegate>> = remember {
        mutableStateOf(
            // [start] revision by elite_lin - 2025/07/31
            // mutableListOf<WtcBadgetTabItemImpl>().apply { addAll(apiViewModel.inventoryDetailTabList) }
            mutableListOf<MuseumBadgetTabItemImpl>().apply { addAll(WtcRfidTabListFactoryImpl().theGalleryApplyDetailTabList) }
            // [end] revision by elite_lin - 2025/07/31
        )
    }
    // </editor-fold desc="tabItems">

    // <editor-fold desc="selectedGalleryApply">
    // [start] revision by elite_lin - 2025/12/12
    val selectedGalleryApply: RfGalleryApplyExt02Delegate by rememberNonNullObjectFrom {
        apiViewModel.selectedGalleryApply ?: WtcRfGalleryApplyItemExt02()
    // [end] revision by elite_lin - 2025/12/12
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

    // <editor-fold desc="progressDialogState">
    val progressDialogState = BuiltInDialogStateImpl(
        theDialogType = DialogType.Progress,
        theDialogState = true,
        theMessage = "更新中...",
        onDismiss = { galleryApplyDetailDialogState.value = BuiltInDialogStateImpl() }
    )
    // </editor-fold desc="progressDialogState">

    // <editor-fold desc="singleActionDialogState">
    val singleActionDialogState = MutableBuiltInDialogStateImpl(
        theDialogType = DialogType.SingleAction,
        theDialogState = true,
        theTitle = "提醒",
        theConfirmTitle = "確定",
        theCancelTitle = "取消",
        onDismiss = { galleryApplyDetailDialogState.value = BuiltInDialogStateImpl() }
    )
    // </editor-fold desc="singleActionDialogState">

    // <editor-fold desc="reloadData">
    val reloadData: () -> Unit = {
        val authData: AuthData? = apiViewModel.theAuthData()
        val selectedGalleryApplyX: RfGalleryApplyExt02Delegate? = apiViewModel.selectedGalleryApply   // revision by elite_lin - 2025/12/12
        if ((null != authData) && (null != selectedGalleryApplyX)) {
            listDataState.value = ListDataState.Loading()
            apiViewModel.apply {
                galleryApplyDetailListStateFlow.value = null
                clearGalleryApplyDetailMaps()
                apiViewModel.runQueryGalleryApplyDetailListTask(
                    queryModel = WtcRfGalleryApplyDetailQueryModel(galleryApplyId = selectedGalleryApplyX.theGalleryApplyId),
                    authToken = authData.authToken
                )
            }

        }
        else {
            Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryParameters - authData is null: [${null == authData}], selectedGalleryApply is null: [${null == selectedGalleryApplyX}]")
        }
    }
    // </editor-fold desc="reloadData">

    // <editor-fold desc="GalleryApplyDetailListResultStateFlowCollector">
    apiViewModel.galleryApplyDetailListResultStateFlow.NullableCollectorExt { queryGalleryApplyDetailListResult: FmApiResult<List<WtcRfGalleryApplyDetailItem>> ->    // revision by elite_lin - 2025/12/12
        coroutineScope.launch {
            when(queryGalleryApplyDetailListResult) {
                is FmApiResult.Success -> {
                    Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryParameters - QueryGalleryApplyDetailListResultStateFlowCollector: [Success]")
                    // [start] revision by elite_lin - 2025/04/25
                    apiViewModel.apply {
                        updateCachedGalleryApplyDetailList(queryGalleryApplyDetailListResult.data)  // added by elite_lin - 2025/04/25
                        reBuildGalleryApplyDetailDataSet(
                            scope = apiViewModel,
                            galleryApplyDetailList = queryGalleryApplyDetailListResult.data,
                            index = selectedTabIndex
                        )
                    }
                    // [end] revision by elite_lin - 2025/04/25
                }
                is FmApiResult.Error -> {
                    Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.SEVERE, "rememberGalleryApplyDetailQueryParameters - QueryGalleryApplyDetailListResultStateFlowCollector： .Error]", (queryGalleryApplyDetailListResult as FmApiResult.Error<List<GalleryItemDelegate>>).cause)

                    apiViewModel.accountReAuthIfNeeded(scope = apiViewModel, cause = queryGalleryApplyDetailListResult.cause)

                    listDataState.value = defaultGalleryApplyDetailQueryUnavailableResult01()
                }
            }
        }
    }
    // </editor-fold desc="GalleryApplyDetailListResultStateFlowCollector">

    // <editor-fold desc="GalleryApplyDetailListStateFlowCollector">
    apiViewModel.galleryApplyDetailListStateFlow.NullableCollectorExt { galleryApplyDetailList: List<GalleryApplyDetailItemDelegate> ->
        Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryParameters - GalleryApplyDetailListStateFlowCollector - galleryApplyDetailList.size: [${galleryApplyDetailList.size}]")
        listDataState.value = ListDataState.Available<GalleryItemDelegate>(theList = galleryApplyDetailList)
    }
    // </editor-fold desc="GalleryApplyDetailListStateFlowCollector">

    // <editor-fold desc="GalleryApplyDetailTabListStateFlowCollector">
    apiViewModel.galleryApplyDetailTabListStateFlow.NullableCollectorExt { galleryApplyTabList: List<TabItemDelegate> ->
        Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryParameters - InventoryDetail [Tab] ListStateFlowCollector")
        tabItems.value = galleryApplyTabList
    }
    // </editor-fold desc="GalleryApplyDetailTabListStateFlowCollector">

    // <editor-fold desc="GalleryApplyDetailUpdateBatchResultStateFlowCollector">
    apiViewModel.galleryApplyDetailUpdateBatchResultStateFlow.NullableCollectorExt { apiResult: FmApiResult<CommonApiResponse> ->
        Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryParameters - InventoryDetailUpdateBatchResultStateFlowCollector")

        coroutineScope.launch {
            when(apiResult) {
                is FmApiResult.Success -> {
                    Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryParameters - GalleryApplyDetailUpdateBatchResultStateFlowCollector: [Success]")
                    reloadData()

                    val dialogState: BuiltInDialogStateDelegate =
                        singleActionDialogState.apply {
                            theMessage = "更新成功"
                            onConfirm = {
                                galleryApplyDetailDialogState.value = BuiltInDialogStateImpl()
                            }
                        }
                    if (galleryApplyDetailDialogState.value.theDialogType != DialogType.SingleAction) {
                        galleryApplyDetailDialogState.value = dialogState
                    }
                }
                is FmApiResult.Error -> {
                    Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.SEVERE, "rememberGalleryApplyDetailQueryParameters - GalleryApplyDetailUpdateBatchResultStateFlowCollector： [Error]", apiResult.cause)

                    apiViewModel.accountReAuthIfNeeded(scope = apiViewModel, cause = apiResult.cause)

                    val dialogState: BuiltInDialogStateDelegate =
                        apiResult.cause.appApiExceptionHandlerExt(
                            context = context,
                            title = "提醒",
                            message = "更新失敗",
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
    }
    // </editor-fold desc="GalleryApplyDetailUpdateBatchResultStateFlowCollector">

    // <editor-fold desc="C72 StateFlowCollectors">
    C72UhfGalleryTagReadResultStateFlowCollector(c72RfidViewModel) { parsedTag: String, uhfTag: C72UhfTagDelegate ->
        apiViewModel.updateGalleryApplyDetailItemByDevice(apiViewModel, parsedTag, selectedTabIndex)
    }

    C72BarcodeReadResultStateFlowCollector(c72RfidViewModel) { readBarCode: String ->
        apiViewModel.updateGalleryApplyDetailItemByDevice(apiViewModel, readBarCode, selectedTabIndex)
    }
    // </editor-fold desc="C72 StateFlowCollectors">

    // <editor-fold desc="R36 StateFlowCollectors">
    R36UhfGalleryTagReadResultStateFlowCollector(r36RfidViewModel) { parsedTag: String, uhfTag: R36UhfTagDelegate ->
        apiViewModel.updateGalleryApplyDetailItemByDevice(apiViewModel, parsedTag, selectedTabIndex)
    }

    R36BarcodeReadResultStateFlowCollector(r36RfidViewModel) { readBarCode: String ->
        apiViewModel.updateGalleryApplyDetailItemByDevice(apiViewModel, readBarCode, selectedTabIndex)
    }
    // </editor-fold desc="R36 StateFlowCollectors">

    // [start] added by elite_lin - 2025/08/31
    // <editor-fold desc="RG768 StateFlowCollector">
    RG768UhfGalleryTagReadResultStateFlowCollector(rG768RfidViewModel) { parsedTag: String, uhfTag: RG768UhfTagDelegate ->
        //Logger.getLogger("rememberGalleryQueryParameters").log(Level.INFO, "rememberGalleryQueryParameters - RG768UhfGalleryTagReadResultStateFlowCollector: parsedTag: [$parsedTag]")
        apiViewModel.updateGalleryApplyDetailItemByDevice(apiViewModel, parsedTag, selectedTabIndex)
    }

//    RG768BarcodeReadResultStateFlowCollector(r36RfidViewModel) { readBarCode: String ->
//        apiViewModel.stringUhfTagDataExtDelegate.putItemIntoUhfTagMap(scope = apiViewModel, key = readBarCode, value = readBarCode, usageScenario = UsageScenarios.GALLERY_QUERY)
//    }
    // </editor-fold desc="RG768 StateFlowCollector">
    // [end] added by elite_lin - 2025/08/31

    return GalleryApplyDetailQueryParameters(
        selectedGalleryApply = selectedGalleryApply,
        galleryApplyDetailDialogState = galleryApplyDetailDialogState,
        // <editor-fold desc="GalleryApplyDetailQueryParameters - theItemClickCallback">
        theItemClickCallback = HolderItemClickDelegate<GalleryItemDelegate> { dataObject: GalleryItemDelegate, action: String, position: Int ->
            Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryParameters - itemClickCallback - delegate: [${dataObject.theDescription}], action: [$action], position: [$position]")
            if (dataObject !is GalleryApplyDetailItemImpl) {
                Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.WARNING, "rememberGalleryApplyDetailQueryParameters - itemClickCallback - delegate: is not an instance of [GalleryApplyDetailItemDelegate]!!")

                return@HolderItemClickDelegate
            }

            coroutineScope.launch {
                when(action) {
                    BaseActions.TAP -> {
                        apiViewModel.selectedGallery = WtcRfGalleryItem(
                            // [start] revision by elite_lin - 2025/12/08
                            theGalleryId = dataObject.theGalleryApplyDetailItem.theGalleryId,
                            theCode = dataObject.theGalleryApplyDetailItem.theGalleryCode,
                            theNameCht = dataObject.theGalleryApplyDetailItem.theGalleryNameCht,
                            // [end] revision by elite_lin - 2025/12/08
                        )
                        navController.navigateToExt(WtcRouteDestination.GalleryDetails.theRoute)
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

        theListDataState = listDataState,

        theSelectedTabIndex = selectedTabIndex,
        theTabItems = tabItems.value,
        // <editor-fold desc="GalleryApplyDetailQueryParameters - theOnTabItemClick">
        theOnTabItemClick = { index: Int ->
            selectedTabIndex = index
            // [start] revision by elite_lin - 2025/04/25
            //apiViewModel.galleryApplyDetailSelectedTabIndexStateFlow.update { selectedTabIndex }
            apiViewModel.apply {
                galleryApplyDetailSelectedTabIndexStateFlow.update { selectedTabIndex }
                // [start] added by elite_lin - 2025/04/25
                // bug fixes: display empty content when clicking tabs
                reBuildGalleryApplyDetailDataSet(
                    scope = apiViewModel,
                    galleryApplyDetailList = apiViewModel.cachedGalleryApplyDetailList,
                    index = selectedTabIndex
                )
                // [end] added by elite_lin - 2025/04/25
            }
            // [end] revision by elite_lin - 2025/04/25
            Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryParameters - theOnTabItemClick - selectedTabIndex: [$selectedTabIndex]!")
        },
        // </editor-fold desc="GalleryApplyDetailQueryParameters - theOnTabItemClick">

        theHasScreenBeenInited = hasScreenBeenInited,
        theCoroutineScope = coroutineScope,
        // <editor-fold desc="GalleryApplyDetailQueryParameters - theLaunchedEffectBlock">
        theLaunchedEffectBlock = {
            Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryParameters - launchedEffectBlock -  hasScreenBeenInited: [$hasScreenBeenInited]!")
            if (!hasScreenBeenInited) {
                Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryParameters - launchedEffectBlock - run init!!")
                listDataState.value = ListDataState.Init()

// ***************************************************************
//                listDataState.value = ListDataState.Loading()
//                apiViewModel.apply {
//                    galleryApplyDetailListStateFlow.value = null
//                    clearGalleryApplyDetailMaps()
//                    getTestGalleryApplyDetailList()
//                }

                delay(1000)
                reloadData()
// ***************************************************************
                hasScreenBeenInited = true
            }
            else { Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryParameters - launchedEffectBlock - no action!!") }
        },
        // </editor-fold desc="GalleryApplyDetailQueryParameters - theLaunchedEffectBlock">

        // <editor-fold desc="GalleryApplyDetailQueryParameters - onReloadButtonClick">
        onReloadButtonClick = {
            coroutineScope.launch {
                reloadData()
            }
        },
        // </editor-fold desc="GalleryApplyDetailQueryParameters - onReloadButtonClick">

        // <editor-fold desc="GalleryApplyDetailQueryParameters - onSaveButtonClick">
        onSaveButtonClick = {
            Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryParameters - onSaveButtonClick")

            ///
//            coroutineScope.launch {
//                galleryApplyDetailDialogState.value = progressDialogState
//                delay(3000L)
//
//                val statusSet = setOf<String>("I", "O")
//                val galleryApplyDetailList: List<GalleryApplyDetailItemDelegate>? = apiViewModel.galleryApplyDetailListStateFlow.value
//                galleryApplyDetailList?.let { applyDetailList: List<GalleryApplyDetailItemDelegate> ->
//                    val newList: List<WtcRfGalleryApplyDetailItemExt> = applyDetailList
//                        .filter { delegate: GalleryApplyDetailItemDelegate ->
//                            (delegate.theGalleryApplyDetailItem.status in statusSet) &&
//                                    (delegate.theHumanChecked || delegate.theRfidChecked)
//                        }
//                        .map { delegate: GalleryApplyDetailItemDelegate ->
//                            delegate.theGalleryApplyDetailItem.copy(
//                                status = if (delegate.theGalleryApplyDetailItem.status == "I") { "O" } else { "I" }
//                            )
//                        }
//                    newList.forEach { applyDetailItem: WtcRfGalleryApplyDetailItemExt ->
//                        Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryParameters - applyDetailItem: [${applyDetailItem.galleryCode}, ${applyDetailItem.status}]")
//                    }
//                }
//
//                galleryApplyDetailDialogState.value = singleActionDialogState.apply {
//                    theMessage = "更新成功"
//                    onConfirm = {
//                        galleryApplyDetailDialogState.value = BuiltInDialogStateImpl()
//                    }
//                }
//            }
            ///

            val authData: AuthData? = apiViewModel.theAuthData()
            if (null == authData) {
                Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.WARNING, "rememberGalleryApplyDetailQueryParameters - onSaveButtonClick - AuthData is null!!")
                return@GalleryApplyDetailQueryParameters
            }

            val galleryApplyDetailList: List<GalleryApplyDetailItemDelegate>? = apiViewModel.galleryApplyDetailListStateFlow.value
            if (galleryApplyDetailList.isNullOrEmpty()) {
                Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.WARNING, "rememberGalleryApplyDetailQueryParameters - onSaveButtonClick - galleryApplyDetailList is null or empty!!")
                return@GalleryApplyDetailQueryParameters
            }

            // ***** further revision is needed - by elite_lin - 2025/12/11 *****
            Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryParameters - onSaveButtonClick - galleryApplyDetailList.size: [${galleryApplyDetailList.size}]")
            val statusSet = setOf<String>("I", "O")
            val newList: List<WtcRfGalleryApplyDetailItem> = galleryApplyDetailList   // revision by elite_lin - 2025/12/12
                .filter { delegate: GalleryApplyDetailItemDelegate ->
                    //Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryParameters - onSaveButtonClick - status: [${delegate.theGalleryApplyDetailItem.status}]")
                    (delegate.theGalleryApplyDetailItem.theStatus in statusSet) &&
                            (delegate.theHumanChecked || delegate.theRfidChecked)
                }
                .map { delegate: GalleryApplyDetailItemDelegate ->
                    // [start] revision by elite_lin - 2025/12/08
//                    delegate.theGalleryApplyDetailItem.copy(
//                        status = if (delegate.theGalleryApplyDetailItem.status == "I") { "O" } else { "I" }
//                    )
                    delegate.theGalleryApplyDetailItem.cloneByStatusExt(
                        status = if (delegate.theGalleryApplyDetailItem.theStatus == "I") { "O" } else { "I" }
                    )
                    // [end] revision by elite_lin - 2025/12/08
                }


            Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryParameters - onSaveButtonClick - newList.size: [${newList.size}]")
            newList.forEach { applyDetailItem: WtcRfGalleryApplyDetailItem ->     // revision by elite_lin - 2025/12/12
                Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.INFO, "rememberGalleryApplyDetailQueryParameters - applyDetailItem: [${applyDetailItem.theGalleryCode}, ${applyDetailItem.theStatus}]")
            }

            if (newList.isEmpty()) {
                Logger.getLogger("rememberGalleryApplyDetailQueryParameters").log(Level.WARNING, "rememberGalleryApplyDetailQueryParameters - onSaveButtonClick - galleryApplyDetailList is null or empty!!")
                coroutineScope.launch {
                    galleryApplyDetailDialogState.value = singleActionDialogState.apply {
                        theMessage = "尚未異動任何藏品出/在庫狀態!!"
                        onConfirm = {
                            galleryApplyDetailDialogState.value = BuiltInDialogStateImpl()
                        }
                    }
                }
                return@GalleryApplyDetailQueryParameters
            }

            coroutineScope.launch {
                galleryApplyDetailDialogState.value = progressDialogState
                apiViewModel.callGalleryApplyDetailUpdateBatchApi(
                    scope = apiViewModel,
                    apiDomain = getApiDomain(),
                    authToken = authData.authToken,
                    applyDetailList = newList,
                )
            }
        },
        // </editor-fold desc="GalleryApplyDetailQueryParameters - onSaveButtonClick">
    )

}


data class GalleryApplyDetailQueryParameters(
    val selectedGalleryApply: RfGalleryApplyExt02Delegate,  // revision by elite_lin - 2025/12/12

    val onReloadButtonClick: () -> Unit = {},

    val onSaveButtonClick: () -> Unit = {},

    val galleryApplyDetailDialogState: State<BuiltInDialogStateDelegate> = mutableStateOf<BuiltInDialogStateDelegate>(
        BuiltInDialogStateImpl()
    ),

    override val theItemClickCallback: HolderItemClickDelegate<GalleryItemDelegate> = DefaultHolderCellClickHandler<GalleryItemDelegate>(),

    override val theListDataState: State<ListDataState<GalleryItemDelegate>> = mutableStateOf<ListDataState<GalleryItemDelegate>>(ListDataState.None()),

    override val theSelectedTabIndex: Int,
    override val theTabItems: List<TabItemDelegate>,
    override val theOnTabItemClick: (Int) -> Unit,
    override val theTabTextFontSize: TextUnit = 20.sp,

    override val theHasScreenBeenInited: Boolean = false,
    override val theCoroutineScope: CoroutineScope = BuiltInCoroutineScopeImpl(),
    override val theLaunchedEffectBlock: suspend CoroutineScope.() -> Unit,
    override val theScrollableThreshold: Int = 3,

) : IBuiltInTabListScreenParameters01<GalleryItemDelegate>


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



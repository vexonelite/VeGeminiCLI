

@Composable
fun WtcInventoryDetailsScreen(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
    c72RfidViewModel: C72RfidViewModel,
    r36RfidViewModel: R36RfidViewModel,
    rG768RfidViewModel: RG768RfidViewModel,     // added by elite_lin - 2025/08/31
) {
    Logger.getLogger("WtcGalleryApplyDetailsScreen").log(Level.INFO, "WtcGalleryApplyDetailsScreen")

    val context: Context = LocalContext.current

    val inventoryDetailsParameters: InventoryDetailsParameters = rememberInventoryDetailsParameters(
        navController = navController,
        apiViewModel = apiViewModel,
        c72RfidViewModel = c72RfidViewModel,
        r36RfidViewModel = r36RfidViewModel,
        rG768RfidViewModel = rG768RfidViewModel,    // added by elite_lin - 2025/08/31
    )

    BuiltInTopAppBarListScreen01(
        inputParameters = BuiltInBottomSheetListScreenParameters01<InventoryItemDelegate>(
            theNavController = navController,
            theRememberedParameters = inventoryDetailsParameters,
            theAvailableListContent = {
                val itemList: List<InventoryItemDelegate> = inventoryDetailsParameters.theListDataState.value.theList
                items(
                    items = ImmutableObjectList<InventoryItemDelegate>(itemList).objectList,
                    key = { inventoryItem: InventoryItemDelegate -> inventoryItem.theIdentifier },
                ) { inventoryItem: InventoryItemDelegate ->
                    InventoryListItem01(
                        inventoryItem = inventoryItem,
                        itemClickCallback = inventoryDetailsParameters.theItemClickCallback,
                        firstLabel = "登錄號：",
//                        nameVisibility = true,
//                        inOutBoundIconVisibility = true,
                    )
                }
            },
            theUnavailableContent = {
                val itemList: List<InventoryItemDelegate> = inventoryDetailsParameters.theListDataState.value.theList
                val unavailableItem: InventoryItemDelegate? = if (itemList.isNotEmpty()) { itemList[0] } else { null }
                ListDataUnavailable01(
                    unavailableText = unavailableItem?.theDescription ?: "",
                    onClick = {
                        if (null != unavailableItem) {
                            inventoryDetailsParameters.theItemClickCallback.onHolderItemClicked(unavailableItem, BaseActions.REFRESH, -1)
                        }
                    },
                )
            },
            theHeaderContent = {
                Text(
                    text = "${inventoryDetailsParameters.selectedInventoryRoomSummary.theName}\n${inventoryDetailsParameters.selectedInventoryRoomSummary.theLocationDescription}",  // revision by elite_lin - 2025/12/11
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
                BuiltInSaveButton01(buttonOnClick = inventoryDetailsParameters.onSaveButtonClick)
            },
            theTopAppBarTitle = stringResource(R.string.nav_header_PlanCheckingDetail),
            theNavigationActionContent = {
                IconButton(
                    onClick = inventoryDetailsParameters.onReloadButtonClick
                ) {
                    Icon(Icons.Filled.Repeat, null, tint = Color.White)
                }
            },
            theListDataInitText = "",
            theTotalNumberVisibility = false,
            theTag = "WtcInventoryDetails",
        ),
    )

    BuiltInTextInputDialog02(
        dialogState = inventoryDetailsParameters.remarkInputDialogState.value,
        textFieldHint = ""
    )

    BuiltInDialogSet01(dialogState = inventoryDetailsParameters.inventoryDetailsDialogState.value)
}


@Composable
fun rememberInventoryDetailsParameters(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
    c72RfidViewModel: C72RfidViewModel,
    r36RfidViewModel: R36RfidViewModel,
    rG768RfidViewModel: RG768RfidViewModel,     // added by elite_lin - 2025/08/31
): InventoryDetailsParameters {

    val context = LocalContext.current

    // <editor-fold desc="selectedTabIndex">
    var selectedTabIndex: Int by rememberIntValueFrom {
        apiViewModel.inventoryDetailSelectedTabIndexStateFlow.value
    }
    // </editor-fold desc="selectedTabIndex">

    // <editor-fold desc="tabItems">
    val tabItems: MutableState<List<TabItemDelegate>> = remember {
        mutableStateOf(
            // [start] revision by elite_lin - 2025/07/31
            //mutableListOf<WtcBadgetTabItemImpl>().apply { addAll(apiViewModel.inventoryDetailTabList) }
            mutableListOf<MuseumBadgetTabItemImpl>().apply { addAll(WtcRfidTabListFactoryImpl().theInventoryDetailTabList) }
            // [end] revision by elite_lin - 2025/07/31
        )
    }
    // </editor-fold desc="tabItems">

    // <editor-fold desc="selectedInventoryRoomSummary">
    // [start] revision by elite_lin - 2025/12/11
//    val selectedInventoryRoomSummary: WtcRfInventoryRoomSummaryItem by rememberNonNullObjectFrom {
//        apiViewModel.selectedInventoryRoomSummary ?: WtcRfInventoryRoomSummaryItem()
    val selectedInventoryRoomSummary: RfInventoryRoomSummaryDelegate by rememberNonNullObjectFrom {
            apiViewModel.selectedInventoryRoomSummary ?: RfInventoryRoomSummaryImpl()
    }
    // [end] revision by elite_lin - 2025/12/11
    // </editor-fold desc="selectedInventoryRoomSummary">

    // <editor-fold desc="listDataState">
    val listDataState: MutableState<ListDataState<InventoryItemDelegate>> = remember {
        mutableStateOf(ListDataState.None())
    }
    // </editor-fold desc="listDataState">

    // <editor-fold desc="inventoryDetailsDialogState">
    val inventoryDetailsDialogState: MutableState<BuiltInDialogStateDelegate> = remember {
        mutableStateOf(BuiltInDialogStateImpl())
    }
    // </editor-fold desc="inventoryDetailsDialogState">

    // <editor-fold desc="progressDialogState">
    val progressDialogState = BuiltInDialogStateImpl(
        theDialogType = DialogType.Progress,
        theDialogState = true,
        theMessage = "更新中...",
        onDismiss = { inventoryDetailsDialogState.value = BuiltInDialogStateImpl() }
    )
    // </editor-fold desc="progressDialogState">

    // <editor-fold desc="singleActionDialogState">
    val singleActionDialogState = MutableBuiltInDialogStateImpl(
        theDialogType = DialogType.SingleAction,
        theDialogState = true,
        theTitle = "提醒",
        theConfirmTitle = "確定",
        theCancelTitle = "取消",
        onDismiss = { inventoryDetailsDialogState.value = BuiltInDialogStateImpl() }
    )
    // </editor-fold desc="singleActionDialogState">

    // <editor-fold desc="remarkInputDialogState">
    val remarkInputDialogState: MutableState<BuiltInWrapperDialogStateDelegate<String>> = remember {
        mutableStateOf(BuiltInWrapperDialogStateImpl<String>())
    }
    // </editor-fold desc="remarkInputDialogState">

    // <editor-fold desc="builtInTextInputDialogState">
    val builtInTextInputDialogState = BuiltInWrapperDialogStateImpl<String>(
        theDialogType = DialogType.TextInput,
        theDialogState = true,
        theTitle = "編輯備註",
        theConfirmTitle = "確定",
        theCancelTitle = "取消",
        onDismiss = { remarkInputDialogState.value = BuiltInWrapperDialogStateImpl<String>() },
    )
    // </editor-fold desc="builtInTextInputDialogState">

    var hasScreenBeenInited: Boolean by rememberSaveable { mutableStateOf(false) }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    // <editor-fold desc="reloadData">
    val reloadData: () -> Unit = {
        val authData: AuthData? = apiViewModel.theAuthData()
        val selectedInventoryRoomSummaryX: RfInventoryRoomSummaryDelegate? = apiViewModel.selectedInventoryRoomSummary    // revision by elite_lin - 2025/12/11
        if ((null != authData) && (null != selectedInventoryRoomSummaryX)) {
            listDataState.value = ListDataState.Loading()
            apiViewModel.apply {
                inventoryDetailListStateFlow.value = null
                clearInventoryDetailMaps()
                callGalleryInventoryDetailListApi(
                    scope = apiViewModel,
                    apiDomain = getApiDomain(),
                    authToken = authData.authToken,
                    queryModel = WtcRfInventoryDetailQueryModel(
                        // [start] revision by elite_lin - 2025/12/11
                        inventoryId = selectedInventoryRoomSummary.theInventoryId,
                        locationDescription = selectedInventoryRoomSummary.theLocationDescription,
                        // [end] revision by elite_lin - 2025/12/11
                    )
                )
            }
        }
        else {
            Logger.getLogger("rememberInventoryDetailsParameters").log(Level.INFO, "rememberInventoryDetailsParameters - reloadData() - authData is null: [${null == authData}], selectedInventoryRoomSummaryX is null: [${null == selectedInventoryRoomSummaryX}]")
        }
    }
    // </editor-fold desc="reloadData">

    // <editor-fold desc="GalleryInventoryDetailListResultStateFlowCollector">
    apiViewModel.galleryInventoryDetailListResultStateFlow.NullableCollectorExt { queryInventoryDetailListResult: FmApiResult<List<WtcRfInventoryDetailItem>> ->
        coroutineScope.launch {
            when(queryInventoryDetailListResult) {
                is FmApiResult.Success -> {
                    Logger.getLogger("rememberInventoryDetailsParameters").log(Level.INFO, "rememberInventoryDetailsParameters - GalleryInventoryDetailListResultStateFlowCollector: [Success]")
                    //val resultList: List<InventoryItemDelegate> = queryInventoryDetailListResult.data
                    apiViewModel.apply {
                        inventoryEntryReloadEventStateFlow.update { true }
                        inventoryRoomSummaryReloadEventStateFlow.update { true }
                        updateCachedInventoryDetailList(queryInventoryDetailListResult.data)    // added by elite_lin - 2025/04/25
                        reBuildInventoryDetailDataSet(
                            scope = apiViewModel,
                            inventoryDetailList = queryInventoryDetailListResult.data,
                            index = selectedTabIndex
                        )
                    }
                }
                is FmApiResult.Error -> {
                    Logger.getLogger("rememberInventoryDetailsParameters").log(Level.SEVERE, "rememberInventoryDetailsParameters - GalleryInventoryDetailListResultStateFlowCollector： [Error]", queryInventoryDetailListResult.cause)

                    apiViewModel.accountReAuthIfNeeded(scope = apiViewModel, cause = queryInventoryDetailListResult.cause)

                    listDataState.value = defaultInventoryDetailQueryUnavailableResult01()
                }
            }
        }
    }
    // </editor-fold desc="GalleryInventoryDetailListResultStateFlowCollector">

    // <editor-fold desc="InventoryDetailListStateFlowCollector">
    apiViewModel.inventoryDetailListStateFlow.NullableCollectorExt { inventoryDetailList: List<InventoryDetailItemDelegate> ->
        Logger.getLogger("rememberInventoryDetailsParameters").log(Level.INFO, "rememberInventoryDetailsParameters - InventoryDetailListStateFlowCollector - inventoryDetailList.size: [${inventoryDetailList.size}]")
        listDataState.value = ListDataState.Available<InventoryItemDelegate>(theList = inventoryDetailList)
    }
    // </editor-fold desc="InventoryDetailListStateFlowCollector">

    // <editor-fold desc="InventoryDetailTabListStateFlowCollector">
    apiViewModel.inventoryDetailTabListStateFlow.NullableCollectorExt { inventoryTabList: List<TabItemDelegate> ->
        Logger.getLogger("rememberInventoryDetailsParameters").log(Level.INFO, "rememberInventoryDetailsParameters - InventoryDetail [Tab] ListStateFlowCollector")
        tabItems.value = inventoryTabList
    }
    // </editor-fold desc="InventoryDetailTabListStateFlowCollector">

    // <editor-fold desc="InventoryDetailUpdateBatchResultStateFlowCollector">
    apiViewModel.galleryInventoryDetailUpdateBatchResultStateFlow.NullableCollectorExt { apiResult: FmApiResult<CommonApiResponse> ->
        Logger.getLogger("rememberInventoryDetailsParameters").log(Level.INFO, "rememberInventoryDetailsParameters - InventoryDetailUpdateBatchResultStateFlowCollector")

        coroutineScope.launch {
            when(apiResult) {
                is FmApiResult.Success -> {
                    Logger.getLogger("rememberInventoryDetailsParameters").log(Level.INFO, "rememberInventoryDetailsParameters - InventoryDetailUpdateBatchResultStateFlowCollector: [Success]")
                    reloadData()

                    inventoryDetailsDialogState.value = singleActionDialogState.apply {
                        theMessage = "更新成功"
                        onConfirm = {
                            coroutineScope.launch {
                                inventoryDetailsDialogState.value = BuiltInDialogStateImpl()
                            }
                        }
                    }

                }
                is FmApiResult.Error -> {
                    Logger.getLogger("rememberInventoryDetailsParameters").log(Level.SEVERE, "rememberInventoryDetailsParameters - InventoryDetailUpdateBatchResultStateFlowCollector： [Error]", apiResult.cause)

                    apiViewModel.accountReAuthIfNeeded(scope = apiViewModel, cause = apiResult.cause)

                    inventoryDetailsDialogState.value =
                        apiResult.cause.appApiExceptionHandlerExt(
                            context = context,
                            title = "提醒",
                            message = "更新失敗",
                            onDismiss = {
                                coroutineScope.launch {
                                    inventoryDetailsDialogState.value = BuiltInDialogStateImpl()
                                }
                            },
                            onConfirm = {
                                coroutineScope.launch {
                                    inventoryDetailsDialogState.value = BuiltInDialogStateImpl()
                                }
                            },
                        )
                }
            }

            // must do this since we need to involve ``reloadData()``
            apiViewModel.galleryInventoryDetailUpdateBatchResultStateFlow.value = null
        }
    }
    // </editor-fold desc="InventoryDetailUpdateBatchResultStateFlowCollector">

    // <editor-fold desc="C72 StateFlowCollector">
    C72UhfGalleryTagReadResultStateFlowCollector(c72RfidViewModel) { parsedTag: String, uhfTag: C72UhfTagDelegate ->
        apiViewModel.updateInventoryDetailItemByDevice(apiViewModel, parsedTag, selectedTabIndex)
    }

    C72BarcodeReadResultStateFlowCollector(c72RfidViewModel) { readBarCode: String ->
        apiViewModel.updateInventoryDetailItemByDevice(apiViewModel, readBarCode, selectedTabIndex)
    }
    // </editor-fold desc="C72 StateFlowCollector">

    // <editor-fold desc="R36 StateFlowCollector">
    R36UhfGalleryTagReadResultStateFlowCollector(r36RfidViewModel) { parsedTag: String, uhfTag: R36UhfTagDelegate ->
        apiViewModel.updateInventoryDetailItemByDevice(apiViewModel, parsedTag, selectedTabIndex)
    }

    R36BarcodeReadResultStateFlowCollector(r36RfidViewModel) { readBarCode: String ->
        apiViewModel.updateInventoryDetailItemByDevice(apiViewModel, readBarCode, selectedTabIndex)
    }
    // </editor-fold desc="R36 StateFlowCollector">

    return InventoryDetailsParameters(
        selectedInventoryRoomSummary = selectedInventoryRoomSummary,

        inventoryDetailsDialogState = inventoryDetailsDialogState,
        remarkInputDialogState = remarkInputDialogState,

        // <editor-fold desc="InventoryDetailsParameters - theItemClickCallback">
        theItemClickCallback = HolderItemClickDelegate<InventoryItemDelegate> { dataObject: InventoryItemDelegate, action: String, position: Int ->
            Logger.getLogger("rememberInventoryDetailsParameters").log(Level.INFO, "rememberInventoryDetailsParameters - itemClickCallback - delegate: [${dataObject.theDescription}], action: [$action], position: [$position]")
            if (dataObject !is InventoryDetailItemImpl) {
                Logger.getLogger("rememberInventoryDetailsParameters").log(Level.WARNING, "rememberInventoryDetailsParameters - itemClickCallback - delegate: is not an instance of [InventoryDetailItemImpl]!!")
                return@HolderItemClickDelegate
            }

            coroutineScope.launch {
                when(action) {
                    BaseActions.TAP -> {
                        apiViewModel.selectedGallery = WtcRfGalleryItem(
                            // [start] revision by elite_lin - 2025/12/08, 11
                            theGalleryId = dataObject.theInventoryDetailItem.theGalleryId,
                            theCode = dataObject.theInventoryDetailItem.theGalleryCode,
                            theNameCht = dataObject.theInventoryDetailItem.theGalleryNameCht,
                            // [end] revision by elite_lin - 2025/12/08, 11
                        )
                        navController.navigateToExt(WtcRouteDestination.GalleryDetails.theRoute)
                    }
                    MuseumRfidActions.EDIT_REMARK -> {
                        remarkInputDialogState.value = builtInTextInputDialogState.copy(
                            theWrappedObject = dataObject.theRemark,
                            onConfirmWithWrappedObject = { remark: String ->
                                remarkInputDialogState.value = BuiltInWrapperDialogStateImpl<String>()
                                val clonedOne: InventoryDetailItemImpl = dataObject.copy(theRemark = remark)
                                apiViewModel.updateInventoryDetailItem(apiViewModel, clonedOne, selectedTabIndex)
                            }
                        )
                    }
                    MuseumRfidActions.HUMAN_CHECK -> {
                        val clonedOne: InventoryDetailItemImpl = dataObject.copy(theHumanChecked = !dataObject.theHumanChecked)
                        apiViewModel.updateInventoryDetailItem(apiViewModel, clonedOne, selectedTabIndex)
                    }
                    MuseumRfidActions.RESET_STATE -> {
                        val clonedOne: InventoryDetailItemImpl = dataObject.copy(theHumanChecked = false, theRfidChecked = false)
                        apiViewModel.updateInventoryDetailItem(apiViewModel, clonedOne, selectedTabIndex)
                    }
                }
            }
        },
        // </editor-fold desc="InventoryDetailsParameters - theItemClickCallback">

        theListDataState = listDataState,

        theSelectedTabIndex = selectedTabIndex,
        theTabItems = tabItems.value,

        // <editor-fold desc="InventoryDetailsParameters - theOnTabItemClick">
        theOnTabItemClick = { index: Int ->
            selectedTabIndex = index
            // [start] revision by elite_lin - 2025/04/25
            //apiViewModel.inventoryDetailSelectedTabIndexStateFlow.update { selectedTabIndex }
            apiViewModel.apply {
                inventoryDetailSelectedTabIndexStateFlow.update { selectedTabIndex }
                // [start] added by elite_lin - 2025/04/25
                // bug fixes: display empty content when clicking tabs
                reBuildInventoryDetailDataSet(
                    scope = apiViewModel,
                    inventoryDetailList = apiViewModel.cachedInventoryDetailList,
                    index = selectedTabIndex
                )
                // [end] added by elite_lin - 2025/04/25
            }
            // [end] revision by elite_lin - 2025/04/25
            Logger.getLogger("rememberInventoryDetailsParameters").log(Level.INFO, "rememberInventoryDetailsParameters - theOnTabItemClick - selectedTabIndex: [$selectedTabIndex]!")
        },
        // </editor-fold desc="InventoryDetailsParameters - theOnTabItemClick">

        theHasScreenBeenInited = hasScreenBeenInited,
        theCoroutineScope = coroutineScope,

        // <editor-fold desc="InventoryDetailsParameters - theLaunchedEffectBlock">
        theLaunchedEffectBlock = {
            Logger.getLogger("rememberInventoryDetailsParameters").log(Level.INFO, "rememberInventoryDetailsParameters - launchedEffectBlock -  hasScreenBeenInited: [$hasScreenBeenInited]!")
            if (!hasScreenBeenInited) {
                Logger.getLogger("rememberInventoryDetailsParameters").log(Level.INFO, "rememberInventoryDetailsParameters - launchedEffectBlock - run init!!")
                listDataState.value = ListDataState.Init()

                delay(1000)
                reloadData()

                hasScreenBeenInited = true
            }
            else { Logger.getLogger("rememberInventoryDetailsParameters").log(Level.INFO, "rememberInventoryDetailsParameters - launchedEffectBlock - no action!!") }
        },
        // </editor-fold desc="InventoryDetailsParameters - theLaunchedEffectBlock">

        // <editor-fold desc="InventoryDetailsParameters - onReloadButtonClick">
        onReloadButtonClick = {
            coroutineScope.launch {
                reloadData()
            }
        },
        // </editor-fold desc="InventoryDetailsParameters - onReloadButtonClick">

        // <editor-fold desc="InventoryDetailsParameters - onSaveButtonClick">
        onSaveButtonClick = {
            Logger.getLogger("rememberInventoryDetailsParameters").log(Level.INFO, "rememberInventoryDetailsParameters - onSaveButtonClick")

            val authData: AuthData? = apiViewModel.theAuthData()
            if (null == authData) {
                Logger.getLogger("rememberInventoryDetailsParameters").log(Level.WARNING, "rememberInventoryDetailsParameters - onSaveButtonClick - AuthData is null!!")
                return@InventoryDetailsParameters
            }

            val inventoryDetailList: List<InventoryDetailItemDelegate>? = apiViewModel.inventoryDetailListStateFlow.value
            if (inventoryDetailList.isNullOrEmpty()) {
                Logger.getLogger("rememberInventoryDetailsParameters").log(Level.WARNING, "rememberInventoryDetailsParameters - onSaveButtonClick - inventoryDetailList is null or empty!!")
                return@InventoryDetailsParameters
            }

            // ***** further revision is needed - by elite_lin - 2025/12/11 *****
            Logger.getLogger("rememberInventoryDetailsParameters").log(Level.INFO, "rememberInventoryDetailsParameters - onSaveButtonClick - inventoryDetailList.size: [${inventoryDetailList.size}]")
            val statusSet = setOf<String>("0", "1")
            val newList: List<WtcRfInventoryDetailItem> = inventoryDetailList
                .filter { delegate: InventoryDetailItemDelegate ->
                    //Logger.getLogger("rememberInventoryDetailsParameters").log(Level.INFO, "rememberInventoryDetailsParameters - onSaveButtonClick - status: [${delegate.theInventoryDetailItem.status}]")
//                    (delegate.theInventoryDetailItem.status in statusSet) &&
//                            (delegate.theHumanChecked || delegate.theRfidChecked)
                    delegate.canBeSubmittedAsChangeExt(statusSet)
                }
                .map { delegate: InventoryDetailItemDelegate ->
                    if (delegate.hasInventoryStateChangedExt(statusSet)) {
                    // [end] revision by elite_lin - 2025/12/11
//                        delegate.theInventoryDetailItem.copy(
//                            checkWay = delegate.getInventoryCheckWayExt(),
//                            status = "2",
//                            remark = delegate.theRemark,
//                        )
                        delegate.theInventoryDetailItem.cloneByCheckWayStatusAndRemarkExt(
                            checkWay = delegate.getInventoryCheckWayExt(),
                            status = "2",
                            remark = delegate.theRemark,
                        )
                    }
                    else {
//                        delegate.theInventoryDetailItem.copy(
//                            remark = delegate.theRemark,
//                        )
                        delegate.theInventoryDetailItem.cloneByCheckWayStatusAndRemarkExt(
                            remark = delegate.theRemark,
                        )
                    // [end] revision by elite_lin - 2025/12/11
                    }

                }

            Logger.getLogger("rememberInventoryDetailsParameters").log(Level.INFO, "rememberInventoryDetailsParameters - onSaveButtonClick - newList.size: [${newList.size}]")
            newList.forEach { inventoryDetailItem: WtcRfInventoryDetailItem ->
                Logger.getLogger("rememberInventoryDetailsParameters").log(Level.INFO, "rememberInventoryDetailsParameters - inventoryDetailItem: [${inventoryDetailItem.theGalleryCode}, ${inventoryDetailItem.theStatus}]")   // revision by elite_lin - 2025/12/11
            }

            if (newList.isEmpty()) {
                Logger.getLogger("rememberInventoryDetailsParameters").log(Level.WARNING, "rememberInventoryDetailsParameters - onSaveButtonClick - galleryApplyDetailList is null or empty!!")
                coroutineScope.launch {
                    inventoryDetailsDialogState.value = singleActionDialogState.apply {
                        theMessage = "尚未異動任何藏品盤點狀態!!"
                        onConfirm = {
                            inventoryDetailsDialogState.value = BuiltInDialogStateImpl()
                        }
                    }
                }
                return@InventoryDetailsParameters
            }

            coroutineScope.launch {
                inventoryDetailsDialogState.value = progressDialogState
                apiViewModel.callGalleryInventoryDetailUpdateBatchApi(
                    scope = apiViewModel,
                    apiDomain = getApiDomain(),
                    authToken = authData.authToken,
                    inventoryDetailList = newList,
                )
            }
        },
        // </editor-fold desc="InventoryDetailsParameters - onSaveButtonClick">
    )
}


data class InventoryDetailsParameters(
    // [start] revision by elite_lin - 2025/12/11
    //val selectedInventoryRoomSummary: WtcRfInventoryRoomSummaryItem,
    val selectedInventoryRoomSummary: RfInventoryRoomSummaryDelegate,
    // [end] revision by elite_lin - 2025/12/11

    val remarkInputDialogState: State<BuiltInWrapperDialogStateDelegate<String>> = mutableStateOf<BuiltInWrapperDialogStateDelegate<String>>(
        BuiltInWrapperDialogStateImpl<String>()
    ),

    val inventoryDetailsDialogState: State<BuiltInDialogStateDelegate> = mutableStateOf<BuiltInDialogStateDelegate>(
        BuiltInDialogStateImpl()
    ),

    val onReloadButtonClick: () -> Unit = {},

    val onSaveButtonClick: () -> Unit = {},

    override val theItemClickCallback: HolderItemClickDelegate<InventoryItemDelegate> = DefaultHolderCellClickHandler<InventoryItemDelegate>(),

    override val theListDataState: State<ListDataState<InventoryItemDelegate>> = mutableStateOf<ListDataState<InventoryItemDelegate>>(ListDataState.None()),

    override val theHasScreenBeenInited: Boolean = false,
    override val theCoroutineScope: CoroutineScope = BuiltInCoroutineScopeImpl(),
    override val theLaunchedEffectBlock: suspend CoroutineScope.() -> Unit = {},

    override val theScrollableThreshold: Int = 3,
    override val theSelectedTabIndex: Int,
    override val theTabItems: List<TabItemDelegate>,
    override val theOnTabItemClick: (Int) -> Unit,
    override val theTabTextFontSize: TextUnit = 20.sp,

) : IBuiltInTabListScreenParameters01<InventoryItemDelegate>


fun defaultInventoryDetailQueryUnavailableResult01(
    description: String = "無查詢結果!",
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



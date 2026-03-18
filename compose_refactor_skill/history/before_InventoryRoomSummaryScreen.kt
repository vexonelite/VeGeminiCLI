

@Composable
fun WtcInventoryRoomSummaryScreen(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
    c72RfidViewModel: C72RfidViewModel,
    r36RfidViewModel: R36RfidViewModel,
) {
    Logger.getLogger("WtcInventoryRoomSummaryScreen").log(Level.INFO, "WtcInventoryRoomSummaryScreen - time: [${System.currentTimeMillis()}]")

    val context = LocalContext.current

    val inventoryRoomSummaryParameters: InventoryRoomSummaryParameters = rememberInventoryRoomSummaryParameters(
        navController = navController,
        apiViewModel = apiViewModel,
        c72RfidViewModel = c72RfidViewModel,
        r36RfidViewModel = r36RfidViewModel
    )

    BuiltInTopAppBarListScreen01(
        inputParameters = BuiltInBottomSheetListScreenParameters01<InventoryItemDelegate>(
            theNavController = navController,
            theRememberedParameters = inventoryRoomSummaryParameters,
            theAvailableListContent = {
                val itemList: List<InventoryItemDelegate> = inventoryRoomSummaryParameters.theListDataState.value.theList
                items(
                    items = ImmutableObjectList<InventoryItemDelegate>(itemList).objectList,
                    key = { inventoryItem: InventoryItemDelegate -> inventoryItem.theIdentifier },
                ) { inventoryItem: InventoryItemDelegate ->
                    InventoryListItem01(
                        inventoryItem = inventoryItem,
                        itemClickCallback = inventoryRoomSummaryParameters.theItemClickCallback,
                        firstLabel = ""
                    )
                }
            },
            theUnavailableContent = {
                val itemList: List<InventoryItemDelegate> = inventoryRoomSummaryParameters.theListDataState.value.theList
                val unavailableItem: InventoryItemDelegate? = if (itemList.isNotEmpty()) { itemList[0] } else { null }
                ListDataUnavailable01(
                    unavailableText = unavailableItem?.theDescription ?: "",
                    onClick = {
                        if (null != unavailableItem) {
                            inventoryRoomSummaryParameters.theItemClickCallback.onHolderItemClicked(unavailableItem, BaseActions.REFRESH, -1)
                        }
                    },
                )
            },
            theHeaderContent = {
                Text(
                    text = inventoryRoomSummaryParameters.selectedInventory.theName,    // revision by elite_lin - 2025/12/11
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
            theTopAppBarTitle = stringResource(R.string.nav_header_InventoryRooms),
            theNavigationActionContent = {
                IconButton(
                    onClick = inventoryRoomSummaryParameters.onReloadButtonClick
                ) {
                    Icon(Icons.Filled.Repeat, null, tint = Color.White)
                }
            },
            theListDataInitText = "",
            theTotalNumberVisibility = false,
            theTag = "WtcInventoryRoomSummary",
        ),
    )

    LaunchedEffect(
        key1 = inventoryRoomSummaryParameters.theDataUpdateEvent,
        key2 = inventoryRoomSummaryParameters.theCurrentBackStackEntry?.destination?.route,
        block = inventoryRoomSummaryParameters.theReloadLaunchedEffectBlock
    )
}


@Composable
fun rememberInventoryRoomSummaryParameters(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
    c72RfidViewModel: C72RfidViewModel,
    r36RfidViewModel: R36RfidViewModel,
): InventoryRoomSummaryParameters {

    // <editor-fold desc="selectedTabIndex">
    var selectedTabIndex: Int by rememberIntValueFrom {
        apiViewModel.inventoryRoomSummarySelectedTabIndexStateFlow.value
    }
    // </editor-fold desc="selectedTabIndex">

    // <editor-fold desc="tabItems">
    val tabItems: MutableState<List<TabItemDelegate>> = remember {
        mutableStateOf(
            // [start] revision by elite_lin - 2025/07/31
            //mutableListOf<WtcBadgetTabItemImpl>().apply { addAll(apiViewModel.inventoryTabList) }
            mutableListOf<MuseumBadgetTabItemImpl>().apply { addAll(WtcRfidTabListFactoryImpl().theInventoryRoomSummaryTabList) }
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

    val updateInventoryDetailsEvent: Boolean by apiViewModel.inventoryRoomSummaryReloadEventStateFlow.collectAsState()
    val currentBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()

    // <editor-fold desc="selectedInventory">
    // [start] revision by elite_lin - 2025/12/11
    val selectedInventory: RfInventoryDelegate by rememberNonNullObjectFrom {
        apiViewModel.selectedInventory ?: RfInventoryImpl() // WtcRfInventoryItem()
    }
    // [end] revision by elite_lin - 2025/12/11
    // </editor-fold desc="selectedInventory">

    // <editor-fold desc="reloadData">
    val reloadData: () -> Unit = {
        val authData: AuthData? = apiViewModel.theAuthData()
        val selectedInventoryX: RfInventoryDelegate? = apiViewModel.selectedInventory   // revision by elite_lin - 2025/12/11
        if ((null != authData) && (null != selectedInventoryX)) {

            apiViewModel.inventoryRoomSummaryReloadEventStateFlow.update { false }

            listDataState.value = ListDataState.Loading()

            apiViewModel.apply {
                clearInventoryRoomSummaryTabListMap()
                inventoryRoomSummaryListStateFlow.value = null
                callGalleryInventoryRoomSummaryListApi(
                    scope = apiViewModel,
                    apiDomain = getApiDomain(),
                    authToken = authData.authToken,
                    queryModel = WtcRfInventoryDetailQueryModel(
                        inventoryId = selectedInventory.theInventoryId    // revision by elite_lin - 2025/12/08
                    )
                )
            }
        }
        else {
            Logger.getLogger("rememberInventoryRoomSummaryParameters").log(Level.INFO, "rememberInventoryRoomSummaryParameters - reloadData() - authData is null: [${null == authData}], selectedInventory is null: [${null == selectedInventory}]")
        }
    }
    // </editor-fold desc="reloadData">

    // <editor-fold desc="GalleryInventoryRoomSummaryListResultStateFlowCollector">
    apiViewModel.galleryInventoryRoomSummaryListResultStateFlow.NullableCollectorExt { queryInventoryRoomSummaryListResult: FmApiResult<List<WtcRfInventoryRoomSummaryItem>> ->
        coroutineScope.launch {
            when(queryInventoryRoomSummaryListResult) {
                is FmApiResult.Success -> {
                    Logger.getLogger("rememberInventoryRoomSummaryParameters").log(Level.INFO, "rememberInventoryRoomSummaryParameters - GalleryInventoryRoomSummaryListResultStateFlowCollector: [Success]")
                    // [start] revision by elite_lin - 2025/04/25
                    apiViewModel.apply {
                        updateCachedInventoryRoomSummaryList(queryInventoryRoomSummaryListResult.data)    // added by elite_lin - 2025/04/25
                        reBuildInventoryRoomSummaryTabListMap(
                            scope = apiViewModel,
                            inventoryRoomSummaryList = queryInventoryRoomSummaryListResult.data,
                            index = selectedTabIndex
                        )
                        //selectedTabIndexToInventoryRoomSummaryList(selectedTabIndex)
                    }
                    // [end] revision by elite_lin - 2025/04/25

                }
                is FmApiResult.Error -> {
                    Logger.getLogger("rememberInventoryRoomSummaryParameters").log(Level.SEVERE, "rememberInventoryRoomSummaryParameters - GalleryInventoryRoomSummaryListResultStateFlowCollector： [Error]", queryInventoryRoomSummaryListResult.cause)
                    apiViewModel.accountReAuthIfNeeded(scope = apiViewModel, cause = queryInventoryRoomSummaryListResult.cause)
                    listDataState.value = defaultInventoryRoomSummaryUnavailableResult01()
                }
            }
        }
    }
    // </editor-fold desc="GalleryInventoryRoomSummaryListResultStateFlowCollector">

    // <editor-fold desc="InventoryRoomSummaryListStateFlowCollector">
    apiViewModel.inventoryRoomSummaryListStateFlow.NullableCollectorExt { inventoryRoomSummaryList: List<InventoryRoomSummaryItemDelegate> ->
        Logger.getLogger("rememberInventoryRoomSummaryParameters").log(Level.INFO, "rememberInventoryRoomSummaryParameters - InventoryRoomSummaryListStateFlowCollector - inventoryRoomSummaryList.size: [${inventoryRoomSummaryList.size}]")
        listDataState.value = ListDataState.Available<InventoryItemDelegate>(theList = inventoryRoomSummaryList)
    }
    // </editor-fold desc="InventoryRoomSummaryListStateFlowCollector">

    // <editor-fold desc="InventoryRoomSummaryTabListStateFlowCollector">
    apiViewModel.inventoryRoomSummaryTabListStateFlow.NullableCollectorExt { inventoryTabList: List<TabItemDelegate> ->
        Logger.getLogger("rememberInventoryRoomSummaryParameters").log(Level.INFO, "rememberInventoryRoomSummaryParameters - InventoryRoomSummary [Tab] ListStateFlowCollector")
        tabItems.value = inventoryTabList
    }
    // </editor-fold desc="InventoryRoomSummaryTabListStateFlowCollector">

    return InventoryRoomSummaryParameters(
        selectedInventory = selectedInventory,

        // <editor-fold desc="InventoryRoomSummaryParameters - theItemClickCallback">
        theItemClickCallback = HolderItemClickDelegate<InventoryItemDelegate> { dataObject: InventoryItemDelegate, action: String, position: Int ->
            Logger.getLogger("rememberInventoryRoomSummaryParameters").log(Level.INFO, "rememberInventoryRoomSummaryParameters - itemClickCallback - delegate: [${dataObject.theDescription}], action: [$action], position: [$position]")
            if (dataObject !is InventoryRoomSummaryItemDelegate) { return@HolderItemClickDelegate }

            coroutineScope.launch {
                apiViewModel.apply {
                    selectedInventoryRoomSummary = dataObject.theInventoryRoomSummaryItem
                    galleryInventoryDetailListResultStateFlow.value = null
                    inventoryDetailSelectedTabIndexStateFlow.value = null
                    inventoryDetailTabListStateFlow.value = null
                    inventoryDetailListStateFlow.value = null
                    galleryInventoryDetailUpdateBatchResultStateFlow.value = null
                    clearCachedInventoryItemList()  // added by elite_lin - 2025/04/25
                    clearInventoryDetailMaps()
                }
                c72RfidViewModel.resetStateFlows()
                r36RfidViewModel.resetStateFlows()
                navController.navigateToExt(WtcRouteDestination.InventoryDetail.theRoute)
            }
        },
        // </editor-fold desc="InventoryRoomSummaryParameters - theItemClickCallback">

        theListDataState = listDataState,
        theSelectedTabIndex = selectedTabIndex,
        theTabItems = tabItems.value,

        // <editor-fold desc="InventoryRoomSummaryParameters - theOnTabItemClick">
        theOnTabItemClick = { index: Int ->
            selectedTabIndex = index
            // [start] revision by elite_lin - 2025/04/25
            // apiViewModel.inventoryRoomSummarySelectedTabIndexStateFlow.update { selectedTabIndex }
            // bug fixes: display empty content when clicking tabs
            apiViewModel.apply {
                inventoryRoomSummarySelectedTabIndexStateFlow.update { selectedTabIndex }
                // [start] added by elite_lin - 2025/04/25
                reBuildInventoryRoomSummaryTabListMap(
                    scope = apiViewModel,
                    inventoryRoomSummaryList = apiViewModel.cachedInventoryRoomSummaryList,
                    index = selectedTabIndex
                )
                // [end] added by elite_lin - 2025/04/25
            }
            // [end] revision by elite_lin - 2025/04/25
            Logger.getLogger("rememberInventoryRoomSummaryParameters").log(Level.INFO, "rememberInventoryRoomSummaryParameters - theOnTabItemClick - selectedTabIndex: [$selectedTabIndex]!")
        },
        // </editor-fold desc="InventoryRoomSummaryParameters - theOnTabItemClick">

        theHasScreenBeenInited = hasScreenBeenInited,
        theCoroutineScope = coroutineScope,

        // <editor-fold desc="InventoryRoomSummaryParameters - theLaunchedEffectBlock">
        theLaunchedEffectBlock = {
            Logger.getLogger("rememberInventoryRoomSummaryParameters").log(Level.INFO, "rememberInventoryRoomSummaryParameters - launchedEffectBlock -  hasScreenBeenInited: [$hasScreenBeenInited]!")
            if (!hasScreenBeenInited) {
                Logger.getLogger("rememberInventoryRoomSummaryParameters").log(Level.INFO, "rememberInventoryRoomSummaryParameters - launchedEffectBlock - run init!!")
                listDataState.value = ListDataState.Init()
                delay(1000)
                reloadData()
                hasScreenBeenInited = true
            }
            else { Logger.getLogger("rememberInventoryRoomSummaryParameters").log(Level.INFO, "rememberInventoryRoomSummaryParameters - launchedEffectBlock - no action!!") }
        },
        // </editor-fold desc="InventoryRoomSummaryParameters - theLaunchedEffectBlock">

        // <editor-fold desc="CeDocumentQueryParameters - theReloadLaunchedEffectBlock">
        theDataUpdateEvent = updateInventoryDetailsEvent,
        theCurrentBackStackEntry = currentBackStackEntry,
        theReloadLaunchedEffectBlock = {
            Logger.getLogger("rememberCeDocumentQueryParameters").log(Level.INFO, "rememberCeDocumentQueryParameters - theReloadLaunchedEffectBlock - [$updateInventoryDetailsEvent], currentBackStackEntry: [${currentBackStackEntry?.destination?.route}]!")
            if ((updateInventoryDetailsEvent) && (currentBackStackEntry?.destination?.route == WtcRoute.INVENTORY_ROOM_SUMMARY)) {
                Logger.getLogger("rememberCeDocumentQueryParameters").log(Level.INFO, "rememberCeDocumentQueryParameters - theReloadLaunchedEffectBlock[***]!")
                reloadData()
            }
        },
        // </editor-fold desc="CeDocumentQueryParameters - theReloadLaunchedEffectBlock">

        // <editor-fold desc="InventoryRoomSummaryParameters - onReloadButtonClick">
        onReloadButtonClick = {
            coroutineScope.launch { reloadData() }
        },
        // </editor-fold desc="InventoryRoomSummaryParameters - onReloadButtonClick">
    )
}


data class InventoryRoomSummaryParameters(
    // revision by elite_lin - 2025/12/11
    //val selectedInventory: WtcRfInventoryItem,
    val selectedInventory: RfInventoryDelegate,
    // revision by elite_lin - 2025/12/11

    val onReloadButtonClick: () -> Unit = {},

    override val theItemClickCallback: HolderItemClickDelegate<InventoryItemDelegate> = DefaultHolderCellClickHandler<InventoryItemDelegate>(),

    override val theListDataState: State<ListDataState<InventoryItemDelegate>> = mutableStateOf<ListDataState<InventoryItemDelegate>>(ListDataState.None()),

    override val theDataUpdateEvent: Boolean = false,
    override val theCurrentBackStackEntry: NavBackStackEntry? = null,
    override val theReloadLaunchedEffectBlock: suspend CoroutineScope.() -> Unit = {},

    override val theHasScreenBeenInited: Boolean = false,
    override val theCoroutineScope: CoroutineScope = BuiltInCoroutineScopeImpl(),
    override val theLaunchedEffectBlock: suspend CoroutineScope.() -> Unit = {},

    override val theScrollableThreshold: Int = 3,
    override val theSelectedTabIndex: Int,
    override val theTabItems: List<TabItemDelegate>,
    override val theOnTabItemClick: (Int) -> Unit,
    override val theTabTextFontSize: TextUnit = 20.sp,

) : IBuiltInTabListScreenParameters01<InventoryItemDelegate>,
    IBuiltInReloadLaunchEffectParameters01


fun defaultInventoryRoomSummaryUnavailableResult01(
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



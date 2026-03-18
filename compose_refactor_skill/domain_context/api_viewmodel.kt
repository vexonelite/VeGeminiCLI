


class WtcRfidTabListFactoryImpl : MuseumRfidTabListFactoryDelegate {

    override val theInventoryEntryTabList: List<MuseumBadgetTabItemImpl> = mutableListOf(
        MuseumBadgetTabItemImpl(theIdentifier = "001", theDescription = MainApplication.getInstance().getString(tw.com.futaba.mis.museum_rfid.compose_ui.R.string.inv_status_undo)),
        MuseumBadgetTabItemImpl(theIdentifier = "002", theDescription = MainApplication.getInstance()
                .getString(tw.com.futaba.mis.museum_rfid.compose_ui.R.string.inv_status_processing)),
        MuseumBadgetTabItemImpl(theIdentifier = "003", theDescription = MainApplication.getInstance()
                .getString(tw.com.futaba.mis.museum_rfid.compose_ui.R.string.inv_status_finished)),
    )

    override val theInventoryRoomSummaryTabList: List<MuseumBadgetTabItemImpl> = mutableListOf(
        MuseumBadgetTabItemImpl(theIdentifier = "001", theDescription = MainApplication.getInstance().getString(R.string.inv_status_undo)),
        MuseumBadgetTabItemImpl(theIdentifier = "002", theDescription = MainApplication.getInstance()
                .getString(tw.com.futaba.mis.museum_rfid.compose_ui.R.string.inv_status_processing)),
        MuseumBadgetTabItemImpl(theIdentifier = "003", theDescription = MainApplication.getInstance()
                .getString(tw.com.futaba.mis.museum_rfid.compose_ui.R.string.inv_status_finished)),
    )

    override val theInventoryDetailTabList: List<MuseumBadgetTabItemImpl> = mutableListOf(
        MuseumBadgetTabItemImpl(theIdentifier = "001", theDescription = MainApplication.getInstance().getString(tw.com.futaba.mis.museum_rfid.compose_ui.R.string.prompt_inbound)),
        MuseumBadgetTabItemImpl(theIdentifier = "002", theDescription = MainApplication.getInstance().getString(tw.com.futaba.mis.museum_rfid.compose_ui.R.string.prompt_outbound)),
    )

    override val theGalleryApplyDetailTabList: List<MuseumBadgetTabItemImpl> = mutableListOf(
        MuseumBadgetTabItemImpl(theIdentifier = "001", theDescription = MainApplication.getInstance().getString(tw.com.futaba.mis.museum_rfid.compose_ui.R.string.prompt_inbound)), // I
        MuseumBadgetTabItemImpl(theIdentifier = "002", theDescription = MainApplication.getInstance().getString(tw.com.futaba.mis.museum_rfid.compose_ui.R.string.prompt_outbound)), // O
    )
}


class ApiViewModel : ViewModel(), MuseumApiModelDelegate,
    CoroutineScopeExtDelegate by BuiltInCoroutineScopeImpl(),
    AccountAuthExtDelegate by BuiltInAccountAuthExtImpl(
        restfulApi = FmWtcMuseumApi.create(domain = theApiDomain()),
        authUrl = theApiDomain() + FmWtcMuseumApi.ACCOUNT_AUTH,
    ),
    MuseumRfGalleryApiExtDelegate by WtcRfGalleryApiExtImpl(),
    WtcRfGalleryExtDelegate by WtcRfGalleryExtImpl(),
    MuseumInventoryEntryExtDelegate by MuseumInventoryEntryExtImpl(WtcRfidTabListFactoryImpl()),
    MuseumInventoryRoomSummaryExtDelegate by MuseumInventoryRoomSummaryExtImpl(WtcRfidTabListFactoryImpl()),
    MuseumInventoryDetailExtDelegate by MuseumInventoryDetailExtImpl(WtcRfidTabListFactoryImpl()),
    MuseumGalleryApplyDetailExtDelegate by MuseumGalleryApplyDetailExtImpl(WtcRfidTabListFactoryImpl()),
    WtcEmployeeExtDelegate by WtcEmployeeExtImpl(),
    AudioPlayerControlsDelegate by ExoPlayerControlsImpl(),
    MuseumSearchCacheItemsDelegate by MuseumSearchCacheItemsImpl(),
    MuseumUiUtilityDelegate
{

    //val stringUhfTagDataExtDelegate: tw.com.futaba.mis.museum.core.UhfTagDataExtDelegate<String, GalleryItemDelegate>
    override val stringUhfTagDataExtDelegate: UhfTagDataExtDelegate<String, GalleryItemDelegate> =
        UhfTagDataExtImpl<String, GalleryItemDelegate>(WtcCodeDeviceGalleryFactory())

    val rawGalleryTagDataExtDelegate: UhfTagDataExtDelegate<RfidUhfTagDelegate, GalleryItemDelegate> =
        UhfTagDataExtImpl<RfidUhfTagDelegate, GalleryItemDelegate>(WtcUhfDeviceGalleryFactory())
    val rawEmployeeTagDataExtDelegate: UhfTagDataExtDelegate<RfidUhfTagDelegate, GalleryItemDelegate> =
        UhfTagDataExtImpl<RfidUhfTagDelegate, GalleryItemDelegate>(WtcUhfEmployeeCodeFactory())

    val selectedRfidTagReportAbnormalList: MutableList<String> = mutableListOf()

    
    override fun onCleared() {
        super.onCleared()
        getRootTaskJob().cancelIfNeeded()
        releasePlayer()
    }

    ///

    override val queryGalleryListResultStateFlow: StateFlow<FmApiResult<List<GalleryItemDelegate>>?> =
        galleryListResultStateFlow.map { queryResult: FmApiResult<List<RfGalleryItemDelegate>>? ->
            if (null == queryResult) { null }
            else {
                when (queryResult) {
                    is FmApiResult.Success -> {
                        Logger.getLogger(getLogTag()).log(Level.INFO, "galleryListResultLiveDataMapper - Success")
                        val delegateList: List<GalleryItemDelegate> = queryResult.data
                            .sortedBy { gallery: RfGalleryItemDelegate -> gallery.theCode }   // revision by elite_lin - 2025/12/08
                            .map { gallery: RfGalleryItemDelegate ->
                                GalleryItemImpl(theDataObject = gallery)
                            }
                        FmApiResult.Success(delegateList)
                    }
                    is FmApiResult.Error -> {
                        Logger.getLogger(getLogTag()).log(Level.SEVERE, "galleryListResultLiveDataMapper - Error: ", queryResult.cause)
                        FmApiResult.Error(queryResult.cause)
                    }
                }
            }
        }
        .stateIn(
            scope = this, // provide a coroutine scope, typically viewModelScope in Android
            started = SharingStarted.Eagerly, // controls when the state flow starts emitting values
            initialValue = null // provide an initial value
        )

    ///

    override var selectedGallery: RfGalleryItemDelegate? = null

    override var selectedGalleryApply: RfGalleryApplyExt02Delegate? = null

    ///
    
    override val queryGalleryApplyListResultStateFlow: StateFlow<FmApiResult<List<GalleryApplyItemDelegate>>?> =
        galleryApplyListResultStateFlow.map { queryResult: FmApiResult<List<RfGalleryApplyExt02Delegate>>? ->
            if (null == queryResult) { null }
            else {
                when (queryResult) {
                    is FmApiResult.Success -> {
                        Logger.getLogger(getLogTag()).log(Level.INFO, "galleryApplyListResultLiveDataMapper - Success")
                        val delegateList: List<GalleryApplyItemDelegate> = queryResult.data.map { galleryApply: RfGalleryApplyExt02Delegate ->
                            GalleryApplyItemImpl(theDataObject = galleryApply)
                        }
                        FmApiResult.Success(delegateList)
                    }
                    is FmApiResult.Error -> {
                        Logger.getLogger(getLogTag()).log(Level.SEVERE, "galleryApplyListResultLiveDataMapper - Error: ", queryResult.cause)
                        FmApiResult.Error(queryResult.cause)
                    }
                }
            }
        }
        .stateIn(
            scope = this, // provide a coroutine scope, typically viewModelScope in Android
            started = SharingStarted.Eagerly, // controls when the state flow starts emitting values
            initialValue = null // provide an initial value
        )

    ///

    override fun getApiDomain(): String = theApiDomain()

    override fun buildGalleryQueryModel(
        keyword: String, nameCht: String, galleryCode: String
    ): RfGalleryQueryModelDelegate = WtcRfGalleryQueryModel(
            theKeyword = keyword,
            theNameCht = nameCht,
            theCode = galleryCode,
        )


    override fun navigateToGalleryDetailsScreen(selectedObject: RfGalleryItemDelegate): String {
        galleryDetailResultStateFlow.value = null
        galleryUpdateResultStateFlow.value = null
        selectedGallery = selectedObject
        return WtcRouteDestination.GalleryDetails.theRoute
    }

    override fun buildGalleryApplyQueryModel(
        keyword: String, dateStart: String, dateEnd: String
    ): RfGalleryApplyQueryModelDelegate {
        return if (keyword.isNotEmpty()) {
            WtcRfGalleryApplyQueryModel(theKeyword = keyword,)
        }
        else {
            WtcRfGalleryApplyQueryModel(
                //applyDateStart = "2023/01/01"
                theApplyDateStart = dateStart, theApplyDateEnd = dateEnd,
            )
        }
    }

    override fun navigateToGalleryApplyDetailsScreen(selectedObject: RfGalleryApplyExt02Delegate): String {
        selectedGalleryApply = selectedObject
        galleryApplyDetailListResultStateFlow.value = null
        galleryApplyDetailSelectedTabIndexStateFlow.value = null
        galleryApplyDetailTabListStateFlow.value = null
        galleryApplyDetailListStateFlow.value = null
        galleryApplyDetailUpdateBatchResultStateFlow.value = null
        clearCachedGalleryApplyDetailList()
        clearGalleryApplyDetailMaps()
        return WtcRouteDestination.ApplyDetails.theRoute
    }

    override fun buildReadLogPassiveQueryModel(tagId: String): RfReadLogPassiveQueryModelDelegate {
        return WtcRfReadLogPassiveQueryModel(theTagId = tagId)
    }

    override fun buildRfGalleryApplyDetailQueryModel(galleryApplyId: String): RfGalleryApplyDetailQueryModelDelegate {
        return WtcRfGalleryApplyDetailQueryModel(theGalleryApplyId = galleryApplyId)
    }

    override fun cloneGalleryApplyDetailByStatus(
        srcGalleryApplyDetail: RfGalleryApplyDetailDelegate, status: String?,
    ): RfGalleryApplyDetailDelegate {
        return srcGalleryApplyDetail.cloneByStatusExt(status = status)
    }

    override fun buildRfInventoryQueryModel(
        keyword: String, dateStart: String, dateEnd: String
    ): RfInventoryQueryModelDelegate {
        return WtcRfInventoryQueryModel(
            theKeyword = keyword,
            theInventoryDateStart = dateStart,
            theInventoryDateEnd = dateEnd
        )
    }

    override fun navigateToInventoryRoomSummaryScreen(selectedObject: RfInventoryDelegate): String {
        selectedInventory = selectedObject
        galleryInventoryRoomSummaryListResultStateFlow.value = null
        inventoryRoomSummarySelectedTabIndexStateFlow.value = null
        inventoryRoomSummaryTabListStateFlow.value = null
        inventoryRoomSummaryListStateFlow.value = null
        clearCachedInventoryRoomSummaryList()
        clearInventoryRoomSummaryTabListMap()
        return WtcRouteDestination.InventoryRoomSummary.theRoute
    }

    override fun buildRfInventoryDetailQueryModel(
        inventoryId: String, locationDescription: String
    ): RfInventoryDetailQueryModelDelegate {
        return WtcRfInventoryDetailQueryModel(
            theInventoryId = inventoryId,
            theLocationDescription = locationDescription
        )
    }

    override fun navigateToInventoryDetailScreen(selectedObject: RfInventoryRoomSummaryDelegate): String {
        selectedInventoryRoomSummary = selectedObject
        galleryInventoryDetailListResultStateFlow.value = null
        inventoryDetailSelectedTabIndexStateFlow.value = null
        inventoryDetailTabListStateFlow.value = null
        inventoryDetailListStateFlow.value = null
        galleryInventoryDetailUpdateBatchResultStateFlow.value = null
        clearCachedInventoryItemList()  
        clearInventoryDetailMaps()
        return WtcRouteDestination.InventoryDetail.theRoute
    }

    override fun isCurrentRouteInventory(route: String?) : Boolean {
        return (route == NtcamRoute.INVENTORY)
    }

    override fun isCurrentRouteInventoryRoomSummary(route: String?) : Boolean {
        return (route == NtcamRoute.INVENTORY_ROOM_SUMMARY)
    }

    override fun cloneInventoryDetailByCheckWayStatusAndRemark(
        srcInventoryDetail: RfInventoryDetailDelegate,
        checkWay: String?, status: String?, remark: String?,
    ): RfInventoryDetailDelegate {
        return if (srcInventoryDetail is WtcRfInventoryDetailItem) {
            srcInventoryDetail.copy(
                theCheckWay = checkWay ?: srcInventoryDetail.theCheckWay,
                theStatus = status ?: srcInventoryDetail.theStatus,
                theRemark = remark ?: srcInventoryDetail.theRemark,
            )
        }
        else {
            WtcRfInventoryDetailItem(
                theInventoryDetailId = srcInventoryDetail.theInventoryDetailId,
                theInventoryId = srcInventoryDetail.theInventoryId,
                theGalleryId = srcInventoryDetail.theGalleryId,
                theGalleryCode = srcInventoryDetail.theGalleryCode,
                theGalleryNameCht = srcInventoryDetail.theGalleryNameCht,
                theGalleryAuthorCht = srcInventoryDetail.theGalleryAuthorCht,
                theGalleryStatus = srcInventoryDetail.theGalleryStatus,
                theRemark = remark ?: srcInventoryDetail.theRemark,
                theStatus = status ?: srcInventoryDetail.theStatus,
                theStatusName = srcInventoryDetail.theStatusName,
                theCheckWay = checkWay ?: srcInventoryDetail.theCheckWay,
                theCheckWayName = srcInventoryDetail.theCheckWayName,
            )
        }
    }

    override fun navigateToRfidTagReportAbnormalScreen(uhfTagList: List<String>): String {
        tagManageLogUpdateBatchResultStateFlow.value = null
        selectedRfidTagReportAbnormalList.apply {
            clear()
            addAll(uhfTagList)
        }
        return NtcamRouteDestination.RfidTagReportAbnormal.theRoute
    }

    override fun buildUpdateGalleryList(
        selectAction: String,
        rackInput: String,
        uhfTagList: List<GalleryItemDelegate>,
    ): Pair<List<RfGalleryItemDelegate>, String> {
        Logger.getLogger("buildUpdateGalleryList").log(Level.INFO, "buildUpdateGalleryList - saveButtonOnClick - selectAction: [${selectAction}], rackInput: [${rackInput}]")

        val emptyResultList = listOf<WtcRfGalleryItem>()

        val actionList: List<String> = getRackActionList()
        if (selectAction !in actionList) {
            return Pair<List<WtcRfGalleryItem>, String>(emptyResultList, "請檢查動作!")
        }

        val onSite = if (selectAction == actionList[0]) { "Y" } else { "N" }

        val rackSplitList: List<String> = if (onSite == "Y") {
            if (rackInput.isNotEmpty()) {
                rackInput
                    .trim()
                    //.replace(" ", "-")
                    .split("-".toRegex())
                    .dropLastWhile { it.isEmpty() }
            }
            else { listOf<String>() }
        }
        else { listOf<String>() }

        if ((onSite == "Y") && (rackSplitList.isEmpty())) {
            return Pair<List<WtcRfGalleryItem>, String>(emptyResultList, "請檢查櫃位!")
        }

        if (uhfTagList.isEmpty()) {
            return Pair<List<WtcRfGalleryItem>, String>(emptyResultList, "尚未提供藏品登入號!")
        }

        val galleryList: List<WtcRfGalleryItem> = uhfTagList.map { galleryItem: GalleryItemDelegate ->
            Logger.getLogger("rememberRackParameters").log(Level.INFO, "buildUpdateGalleryList - saveButtonOnClick - code: [${galleryItem.theIdentifier}], onSite: [$onSite]")
            WtcRfGalleryItem(
                // [start] revision by elite_lin - 2025/12/08
                theCode = galleryItem.theIdentifier,
                theOnSite = onSite,
                theLocation1 = if (rackSplitList.isNotEmpty()) { rackSplitList[0] } else { "" },
                theLocation2 = if (rackSplitList.size >= 2) { rackSplitList[1] } else { "" },
                theLocation3 = if (rackSplitList.size >= 3) { rackSplitList[2] } else { "" }
                // [end] revision by elite_lin - 2025/12/08
            )
        }

        return Pair<List<WtcRfGalleryItem>, String>(galleryList, "")
    }

    override fun cloneRfidTagGalleryItemByHumanCheck(
        srcRfidTagGalleryItem: RfidTagGalleryItemDelegate,
        humanChecked: Boolean,
    ): RfidTagGalleryItemDelegate {
        return if (srcRfidTagGalleryItem is RfidTagGalleryItemImpl) {
            srcRfidTagGalleryItem.copy(theHumanChecked = humanChecked)
        }
        else {
            RfidTagGalleryItemImpl(
                theDataObject = srcRfidTagGalleryItem.theDataObject,
                theIdentifier = srcRfidTagGalleryItem.theIdentifier,
                theCellType = srcRfidTagGalleryItem.theCellType,
                theDescription = srcRfidTagGalleryItem.theDescription,
                theAction = srcRfidTagGalleryItem.theAction,
                theUsageScenario = srcRfidTagGalleryItem.theUsageScenario,
                theHumanChecked = humanChecked,
            )
        }
    }

    override fun cloneGalleryByCodeAndName(
        srcGalleryItem: RfGalleryItemDelegate, code: String?, nameCht: String?
    ): RfGalleryItemDelegate {
        return if (srcGalleryItem is NtmofaRfGalleryItem) {
            srcGalleryItem.copy(
                theCode = code ?: srcGalleryItem.theCode,
                theNameCht = nameCht ?: srcGalleryItem.theNameCht,
            )
        }
        else {
            NtmofaRfGalleryItem(
                theGalleryId = srcGalleryItem.theGalleryId,
                theCode = code ?: srcGalleryItem.theCode,
                theNameCht = nameCht ?: srcGalleryItem.theNameCht,
                theAuthorCht = srcGalleryItem.theAuthorCht,
                theKind = srcGalleryItem.theKind,
                thePartsQty = srcGalleryItem.thePartsQty,
                theLength = srcGalleryItem.theLength,
                theWidth = srcGalleryItem.theWidth,
                theHeight = srcGalleryItem.theHeight,
                theLocation1 = srcGalleryItem.theLocation1,
                theLocation2 = srcGalleryItem.theLocation2,
                theLocation3 = srcGalleryItem.theLocation3,
                thePlace = srcGalleryItem.thePlace,
                theRemark = srcGalleryItem.theRemark,
                theStatus = srcGalleryItem.theStatus,
            )
        }
    }
}



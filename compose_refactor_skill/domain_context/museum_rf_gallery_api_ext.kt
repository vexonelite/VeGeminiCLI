

interface MuseumRfGalleryApiExtDelegate {

    val galleryListResultStateFlow: MutableStateFlow<FmApiResult<List<RfGalleryItemDelegate>>?>
    fun callGalleryListApi(
        scope: CoroutineScope,
        apiDomain: String,
        authToken: String,
        queryModel: RfGalleryQueryModelDelegate
    )

    ///

    val galleryDetailResultStateFlow: MutableStateFlow<FmApiResult<List<RfGalleryItemDelegate>>?>
    fun callGalleryDetailApi(
        scope: CoroutineScope,
        apiDomain: String,
        authToken: String,
        queryModel: RfGalleryQueryModelDelegate
    )

    ///
    var galleryUpdateJob: Job?
    val galleryUpdateResultStateFlow: MutableStateFlow<FmApiResult<CommonApiResponse>?>
    fun callGalleryUpdateApi(
        scope: CoroutineScope,
        apiDomain: String,
        authToken: String,
        updatedGallery: RfGalleryItemDelegate
    )

    ///
    val galleryUpdateOnSiteResultStateFlow: MutableStateFlow<FmApiResult<CommonApiResponse>?>
    fun callGalleryUpdateOnSiteApi(
        scope: CoroutineScope,
        apiDomain: String,
        authToken: String,
        galleryList: List<RfGalleryItemDelegate>
    )

    ///

    val queryReadLogPassiveListResultStateFlow: MutableStateFlow<FmApiResult<List<RfReadLogPassiveDelegate>>?>
    fun callReadLogPassiveListApi(
        scope: CoroutineScope,
        apiDomain: String,
        authToken: String,
        queryModel: RfReadLogPassiveQueryModelDelegate
    )

    val storeRoomListResultStateFlow: MutableStateFlow<FmApiResult<List<MuseumRfStoreRoomSummary>>?>
    fun callStoreRoomListApi(
        scope: CoroutineScope,
        apiDomain: String,
        authToken: String,
        queryModel: RfStoreRoomQueryModelDelegate
    )

    ///

    val galleryApplyListResultStateFlow: MutableStateFlow<FmApiResult<List<RfGalleryApplyExt02Delegate>>?>
    fun callGalleryApplyListApi(
        scope: CoroutineScope,
        apiDomain: String,
        authToken: String,
        queryModel: RfGalleryApplyQueryModelDelegate
    )

    ///

    val galleryApplyDetailListResultStateFlow: MutableStateFlow<FmApiResult<List<RfGalleryApplyDetailDelegate>>?>
    fun callGalleryApplyDetailListApi(
        scope: CoroutineScope,
        apiDomain: String,
        authToken: String,
        queryModel: RfGalleryApplyDetailQueryModelDelegate
    )


    val galleryApplyDetailUpdateBatchResultStateFlow: MutableStateFlow<FmApiResult<CommonApiResponse>?>
    fun callGalleryApplyDetailUpdateBatchApi(
        scope: CoroutineScope,
        apiDomain: String,
        authToken: String,
        applyDetailList: List<RfGalleryApplyDetailDelegate>
    )


    val galleryInventoryListResultStateFlow: MutableStateFlow<FmApiResult<List<RfInventoryDelegate>>?>
    fun callGalleryInventoryListApi(
        scope: CoroutineScope,
        apiDomain: String,
        authToken: String,
        queryModel: RfInventoryQueryModelDelegate
    )

    ///

    val galleryInventoryRoomSummaryListResultStateFlow: MutableStateFlow<FmApiResult<List<RfInventoryRoomSummaryDelegate>>?>
    fun callGalleryInventoryRoomSummaryListApi(
        scope: CoroutineScope,
        apiDomain: String,
        authToken: String,
        queryModel: RfInventoryDetailQueryModelDelegate
    )

    ///

    val galleryInventoryDetailListResultStateFlow: MutableStateFlow<FmApiResult<List<RfInventoryDetailDelegate>>?>
    fun callGalleryInventoryDetailListApi(
        scope: CoroutineScope,
        apiDomain: String,
        authToken: String,
        queryModel: RfInventoryDetailQueryModelDelegate
    )

    ///
    val galleryInventoryDetailUpdateBatchResultStateFlow: MutableStateFlow<FmApiResult<CommonApiResponse>?>
    fun callGalleryInventoryDetailUpdateBatchApi(
        scope: CoroutineScope,
        apiDomain: String,
        authToken: String,
        inventoryDetailList: List<RfInventoryDetailDelegate>
    )

    ///
    val tagManageLogUpdateBatchResultStateFlow: MutableStateFlow<FmApiResult<CommonApiResponse>?>
    fun callTagManageLogUpdateBatchApi(
        scope: CoroutineScope,
        apiDomain: String,
        authToken: String,
        tagManageLogList: List<RfTagManageLogItemDelegate>
    )

}


interface MuseumRfGalleryExtDelegate {
    val stringUhfTagDataExtDelegate: UhfTagDataExtDelegate<String, GalleryItemDelegate>
    val rawGalleryTagDataExtDelegate: UhfTagDataExtDelegate<RfidUhfTagDelegate, GalleryItemDelegate>
    val rawEmployeeTagDataExtDelegate: UhfTagDataExtDelegate<RfidUhfTagDelegate, GalleryItemDelegate>
    val selectedRfidTagReportAbnormalList: MutableList<String>

    val queryGalleryListResultStateFlow: StateFlow<FmApiResult<List<GalleryItemDelegate>>?>
    var selectedGallery: RfGalleryItemDelegate?

    val queryGalleryApplyListResultStateFlow: StateFlow<FmApiResult<List<GalleryApplyItemDelegate>>?>
    var selectedGalleryApply: RfGalleryApplyExt02Delegate?
}


interface MuseumSearchCacheItemsDelegate {
    var theCachedKeyword: String
    var theCachedGalleryCode: String
    var theCachedName: String

    fun getCachedSearchParameters(): List<String>

    fun resetCachedSearchParameters()
}


class MuseumSearchCacheItemsImpl : MuseumSearchCacheItemsDelegate {
    override var theCachedKeyword: String = ""
    override var theCachedGalleryCode: String = ""
    override var theCachedName: String = ""

    override fun getCachedSearchParameters(): List<String> =
        listOf<String>(theCachedKeyword, theCachedGalleryCode, theCachedName)


    override fun resetCachedSearchParameters() {
        theCachedKeyword = ""
        theCachedGalleryCode = ""
        theCachedName = ""
    }
}


interface MuseumApiModelDelegate : CoroutineScopeExtDelegate, AccountAuthExtDelegate,
    MuseumRfGalleryApiExtDelegate, MuseumRfGalleryExtDelegate,
    MuseumInventoryEntryExtDelegate,
    MuseumInventoryRoomSummaryExtDelegate,
    MuseumInventoryDetailExtDelegate,
    MuseumGalleryApplyDetailExtDelegate


///-----------------------------------------------------------------------------





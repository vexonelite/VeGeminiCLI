interface MuseumUiUtilityDelegate {
    fun getApiDomain(): String

    fun buildGalleryQueryModel(
        keyword: String, nameCht: String, galleryCode: String
    ): RfGalleryQueryModelDelegate

    fun navigateToGalleryDetailsScreen(selectedObject: RfGalleryItemDelegate): String

    fun buildGalleryApplyQueryModel(
        keyword: String, dateStart: String, dateEnd: String
    ): RfGalleryApplyQueryModelDelegate

    // need to reset StateFlow for the RFID
    fun navigateToGalleryApplyDetailsScreen(selectedObject: RfGalleryApplyExt02Delegate): String

    fun buildReadLogPassiveQueryModel(tagId: String): RfReadLogPassiveQueryModelDelegate

    fun buildRfGalleryApplyDetailQueryModel(galleryApplyId: String): RfGalleryApplyDetailQueryModelDelegate

    fun cloneGalleryApplyDetailByStatus(
        srcGalleryApplyDetail: RfGalleryApplyDetailDelegate, status: String?,
    ): RfGalleryApplyDetailDelegate

    fun buildRfInventoryQueryModel(
        keyword: String, dateStart: String, dateEnd: String
    ): RfInventoryQueryModelDelegate

    fun navigateToInventoryRoomSummaryScreen(selectedObject: RfInventoryDelegate): String

    fun buildRfInventoryDetailQueryModel(
        inventoryId: String, locationDescription: String
    ): RfInventoryDetailQueryModelDelegate

    // need to reset StateFlow for the RFID
    fun navigateToInventoryDetailScreen(selectedObject: RfInventoryRoomSummaryDelegate): String

    fun isCurrentRouteInventory(route: String?) : Boolean

    fun isCurrentRouteInventoryRoomSummary(route: String?) : Boolean

    fun cloneInventoryDetailByCheckWayStatusAndRemark(
        srcInventoryDetail: RfInventoryDetailDelegate,
        checkWay: String?, status: String?, remark: String?,
    ): RfInventoryDetailDelegate
    
    fun navigateToRfidTagReportAbnormalScreen(uhfTagList: List<String>): String

    fun buildUpdateGalleryList(
        selectAction: String,
        rackInput: String,
        uhfTagList: List<GalleryItemDelegate>,
    ): Pair<List<RfGalleryItemDelegate>, String>

    fun cloneRfidTagGalleryItemByHumanCheck(
        srcRfidTagGalleryItem: RfidTagGalleryItemDelegate,
        humanChecked: Boolean,
    ): RfidTagGalleryItemDelegate

    fun cloneGalleryByCodeAndName(
        srcGalleryItem: RfGalleryItemDelegate, code: String?, nameCht: String?
    ): RfGalleryItemDelegate

}


interface MuseumSearchItemsDelegate : ISearchButtonDelete {
    val theKeywordValue: TextFieldValue
    val theKeywordOnValueChange: (TextFieldValue) -> Unit
    val theKeywordHint: String
    val theKeywordSelectAllOnFocus: Boolean
    val theKeywordOnFocusChanged: (FocusState) -> Unit

    val theGalleryCodeValue: TextFieldValue
    val theGalleryCodeOnValueChange: (TextFieldValue) -> Unit
    val theGalleryCodeHint: String
    val theGalleryCodeSelectAllOnFocus: Boolean
    val theGalleryCodeOnFocusChanged: (FocusState) -> Unit
    val theGalleryCodeVisibility: Boolean

    val theNameValue: TextFieldValue
    val theNameOnValueChange: (TextFieldValue) -> Unit
    val theNameHint: String
    val theNameSelectAllOnFocus: Boolean
    val theNameOnFocusChanged: (FocusState) -> Unit
    val theNameVisibility: Boolean

    val theClearButtonVisibility: Boolean
    val theOnClearButtonClick: () -> Unit
}


data class MuseumSearchItemsImpl(
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
    override val theOnSearchButtonClick: () -> Unit = {},
) : MuseumSearchItemsDelegate

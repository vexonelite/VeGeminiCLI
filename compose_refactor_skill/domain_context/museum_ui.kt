

fun RfGalleryItemDelegate.buildKindAndSizeExt(): String {
    val lengthStr = if (null != this@buildKindAndSizeExt.theLength) { "${this@buildKindAndSizeExt.theLength}" } else { "0.0" }
    val widthStr = if (null != this@buildKindAndSizeExt.theWidth) { "${this@buildKindAndSizeExt.theWidth}" } else { "0.0" }
    val heightStr = if (null != this@buildKindAndSizeExt.theHeight) { "${this@buildKindAndSizeExt.theHeight}" } else { "0.0" }
    return "${this@buildKindAndSizeExt.theKind} / $lengthStr x $widthStr x $heightStr"
}


/** added by elite_lin - 2025/12/08 */
fun RfGalleryItemDelegate.buildGalleryDetailItemListExt(
    isReadOnly: Boolean = false
): List<GalleryDetailItem> {
    val type = if (isReadOnly) { GalleryDetailItemType.Text } else { GalleryDetailItemType.TextField }
    return listOf<GalleryDetailItem>(
        GalleryDetailItem(title = "A", value = this@buildGalleryDetailItemListExt.theCode),
        GalleryDetailItem(title = "B", value = this@buildGalleryDetailItemListExt.theNameCht),
        GalleryDetailItem(title = "C", value = this@buildGalleryDetailItemListExt.theAuthorCht),
        GalleryDetailItem(title = "D", value = this@buildGalleryDetailItemListExt.buildKindAndSizeExt()),
        GalleryDetailItem(title = "E", value = (this@buildGalleryDetailItemListExt.thePartsQty?.toInt().toString() ?: "")),
    )
}


fun RfGalleryItemDelegate.buildPairListExt(): List<Pair<String, String>> =
    listOf<Pair<String, String>>(
        Pair<String, String>("A：", this@buildPairListExt.theCode),
        Pair<String, String>("B：", this@buildPairListExt.theNameCht),
        Pair<String, String>("C：", this@buildPairListExt.theAuthorCht),
        Pair<String, String>("D：", this@buildPairListExt.buildKindAndSizeExt()),
        Pair<String, String>("E：", (this@buildPairListExt.thePartsQty?.toInt().toString() ?: "")),
        Pair<String, String>("F：", this@buildPairListExt.theLocation1),
        Pair<String, String>("G：", this@buildPairListExt.theLocation2),
        Pair<String, String>("H：", this@buildPairListExt.theLocation3),
        Pair<String, String>("I：", this@buildPairListExt.thePlace),
        Pair<String, String>("J：", this@buildPairListExt.theRemark),
    )

///

interface MuseumBadgetTabItemDelegate : BadgetTabItemDelegate {   
    val theCurrentNumber: Int
}


data class MuseumBadgetTabItemImpl(   
    override val theIdentifier: String = "",
    override val theDescription: String = "",
    override val theBadgetNumber: Int = 0,
    override val theCurrentNumber: Int = 0,
) : MuseumBadgetTabItemDelegate


interface MuseumRfidTabListFactoryDelegate {

    val theInventoryEntryTabList: List<MuseumBadgetTabItemImpl>

    val theInventoryRoomSummaryTabList: List<MuseumBadgetTabItemImpl>

    val theInventoryDetailTabList: List<MuseumBadgetTabItemImpl>

    val theGalleryApplyDetailTabList: List<MuseumBadgetTabItemImpl>
}

///

enum class GalleryDetailItemType {
    Text,
    TextField,
}


data class GalleryDetailItem(
    val title: String = "",
    val value: String = "",
    val type: GalleryDetailItemType = GalleryDetailItemType.Text,
)


object MuseumRfidActions {  
    const val EDIT_REMARK = "_action_EDIT_REMARK"
    const val HUMAN_CHECK = "_action_HUMAN_CHECK"
    const val RESET_STATE = "_action_RESET_STATE"
    const val RFID_TAG_REWRITE = "_action_RFID_TAG_REWRITE"
    const val RFID_TAG_REMARK = "_action_RFID_TAG_REMARK"
}


enum class UsageScenarios {
    GALLERY_QUERY,
    RACK,
    RFID_TAG,
}

///

interface UsageScenarioDelegate {
    val theUsageScenario: UsageScenarios
}


interface HumanCheckedDelegate {
    val theHumanChecked: Boolean
}


interface RfidCheckedDelegate {
    val theRfidChecked: Boolean
}


interface RfidMixHumanCheckedDelegate : RfidCheckedDelegate, HumanCheckedDelegate


fun RfidMixHumanCheckedDelegate.getInventoryCheckWayExt(): String =
    if (theHumanChecked) { "M" }
    else if (theRfidChecked) { "R" }
    else { "" }


///

interface GalleryItemDelegate : TextLabelWrapperDelegate<RfGalleryItemDelegate>   


data class GalleryItemImpl(
    override val theDataObject: RfGalleryItemDelegate = RfGalleryItemImpl(),
    override val theIdentifier: String = theDataObject.theGalleryId,
    override val theCellType: Int = BaseCellTypes.ITEM,
    override val theDescription: String = theDataObject.theNameCht,
    override val theAction: String = BaseActions.TAP,
) : GalleryItemDelegate

///

interface DeviceGalleryItemDelegate : GalleryItemDelegate, UsageScenarioDelegate


data class DeviceGalleryItemImpl(
    override val theDataObject: RfGalleryItemDelegate = RfGalleryItemImpl(),
    override val theIdentifier: String = theDataObject.theGalleryId,
    override val theCellType: Int = BaseCellTypes.ITEM,
    override val theDescription: String = theDataObject.theNameCht,
    override val theAction: String = BaseActions.TAP,
    override val theUsageScenario: UsageScenarios = UsageScenarios.GALLERY_QUERY,
) : DeviceGalleryItemDelegate

///

interface GalleryApplyItemDelegate : TextLabelWrapperDelegate<RfGalleryApplyExt02Delegate>  


data class GalleryApplyItemImpl(
    override val theDataObject: RfGalleryApplyExt02Delegate = RfGalleryApplyExtImpl(),
    override val theIdentifier: String = theDataObject.theGalleryApplyId,
    override val theCellType: Int = BaseCellTypes.ITEM,
    override val theDescription: String = theDataObject.theCaseName,
    override val theAction: String = BaseActions.TAP,
) : GalleryApplyItemDelegate

///

interface GalleryApplyDetailItemDelegate : GalleryItemDelegate, RfidMixHumanCheckedDelegate {
    val theGalleryApplyDetailItem: RfGalleryApplyDetailDelegate   

}


data class GalleryApplyDetailItemImpl(
    override val theGalleryApplyDetailItem: RfGalleryApplyDetailDelegate = RfGalleryApplyDetailExtImpl(),
    override val theIdentifier: String = theGalleryApplyDetailItem.theGalleryApplyDetailId,
    override val theCellType: Int = BaseCellTypes.ITEM,
    override val theDescription: String = theGalleryApplyDetailItem.theGalleryNameCht,
    override val theAction: String = BaseActions.TAP,
    override val theDataObject: RfGalleryItemDelegate = RfGalleryItemImpl(
        theGalleryId = theGalleryApplyDetailItem.theGalleryId,
        theCode = theGalleryApplyDetailItem.theGalleryCode,
        theNameCht = theGalleryApplyDetailItem.theGalleryNameCht,
        theNameEng = theGalleryApplyDetailItem.theGalleryNameEng,
        theStatus = theGalleryApplyDetailItem.theStatus,
        theStatusName = theGalleryApplyDetailItem.theStatusName,
    ),


    override val theHumanChecked: Boolean = false,
    override val theRfidChecked: Boolean = false,
) : GalleryApplyDetailItemDelegate

///

interface InventoryItemDelegate : TextLabelWrapperDelegate<RfInventoryDelegate>   


data class InventoryItemImpl(
    override val theDataObject: RfInventoryDelegate = RfInventoryImpl(),
    override val theIdentifier: String = theDataObject.theInventoryId,
    override val theCellType: Int = BaseCellTypes.ITEM,
    override val theDescription: String = theDataObject.theName,
    override val theAction: String = BaseActions.TAP,
) : InventoryItemDelegate


interface InventoryItemFactoryDelegate {
    fun createInventoryItem(): InventoryItemDelegate
}


interface InventoryRoomSummaryItemDelegate: InventoryItemDelegate {
    val theInventoryRoomSummaryItem: RfInventoryRoomSummaryDelegate   
}


data class InventoryRoomSummaryItemImpl(
    override val theInventoryRoomSummaryItem: RfInventoryRoomSummaryDelegate = RfInventoryRoomSummaryImpl(),
    override val theDataObject: RfInventoryDelegate = RfInventoryImpl(
        theInventoryId = "${theInventoryRoomSummaryItem.theInventoryId}_${theInventoryRoomSummaryItem.theLocationDescription}",
        theStatus = theInventoryRoomSummaryItem.theStatus
    ),
    override val theIdentifier: String = theDataObject.theInventoryId,
    override val theCellType: Int = BaseCellTypes.ITEM,
    override val theDescription: String = theInventoryRoomSummaryItem.theLocationDescription,
    override val theAction: String = BaseActions.TAP,
) : InventoryRoomSummaryItemDelegate


interface InventoryRoomSummaryItemFactoryDelegate {
    fun createRoomSummaryItem(): InventoryItemDelegate
}

///

interface InventoryDetailItemDelegate : InventoryItemDelegate, RfidMixHumanCheckedDelegate {
    val theInventoryDetailItem: RfInventoryDetailDelegate   
    val theInOutBoundStatus: String
    val theApiState: String
    val theRemark: String
}


fun InventoryDetailItemDelegate.hasRemarkChangedExt(): Boolean =
    this.theRemark != this.theInventoryDetailItem.theRemark   


fun InventoryDetailItemDelegate.hasInventoryStateChangedExt(statusSet: Set<String>): Boolean =
    (this.theInventoryDetailItem.theStatus in statusSet) &&    
            (this.theHumanChecked || this.theRfidChecked)


fun InventoryDetailItemDelegate.canBeSubmittedAsChangeExt(statusSet: Set<String>): Boolean =
    hasRemarkChangedExt() || hasInventoryStateChangedExt(statusSet)


data class InventoryDetailItemImpl(
    override val theInventoryDetailItem: RfInventoryDetailDelegate = RfInventoryDetailImpl(),
    override val theIdentifier: String = theInventoryDetailItem.theInventoryDetailId,
    override val theCellType: Int = BaseCellTypes.ITEM,
    override val theDescription: String = theInventoryDetailItem.theGalleryCode,
    override val theAction: String = BaseActions.TAP,
    override val theDataObject: RfInventoryDelegate = RfInventoryImpl(
        theInventoryId = theInventoryDetailItem.theInventoryDetailId,
        theName = theInventoryDetailItem.theGalleryNameCht,
        theStatus = theInventoryDetailItem.theStatus
    ),
    override val theInOutBoundStatus: String = theInventoryDetailItem.theGalleryStatus,
    override val theApiState: String = theInventoryDetailItem.theStatus,
    override val theRemark: String = theInventoryDetailItem.theRemark,
    override val theHumanChecked: Boolean = false,
    override val theRfidChecked: Boolean = false,
) : InventoryDetailItemDelegate

///

interface RfidTagGalleryItemDelegate : GalleryItemDelegate, HumanCheckedDelegate, UsageScenarioDelegate


data class RfidTagGalleryItemImpl(
    override val theDataObject: RfGalleryItemDelegate = RfGalleryItemImpl(),
    override val theIdentifier: String = theDataObject.theGalleryId,
    override val theCellType: Int = BaseCellTypes.ITEM,
    override val theDescription: String = theDataObject.theNameCht,
    override val theAction: String = BaseActions.TAP,
    override val theUsageScenario: UsageScenarios = UsageScenarios.RFID_TAG,
    override val theHumanChecked: Boolean = false,
) : RfidTagGalleryItemDelegate


// [start] added by gemini - 2026/03/17
fun String.theWtcGalleryCodeToEpcHexStringExt(): String {
    // Simple mock implementation if the real one is missing
    return this.toByteArray().joinToString("") { "%02x".format(it) }.padEnd(24, '0').take(24)
}


fun RfidUhfTagDelegate.parseEPCtoGalleryTagNoExt(): String {
    // Simple mock implementation if the real one is missing
    return try {
        val hex = this.theEPC
        val bytes = hex.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
        String(bytes).trim { it <= ' ' || it == '\u0000' }
    } catch (e: Exception) {
        this.theEPC
    }
}
// [end] added by gemini - 2026/03/17

///

interface StoreRoomItemDelegate : TextLabelWrapperDelegate<RfStoreRoomDelegate>


data class StoreRoomItemImpl(
    override val theDataObject: RfStoreRoomDelegate = RfStoreRoomImpl(),
    override val theIdentifier: String = theDataObject.theStoreRoomId,
    override val theCellType: Int = BaseCellTypes.ITEM,
    override val theDescription: String = theDataObject.theName,
    override val theAction: String = BaseActions.TAP,
) : StoreRoomItemDelegate


data class MuseumRfStoreRoomSummary(
    val theArea: String,
    val itemCount: Int,
    val items: List<StoreRoomItemDelegate>
)


fun List<RfStoreRoomDelegate>.toAreaList(): List<MuseumRfStoreRoomSummary> {
    return this
        .groupBy { theItem: RfStoreRoomDelegate -> theItem.theAreaId }
        .map { (area: String, items: List<RfStoreRoomDelegate>) ->
            val delegateList = items.map { theItem: RfStoreRoomDelegate ->
                StoreRoomItemImpl(theDataObject = theItem,)
            }
            MuseumRfStoreRoomSummary(
                theArea = area,
                itemCount = items.size,
                items = delegateList,
            )
        }
}

///

interface EoEmployeeItemDelegate : TextLabelDelegate {
    val theEmployee: EoEmployeeExtDelegate   
}


data class EoEmployeeItemImpl(
    override val theEmployee: EoEmployeeExtDelegate,    
    override val theIdentifier: String = theEmployee.theEmployeeId,
    override val theCellType: Int = BaseCellTypes.ITEM,
    override val theDescription: String = theEmployee.theName,
    override val theAction: String = theEmployee.theCode,
) : EoEmployeeItemDelegate



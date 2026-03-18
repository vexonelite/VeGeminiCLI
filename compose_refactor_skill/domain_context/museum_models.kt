

interface KeywordQueryModelDelegate {
    val theKeyword: String
}


interface RfGalleryQueryModelDelegate : KeywordQueryModelDelegate {
    val theGalleryId: String
    val theCode: String
    val theNameCht: String
    val theNameEng: String
    val theAuthorCht: String
    val theAuthorEng: String
    val theKind: String
}


interface RfGalleryItemDelegate {
    val theGalleryId: String
    val theCode: String
    val theNameCht: String
    val theNameEng: String
    val theAuthorCht: String
    val theKind: String
    val theImageUrl: String
    val thePartsQty: Float?
    val theLength: Float?
    val theWidth: Float?
    val theHeight: Float?
    val theLocation1: String
    val theLocation2: String
    val theLocation3: String
    //val theLocation4: String
    val thePlace: String
    val theRemark: String
    val theStatus: String
    val theStatusName: String
    val theOnSite: String
}


data class RfGalleryItemImpl(
    override val theGalleryId: String = generateRandomStringViaUuid(),
    override val theCode: String = "",
    override val theNameCht: String = "",
    override val theNameEng: String = "",
    override val theAuthorCht: String = "",
    override val theKind: String = "",
    override val theImageUrl: String = "", 
    override val thePartsQty: Float? = null,
    override val theLength: Float? = null,
    override val theWidth: Float? = null,
    override val theHeight: Float? = null,
    override val theLocation1: String = "",
    override val theLocation2: String = "",
    override val theLocation3: String = "",
    override val thePlace: String = "",
    override val theRemark: String = "",
    override val theStatus: String = "",
    override val theStatusName: String = "",
    override val theOnSite: String = "", 
) : RfGalleryItemDelegate


///

interface RfStoreRoomQueryModelDelegate : KeywordQueryModelDelegate {
    val theStoreRoomId: String
    val theCode: String
    val theName: String
    val theWarehouse: String
    val theWarehouseName: String
}


interface RfStoreRoomDelegate {
    val theStoreRoomId: String
    val theAreaId: String
    val theCode: String
    val theName: String
    val thePositionX: String
    val thePositionY: String
    val theWarehouse: String
    val theWarehouseName: String
}


data class RfStoreRoomImpl(
    override val theStoreRoomId: String = generateRandomStringViaUuid(),
    override val theAreaId: String = "",
    override val theCode: String = "",
    override val theName: String = "",
    override val thePositionX: String = "",
    override val thePositionY: String = "",
    override val theWarehouse: String = "",
    override val theWarehouseName: String = "",
) : RfStoreRoomDelegate


interface RfReadLogPassiveQueryModelDelegate : KeywordQueryModelDelegate {
    val theDeviceId: String
    val theDeviceDesc: String
    val theAntenna: String
    val theObjectId: String
    val theObjectCode: String
    val theObjectNameCht: String
    val theObjectNameEng: String
    val theReadTime: String
    val theReadTimeStart: String
    val theReadTimeEnd: String
    val theTagId: String
    val theTagData: String
}


interface RfReadLogPassiveDelegate {
    val theReadLogPassiveId: String
    val theDeviceId: String
    val theDeviceDescription: String
    val theAntenna: String
    val theObjectId: String
    val theObjectCode: String
    val theObjectNameCht: String
    val theObjectNameEng: String
    val theTagId: String
    val theTagData: String
    val theReadTime: String
}

///

interface RfGalleryApplyQueryModelDelegate : KeywordQueryModelDelegate {
    val theGalleryApplyId: String
    val theApplyNo: String
    val theApplyUnit: String
    val theApplicant: String
    val theApplyDate: String
    val theApplyDateStart: String
    val theApplyDateEnd: String
}


interface RfGalleryApplyDelegate {
    val theGalleryApplyId: String
    val theApplicant: String
    val theApplyDate: String
    val theApplyNo: String
    val theApplyUnit: String
    val theDestination: String
    val theExhibitionName: String
    val theExhibitionLocation: String
    val theExhibitionPeriod: String
    //val theExhibitionUrl: String
    val theCaseName: String
    val theStartDate: String
    val theEndDate: String
}


interface RfGalleryApplyExt02Delegate : RfGalleryApplyDelegate {
    val theApplyDetails: List<RfGalleryApplyDetailDelegate>
}


data class RfGalleryApplyExtImpl(
    override val theGalleryApplyId: String = generateRandomStringViaUuid(),
    override val theApplicant: String = "",
    override val theApplyDate: String = "",
    override val theApplyNo: String = "",
    override val theApplyUnit: String = "",
    override val theDestination: String = "",
    override val theExhibitionName: String = "",
    override val theExhibitionLocation: String = "",
    override val theExhibitionPeriod: String = "",
    override val theCaseName: String = "",
    override val theStartDate: String = "",
    override val theEndDate: String = "",
    override val theApplyDetails: List<RfGalleryApplyDetailDelegate> = listOf(),
) : RfGalleryApplyExt02Delegate


interface RfGalleryApplyDetailQueryModelDelegate : KeywordQueryModelDelegate {
    val theGalleryApplyId: String
}


interface RfGalleryApplyDetailDelegate {
    val theGalleryApplyDetailId: String
    val theStatus: String
    val theStatusName: String
    val theGalleryApplyId: String
    val theGalleryId: String
    val theGalleryCode: String
    val theGalleryKind: String
    val theGalleryNameCht: String
    val theGalleryNameEng: String
    val theGalleryAuthorCht: String
    val theGalleryAuthorEng: String
    val theGalleryLocation1: String
    val theGalleryLocation2: String
    val theGalleryLocation3: String
    val theGalleryLocation4: String
    val theGalleryLocationDescription: String
}


data class RfGalleryApplyDetailExtImpl(
    override val theGalleryApplyDetailId: String = generateRandomStringViaUuid(),
    override val theStatus: String = "",
    override val theStatusName: String = "",
    override val theGalleryApplyId: String = "",
    override val theGalleryId: String = "",
    override val theGalleryCode: String = "",
    override val theGalleryKind: String = "",
    override val theGalleryNameCht: String = "",
    override val theGalleryNameEng: String = "",
    override val theGalleryAuthorCht: String = "",
    override val theGalleryAuthorEng: String = "",
    override val theGalleryLocation1: String = "",
    override val theGalleryLocation2: String = "",
    override val theGalleryLocation3: String = "",
    override val theGalleryLocation4: String = "",
    override val theGalleryLocationDescription: String = "",
) : RfGalleryApplyDetailDelegate

///

interface RfInventoryIdQueryModelDelegate : KeywordQueryModelDelegate {
    val theInventoryId: String
}


interface RfInventoryQueryModelDelegate : RfInventoryIdQueryModelDelegate {
    val theName: String
    val theInventoryDate: String
    val theInventoryDateStart: String
    val theInventoryDateEnd: String
}


interface RfInventoryDelegate {
    val theInventoryId: String
    val theInventoryNo: String
    val theName: String
    val theStatus: String
}


data class RfInventoryImpl(
    override val theInventoryId: String = generateRandomStringViaUuid(),
    override val theInventoryNo: String = "",
    override val theName: String = "",
    override val theStatus: String = ""
) : RfInventoryDelegate


interface RfInventoryRoomSummaryDelegate {
    val theInventoryId: String
    val theName: String
    val theLocationDescription: String
    val theStatus: String
}


data class RfInventoryRoomSummaryImpl(
    override val theInventoryId: String = generateRandomStringViaUuid(),
    override val theName: String = "",
    override val theLocationDescription: String = "",
    override val theStatus: String = "",
) : RfInventoryRoomSummaryDelegate


interface RfInventoryDetailQueryModelDelegate : RfInventoryIdQueryModelDelegate {
    val theLocation1: String
    val theLocation2: String
    val theLocation3: String
    val theLocation4: String
    val theLocationDescription: String
}


interface RfInventoryDetailDelegate {
    val theInventoryDetailId: String
    val theInventoryId: String
    val theGalleryId: String
    val theGalleryCode: String
    val theGalleryNameCht: String
    val theGalleryAuthorCht: String
    val theGalleryStatus: String
    val theRemark: String
    val theStatus: String
    val theStatusName: String
    val theCheckWay: String
    val theCheckWayName: String
}


data class RfInventoryDetailImpl(
    override val theInventoryDetailId: String = generateRandomStringViaUuid(),
    override val theInventoryId: String = "",
    override val theGalleryId: String = "",
    override val theGalleryCode: String = "",
    override val theGalleryNameCht: String = "",
    override val theGalleryAuthorCht: String = "",
    override val theGalleryStatus: String = "",
    override val theRemark: String = "",
    override val theStatus: String = "",
    override val theStatusName: String = "",
    override val theCheckWay: String = "",
    override val theCheckWayName: String = "",
) : RfInventoryDetailDelegate



interface EoEmployeeDelegate : UpdatedAtDelegate {
    val theEmployeeId: String
    val theCode: String
    val theName: String
    val theLoginAccount: String
}


interface EoEmployeeExtDelegate : EoEmployeeDelegate {
    val theCardId: String
    val theUhfTagId: String
}


interface RfTagManageLogItemDelegate {
    /**   */
    val theTagManageLogId: String
    /**  */
    val theEvent: String
    /**  */
    val theEventName: String
    /**  */
    val theEventTime: String
    /**  */
    val theKind: String
    /**  */
    val theKindName: String
    /**  */
    val theObjectCode: String
    /**  */
    val theObjectId: String
    /**  */
    val theObjectName: String
    /**  */
    val theQuantity: Int
    /**  */
    val theRemark: String
    /**  */
    val theRagId: String
}


data class RfTagManageLogItemImpl(
    override val theTagManageLogId: String = generateRandomStringViaUuid(),
    override val theEvent: String = "",
    override val theEventName: String = "",
    override val theEventTime: String = "",
    override val theKind: String = "",
    override val theKindName: String = "",
    override val theObjectCode: String = "",
    override val theObjectId: String = "",
    override val theObjectName: String = "",
    override val theQuantity: Int = 0,
    override val theRemark: String = "",
    override val theRagId: String = "",
) : RfTagManageLogItemDelegate



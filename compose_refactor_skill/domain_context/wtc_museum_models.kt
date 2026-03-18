

@Serializable
data class WtcRfGalleryQueryModel(
    /**  */
    @SerializedName("Q_Keyword") @SerialName("Q_Keyword")
    override val theKeyword: String = "",
    @SerializedName("Q_GalleryId") @SerialName("Q_GalleryId")
    override val theGalleryId: String = "",
    /**  */
    @SerializedName("Q_Code") @SerialName("Q_Code")
    override val theCode: String = "",
    /**  */
    @SerializedName("Q_NameCht") @SerialName("Q_NameCht")
    override val theNameCht: String = "",
    /**  */
    @SerializedName("Q_NameEng") @SerialName("Q_NameEng")
    override val theNameEng: String = "",
    /**  */
    @SerializedName("Q_AuthorCht") @SerialName("Q_AuthorCht")
    override val theAuthorCht: String = "",
    /**  */
    @SerializedName("Q_AuthorEng") @SerialName("Q_AuthorEng")
    override val theAuthorEng: String = "",
    /**  */
    @SerializedName("Q_Kind") @SerialName("Q_Kind")
    override val theKind: String = "",
) : RfGalleryQueryModelDelegate


@Serializable
data class WtcRfGalleryItem(
    /**  */
    @SerializedName("RFG_GalleryId") @SerialName("RFG_GalleryId")
    override val theGalleryId: String = "",
    /**  */
    @SerializedName("RFG_Code") @SerialName("RFG_Code")
    override val theCode: String = "",
    /**  */
    @SerializedName("RFG_NameCht") @SerialName("RFG_NameCht")
    override val theNameCht: String = "",
    /**  */
    @SerializedName("RFG_NameEng") @SerialName("RFG_NameEng")
    override val theNameEng: String = "",
    /**  */
    @SerializedName("RFG_AuthorCht") @SerialName("RFG_AuthorCht")
    override val theAuthorCht: String = "",
    /**  */
    @SerializedName("RFG_AuthorEng") @SerialName("RFG_AuthorEng") val authorEng: String = "",
    /**  */
    @SerializedName("RFG_Kind") @SerialName("RFG_Kind")
    override val theKind: String = "",

    /**  */
    @SerializedName("RFG_Group") @SerialName("RFG_Group") val group: String = "",
    /**  */
    @SerializedName("RFG_GroupName_XX") @SerialName("RFG_GroupName_XX") val groupName: String = "",
    /**  */
    @SerializedName("RFG_Situation") @SerialName("RFG_Situation") val situation: String = "",
    /**  */
    @SerializedName("RFG_ImageUrl") @SerialName("RFG_ImageUrl")
    override val theImageUrl: String = "",   
    /**  */
    @SerializedName("RFG_Loc1") @SerialName("RFG_Loc1")
    override val theLocation1: String = "",
    /**  */
    @SerializedName("RFG_Loc2") @SerialName("RFG_Loc2")
    override val theLocation2: String = "",
    /**  */
    @SerializedName("RFG_Loc3") @SerialName("RFG_Loc3")
    override val theLocation3: String = "",
    /**  */
    @SerializedName("RFG_Loc4") @SerialName("RFG_Loc4")
    val location4: String = "",
    /**  */
    @SerializedName("RFG_LocDesc_XX") @SerialName("RFG_LocDesc_XX") val locationDescription: String = "",
    /**  */
    @SerializedName("RFG_Place") @SerialName("RFG_Place")
    override val thePlace: String = "",
    /**  */
    @SerializedName("RFG_PartsQty") @SerialName("RFG_PartsQty")
    override val thePartsQty: Float? = null,
    /**  */
    @SerializedName("RFG_Length") @SerialName("RFG_Length")
    override val theLength: Float? = null,
    /**  */
    @SerializedName("RFG_Width") @SerialName("RFG_Width")
    override val theWidth: Float? = null,
    /**  */
    @SerializedName("RFG_Height") @SerialName("RFG_Height")
    override val theHeight: Float? = null,
    /**  */
    @SerializedName("RFG_FilmLength") @SerialName("RFG_FilmLength") val filmLength: String = "",
    /**  */
    @SerializedName("RFG_ReferenceId") @SerialName("RFG_ReferenceId") val referenceId: Float? = null,
    /**  */
    @SerializedName("RFG_Remark") @SerialName("RFG_Remark")
    override val theRemark: String = "",
    /**  */
    @SerializedName("RFG_Status") @SerialName("RFG_Status")
    override val theStatus: String = "",
    /**  */
    @SerializedName("RFG_StatusName_XX") @SerialName("RFG_StatusName_XX")
    override val theStatusName: String = "",
    /**  */
    @SerializedName("RFG_OnSite") @SerialName("RFG_OnSite")
    override val theOnSite: String = "",
    /**  */
    @SerializedName("RFG_OnSiteName_XX") @SerialName("RFG_OnSiteName_XX") val onSiteName: String = "",
    /**  */
    @SerializedName("RFG_NeedUpload") @SerialName("RFG_NeedUpload") val needUpload: String = "",
) : RfGalleryItemDelegate {     
    override fun toString(): String =
        when(val json = KsnObjectToJsonStringConverter3<WtcRfGalleryItem>(WtcRfGalleryItem.serializer()).convertIntoData(this)) {
            is FmApiResult.Success -> { json.data }
            else -> { "" }
        }

}


@Serializable
data class WtcGalleryListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcRfGalleryItem> = listOf(),
) : BaseRestfulApiResponse()

///


@Serializable
data class WtcRfGalleryApplyQueryModel(
    /**  */
    @SerializedName("Q_Keyword") @SerialName("Q_Keyword")
    override val theKeyword: String = "",
    /**  */
    @SerializedName("Q_GalleryApplyId") @SerialName("Q_GalleryApplyId")
    override val theGalleryApplyId: String = "",
    /**  */
    @SerializedName("Q_ApplyNo") @SerialName("Q_ApplyNo")
    override val theApplyNo: String = "",
    /**  */
    @SerializedName("Q_ApplyUnit") @SerialName("Q_ApplyUnit")
    override val theApplyUnit: String = "",
    /**  */
    @SerializedName("Q_Applicant") @SerialName("Q_Applicant")
    override val theApplicant: String = "",
    /**  */
    @SerializedName("Q_ApplyDate") @SerialName("Q_ApplyDate")
    override val theApplyDate: String = "",
    /**  */
    @SerializedName("Q_ApplyDateStart") @SerialName("Q_ApplyDateStart")
    override val theApplyDateStart: String = "",
    /**  */
    @SerializedName("Q_ApplyDateEnd") @SerialName("Q_ApplyDateEnd")
    override val theApplyDateEnd: String = "",
) : RfGalleryApplyQueryModelDelegate


/**  */
@Serializable
data class WtcRfGalleryApplyItem(
    /**  */
    @SerializedName("RFGA_GalleryApplyId") @SerialName("RFGA_GalleryApplyId")
    override val theGalleryApplyId: String = "",
    /**  */
    @SerializedName("RFGA_Applicant") @SerialName("RFGA_Applicant")
    override val theApplicant: String = "",
    /**  */
    @SerializedName("RFGA_ApplyDate") @SerialName("RFGA_ApplyDate")
    override val theApplyDate: String = "",
    /**  */
    @SerializedName("RFGA_ApplyNo") @SerialName("RFGA_ApplyNo")
    override val theApplyNo: String = "",
    /**  */
    @SerializedName("RFGA_ApplyUnit") @SerialName("RFGA_ApplyUnit")
    override val theApplyUnit: String = "",
    /**  */
    @SerializedName("RFGA_Destination") @SerialName("RFGA_Destination")
    override val theDestination: String = "",
    /**  */
    @SerializedName("RFGA_ExhibitionName") @SerialName("RFGA_ExhibitionName")
    override val theExhibitionName: String = "",
    /**  */
    @SerializedName("RFGA_ExhibitionLocation") @SerialName("RFGA_ExhibitionLocation")
    override val theExhibitionLocation: String = "",
    /**  */
    @SerializedName("RFGA_ExhibitionPeriod") @SerialName("RFGA_ExhibitionPeriod")
    override val theExhibitionPeriod: String = "",
    /**  */
    @SerializedName("RFGA_ExhibitionUrl") @SerialName("RFGA_ExhibitionUrl")
    val exhibitionUrl: String = "",
    /**  */
    @SerializedName("RFGA_CaseName") @SerialName("RFGA_CaseName")
    override val theCaseName: String = "",
    /**  */
    @SerializedName("RFGA_StartDate") @SerialName("RFGA_StartDate")
    override val theStartDate: String = "",
    /**  */
    @SerializedName("RFGA_EndDate") @SerialName("RFGA_EndDate")
    override val theEndDate: String = "",
    /**  */
    @SerializedName("RFGA_ReferenceId") @SerialName("RFGA_ReferenceId")
    val referenceId: Float? = null,
    
) : RfGalleryApplyDelegate {
    override fun toString(): String =
        when(val json = KsnObjectToJsonStringConverter3<WtcRfGalleryApplyItem>(serializer()).convertIntoData(this)) {
            is FmApiResult.Success -> { json.data }
            else -> { "" }
        }
}


@Serializable
data class WtcGalleryApplyListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcRfGalleryApplyItem> = listOf(),
) : BaseRestfulApiResponse()



/**  */
@Serializable
data class WtcRfGalleryApplyItemExt02(
    /**  */
    @SerializedName("RFGA_GalleryApplyId") @SerialName("RFGA_GalleryApplyId")
    override val theGalleryApplyId: String = "",
    /**  */
    @SerializedName("RFGA_Applicant") @SerialName("RFGA_Applicant")
    override val theApplicant: String = "",
    /**  */
    @SerializedName("RFGA_ApplyDate") @SerialName("RFGA_ApplyDate")
    override val theApplyDate: String = "",
    /**  */
    @SerializedName("RFGA_ApplyNo") @SerialName("RFGA_ApplyNo")
    override val theApplyNo: String = "",
    /**  */
    @SerializedName("RFGA_ApplyUnit") @SerialName("RFGA_ApplyUnit")
    override val theApplyUnit: String = "",
    /**  */
    @SerializedName("RFGA_Destination") @SerialName("RFGA_Destination")
    override val theDestination: String = "",
    /**  */
    @SerializedName("RFGA_ExhibitionName") @SerialName("RFGA_ExhibitionName")
    override val theExhibitionName: String = "",
    /**  */
    @SerializedName("RFGA_ExhibitionLocation") @SerialName("RFGA_ExhibitionLocation")
    override val theExhibitionLocation: String = "",
    /**  */
    @SerializedName("RFGA_ExhibitionPeriod") @SerialName("RFGA_ExhibitionPeriod")
    override val theExhibitionPeriod: String = "",
    /**  */
    @SerializedName("RFGA_ExhibitionUrl") @SerialName("RFGA_ExhibitionUrl")
    val exhibitionUrl: String = "",
    /**  */
    @SerializedName("RFGA_CaseName") @SerialName("RFGA_CaseName")
    override val theCaseName: String = "",
    /**  */
    @SerializedName("RFGA_StartDate") @SerialName("RFGA_StartDate")
    override val theStartDate: String = "",
    /**  */
    @SerializedName("RFGA_EndDate") @SerialName("RFGA_EndDate")
    override val theEndDate: String = "",
    /**  */
    @SerializedName("RFGA_ReferenceId") @SerialName("RFGA_ReferenceId")
    val referenceId: Float? = null,

    @SerializedName("Children_Detail") @SerialName("Children_Detail")
    override val theApplyDetails: List<WtcRfGalleryApplyDetailItem> = emptyList(),
) : RfGalleryApplyExt02Delegate {
    override fun toString(): String =
        when(val json = KsnObjectToJsonStringConverter3<WtcRfGalleryApplyItemExt02>(serializer()).convertIntoData(this)) {
            is FmApiResult.Success -> { json.data }
            else -> { "" }
        }
}


/**  */
@Serializable
data class WtcGalleryApplyListResponse02(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcRfGalleryApplyItemExt02> = listOf(),
) : BaseRestfulApiResponse()

///

@Serializable
data class WtcRfGalleryApplyDetailQueryModel(
    /**  */
    @SerializedName("Q_Keyword") @SerialName("Q_Keyword")
    override val theKeyword: String = "",
    /**  */
    @SerializedName("Q_GalleryApplyId") @SerialName("Q_GalleryApplyId")
    override val theGalleryApplyId: String = "",
) : RfGalleryApplyDetailQueryModelDelegate


/**  */
@Serializable
data class WtcRfGalleryApplyDetailItem(
    /**  */
    @SerializedName("RFGAD_GalleryApplyDetailId") @SerialName("RFGAD_GalleryApplyDetailId")
    override val theGalleryApplyDetailId: String = "",
    /**  */
    @SerializedName("RFGAD_Status") @SerialName("RFGAD_Status")
    override val theStatus: String = "",
    /**  */
    @SerializedName("RFGAD_StatusName_XX") @SerialName("RFGAD_StatusName_XX")
    override val theStatusName: String = "",
    /**  */
    @SerializedName("RFGAD_GalleryApplyId") @SerialName("RFGAD_GalleryApplyId")
    override val theGalleryApplyId: String = "",
    /**  */
    @SerializedName("RFGAD_Applicant_XX") @SerialName("RFGAD_Applicant_XX")
    val applicant: String = "",
    /**  */
    @SerializedName("RFGAD_ApplyDate_XX") @SerialName("RFGAD_ApplyDate_XX")
    val applyDate: String = "",
    /**  */
    @SerializedName("RFGAD_ApplyNo_XX") @SerialName("RFGAD_ApplyNo_XX")
    val applyNo: String = "",
    /**  */
    @SerializedName("RFGAD_ApplyUnit_XX") @SerialName("RFGAD_ApplyUnit_XX")
    val applyUnit: String = "",
    /**  */
    @SerializedName("RFGAD_Destination_XX") @SerialName("RFGAD_Destination_XX")
    val destination: String = "",
    /**  */
    @SerializedName("RFGAD_ExhibitionName_XX") @SerialName("RFGAD_ExhibitionName_XX")
    val exhibitionName: String = "",
    /**  */
    @SerializedName("RFGAD_StartDate_XX") @SerialName("RFGAD_StartDate_XX")
    val startDate: String = "",
    /**  */
    @SerializedName("RFGAD_EndDate_XX") @SerialName("RFGAD_EndDate_XX")
    val endDate: String = "",
    /**  */
    @SerializedName("RFGAD_GalleryId") @SerialName("RFGAD_GalleryId")
    override val theGalleryId: String = "",
    /**  */
    @SerializedName("RFGAD_GalleryCode_XX") @SerialName("RFGAD_GalleryCode_XX")
    override val theGalleryCode: String = "",
    /**  */
    @SerializedName("RFGAD_GalleryKind_XX") @SerialName("RFGAD_GalleryKind_XX")
    override val theGalleryKind: String = "",
    /**  */
    @SerializedName("RFGAD_GalleryNameCht_XX") @SerialName("RFGAD_GalleryNameCht_XX")
    override val theGalleryNameCht: String = "",
    /**  */
    @SerializedName("RFGAD_GalleryNameEng_XX") @SerialName("RFGAD_GalleryNameEng_XX")
    override val theGalleryNameEng: String = "",
    /**  */
    @SerializedName("RFGAD_GalleryAuthorCht_XX") @SerialName("RFGAD_GalleryAuthorCht_XX")
    override val theGalleryAuthorCht: String = "",
    /**  */
    @SerializedName("RFGAD_GalleryAuthorEng_XX") @SerialName("RFGAD_GalleryAuthorEng_XX")
    override val theGalleryAuthorEng: String = "",
    /**  */
    @SerializedName("RFGAD_GalleryLoc1_XX") @SerialName("RFGAD_GalleryLoc1_XX")
    override val theGalleryLocation1: String = "",
    /**  */
    @SerializedName("RFGAD_GalleryLoc2_XX") @SerialName("RFGAD_GalleryLoc2_XX")
    override val theGalleryLocation2: String = "",
    /**  */
    @SerializedName("RFGAD_GalleryLoc3_XX") @SerialName("RFGAD_GalleryLoc3_XX")
    override val theGalleryLocation3: String = "",
    /**  */
    @SerializedName("RFGAD_GalleryLoc4_XX") @SerialName("RFGAD_GalleryLoc4_XX")
    override val theGalleryLocation4: String = "",
    /**  */
    @SerializedName("RFGAD_GalleryLocDesc_XX") @SerialName("RFGAD_GalleryLocDesc_XX")
    override val theGalleryLocationDescription: String = "",
    /**  */
    @SerializedName("RFGAD_GalleryPartsQty_XX") @SerialName("RFGAD_GalleryPartsQty_XX")
    val galleryPartsQuantity: Float? = null,
    /**  */
    @SerializedName("RFGAD_InViewRecordId") @SerialName("RFGAD_InViewRecordId")
    val inViewRecordId: String = "",
    /**  */
    @SerializedName("RFGAD_InViewDate_XX") @SerialName("RFGAD_InViewDate_XX")
    val inViewDate: String = "",
    /**  */
    @SerializedName("RFGAD_OutViewRecordId") @SerialName("RFGAD_OutViewRecordId")
    val outViewRecordId: String = "",
    /**  */
    @SerializedName("RFGAD_OutViewDate_XX") @SerialName("RFGAD_OutViewDate_XX")
    val outViewDate: String = "",
    /**  */
    @SerializedName("RFGAD_InOutTime") @SerialName("RFGAD_InOutTime")
    val inOutTime: String = "",
    /**  */
    @SerializedName("RFGAD_NeedUpload") @SerialName("RFGAD_NeedUpload")
    val needUpload: String = "",
    /**  */
    @SerializedName("RFGAD_ImageUrl_XX") @SerialName("RFGAD_ImageUrl_XX")
    val imageUrl: String = "",
    /**  */
    @SerializedName("RFGAD_DigiFileCount_XX") @SerialName("RFGAD_DigiFileCount_XX")
    val digiFileCount: Int =  0,
    /**  */
    @SerializedName("RFGAD_ThreeDFileCount_XX") @SerialName("RFGAD_ThreeDFileCount_XX")
    val threeDFileCount: Int = 0,
    /**  */
    @SerializedName("RFGAD_Processor") @SerialName("RFGAD_Processor")
    val processor: String = "",
    /**  */
    @SerializedName("RFGAD_ProcessDate") @SerialName("RFGAD_ProcessDate")
    val processDate: String = "",
    /**  */
    @SerializedName("RFGAD_ReturnDate") @SerialName("RFGAD_ReturnDate")
    val returnDate: String = "",
    @SerializedName("Children_CVBase") @SerialName("Children_CVBase")
    val cvBases: List<WtcCvBaseItemExt> = emptyList(),
): RfGalleryApplyDetailDelegate {
    override fun toString(): String =
        when(val json = KsnObjectToJsonStringConverter3<WtcRfGalleryApplyDetailItem>(serializer()).convertIntoData(this)) {
            is FmApiResult.Success -> { json.data }
            else -> { "" }
        }
}


@Serializable
data class WtcGalleryApplyDetailListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcRfGalleryApplyDetailItem> = listOf(),
) : BaseRestfulApiResponse()

///

@Serializable
data class WtcRfInventoryQueryModel(
    /**  */
    @SerializedName("Q_Keyword") @SerialName("Q_Keyword")
    override val theKeyword: String = "",
    /**  */
    @SerializedName("Q_InventoryId") @SerialName("Q_InventoryId")
    override val theInventoryId: String = "",
    /**  */
    @SerializedName("Q_Name") @SerialName("Q_Name")
    override val theName: String = "",
    /**  */
    @SerializedName("Q_InvDate") @SerialName("Q_InvDate")
    override val theInventoryDate: String = "",
    /**  */
    @SerializedName("Q_InvDateStart") @SerialName("Q_InvDateStart")
    override val theInventoryDateStart: String = "",
    /**  */
    @SerializedName("Q_InvDateEnd") @SerialName("Q_InvDateEnd")
    override val theInventoryDateEnd: String = "",
    /**  */
    @SerializedName("Q_Manager") @SerialName("Q_Manager") val manager: String = "",
) : RfInventoryQueryModelDelegate


/**  */
@Serializable
data class WtcRfInventoryItem(
    /**  */
    @SerializedName("RFI_InventoryId") @SerialName("RFI_InventoryId")
    override val theInventoryId: String = "",
    /**  */
    @SerializedName("RFI_No") @SerialName("RFI_No")
    override val theInventoryNo: String = "",
    /**  */
    @SerializedName("RFI_Name") @SerialName("RFI_Name")
    override val theName: String = "",
    /**  */
    @SerializedName("RFI_DateRange") @SerialName("RFI_DateRange") val dateRange: String = "",
    /**  */
    @SerializedName("RFI_DateStart_XX") @SerialName("RFI_DateStart_XX") val dateRangeStart: String = "",
    /**  */
    @SerializedName("RFI_DateEnd_XX") @SerialName("RFI_DateEnd_XX") val dateRangeEnd: String = "",
    /**  */
    @SerializedName("RFI_Manager") @SerialName("RFI_Manager") val manager: String = "",
    /**  */
    @SerializedName("RFI_Mode") @SerialName("RFI_Mode") val mode: String = "",
    /**  */
    @SerializedName("RFI_Range") @SerialName("RFI_Range") val range: String = "",
    /**  */
    @SerializedName("RFI_Status_XX") @SerialName("RFI_Status_XX")
    override val theStatus: String = "",   // revision by elite_lin - 2025/12/11
    /**  */
    @SerializedName("RFI_StatusName_XX") @SerialName("RFI_StatusName_XX") val statusName: String = "",
    /**  */
    @SerializedName("RFI_ReferenceId") @SerialName("RFI_ReferenceId") val referenceId: Int = -1,
    /**  */
    @SerializedName("RFI_UndoCount_XX") @SerialName("RFI_UndoCount_XX") val undoCount: Int = 0,
    /**  */
    @SerializedName("RFI_FinishedCount_XX") @SerialName("RFI_FinishedCount_XX") val finishedCount: Int = 0,
    /**  */
    @SerializedName("RFI_TotalCount_XX") @SerialName("RFI_TotalCount_XX") val totalCount: Int = 0,
) : RfInventoryDelegate {   
    override fun toString(): String =
        when(val json = KsnObjectToJsonStringConverter3<WtcRfInventoryItem>(serializer()).convertIntoData(this)) {
            is FmApiResult.Success -> { json.data }
            else -> { "" }
        }
}


@Serializable
data class WtcRfInventoryListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcRfInventoryItem> = listOf(),
) : BaseRestfulApiResponse()

///

@Serializable
data class WtcRfInventoryRoomSummaryItem(
    /**  */
    @SerializedName("InventoryId") @SerialName("InventoryId")
    override val theInventoryId: String = "",
    /**  */
    @SerializedName("InvName") @SerialName("InvName")
    override val theName: String = "",
    /**  */
    @SerializedName("Loc1") @SerialName("Loc1") val location1: String = "",
    /**  */
    @SerializedName("Loc2") @SerialName("Loc2") val location2: String = "",
    /**  */
    @SerializedName("Loc3") @SerialName("Loc3") val location3: String = "",
    /**  */
    @SerializedName("Loc4") @SerialName("Loc4") val location4: String = "",
    /**  */
    @SerializedName("LocDesc") @SerialName("LocDesc")
    override val theLocationDescription: String = "",
    /**  */
    @SerializedName("Status") @SerialName("Status")
    override val theStatus: String = "",
    /**  */
    @SerializedName("StatusName") @SerialName("StatusName") val statusName: String = "",
    /**  */
    @SerializedName("UndoCount") @SerialName("UndoCount") val undoCount: Int = 0,
    /**  */
    @SerializedName("FinishedCount") @SerialName("FinishedCount") val finishedCount: Int = 0,
    /**  */
    @SerializedName("TotalCount") @SerialName("TotalCount") val totalCount: Int = 0,
) : RfInventoryRoomSummaryDelegate {    
    override fun toString(): String =
        when(val json = KsnObjectToJsonStringConverter3<WtcRfInventoryRoomSummaryItem>(serializer()).convertIntoData(this)) {
            is FmApiResult.Success -> { json.data }
            else -> { "" }
        }
}


@Serializable
data class WtcRfInventoryRoomSummaryListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcRfInventoryRoomSummaryItem> = listOf(),
) : BaseRestfulApiResponse()

///

@Serializable
data class WtcRfInventoryDetailQueryModel(
    /**  */
    @SerializedName("Q_Keyword") @SerialName("Q_Keyword")
    override val theKeyword: String = "",
    /**  */
    @SerializedName("Q_InventoryId") @SerialName("Q_InventoryId")
    override val theInventoryId: String = "",
    /**  */
    @SerializedName("Q_GalleryLoc1") @SerialName("Q_GalleryLoc1")
    override val theLocation1: String = "",
    /**  */
    @SerializedName("Q_GalleryLoc2") @SerialName("Q_GalleryLoc2")
    override val theLocation2: String = "",
    /**  */
    @SerializedName("Q_GalleryLoc3") @SerialName("Q_GalleryLoc3")
    override val theLocation3: String = "",
    /**  */
    @SerializedName("Q_GalleryLoc4") @SerialName("Q_GalleryLoc4")
    override val theLocation4: String = "",
    /**  */
    @SerializedName("Q_GalleryLocDesc") @SerialName("Q_GalleryLocDesc")
    override val theLocationDescription: String = "",
) : RfInventoryDetailQueryModelDelegate


/**  */
@Serializable
data class WtcRfInventoryDetailItem(
    /**  */
    @SerializedName("RFID_InventoryDetailId") @SerialName("RFID_InventoryDetailId")
    override val theInventoryDetailId: String = "",
    /**  */
    @SerializedName("RFID_InventoryId") @SerialName("RFID_InventoryId")
    override val theInventoryId: String = "",
    /**  */
    @SerializedName("RFID_InventoryNo_XX") @SerialName("RFID_InventoryNo_XX") val inventoryNo: String = "",
    /**  */
    @SerializedName("RFID_InventoryName_XX") @SerialName("RFID_InventoryName_XX") val inventoryName: String = "",
    /**  */
    @SerializedName("RFID_GalleryId") @SerialName("RFID_GalleryId")
    override val theGalleryId: String = "",
    /**  */
    @SerializedName("RFID_GalleryCode_XX") @SerialName("RFID_GalleryCode_XX")
    override val theGalleryCode: String = "",
    /**  */
    @SerializedName("RFID_GalleryNameCht_XX") @SerialName("RFID_GalleryNameCht_XX")
    override val theGalleryNameCht: String = "",
    /**  */
    @SerializedName("RFID_GalleryNameEng_XX") @SerialName("RFID_GalleryNameEng_XX") val galleryNameEng: String = "",
    /**  */
    @SerializedName("RFID_GalleryAuthorCht_XX") @SerialName("RFID_GalleryAuthorCht_XX")
    override val theGalleryAuthorCht: String = "",
    /**  */
    @SerializedName("RFID_GalleryAuthorEng_XX") @SerialName("RFID_GalleryAuthorEng_XX") val galleryAuthorEng: String = "",
    /**  */
    @SerializedName("RFID_GalleryKind_XX") @SerialName("RFID_GalleryKind_XX") val galleryKind: String = "",
    /**  */
    @SerializedName("RFID_GalleryLoc1_XX") @SerialName("RFID_GalleryLoc1_XX") val galleryLocation1: String = "",
    /**  */
    @SerializedName("RFID_GalleryLoc2_XX") @SerialName("RFID_GalleryLoc2_XX") val galleryLocation2: String = "",
    /**  */
    @SerializedName("RFID_GalleryLoc3_XX") @SerialName("RFID_GalleryLoc3_XX") val galleryLocation3: String = "",
    /**  */
    @SerializedName("RFID_GalleryLoc4_XX") @SerialName("RFID_GalleryLoc4_XX") val galleryLocation4: String = "",
    /**  */
    @SerializedName("RFID_GalleryLocDesc_XX") @SerialName("RFID_GalleryLocDesc_XX") val galleryLocationDescription: String = "",
    /**  */
    @SerializedName("RFID_GalleryPartsQty_XX") @SerialName("RFID_GalleryPartsQty_XX") val galleryPartsQty: Int = 0,
    /**  */
    @SerializedName("RFID_GalleryStatus_XX") @SerialName("RFID_GalleryStatus_XX")
    override val theGalleryStatus: String = "",
    /**  */
    @SerializedName("RFID_CheckQty") @SerialName("RFID_CheckQty") val checkQty: Int = 0,
    /**  */
    @SerializedName("RFID_CheckTime") @SerialName("RFID_CheckTime") val checkTime: String = "",
    /**  */
    @SerializedName("RFID_OperatorId") @SerialName("RFID_OperatorId") val operatorId: String = "",
    /**  */
    @SerializedName("RFID_OperatorCode_XX") @SerialName("RFID_OperatorCode_XX") val operatorCode: String = "",
    /**  */
    @SerializedName("RFID_OperatorName_XX") @SerialName("RFID_OperatorName_XX") val operatorName: String = "",
    /**  */
    @SerializedName("RFID_ReferenceId") @SerialName("RFID_ReferenceId") val referenceId: Int = -1,
    /**  */
    @SerializedName("RFID_Remark") @SerialName("RFID_Remark")
    override val theRemark: String = "",
    /**  */
    @SerializedName("RFID_Status") @SerialName("RFID_Status")
    override val theStatus: String = "",
    /**  */
    @SerializedName("RFID_StatusName_XX") @SerialName("RFID_StatusName_XX")
    override val theStatusName: String = "",
    /**  */
    @SerializedName("RFID_CheckWay") @SerialName("RFID_CheckWay")
    override val theCheckWay: String = "",
    /**  */
    @SerializedName("RFID_CheckWayName_XX") @SerialName("RFID_CheckWayName_XX")
    override val theCheckWayName: String = "",
    /**  */
    @SerializedName("RFID_NeedUpload") @SerialName("RFID_NeedUpload") val needUpload: String = "",
) : RfInventoryDetailDelegate {   
    override fun toString(): String =
        when(val json = KsnObjectToJsonStringConverter3<WtcRfInventoryDetailItem>(serializer()).convertIntoData(this)) {
            is FmApiResult.Success -> { json.data }
            else -> { "" }
        }
}



fun RfInventoryDetailDelegate.cloneByCheckWayStatusAndRemarkExt(
    checkWay: String? = null,
    status: String? = null,
    remark: String? = null,
) : WtcRfInventoryDetailItem {
    return if (this@cloneByCheckWayStatusAndRemarkExt is WtcRfInventoryDetailItem) {
        return this@cloneByCheckWayStatusAndRemarkExt.copy(
            theCheckWay = checkWay ?: this@cloneByCheckWayStatusAndRemarkExt.theCheckWay,
            theStatus = status ?: this@cloneByCheckWayStatusAndRemarkExt.theStatus,
            theRemark = remark ?: this@cloneByCheckWayStatusAndRemarkExt.theRemark,
        )
    }
    else {
        WtcRfInventoryDetailItem(
            theInventoryDetailId = this@cloneByCheckWayStatusAndRemarkExt.theInventoryDetailId,
            theInventoryId = this@cloneByCheckWayStatusAndRemarkExt.theInventoryId,
            theGalleryId = this@cloneByCheckWayStatusAndRemarkExt.theGalleryId,
            theGalleryCode = this@cloneByCheckWayStatusAndRemarkExt.theGalleryCode,
            theGalleryNameCht = this@cloneByCheckWayStatusAndRemarkExt.theGalleryNameCht,
            theGalleryAuthorCht = this@cloneByCheckWayStatusAndRemarkExt.theGalleryAuthorCht,
            theGalleryStatus = this@cloneByCheckWayStatusAndRemarkExt.theGalleryStatus,
            theRemark = remark ?: this@cloneByCheckWayStatusAndRemarkExt.theRemark,
            theStatus = status ?: this@cloneByCheckWayStatusAndRemarkExt.theStatus,
            theStatusName = this@cloneByCheckWayStatusAndRemarkExt.theStatusName,
            theCheckWay = checkWay ?: this@cloneByCheckWayStatusAndRemarkExt.theCheckWay,
            theCheckWayName = this@cloneByCheckWayStatusAndRemarkExt.theCheckWayName,
        )
    }
}


@Serializable
data class WtcRfInventoryDetailListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcRfInventoryDetailItem> = listOf(),
) : BaseRestfulApiResponse()

///

@Serializable
data class WtcRfReadLogPassiveQueryModel(
    /**  */
    @SerializedName("Q_Keyword") @SerialName("Q_Keyword")
    override val theKeyword: String = "",
    /**  */
    @SerializedName("Q_DeviceId") @SerialName("Q_DeviceId")
    override val theDeviceId: String = "",
    /**  */
    @SerializedName("Q_DeviceDesc") @SerialName("Q_DeviceDesc")
    override val theDeviceDesc: String = "",
    /**  */
    @SerializedName("Q_Antenna") @SerialName("Q_Antenna")
    override val theAntenna: String = "",
    /**  */
    @SerializedName("Q_ObjectId") @SerialName("Q_ObjectId")
    override val theObjectId: String = "",
    /**  */
    @SerializedName("Q_ObjectCode") @SerialName("Q_ObjectCode")
    override val theObjectCode: String = "",
    /**  */
    @SerializedName("Q_ObjectNameCht") @SerialName("Q_ObjectNameCht")
    override val theObjectNameCht: String = "",
    /**  */
    @SerializedName("Q_ObjectNameEng") @SerialName("Q_ObjectNameEng")
    override val theObjectNameEng: String = "",
    /**  */
    @SerializedName("Q_ReadTime") @SerialName("Q_ReadTime")
    override val theReadTime: String = "",
    /**  */
    @SerializedName("Q_ReadTimeStart") @SerialName("Q_ReadTimeStart")
    override val theReadTimeStart: String = "",
    /**  */
    @SerializedName("Q_ReadTimeEnd") @SerialName("Q_ReadTimeEnd")
    override val theReadTimeEnd: String = "",
    /**  */
    @SerializedName("Q_TagId") @SerialName("Q_TagId")
    override val theTagId: String = "",
    /**  */
    @SerializedName("Q_TagData") @SerialName("Q_TagData")
    override val theTagData: String = "",
) : RfReadLogPassiveQueryModelDelegate

///

@Serializable
data class WtcRfReadLogPassiveItem(
    /**  */
    @SerializedName("RFRLP_ReadLogPassiveId") @SerialName("RFRLP_ReadLogPassiveId")
    override val theReadLogPassiveId: String = "",
    /**  */
    @SerializedName("RFRLP_DeviceId") @SerialName("RFRLP_DeviceId")
    override val theDeviceId: String = "",
    /**  */
    @SerializedName("RFRLP_DeviceDesc_XX") @SerialName("RFRLP_DeviceDesc_XX")
    override val theDeviceDescription: String = "",
    /**  */
    @SerializedName("RFRLP_Antenna") @SerialName("RFRLP_Antenna")
    override val theAntenna: String = "",
    /**  */
    @SerializedName("RFRLP_ObjectId") @SerialName("RFRLP_ObjectId")
    override val theObjectId: String = "",
    /**  */
    @SerializedName("RFRLP_ObjectCode_XX") @SerialName("RFRLP_ObjectCode_XX")
    override val theObjectCode: String = "",
    /**  */
    @SerializedName("RFRLP_ObjectNameCht_XX") @SerialName("RFRLP_ObjectNameCht_XX")
    override val theObjectNameCht: String = "",
    /**  */
    @SerializedName("RFRLP_ObjectNameEng_XX") @SerialName("RFRLP_ObjectNameEng_XX")
    override val theObjectNameEng: String = "",
    /**  */
    @SerializedName("RFRLP_TagId") @SerialName("RFRLP_TagId")
    override val theTagId: String = "",
    /**  */
    @SerializedName("RFRLP_TagData") @SerialName("RFRLP_TagData")
    override val theTagData: String = "",
    /**  */
    @SerializedName("RFRLP_ReadTime") @SerialName("RFRLP_ReadTime")
    override val theReadTime: String = "",
) : RfReadLogPassiveDelegate {
    override fun toString(): String =
        when(val json = KsnObjectToJsonStringConverter3<WtcRfReadLogPassiveItem>(serializer()).convertIntoData(this)) {
            is FmApiResult.Success -> { json.data }
            else -> { "" }
        }
}


@Serializable
data class WtcRfReadLogPassiveListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcRfReadLogPassiveItem> = listOf(),
) : BaseRestfulApiResponse()

///

@Serializable
data class WtcEoEmployeeQueryModel(
    /**  */
    @SerializedName("Q_Keyword") @SerialName("Q_Keyword") val keyword: String = "",
    /**  */
    @SerializedName("Q_HasUHFTag") @SerialName("Q_HasUHFTag") val hasUHFTag: String = "",
)


@Serializable
data class WtcEoEmployeeItem(
    /**  */
    @SerializedName("EOE_EmployeeId") @SerialName("EOE_EmployeeId")
    override val theEmployeeId: String = "",
    /**  */
    @SerializedName("EOE_CAddress") @SerialName("EOE_CAddress")
    val cAddress: String = "",
    /**  */
    @SerializedName("EOE_CardId") @SerialName("EOE_CardId")
    override val theCardId: String = "",

    /**  */
    @SerializedName("EOE_DepartmentCode_XX") @SerialName("EOE_DepartmentCode_XX")
    val departmentCode: String = "",
    /**  */
    @SerializedName("EOE_DepartmentFullName_XX") @SerialName("EOE_DepartmentFullName_XX")
    val departmentFullName: String = "",
    /**  */
    @SerializedName("EOE_DepartmentId") @SerialName("EOE_DepartmentId")
    val departmentId: String = "",
    /**  */
    @SerializedName("EOE_DepartmentName_XX") @SerialName("EOE_DepartmentName_XX")
    val departmentName: String = "",
    /**  */
    @SerializedName("EOE_EmployeeCode") @SerialName("EOE_EmployeeCode")
    override val theCode: String = "",
    /**  */
    @SerializedName("EOE_EmployeeEmail") @SerialName("EOE_EmployeeEmail")
    val email: String = "",
    /**  */
    @SerializedName("EOE_EmployeeFullName_XX") @SerialName("EOE_EmployeeFullName_XX")
    val fullName: String = "",
    /**  */
    @SerializedName("EOE_EmployeeName") @SerialName("EOE_EmployeeName")
    override val theName: String = "",
    /**  */
    @SerializedName("EOE_EmployeeSearchName_XX") @SerialName("EOE_EmployeeSearchName_XX")
    val searchName: String = "",
    /**  */
    @SerializedName("EOE_EmployeeSid") @SerialName("EOE_EmployeeSid")
    val sid: String = "",
    /**  */
    @SerializedName("EOE_EmployeeStandardName_XX") @SerialName("EOE_EmployeeStandardName_XX")
    val standardName: String = "",
    /**  */
    @SerializedName("EOE_EmployeeTitleCode_XX") @SerialName("EOE_EmployeeTitleCode_XX")
    val titleCode: String = "",
    /**  */
    @SerializedName("EOE_EmployeeTitleId") @SerialName("EOE_EmployeeTitleId")
    val titleId: String = "",
    /**  */
    @SerializedName("EOE_EmployeeTitleName_XX") @SerialName("EOE_EmployeeTitleName_XX")
    val titleName: String = "",

    /**  */
    @SerializedName("EOE_EntryDate") @SerialName("EOE_EntryDate")
    val entryDate: String = "",
    /**  */
    @SerializedName("EOE_IsAdUser") @SerialName("EOE_IsAdUser")
    val isAdUser: String = "",
    /**  */
    @SerializedName("EOE_IsLeave_XX") @SerialName("EOE_IsLeave_XX")
    val isLeave: String = "",
    /**  */
    @SerializedName("EOE_LeaveDate") @SerialName("EOE_LeaveDate")
    val leaveDate: String = "",
    /**  */
    @SerializedName("EOE_LoginAccount_XX") @SerialName("EOE_LoginAccount_XX")
    override val theLoginAccount: String = "",
    /**  */
    @SerializedName("EOE_LoginPassword_XX") @SerialName("EOE_LoginPassword_XX")
    val loginPassword: String = "",
    /**  */
    @SerializedName("EOE_PersonalImage") @SerialName("EOE_PersonalImage")
    val personalImage: String = "",
    /**  */
    @SerializedName("EOE_Phone1") @SerialName("EOE_Phone1")
    val phone1: String = "",
    /**  */
    @SerializedName("EOE_Phone2") @SerialName("EOE_Phone2")
    val phone2: String = "",
    /**  */
    @SerializedName("EOE_Remark") @SerialName("EOE_Remark")
    val remark: String = "",
    /**  */
    @SerializedName("EOE_Sex") @SerialName("EOE_Sex")
    val sex: String = "",
    /**  */
    @SerializedName("EOE_SexName_XX") @SerialName("EOE_SexName_XX")
    val sexName: String = "",
    /**  */
    @SerializedName("EOE_Signature") @SerialName("EOE_Signature")
    val signature: String = "",
    /**  */
    @SerializedName("EOE_SmsNumber") @SerialName("EOE_SmsNumber")
    val smsNumber: String = "",
    /**  */
    @SerializedName("EOE_TagNo") @SerialName("EOE_TagNo")
    val tagNo: String = "",
    /**  */
    @SerializedName("EOE_UHFTagID") @SerialName("EOE_UHFTagID")
    override val theUhfTagId: String = "",

    override val theUpdatedAt: String = "",

) : EoEmployeeExtDelegate {
    override fun toString(): String =
        when(val json = KsnObjectToJsonStringConverter3<WtcEoEmployeeItem>(serializer()).convertIntoData(this)) {
            is FmApiResult.Success -> { json.data }
            else -> { "" }
        }
}


@Serializable
data class WtcEoEmployeeListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcEoEmployeeItem> = listOf(),
) : BaseRestfulApiResponse()



@Serializable
data class WtcRfTagManageLogItem(
    /**  */
    @SerializedName("RFTML_TagManageLogId") @SerialName("RFTML_TagManageLogId")
    override val theTagManageLogId: String = "",
    /**  */
    @SerializedName("RFTML_Event") @SerialName("RFTML_Event")
    override val theEvent: String = "",
    /**  */
    @SerializedName("RFTML_EventName_XX") @SerialName("RFTML_EventName_XX")
    override val theEventName: String = "",
    /**  */
    @SerializedName("RFTML_EventTime") @SerialName("RFTML_EventTime")
    override val theEventTime: String = "",
    /**  */
    @SerializedName("RFTML_Kind") @SerialName("RFTML_Kind")
    override val theKind: String = "",
    /**  */
    @SerializedName("RFTML_KindName_XX") @SerialName("RFTML_KindName_XX")
    override val theKindName: String = "",
    /**  */
    @SerializedName("RFTML_ObjectCode_XX") @SerialName("RFTML_ObjectCode_XX")
    override val theObjectCode: String = "",
    /**  */
    @SerializedName("RFTML_ObjectId") @SerialName("RFTML_ObjectId")
    override val theObjectId: String = "",
    /**  */
    @SerializedName("RFTML_ObjectName_XX") @SerialName("RFTML_ObjectName_XX")
    override val theObjectName: String = "",
    /**  */
    @SerializedName("RFTML_Qty") @SerialName("RFTML_Qty")
    override val theQuantity: Int = 0,
    /**  */
    @SerializedName("RFTML_Remark") @SerialName("RFTML_Remark")
    override val theRemark: String = "",
    /**  */
    @SerializedName("RFTML_TagId") @SerialName("RFTML_TagId")
    override val theTagId: String = "",
) : RfTagManageLogItemDelegate {
    override fun toString(): String =
        when(val json = KsnObjectToJsonStringConverter3<WtcRfTagManageLogItem>(serializer()).convertIntoData(this)) {
            is FmApiResult.Success -> { json.data }
            else -> { "" }
        }
}


@Serializable
data class WtcRfTagManageLogListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcRfTagManageLogItem> = listOf(),
) : BaseRestfulApiResponse()



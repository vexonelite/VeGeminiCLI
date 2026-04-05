

interface KeywordQueryModelDelegate {
    /**  */
    val theKeyword: String
}


@Serializable
abstract class BaseRestfulApiResponse (
    @SerializedName("Success") @SerialName("Success") val isSuccess: Boolean = false,
    @SerializedName("Code") @SerialName("Code") val theStatusCode: String = "",
    @SerializedName("Message") @SerialName("Message") val theMessage: String = "",
    @SerializedName("DataTime") @SerialName("DataTime") val theDataTime: String = "",
) : FmApiResponseDelegate {

    override fun hasSuccessfulResult(): Boolean = isSuccess &&
            ( (theStatusCode == FmRestfulApiConstants.CODE_SUCCESS) ||
                    (theStatusCode == FmRestfulApiConstants.CODE_SAVE_SUCCESS) ||
                    (theStatusCode == FmRestfulApiConstants.CODE_DELETE_SUCCESS))

    override fun buildFmApiErrorException(): ApiErrorException = ApiErrorException(theMessage, theStatusCode)
}



interface ProcessableQuestionListDelegate<out T> {
    /**
     * Processes the delegate and returns a list of corresponding entities.
     */
    fun toRoomDbEntities(updatedAt: String): List<T>
}


interface DirtyFlagDelegate {
    /** Y / N */
    val theIsDirty: String
}


interface SyncSateDelegate {
    /** D: Default / S: Synced (Y) / P: Pending (N) */
    val theIsSynced: String
}


interface SyncedAtDelegate {
    val theSyncedAt: String
}


interface DirtySyncDelegate: DirtyFlagDelegate, UpdatedAtDelegate, SyncedAtDelegate, SyncSateDelegate


interface CvBaseDisplayAccessionNumberDelegate {
    val theDisplayAccessionNumber: String
}


interface RfGalleryItemDelegate {
    val theGalleryId: String
    /**  */
    val theCode: String
    /**  */
    val theNameCht: String
    /**  */
    val theNameEng: String
    /**  */
    val theAuthorCht: String
    /**  */
    val theKind: String

    /**  */
    val theImageUrl: String

    /**  */
    val thePartsQty: Float?
    /**  */
    val theLength: Float?
    /**  */
    val theWidth: Float?
    /**  */
    val theHeight: Float?
    /**  */
    val theLocation1: String
    /**  */
    val theLocation2: String
    /**  */
    val theLocation3: String
    /**  */
    //val theLocation4: String
    /**  */
    val thePlace: String
    /**  */
    val theRemark: String
    /**  */
    val theStatus: String
    /**  */
    val theStatusName: String

    /**  */
    val theOnSite: String

}


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
) : RfGalleryItemDelegate


@Serializable
data class WtcGalleryListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcRfGalleryItem> = listOf(),
) : BaseRestfulApiResponse()


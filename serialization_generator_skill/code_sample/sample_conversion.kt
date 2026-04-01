
interface RfGalleryQueryModelDelegate : KeywordQueryModelDelegate {
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
    val theAuthorEng: String
    /**  */
    val theKind: String
}


/**
 * {
 *   "Q_Keyword": "string",
 *   "Q_GalleryId": "string",
 *   "Q_Code": "string",
 *   "Q_NameCht": "string",
 *   "Q_NameEng": "string",
 *   "Q_AuthorCht": "string",
 *   "Q_AuthorEng": "string",
 *   "Q_Kind": "string",
 *   "Q_Level": "string"
 * }
 */
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

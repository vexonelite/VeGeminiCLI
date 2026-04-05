
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import com.google.gson.annotations.SerializedName



** added by elite_lin - 2026/04/01 */
@Serializable
data class FmMuseumSortItem(
    /**  */
    @SerializedName("ColumnName") @SerialName("ColumnName")
    override val theColumnName: String = "",
    /**  */
    @SerializedName("Direction") @SerialName("Direction")
    override val theDirection: String = ""
) : MuseumSortItemDelegate


/** added by elite_lin - 2026/04/01 */
@Serializable
data class WtcCvBaseQueryModel(
    /**  */
    @SerializedName("Q_Keyword") @SerialName("Q_Keyword")
    override val theKeyword: String = "",

    /**  */
    @SerializedName("Q_BaseID") @SerialName("Q_BaseID")
    override val theBaseId: String = "",
    /**  */
    @SerializedName("Q_YYYY") @SerialName("Q_YYYY")
    override val theYear: Int? = null,
    /**  */
    @SerializedName("Q_Secondary") @SerialName("Q_Secondary")
    override val theSecondary: Int? = null,
    /**  */
    @SerializedName("Q_AccessionNumber") @SerialName("Q_AccessionNumber")
    override val theAccessionNumber: String = "",
    /**  */
    @SerializedName("Q_AccessionNumber2") @SerialName("Q_AccessionNumber2")
    override val theAccessionNumber2: String = "",
    /**  */
    @SerializedName("Q_ViewReasonCode") @SerialName("Q_ViewReasonCode")
    override val theViewReasonCode: String = "",
    /**  */
    @SerializedName("Q_Author") @SerialName("Q_Author")
    override val theAuthor: String = "",
    /**  */
    @SerializedName("Q_StorageLocation") @SerialName("Q_StorageLocation")
    override val theStorageLocation: String = "",
    /**  */
    @SerializedName("Q_Kind") @SerialName("Q_Kind")
    override val theKind: String = "",
    /**  */
    @SerializedName("Q_KindChildren") @SerialName("Q_KindChildren")
    override val theKindChildren: String = "",
    /**  */
    @SerializedName("Q_Level") @SerialName("Q_Level")
    override val theLevel: String = "",
    /**  */
    @SerializedName("Q_BeginDate") @SerialName("Q_BeginDate")
    override val theBeginDate: String = "",
    /**  */
    @SerializedName("Q_EndDate") @SerialName("Q_EndDate")
    override val theEndDate: String = "",
    /**  */
    @SerializedName("Q_GalleryCode") @SerialName("Q_GalleryCode")
    override val theGalleryCode: String = "",
    /**  */
    @SerializedName("Q_BaseDetail2ID") @SerialName("Q_BaseDetail2ID")
    override val theBaseDetail2Id: String = "",
    /**  */
    @SerializedName("Q_SerialNumber") @SerialName("Q_SerialNumber")
    override val theSerialNumber: String = "",
    /**  */
    @SerializedName("Q_GalleryID") @SerialName("Q_GalleryID")
    override val theGalleryId: String = "",
    /**  */
    @SerializedName("Q_Semicolon") @SerialName("Q_Semicolon")
    override val theSemicolon: String = "",
    /**  */
    @SerializedName("Q_Status") @SerialName("Q_Status")
    override val theStatus: String = "",

//    /**  */
//    @SerializedName("Q_PageIndex") @SerialName("Q_PageIndex")
//    override val thePageIndex: Float? = null,
//    /**  */
//    @SerializedName("Q_PageSize") @SerialName("Q_PageSize")
//    override val thePageSize: Float? = null,
//    /**  */
//    @SerializedName("Sort") @SerialName("Sort")
//    override val theSort: List<WtcMuseumSortItem> = listOf()
) : MuseumCvBaseQueryModelDelegate


/** added by elite_lin - 2026/04/01 */
@Serializable
data class WtcCvBaseItem(
    /**  */
    @SerializedName("CVB_BaseID") @SerialName("CVB_BaseID")
    override val theBaseId: String = "",
    /**  */
    @SerializedName("CVB_Years") @SerialName("CVB_Years")
    override val theYearStr: String = "",
    /**  */
    @SerializedName("CVB_YYYY") @SerialName("CVB_YYYY")
    override val theYear: Int? = 0,
    /**  */
    @SerializedName("CVB_Secondary") @SerialName("CVB_Secondary")
    override val theSecondary: Int? = 0,
    /**  */
    @SerializedName("CVB_AccessionNumber") @SerialName("CVB_AccessionNumber")
    override val theAccessionNumber: String = "",
    /**  */
    @SerializedName("CVB_AccessionNumber_XX") @SerialName("CVB_AccessionNumber_XX")
    override val theAccessionNumberXX: String = "",
    /**  */
    @SerializedName("CVB_AccessionNumber2") @SerialName("CVB_AccessionNumber2")
    override val theAccessionNumber2: String = "",

    /**  */
    @SerializedName("CVB_ViewDate") @SerialName("CVB_ViewDate")
    override val theViewDate: String = "",
    /**  */
    @SerializedName("CVB_ViewReason") @SerialName("CVB_ViewReason")
    override val theViewReason: String = "",
    /**  */
    @SerializedName("CVB_ViewReasonCode_XX") @SerialName("CVB_ViewReasonCode_XX")
    override val theViewReasonCode: String = "",
    /**  */
    @SerializedName("CVB_ViewReasonName_XX") @SerialName("CVB_ViewReasonName_XX")
    override val theViewReasonName: String = "",

    /**  */
    @SerializedName("CVB_GalleryID") @SerialName("CVB_GalleryID")
    override val theGalleryId: String = "",
    /**  */
    @SerializedName("CVB_GalleryCode_XX") @SerialName("CVB_GalleryCode_XX")
    override val theGalleryCode: String = "",

    /**  */
    @SerializedName("CVB_Author") @SerialName("CVB_Author")
    override val theAuthor: String = "",
    /**  */
    @SerializedName("CVB_AuthCode") @SerialName("CVB_AuthCode")
    override val theAuthCode: String = "",

    /**  */
    @SerializedName("CVB_EnAuthor") @SerialName("CVB_EnAuthor")
    override val theEngAuthor: String = "",
    /**  */
    @SerializedName("CVB_WritName") @SerialName("CVB_WritName")
    override val theWritName: String = "",
    /**  */
    @SerializedName("CVB_EnWritName") @SerialName("CVB_EnWritName")
    override val theEngWritName: String = "",

    /**  */
    @SerializedName("CVB_Source") @SerialName("CVB_Source")
    override val theSource: String = "",
    /**  */
    @SerializedName("CVB_Source_XX") @SerialName("CVB_Source_XX")
    override val theSourceXX: String = "",
    /**  */
    @SerializedName("CVB_SerialNumber") @SerialName("CVB_SerialNumber")
    override val theSerialNumber: String = "",

    /**  */
    @SerializedName("CVB_Kind") @SerialName("CVB_Kind")
    override val theKind: String = "",
    /**  */
    @SerializedName("CVB_KindMain_XX") @SerialName("CVB_KindMain_XX")
    override val theKindMain: String = "",
    /**  */
    @SerializedName("CVB_KindName_XX") @SerialName("CVB_KindName_XX")
    override val theKindName: String = "",
    /**  */
    @SerializedName("CVB_KindChildren") @SerialName("CVB_KindChildren")
    override val theKindChildren: String = "",
    /**  */
    @SerializedName("CVB_KindRemark") @SerialName("CVB_KindRemark")
    override val theKindRemark: String = "",

    /**  */
    @SerializedName("CVB_Inspector") @SerialName("CVB_Inspector")
    override val theInspector: String = "",
    /**  */
    @SerializedName("CVB_Recorder") @SerialName("CVB_Recorder")
    override val theRecorder: String = "",

    /**  */
    @SerializedName("CVB_Semicolon") @SerialName("CVB_Semicolon")
    override val theSemicolon: String = "",
    /**  */
    @SerializedName("CVB_StorageLocation") @SerialName("CVB_StorageLocation")
    override val theStorageLocation: String = "",

    /**  */
    @SerializedName("CVB_Status") @SerialName("CVB_Status")
    override val theStatus: String = "",
    /**  */
    @SerializedName("CVB_StatusName_XX") @SerialName("CVB_StatusName_XX")
    override val theStatusName: String = "",

    /**  */
    @SerializedName("CVB_Remark") @SerialName("CVB_Remark")
    override val theRemark: String = "",
    /**  */
    @SerializedName("CVB_IsFrame") @SerialName("CVB_IsFrame")
    override val theIsFrame: String = "",
    /**  */
    @SerializedName("CVB_Level") @SerialName("CVB_Level")
    override val theLevel: String = "",

//    /**  */
//    @SerializedName("CVB_BaseMaterial") @SerialName("CVB_BaseMaterial")
//    override val theBaseMaterial: String = "",
//    /**  */
//    @SerializedName("CVB_Carrier") @SerialName("CVB_Carrier")
//    override val theCarrier: String = "",
//    /**  */
//    @SerializedName("CVB_Code") @SerialName("CVB_Code")
//    override val theCode: String = "",
//    /**  */
//    @SerializedName("CVB_DigitizeDate") @SerialName("CVB_DigitizeDate")
//    override val theDigitizeDate: String = "",
//    /**  */
//    @SerializedName("CVB_DigitizePerson") @SerialName("CVB_DigitizePerson")
//    override val theDigitizePerson: String = "",
//    /**  */
//    @SerializedName("CVB_DimensionX") @SerialName("CVB_DimensionX")
//    override val theDimensionX: Float? = null,
//    /**  */
//    @SerializedName("CVB_DimensionY") @SerialName("CVB_DimensionY")
//    override val theDimensionY: Float? = null,
//    /**  */
//    @SerializedName("CVB_DimensionZ") @SerialName("CVB_DimensionZ")
//    override val theDimensionZ: Float? = null,
//    /**  */
//    @SerializedName("CVB_Edition") @SerialName("CVB_Edition")
//    override val theEdition: String = "",
//
//    /**  */
//    @SerializedName("CVB_FramedForm") @SerialName("CVB_FramedForm")
//    override val theFramedForm: String = "",
//
//    /**  */
//    @SerializedName("CVB_ImageUrl") @SerialName("CVB_ImageUrl")
//    override val theImageUrl: String = "",
//
//    /**  */
//    @SerializedName("CVB_Loc1") @SerialName("CVB_Loc1")
//    override val theLocation1: String = "",
//    /**  */
//    @SerializedName("CVB_Loc2") @SerialName("CVB_Loc2")
//    override val theLocation2: String = "",
//    /**  */
//    @SerializedName("CVB_Loc3") @SerialName("CVB_Loc3")
//    override val theLocation3: String = "",
//    /**  */
//    @SerializedName("CVB_Loc4") @SerialName("CVB_Loc4")
//    override val theLocation4: String = "",
//    /**  */
//    @SerializedName("CVB_MainQty") @SerialName("CVB_MainQty")
//    override val theMainQty: Float? = null,
//    /**  */
//    @SerializedName("CVB_MediaLength") @SerialName("CVB_MediaLength")
//    override val theMediaLength: Float? = null,
//    /**  */
//    @SerializedName("CVB_MountingMaterialH") @SerialName("CVB_MountingMaterialH")
//    override val theMountingMaterialH: Float? = null,
//    /**  */
//    @SerializedName("CVB_MountingMaterialL") @SerialName("CVB_MountingMaterialL")
//    override val theMountingMaterialL: Float? = null,
//    /**  */
//    @SerializedName("CVB_MountingMaterialW") @SerialName("CVB_MountingMaterialW")
//    override val theMountingMaterialW: Float? = null,
//    /**  */
//    @SerializedName("CVB_PartsQty") @SerialName("CVB_PartsQty")
//    override val thePartsQty: Float? = null,
//    /**  */
//    @SerializedName("CVB_ProtectiveMeasures") @SerialName("CVB_ProtectiveMeasures")
//    override val theProtectiveMeasures: String = "",
//    /**  */
//    @SerializedName("CVB_ScreenProtect") @SerialName("CVB_ScreenProtect")
//    override val theScreenProtect: String = "",
//
//    /**  */
//    @SerializedName("CVB_SizeDesc") @SerialName("CVB_SizeDesc")
//    override val theSizeDesc: String = "",
//    /**  */
//    @SerializedName("CVB_Length") @SerialName("CVB_Length")
//    override val theLength: Float? = null,
//    /**  */
//    @SerializedName("CVB_Width") @SerialName("CVB_Width")
//    override val theWidth: Float? = null,
//
//    /**  */
//    @SerializedName("CVB_Height") @SerialName("CVB_Height")
//    override val theHeight: Float? = null,
//    /**  */
//    @SerializedName("CVB_Weight") @SerialName("CVB_Weight")
//    override val theWeight: Float? = null,

) : MuseumCvBaseItemDelegate {
    override fun toString(): String =
        when(val json = KsnObjectToJsonStringConverter3<WtcCvBaseItem>(serializer()).convertIntoData(this)) {
            is FmApiResult.Success -> { json.data }
            else -> { "" }
        }

    override val theDisplayAccessionNumber: String =
        // if (theAccessionNumber.isNotEmpty()) { theAccessionNumber } else { theAccessionNumber2 }
        theAccessionNumber.ifEmpty { theAccessionNumber2 }


}


/** added by elite_lin - 2026/04/01 */
@Serializable
data class WtcCvAuthorItem(
    /**  */
    @SerializedName("CVA_AuthorID") @SerialName("CVA_AuthorID")
    override val theAuthorId: String = "",
    /**  */
    @SerializedName("CVA_BaseID") @SerialName("CVA_BaseID")
    override val theBaseId: String = "",

    /**  */
    @SerializedName("CVA_Documents") @SerialName("CVA_Documents")
    override val theDocuments: String = "",
    /**  */
    @SerializedName("CVA_Documents2") @SerialName("CVA_Documents2")
    override val theDocuments2: String = "",
    /**  */
    @SerializedName("CVA_ExhibitRecord") @SerialName("CVA_ExhibitRecord")
    override val theExhibitRecord: String = "",
    /**  */
    @SerializedName("CVA_Guarantee") @SerialName("CVA_Guarantee")
    override val theGuarantee: String = "",
    /**  */
    @SerializedName("CVA_Installation") @SerialName("CVA_Installation")
    override val theInstallation: String = "",
    /**  */
    @SerializedName("CVA_IsProviderName_XX") @SerialName("CVA_IsProviderName_XX")
    override val theIsProviderName: String = "",
    /**  */
    @SerializedName("CVA_Provider") @SerialName("CVA_Provider")
    override val theProvider: String = "",

) : MuseumCvAuthorItemDelegate


/** added by elite_lin - 2026/04/01 */
@Serializable
data class WtcCvBaseDetail1Item(
    /**  */
    @SerializedName("CVBD1_BaseDetail1ID") @SerialName("CVBD1_BaseDetail1ID")
    override val theBaseDetail1Id: String = "",
    /**  */
    @SerializedName("CVBD1_BaseID") @SerialName("CVBD1_BaseID")
    override val theBaseId: String = "",
    /**  */
    @SerializedName("CVBD1_GalleryContentId") @SerialName("CVBD1_GalleryContentId")
    override val theGalleryContentId: String = "",
    /**  */
    @SerializedName("CVBD1_Semicolon") @SerialName("CVBD1_Semicolon")
    override val theSemicolon: String = "",
    /**  */
    @SerializedName("CVBD1_Remark") @SerialName("CVBD1_Remark")
    override val theRemark: String = "",

) : MuseumCvBaseDetail1Delegate


/** added by elite_lin - 2026/04/01 */
@Serializable
data class WtcCvBaseDetail2Item(
    /**  */
    @SerializedName("CVBD2_BaseDetail2ID") @SerialName("CVBD2_BaseDetail2ID")
    override val theBaseDetail2Id: String = "",
    /**  */
    @SerializedName("CVBD2_BaseID") @SerialName("CVBD2_BaseID")
    override val theBaseId: String = "",

    /**  */
    @SerializedName("CVBD2_IsBack") @SerialName("CVBD2_IsBack")
    override val theIsBack: String = "",
    /**  */
    @SerializedName("CVBD2_IsFront") @SerialName("CVBD2_IsFront")
    override val theIsFront: String = "",
    /**  */
    @SerializedName("CVBD2_IsOther") @SerialName("CVBD2_IsOther")
    override val theIsOther: String = "",
    /**  */
    @SerializedName("CVBD2_IsPhoto") @SerialName("CVBD2_IsPhoto")
    override val theIsPhoto: String = "",
    /**  */
    @SerializedName("CVBD2_Signature1") @SerialName("CVBD2_Signature1")
    override val theSignature1: String = "",
    /**  */
    @SerializedName("CVBD2_Signature2") @SerialName("CVBD2_Signature2")
    override val theSignature2: String = "",
    /**  */
    @SerializedName("CVBD2_Signature3") @SerialName("CVBD2_Signature3")
    override val theSignature3: String = "",

) : MuseumCvBaseDetail2Delegate


/** added by elite_lin - 2026/04/01 */
@Serializable
data class WtcCvAttachFileItem(
    /**  */
    @SerializedName("CVAF_AttachFileId") @SerialName("CVAF_AttachFileId")
    override val theAttachFileId: String = "",

    /**  */
    @SerializedName("CVAF_FileName") @SerialName("CVAF_FileName")
    override val theFileName: String = "",
    /**  */
    @SerializedName("CVAF_FullName") @SerialName("CVAF_FullName")
    override val theFullName: String = "",
    /**  */
    @SerializedName("CVAF_GroupCode") @SerialName("CVAF_GroupCode")
    override val theGroupCode: String = "",
    /**  */
    @SerializedName("CVAF_IsLink") @SerialName("CVAF_IsLink")
    override val theIsLink: String = "",
    /**  */
    @SerializedName("CVAF_MIME") @SerialName("CVAF_MIME")
    override val theMime: String = "",
    /**  */
    @SerializedName("CVAF_ObjectId") @SerialName("CVAF_ObjectId")
    override val theObjectId: String = "",
    /**  */
    @SerializedName("CVAF_RefId") @SerialName("CVAF_RefId")
    override val theRefId: String = "",
    /**  */
    @SerializedName("CVAF_Size") @SerialName("CVAF_Size")
    override val theSize: Float? = 0f,

) : MuseumCvAttachFileItemDelegate


/** added by elite_lin - 2026/04/01 */
@Serializable
data class WtcCvBaseNestedDetailItem(
    /**  */
    @SerializedName("AccessionNumber") @SerialName("AccessionNumber")
    override val theAccessionNumber: String = "",
    /**  */
    @SerializedName("Kind") @SerialName("Kind")
    override val theKind: String = "",
    /**  */
    @SerializedName("KindChildren") @SerialName("KindChildren")
    override val theKindChildren: String = "",
    /**  */
    @SerializedName("Number") @SerialName("Number")
    override val theNumber: String = "",
    /**  */
    @SerializedName("BaseID") @SerialName("BaseID")
    override val theBaseId: String = "",
    /**  */
    @SerializedName("IsReView") @SerialName("IsReView")
    override val theIsReView: String = "",
    /**  */
    @SerializedName("IsView") @SerialName("IsView")
    override val theIsView: String = ""

) : MuseumCvBaseNestedDetailItemDelegate


/** added by elite_lin - 2026/04/01 */
@Serializable
data class WtcCvBaseItemExt(
    /**  */
    @SerializedName("CVB_BaseID") @SerialName("CVB_BaseID")
    override val theBaseId: String = "",
    /**  */
    @SerializedName("CVB_Years") @SerialName("CVB_Years")
    override val theYearStr: String = "",
    /**  */
    @SerializedName("CVB_YYYY") @SerialName("CVB_YYYY")
    override val theYear: Int? = 0,
    /**  */
    @SerializedName("CVB_Secondary") @SerialName("CVB_Secondary")
    override val theSecondary: Int? = 0,
    /**  */
    @SerializedName("CVB_AccessionNumber") @SerialName("CVB_AccessionNumber")
    override val theAccessionNumber: String = "",
    /**  */
    @SerializedName("CVB_AccessionNumber_XX") @SerialName("CVB_AccessionNumber_XX")
    override val theAccessionNumberXX: String = "",
    /**  */
    @SerializedName("CVB_AccessionNumber2") @SerialName("CVB_AccessionNumber2")
    override val theAccessionNumber2: String = "",

    /**  */
    @SerializedName("CVB_ViewDate") @SerialName("CVB_ViewDate")
    override val theViewDate: String = "",
    /**  */
    @SerializedName("CVB_ViewReason") @SerialName("CVB_ViewReason")
    override val theViewReason: String = "",
    /**  */
    @SerializedName("CVB_ViewReasonCode_XX") @SerialName("CVB_ViewReasonCode_XX")
    override val theViewReasonCode: String = "",
    /**  */
    @SerializedName("CVB_ViewReasonName_XX") @SerialName("CVB_ViewReasonName_XX")
    override val theViewReasonName: String = "",

    /**  */
    @SerializedName("CVB_GalleryID") @SerialName("CVB_GalleryID")
    override val theGalleryId: String = "",
    /**  */
    @SerializedName("CVB_GalleryCode_XX") @SerialName("CVB_GalleryCode_XX")
    override val theGalleryCode: String = "",

    /**  */
    @SerializedName("CVB_Author") @SerialName("CVB_Author")
    override val theAuthor: String = "",
    /**  */
    @SerializedName("CVB_AuthCode") @SerialName("CVB_AuthCode")
    override val theAuthCode: String = "",

    /**  */
    @SerializedName("CVB_EnAuthor") @SerialName("CVB_EnAuthor")
    override val theEngAuthor: String = "",
    /**  */
    @SerializedName("CVB_WritName") @SerialName("CVB_WritName")
    override val theWritName: String = "",
    /**  */
    @SerializedName("CVB_EnWritName") @SerialName("CVB_EnWritName")
    override val theEngWritName: String = "",

    /**  */
    @SerializedName("CVB_Source") @SerialName("CVB_Source")
    override val theSource: String = "",
    /**  */
    @SerializedName("CVB_Source_XX") @SerialName("CVB_Source_XX")
    override val theSourceXX: String = "",
    /**  */
    @SerializedName("CVB_SerialNumber") @SerialName("CVB_SerialNumber")
    override val theSerialNumber: String = "",

    /**  */
    @SerializedName("CVB_Kind") @SerialName("CVB_Kind")
    override val theKind: String = "",
    /**  */
    @SerializedName("CVB_KindMain_XX") @SerialName("CVB_KindMain_XX")
    override val theKindMain: String = "",
    /**  */
    @SerializedName("CVB_KindName_XX") @SerialName("CVB_KindName_XX")
    override val theKindName: String = "",
    /**  */
    @SerializedName("CVB_KindChildren") @SerialName("CVB_KindChildren")
    override val theKindChildren: String = "",
    /**  */
    @SerializedName("CVB_KindRemark") @SerialName("CVB_KindRemark")
    override val theKindRemark: String = "",

    /**  */
    @SerializedName("CVB_Inspector") @SerialName("CVB_Inspector")
    override val theInspector: String = "",
    /**  */
    @SerializedName("CVB_Recorder") @SerialName("CVB_Recorder")
    override val theRecorder: String = "",

    /**  */
    @SerializedName("CVB_Semicolon") @SerialName("CVB_Semicolon")
    override val theSemicolon: String = "",
    /**  */
    @SerializedName("CVB_StorageLocation") @SerialName("CVB_StorageLocation")
    override val theStorageLocation: String = "",

    /**  */
    @SerializedName("CVB_Status") @SerialName("CVB_Status")
    override val theStatus: String = "",
    /**  */
    @SerializedName("CVB_StatusName_XX") @SerialName("CVB_StatusName_XX")
    override val theStatusName: String = "",

    /**  */
    @SerializedName("CVB_Remark") @SerialName("CVB_Remark")
    override val theRemark: String = "",
    /**  */
    @SerializedName("CVB_IsFrame") @SerialName("CVB_IsFrame")
    override val theIsFrame: String = "",
    /**  */
    @SerializedName("CVB_Level") @SerialName("CVB_Level")
    override val theLevel: String = "",

//    /**  */
//    @SerializedName("CVB_BaseMaterial") @SerialName("CVB_BaseMaterial")
//    override val theBaseMaterial: String = "",
//    /**  */
//    @SerializedName("CVB_Carrier") @SerialName("CVB_Carrier")
//    override val theCarrier: String = "",
//    /**  */
//    @SerializedName("CVB_Code") @SerialName("CVB_Code")
//    override val theCode: String = "",
//    /**  */
//    @SerializedName("CVB_DigitizeDate") @SerialName("CVB_DigitizeDate")
//    override val theDigitizeDate: String = "",
//    /**  */
//    @SerializedName("CVB_DigitizePerson") @SerialName("CVB_DigitizePerson")
//    override val theDigitizePerson: String = "",
//    /**  */
//    @SerializedName("CVB_DimensionX") @SerialName("CVB_DimensionX")
//    override val theDimensionX: Float? = null,
//    /**  */
//    @SerializedName("CVB_DimensionY") @SerialName("CVB_DimensionY")
//    override val theDimensionY: Float? = null,
//    /**  */
//    @SerializedName("CVB_DimensionZ") @SerialName("CVB_DimensionZ")
//    override val theDimensionZ: Float? = null,
//    /**  */
//    @SerializedName("CVB_Edition") @SerialName("CVB_Edition")
//    override val theEdition: String = "",
//
//    /**  */
//    @SerializedName("CVB_FramedForm") @SerialName("CVB_FramedForm")
//    override val theFramedForm: String = "",
//
//    /**  */
//    @SerializedName("CVB_ImageUrl") @SerialName("CVB_ImageUrl")
//    override val theImageUrl: String = "",
//
//    /**  */
//    @SerializedName("CVB_Loc1") @SerialName("CVB_Loc1")
//    override val theLocation1: String = "",
//    /**  */
//    @SerializedName("CVB_Loc2") @SerialName("CVB_Loc2")
//    override val theLocation2: String = "",
//    /**  */
//    @SerializedName("CVB_Loc3") @SerialName("CVB_Loc3")
//    override val theLocation3: String = "",
//    /**  */
//    @SerializedName("CVB_Loc4") @SerialName("CVB_Loc4")
//    override val theLocation4: String = "",
//    /**  */
//    @SerializedName("CVB_MainQty") @SerialName("CVB_MainQty")
//    override val theMainQty: Float? = null,
//    /**  */
//    @SerializedName("CVB_MediaLength") @SerialName("CVB_MediaLength")
//    override val theMediaLength: Float? = null,
//    /**  */
//    @SerializedName("CVB_MountingMaterialH") @SerialName("CVB_MountingMaterialH")
//    override val theMountingMaterialH: Float? = null,
//    /**  */
//    @SerializedName("CVB_MountingMaterialL") @SerialName("CVB_MountingMaterialL")
//    override val theMountingMaterialL: Float? = null,
//    /**  */
//    @SerializedName("CVB_MountingMaterialW") @SerialName("CVB_MountingMaterialW")
//    override val theMountingMaterialW: Float? = null,
//    /**  */
//    @SerializedName("CVB_PartsQty") @SerialName("CVB_PartsQty")
//    override val thePartsQty: Float? = null,
//    /**  */
//    @SerializedName("CVB_ProtectiveMeasures") @SerialName("CVB_ProtectiveMeasures")
//    override val theProtectiveMeasures: String = "",
//    /**  */
//    @SerializedName("CVB_ScreenProtect") @SerialName("CVB_ScreenProtect")
//    override val theScreenProtect: String = "",
//
//    /**  */
//    @SerializedName("CVB_SizeDesc") @SerialName("CVB_SizeDesc")
//    override val theSizeDesc: String = "",
//    /**  */
//    @SerializedName("CVB_Length") @SerialName("CVB_Length")
//    override val theLength: Float? = null,
//    /**  */
//    @SerializedName("CVB_Width") @SerialName("CVB_Width")
//    override val theWidth: Float? = null,
//
//    /**  */
//    @SerializedName("CVB_Height") @SerialName("CVB_Height")
//    override val theHeight: Float? = null,
//    /**  */
//    @SerializedName("CVB_Weight") @SerialName("CVB_Weight")
//    override val theWeight: Float? = null,

    /**  */
    @SerializedName("Children_Author") @SerialName("Children_Author")
    override val theAuthors: List<MuseumCvAuthorItemDelegate> = listOf(),
    /**  */
    @SerializedName("Children_Detail1") @SerialName("Children_Detail1")
    override val theDetail1s: List<WtcCvBaseDetail1Item> = listOf(),
    /**  */
    @SerializedName("Children_Detail2") @SerialName("Children_Detail2")
    override val theDetail2s: List<WtcCvBaseDetail2Item> = listOf(),
    /**  */
    @SerializedName("Children_AttachFile") @SerialName("Children_AttachFile")
    override val theAttachedFiles: List<WtcCvAttachFileItem> = listOf(),
    /**  */
    @SerializedName("Detail") @SerialName("Detail")
    override val theNestedDetails: List<WtcCvBaseNestedDetailItem> = listOf(),

    /**  */
    @SerializedName("ViewReasonName") @SerialName("ViewReasonName")
    override val theViewReasonNameExt: String = "",
    /**  */
    @SerializedName("IsLock") @SerialName("IsLock")
    override val theIsLockExt: String = "",
    /**  */
    @SerializedName("IsView") @SerialName("IsView")
    override val theIsViewExt: String = "",
    /**  */
    @SerializedName("SemicolonCount") @SerialName("SemicolonCount")
    override val theSemicolonCountExt: Float? = null

) : MuseumCvBaseItemExtDelegate {

    override val theDisplayAccessionNumber: String =
        // if (theAccessionNumber.isNotEmpty()) { theAccessionNumber } else { theAccessionNumber2 }
        theAccessionNumber.ifEmpty { theAccessionNumber2 }


}


/** added by elite_lin - 2026/04/01 */
@Serializable
data class WtcCvBaseItemListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcCvBaseItem> = listOf(),
    @SerializedName("TotalCount") @SerialName("TotalCount") val theTotalCount: Float? = null
) : BaseRestfulApiResponse()


/** added by elite_lin - 2026/04/01 */
@Serializable
data class WtcCvBaseItemExtListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcCvBaseItemExt> = listOf(),
    @SerializedName("TotalCount") @SerialName("TotalCount") val theTotalCount: Float? = null
) : BaseRestfulApiResponse()


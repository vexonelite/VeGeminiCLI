
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


/** added by elite_lin - 2026/04/05 */
ok
@Serializable
data class WtcControlGroupQuery(
    /**  */
    @SerializedName("Q_CloneId") @SerialName("Q_CloneId")
    override val theCloneId: String = "",
    /**  */
    @SerializedName("Q_BaseID") @SerialName("Q_BaseID")
    override val theBaseId: String = "",
    /**  */
    @SerializedName("Q_ControlGroupId") @SerialName("Q_ControlGroupId")
    override val theControlGroupId: String = "",
    /**  */
    @SerializedName("Q_Kind") @SerialName("Q_Kind")
    override val theKind: String = "",
    /**  */
    @SerializedName("Q_KindName") @SerialName("Q_KindName")
    override val theKindName: String = "",
    /**  */
    @SerializedName("Q_Name") @SerialName("Q_Name")
    override val theName: String = "",
    /**  */
    @SerializedName("Q_ReName") @SerialName("Q_ReName")
    override val theReName: String = "",
    /**  */
    @SerializedName("Q_Enabled") @SerialName("Q_Enabled")
    override val theEnabled: String = "",
    /**  */
    @SerializedName("Q_EnabledName") @SerialName("Q_EnabledName")
    override val theEnabledName: String = "",
    /**  */
    @SerializedName("Q_Keyword") @SerialName("Q_Keyword")
    override val theKeyword: String = "",
    /**  */
    @SerializedName("Q_QueryLevel") @SerialName("Q_QueryLevel")
    override val theQueryLevel: String = "",
    /**  */
    @SerializedName("Q_PageIndex") @SerialName("Q_PageIndex")
    override val thePageIndex: Float? = null,
    /**  */
    @SerializedName("Q_PageSize") @SerialName("Q_PageSize")
    override val thePageSize: Float? = null,
    /**  */
    @SerializedName("Sort") @SerialName("Sort")
    override val theSort: List<FmMuseumSortItem> = listOf()
) : MuseumCvControlGroupQueryDelegate


/** added by elite_lin - 2026/04/05 */
ok
@Serializable
data class WtcControlGroup(
    /**  */
    @SerializedName("CVCG_CloneId") @SerialName("CVCG_CloneId")
    override val theCloneId: String = "",
    /**  */
    @SerializedName("CVCG_ActInspector") @SerialName("CVCG_ActInspector")
    override val theActInspector: String = "",
    /**  */
    @SerializedName("CVCG_ActRecorder") @SerialName("CVCG_ActRecorder")
    override val theActRecorder: String = "",
    /**  */
    @SerializedName("CVCG_BaseId") @SerialName("CVCG_BaseId")
    override val theBaseId: String = "",
    /**  */
    @SerializedName("CVCG_ControlGroupId") @SerialName("CVCG_ControlGroupId")
    override val theControlGroupId: String = "",
    /**  */
    @SerializedName("CVCG_Enabled") @SerialName("CVCG_Enabled")
    override val theEnabled: String = "",
    /**  */
    @SerializedName("CVCG_EnabledName_XX") @SerialName("CVCG_EnabledName_XX")
    override val theEnabledName: String = "",
    /**  */
    @SerializedName("CVCG_Kind") @SerialName("CVCG_Kind")
    override val theKind: String = "",
    /**  */
    @SerializedName("CVCG_KindName_XX") @SerialName("CVCG_KindName_XX")
    override val theKindName: String = "",
    /**  */
    @SerializedName("CVCG_Name") @SerialName("CVCG_Name")
    override val theName: String = "",
    /**  */
    @SerializedName("CVCG_ReName") @SerialName("CVCG_ReName")
    override val theReName: String = "",
    /**  */
    @SerializedName("Children_ControlGroupDetail") @SerialName("Children_ControlGroupDetail")
    override val theChildrenControlGroupDetails: List<WtcControlGroupDetail> = listOf()
) : MuseumCvControlGroupDelegate


/** added by elite_lin - 2026/04/05 */
@Serializable
data class WtcControlGroupDetail(
    /**  */
    @SerializedName("CVCGD_CloneId") @SerialName("CVCGD_CloneId")
    override val theCloneId: String = "", // theControlGroupDetailId

    /** Reference to ``CvBase.CVB_BaseID */
    @SerializedName("CVCGD_BaseId_XX") @SerialName("CVCGD_BaseId_XX")
    override val theBaseId: String = "", //theRefCvBaseId

    /** Reference to ``CvControlGroup.CVCG_CloneId */
    @SerializedName("CVCGD_CGCloneId") @SerialName("CVCGD_CGCloneId")
    override val theCGCloneId: String = "", //theRefCvControlGroupId

    /** Reference to ``CvControlGroup.CVCG_CloneId */
    @SerializedName("CVCGD_CGID_XX") @SerialName("CVCGD_CGID_XX")
    override val theCGID: String = "", // theRefCvCgId

    /** Reference to ``SyControlGroupDetail.SYCGD_ControlGroupDetailId.`` */
    @SerializedName("CVCGD_ControlGroupDetailId") @SerialName("CVCGD_ControlGroupDetailId")
    override val theControlGroupDetailId: String = "",  // theRefSyControlGroupDetailId

    /**  */
    @SerializedName("CVCGD_ControlGroupName_XX") @SerialName("CVCGD_ControlGroupName_XX")
    override val theControlGroupName: String = "",
    /**  */
    @SerializedName("CVCGD_Enabled") @SerialName("CVCGD_Enabled")
    override val theEnabled: String = "",
    /**  */
    @SerializedName("CVCGD_EnabledName_XX") @SerialName("CVCGD_EnabledName_XX")
    override val theEnabledName: String = "",
    /**  */
    @SerializedName("CVCGD_Name") @SerialName("CVCGD_Name")
    override val theName: String = "",
    /**  */
    @SerializedName("CVCGD_SortNo") @SerialName("CVCGD_SortNo")
    override val theSortNo: Float? = null,
    /**  */
    @SerializedName("Children_ControlSetting") @SerialName("Children_ControlSetting")
    override val theChildrenControlSettings: List<WtcControlSetting> = listOf()
) : MuseumCvControlGroupDetailDelegate


/** added by elite_lin - 2026/04/05 */
@Serializable
data class WtcControlSetting(
    /**  */
    @SerializedName("CVCS_CloneId") @SerialName("CVCS_CloneId")
    override val theCloneId: String = "",  //theControlSettingId

    /** Reference to ``CvBase.CVB_BaseID */
    @SerializedName("CVCS_BaseId_XX") @SerialName("CVCS_BaseId_XX")
    override val theBaseId: String = "",  // theRefCvBaseId

    /** Reference to ``CvControlGroupDetail.CVCGD_CloneId`` */
    @SerializedName("CVCS_CGDCloneId") @SerialName("CVCS_CGDCloneId")
    override val theCGDCloneId: String = "", // theRefCvControlGroupDetailId

    /** Reference to ``CvControlGroupDetail.CVCGD_CloneId`` */
    @SerializedName("CVCS_CGDID_XX") @SerialName("CVCS_CGDID_XX")
    override val theCGDID: String = "",  // theRefCvCgdId

    /** Reference to ``CvControlGroup.CVCG_CloneId`` */
    @SerializedName("CVCS_CGID_XX") @SerialName("CVCS_CGID_XX")
    override val theCGID: String = "",

    /** Reference to ``SyControlSetting.SYCS_ControlSettingId`` */
    @SerializedName("CVCS_ControlSettingId") @SerialName("CVCS_ControlSettingId")
    override val theControlSettingId: String = "",  // theRefSyControlSettingId

    /**  */
    @SerializedName("CVCS_CGDSortNo_XX") @SerialName("CVCS_CGDSortNo_XX")
    override val theCGDSortNo: Float? = null,  // theCvControlGroupDetailSortNo

    /**  */
    @SerializedName("CVCS_ControlGroupName_XX") @SerialName("CVCS_ControlGroupName_XX")
    override val theControlGroupName: String = "",

    /**  */
    @SerializedName("CVCS_ControlGroupDetailName_XX") @SerialName("CVCS_ControlGroupDetailName_XX")
    override val theControlGroupDetailName: String = "",

    /**  */
    @SerializedName("CVCS_Name") @SerialName("CVCS_Name")
    override val theName: String = "",
    /**  */
    @SerializedName("CVCS_Remark") @SerialName("CVCS_Remark")
    override val theRemark: String = "",
    /**  */
    @SerializedName("CVCS_SortNo") @SerialName("CVCS_SortNo")
    override val theSortNo: Float? = null,
    
    /**  */
    @SerializedName("Children_ControlProperty") @SerialName("Children_ControlProperty")
    override val theChildrenControlProperties: List<WtcControlProperty> = listOf()
) : MuseumCvControlSettingDelegate


/** added by elite_lin - 2026/04/05 */
@Serializable
data class WtcControlProperty(
    /**  */
    @SerializedName("CVCP_CloneId") @SerialName("CVCP_CloneId")
    override val theCloneId: String = "",  // theControlPropertyId

    /** Reference to ``CvBase.CVB_BaseID */
    @SerializedName("CVCP_BaseId_XX") @SerialName("CVCP_BaseId_XX")
    override val theBaseId: String = "", // theRefCvBaseId

    /** Reference to ``CvControlGroup.CVCG_CloneId`` */
    @SerializedName("CVCP_CGID_XX") @SerialName("CVCP_CGID_XX")
    override val theCGID: String = "",  // theRefCvControlGroupId

    /** Reference to ``CvControlGroupDetail.CVCGD_CloneId`` */
    @SerializedName("CVCP_CGDID_XX") @SerialName("CVCP_CGDID_XX")
    override val theCGDID: String = "", theRefCvControlGroupDetailId

    /** Reference to ``CvControlSetting.CVCS_CloneId */
    @SerializedName("CVCP_CSCloneId") @SerialName("CVCP_CSCloneId")
    override val theCSCloneId: String = "", // theRefCvControlSettingId

    /** Reference to ``CvControlSetting.CVCS_CloneId */
    @SerializedName("CVCP_CSID_XX") @SerialName("CVCP_CSID_XX")
    override val theCSID: String = "", // theRefCvCsId

    /** Reference to ``SyControlProperty.SYCP_ControlPropertyId */
    @SerializedName("CVCP_ControlPropertyId") @SerialName("CVCP_ControlPropertyId")
    override val theControlPropertyId: String = "", // theRefSyControlPropertyId

    /** Reference to ``SyControlCode.SYCC_ControlCodeId */
    @SerializedName("CVCP_ControlCodeId") @SerialName("CVCP_ControlCodeId")
    override val theControlCodeId: String = "", // theRefSyControlCodeId

    /**  */
    @SerializedName("CVCP_ControlGroupName_XX") @SerialName("CVCP_ControlGroupName_XX")
    override val theControlGroupName: String = "",

    /**  */
    @SerializedName("CVCP_ControlGroupDetailName_XX") @SerialName("CVCP_ControlGroupDetailName_XX")
    override val theControlGroupDetailName: String = "",
    /**  */
    @SerializedName("CVCP_CGDSortNo_XX") @SerialName("CVCP_CGDSortNo_XX")
    override val theCGDSortNo: Float? = null,  // theControlGroupDetailSortNo

    /**  */
    @SerializedName("CVCP_ControlSettingName_XX") @SerialName("CVCP_ControlSettingName_XX")
    override val theControlSettingName: String = "",
    /**  */
    @SerializedName("CVCP_CSSortNo_XX") @SerialName("CVCP_CSSortNo_XX")
    override val theCSSortNo: Float? = null,  // theControlControlSettingSortNo

    /**  */
    @SerializedName("CVCP_SortNo") @SerialName("CVCP_SortNo")
    override val theSortNo: Float? = null,  //theControlPropertySortNo

    /**  */
    @SerializedName("CVCP_ControlCode_XX") @SerialName("CVCP_ControlCode_XX")
    override val theControlCode: String = "",
    /**  */
    @SerializedName("CVCP_ControlCodeName_XX") @SerialName("CVCP_ControlCodeName_XX")
    override val theControlCodeName: String = "",
    /**  */
    @SerializedName("CVCP_ControlCodeSortNo_XX") @SerialName("CVCP_ControlCodeSortNo_XX")
    override val theControlCodeSortNo: Float? = null,
    
    /**  */
    @SerializedName("CVCP_ControlCodeGroupId") @SerialName("CVCP_ControlCodeGroupId")
    override val theControlCodeGroupId: String = "",
    /**  */
    @SerializedName("CVCP_ControlCodeGroupName_XX") @SerialName("CVCP_ControlCodeGroupName_XX")
    override val theControlCodeGroupName: String = "",
    /**  */
    @SerializedName("CVCP_ControlCodeGroupSortNo_XX") @SerialName("CVCP_ControlCodeGroupSortNo_XX")
    override val theControlCodeGroupSortNo: Float? = null,

    /**  */
    @SerializedName("Children_ControlCode") @SerialName("Children_ControlCode")
    override val theChildrenControlCodes: List<WtcControlCode> = listOf(),
    /**  */
    @SerializedName("Children_ControlCodeGroup") @SerialName("Children_ControlCodeGroup")
    override val theChildrenControlCodeGroups: List<WtcControlCodeGroup> = listOf(),
    /**  */
    @SerializedName("Children_ControlPropertyItem") @SerialName("Children_ControlPropertyItem")
    override val theChildrenControlPropertyItems: List<WtcControlPropertyItem> = listOf()
) : MuseumCvControlPropertyDelegate


/** added by elite_lin - 2026/04/05 */
@Serializable
data class WtcControlPropertyItem(
    /**  */
    @SerializedName("CVCPI_CloneId") @SerialName("CVCPI_CloneId")
    override val theCloneId: String = "",
    /**  */
    @SerializedName("CVCPI_BaseId_XX") @SerialName("CVCPI_BaseId_XX")
    override val theBaseId: String = "",
    /**  */
    @SerializedName("CVCPI_CGDID_XX") @SerialName("CVCPI_CGDID_XX")
    override val theCGDID: String = "",
    /**  */
    @SerializedName("CVCPI_CGDSortNo_XX") @SerialName("CVCPI_CGDSortNo_XX")
    override val theCGDSortNo: Float? = null,
    /**  */
    @SerializedName("CVCPI_CGID_XX") @SerialName("CVCPI_CGID_XX")
    override val theCGID: String = "",
    /**  */
    @SerializedName("CVCPI_Code") @SerialName("CVCPI_Code")
    override val theCode: String = "",
    /**  */
    @SerializedName("CVCPI_CodeGroupId") @SerialName("CVCPI_CodeGroupId")
    override val theCodeGroupId: String = "",
    /**  */
    @SerializedName("CVCPI_ControlCode_XX") @SerialName("CVCPI_ControlCode_XX")
    override val theControlCode: String = "",
    /**  */
    @SerializedName("CVCPI_ControlGroupDetailName_XX") @SerialName("CVCPI_ControlGroupDetailName_XX")
    override val theControlGroupDetailName: String = "",
    /**  */
    @SerializedName("CVCPI_ControlGroupName_XX") @SerialName("CVCPI_ControlGroupName_XX")
    override val theControlGroupName: String = "",
    /**  */
    @SerializedName("CVCPI_ControlPropertyId") @SerialName("CVCPI_ControlPropertyId")
    override val theControlPropertyId: String = "",
    /**  */
    @SerializedName("CVCPI_ControlPropertyItemId") @SerialName("CVCPI_ControlPropertyItemId")
    override val theControlPropertyItemId: String = "",
    /**  */
    @SerializedName("CVCPI_ControlSettingName_XX") @SerialName("CVCPI_ControlSettingName_XX")
    override val theControlSettingName: String = "",
    /**  */
    @SerializedName("CVCPI_CPCloneId") @SerialName("CVCPI_CPCloneId")
    override val theCPCloneId: String = "",
    /**  */
    @SerializedName("CVCPI_CPID_XX") @SerialName("CVCPI_CPID_XX")
    override val theCPID: String = "",
    /**  */
    @SerializedName("CVCPI_CPSortNo_XX") @SerialName("CVCPI_CPSortNo_XX")
    override val theCPSortNo: Float? = null,
    /**  */
    @SerializedName("CVCPI_CSID_XX") @SerialName("CVCPI_CSID_XX")
    override val theCSID: String = "",
    /**  */
    @SerializedName("CVCPI_CSSortNo_XX") @SerialName("CVCPI_CSSortNo_XX")
    override val theCSSortNo: Float? = null,
    /**  */
    @SerializedName("CVCPI_DIndex") @SerialName("CVCPI_DIndex")
    override val theDIndex: Float? = null,
    /**  */
    @SerializedName("CVCPI_FileMode") @SerialName("CVCPI_FileMode")
    override val theFileMode: String = "",
    /**  */
    @SerializedName("CVCPI_Height") @SerialName("CVCPI_Height")
    override val theHeight: Float? = null,
    /**  */
    @SerializedName("CVCPI_Index") @SerialName("CVCPI_Index")
    override val theIndex: Float? = null,
    /**  */
    @SerializedName("CVCPI_ItemLabel") @SerialName("CVCPI_ItemLabel")
    override val theItemLabel: String = "",
    /**  */
    @SerializedName("CVCPI_ItemSort") @SerialName("CVCPI_ItemSort")
    override val theItemSort: Float? = null,
    /**  */
    @SerializedName("CVCPI_ItemValue") @SerialName("CVCPI_ItemValue")
    override val theItemValue: String = "",
    /**  */
    @SerializedName("CVCPI_MIndex") @SerialName("CVCPI_MIndex")
    override val theMIndex: Float? = null,
    /**  */
    @SerializedName("CVCPI_NodeId") @SerialName("CVCPI_NodeId")
    override val theNodeId: String = "",
    /**  */
    @SerializedName("CVCPI_ParentId") @SerialName("CVCPI_ParentId")
    override val theParentId: String = "",
    /**  */
    @SerializedName("CVCPI_RecordValue") @SerialName("CVCPI_RecordValue")
    override val theRecordValue: String = "",
    /**  */
    @SerializedName("CVCPI_RelactionClickValue") @SerialName("CVCPI_RelactionClickValue")
    override val theRelactionClickValue: String = "",
    /**  */
    @SerializedName("CVCPI_RelactionControl") @SerialName("CVCPI_RelactionControl")
    override val theRelactionControl: String = "",
    /**  */
    @SerializedName("CVCPI_RelactionControlName") @SerialName("CVCPI_RelactionControlName")
    override val theRelactionControlName: String = "",
    /**  */
    @SerializedName("CVCPI_RelactionValue") @SerialName("CVCPI_RelactionValue")
    override val theRelactionValue: String = "",
    /**  */
    @SerializedName("CVCPI_Rows") @SerialName("CVCPI_Rows")
    override val theRows: Float? = null,
    /**  */
    @SerializedName("CVCPI_Subject") @SerialName("CVCPI_Subject")
    override val theSubject: String = "",
    /**  */
    @SerializedName("CVCPI_Type") @SerialName("CVCPI_Type")
    override val theType: String = "",
    /**  */
    @SerializedName("CVCPI_Url") @SerialName("CVCPI_Url")
    override val theUrl: String = "",
    /**  */
    @SerializedName("CVCPI_UrlAction") @SerialName("CVCPI_UrlAction")
    override val theUrlAction: String = "",
    /**  */
    @SerializedName("CVCPI_Width") @SerialName("CVCPI_Width")
    override val theWidth: Float? = null
) : MuseumCvControlPropertyItemDelegate


/** added by elite_lin - 2026/04/05 */
ok
@Serializable
data class WtcControlCode(
    /**  */
    @SerializedName("SYCC_ControlCodeId") @SerialName("SYCC_ControlCodeId")
    override val theControlCodeId: String = "",
    /**  */
    @SerializedName("SYCC_Code") @SerialName("SYCC_Code")
    override val theCode: String = "",
    /**  */
    @SerializedName("SYCC_Enabled") @SerialName("SYCC_Enabled")
    override val theEnabled: String = "",
    /**  */
    @SerializedName("SYCC_EnabledName_XX") @SerialName("SYCC_EnabledName_XX")
    override val theEnabledName: String = "",
    /**  */
    @SerializedName("SYCC_Icon") @SerialName("SYCC_Icon")
    override val theIcon: String = "",
    /**  */
    @SerializedName("SYCC_Name") @SerialName("SYCC_Name")
    override val theName: String = "",
    /**  */
    @SerializedName("SYCC_SortNo") @SerialName("SYCC_SortNo")
    override val theSortNo: Float? = null
) : MuseumCvControlCodeDelegate


/** added by elite_lin - 2026/04/05 */
@Serializable
data class WtcControlCodeGroup(
    /**  */
    @SerializedName("CVCCG_CloneId") @SerialName("CVCCG_CloneId")
    override val theCloneId: String = "",
    /**  */
    @SerializedName("CVCCG_ControlCodeGroupId") @SerialName("CVCCG_ControlCodeGroupId")
    override val theControlCodeGroupId: String = "",
    /**  */
    @SerializedName("CVCCG_CPCloneId") @SerialName("CVCCG_CPCloneId")
    override val theCPCloneId: String = "",
    /**  */
    @SerializedName("CVCCG_Enabled") @SerialName("CVCCG_Enabled")
    override val theEnabled: String = "",
    /**  */
    @SerializedName("CVCCG_EnabledName_XX") @SerialName("CVCCG_EnabledName_XX")
    override val theEnabledName: String = "",
    /**  */
    @SerializedName("CVCCG_Name") @SerialName("CVCCG_Name")
    override val theName: String = "",
    /**  */
    @SerializedName("CVCCG_SortNo") @SerialName("CVCCG_SortNo")
    override val theSortNo: Float? = null,
    /**  */
    @SerializedName("Children_ControlProperty") @SerialName("Children_ControlProperty")
    override val theChildrenControlProperties: List<WtcControlProperty> = listOf(),
    /**  */
    @SerializedName("Children_ControlPropertyItem") @SerialName("Children_ControlPropertyItem")
    override val theChildrenControlPropertyItems: List<WtcControlPropertyItem> = listOf()
) : MuseumCvControlCodeGroupDelegate


/** added by elite_lin - 2026/04/05 */
@Serializable
data class WtcControlGroupListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcControlGroup> = listOf(),
    @SerializedName("TotalCount") @SerialName("TotalCount") val theTotalCount: Float? = null
) : BaseRestfulApiResponse()


/** added by elite_lin - 2026/04/05 */
@Serializable
data class WtcControlGroupDetailListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcControlGroupDetail> = listOf(),
    @SerializedName("TotalCount") @SerialName("TotalCount") val theTotalCount: Float? = null
) : BaseRestfulApiResponse()


/** added by elite_lin - 2026/04/05 */
@Serializable
data class WtcControlSettingListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcControlSetting> = listOf(),
    @SerializedName("TotalCount") @SerialName("TotalCount") val theTotalCount: Float? = null
) : BaseRestfulApiResponse()


/** added by elite_lin - 2026/04/05 */
@Serializable
data class WtcControlPropertyListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcControlProperty> = listOf(),
    @SerializedName("TotalCount") @SerialName("TotalCount") val theTotalCount: Float? = null
) : BaseRestfulApiResponse()


/** added by elite_lin - 2026/04/05 */
@Serializable
data class WtcControlPropertyItemListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcControlPropertyItem> = listOf(),
    @SerializedName("TotalCount") @SerialName("TotalCount") val theTotalCount: Float? = null
) : BaseRestfulApiResponse()


/** added by elite_lin - 2026/04/05 */
@Serializable
data class WtcControlCodeListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcControlCode> = listOf(),
    @SerializedName("TotalCount") @SerialName("TotalCount") val theTotalCount: Float? = null
) : BaseRestfulApiResponse()


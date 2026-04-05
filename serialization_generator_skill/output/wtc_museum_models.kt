
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import com.google.gson.annotations.SerializedName

@Serializable
abstract class BaseRestfulApiResponse(
    @SerializedName("Success") @SerialName("Success") val isSuccess: Boolean = false,
    @SerializedName("Code") @SerialName("Code") val theStatusCode: String = "",
    @SerializedName("Message") @SerialName("Message") val theMessage: String = "",
    @SerializedName("DataTime") @SerialName("DataTime") val theDataTime: String = "",
)

@Serializable
data class WtcMuseumSortItem(
    /**  */
    @SerializedName("ColumnName") @SerialName("ColumnName")
    override val theColumnName: String = "",
    /**  */
    @SerializedName("Direction") @SerialName("Direction")
    override val theDirection: String = ""
) : MuseumSortItemDelegate

@Serializable
data class WtcMuseumQueryModel(
    /**  */
    @SerializedName("Q_BaseID") @SerialName("Q_BaseID")
    override val theBaseId: String = "",
    /**  */
    @SerializedName("Q_RecordID") @SerialName("Q_RecordID")
    override val theRecordId: String = "",
    /**  */
    @SerializedName("Q_AccessionNumber") @SerialName("Q_AccessionNumber")
    override val theAccessionNumber: String = "",
    /**  */
    @SerializedName("Q_AccessionNumber2") @SerialName("Q_AccessionNumber2")
    override val theAccessionNumber2: String = "",
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
    @SerializedName("Q_YYYY") @SerialName("Q_YYYY")
    override val theYyyy: Float? = null,
    /**  */
    @SerializedName("Q_Secondary") @SerialName("Q_Secondary")
    override val theSecondary: Float? = null,
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
    @SerializedName("Q_ViewReasonCode") @SerialName("Q_ViewReasonCode")
    override val theViewReasonCode: String = "",
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
    /**  */
    @SerializedName("Q_Form") @SerialName("Q_Form")
    override val theForm: String = "",
    /**  */
    @SerializedName("RequestKey") @SerialName("RequestKey")
    override val theRequestKey: String = "",
    /**  */
    @SerializedName("Q_Keyword") @SerialName("Q_Keyword")
    override val theKeyword: String = "",
    /**  */
    @SerializedName("Q_CreatorId") @SerialName("Q_CreatorId")
    override val theCreatorId: String = "",
    /**  */
    @SerializedName("Q_CreatorCode") @SerialName("Q_CreatorCode")
    override val theCreatorCode: String = "",
    /**  */
    @SerializedName("Q_CreatorName") @SerialName("Q_CreatorName")
    override val theCreatorName: String = "",
    /**  */
    @SerializedName("Q_CreateDate") @SerialName("Q_CreateDate")
    override val theCreateDate: String = "",
    /**  */
    @SerializedName("Q_CreateDateFrom") @SerialName("Q_CreateDateFrom")
    override val theCreateDateFrom: String = "",
    /**  */
    @SerializedName("Q_CreateDateTo") @SerialName("Q_CreateDateTo")
    override val theCreateDateTo: String = "",
    /**  */
    @SerializedName("Q_UpdaterId") @SerialName("Q_UpdaterId")
    override val theUpdaterId: String = "",
    /**  */
    @SerializedName("Q_UpdaterCode") @SerialName("Q_UpdaterCode")
    override val theUpdaterCode: String = "",
    /**  */
    @SerializedName("Q_UpdaterName") @SerialName("Q_UpdaterName")
    override val theUpdaterName: String = "",
    /**  */
    @SerializedName("Q_UpdateDate") @SerialName("Q_UpdateDate")
    override val theUpdateDate: String = "",
    /**  */
    @SerializedName("Q_UpdateDateFrom") @SerialName("Q_UpdateDateFrom")
    override val theUpdateDateFrom: String = "",
    /**  */
    @SerializedName("Q_UpdateDateTo") @SerialName("Q_UpdateDateTo")
    override val theUpdateDateTo: String = "",
    /**  */
    @SerializedName("Q_QueryLevel") @SerialName("Q_QueryLevel")
    override val theQueryLevel: String = "",
    /**  */
    @SerializedName("ReportType") @SerializedName("ReportType")
    override val theReportType: Float? = null,
    /**  */
    @SerializedName("ReportId") @SerialName("ReportId")
    override val theReportId: String = "",
    /**  */
    @SerializedName("ExportName") @SerialName("ExportName")
    override val theExportName: String = "",
    /**  */
    @SerializedName("ReportTitle") @SerialName("ReportTitle")
    override val theReportTitle: String = "",
    /**  */
    @SerializedName("ReportSourceType") @SerialName("ReportSourceType")
    override val theReportSourceType: Float? = null,
    /**  */
    @SerializedName("ReportPrintMethod") @SerialName("ReportPrintMethod")
    override val theReportPrintMethod: String = "",
    /**  */
    @SerializedName("ReportScheduleDate") @SerialName("ReportScheduleDate")
    override val theReportScheduleDate: String = "",
    /**  */
    @SerializedName("ReportScheduleHour") @SerialName("ReportScheduleHour")
    override val theReportScheduleHour: Float? = null,
    /**  */
    @SerializedName("ReportScheduleMin") @SerialName("ReportScheduleMin")
    override val theReportScheduleMin: Float? = null,
    /**  */
    @SerializedName("ClientMessage") @SerializedName("ClientMessage")
    override val theClientMessage: String = "",
    /**  */
    @SerializedName("Q_PageIndex") @SerialName("Q_PageIndex")
    override val thePageIndex: Float? = null,
    /**  */
    @SerializedName("Q_PageSize") @SerialName("Q_PageSize")
    override val thePageSize: Float? = null,
    /**  */
    @SerializedName("Sort") @SerialName("Sort")
    override val theSort: List<WtcMuseumSortItem> = listOf()
) : MuseumQueryModelDelegate

@Serializable
data class WtcMuseumGalleryItem(
    /**  */
    @SerializedName("CVB_BaseID") @SerialName("CVB_BaseID")
    override val theBaseId: String = "",
    /**  */
    @SerializedName("CVB_AccessionNumber") @SerialName("CVB_AccessionNumber")
    override val theAccessionNumber: String = "",
    /**  */
    @SerializedName("CVB_AccessionNumber_XX") @SerialName("CVB_AccessionNumber_XX")
    override val theAccessionNumberXx: String = "",
    /**  */
    @SerializedName("CVB_AccessionNumber2") @SerialName("CVB_AccessionNumber2")
    override val theAccessionNumber2: String = "",
    /**  */
    @SerializedName("CVB_AuthCode") @SerialName("CVB_AuthCode")
    override val theAuthCode: String = "",
    /**  */
    @SerializedName("CVB_Author") @SerialName("CVB_Author")
    override val theAuthor: String = "",
    /**  */
    @SerializedName("CVB_BaseMaterial") @SerialName("CVB_BaseMaterial")
    override val theBaseMaterial: String = "",
    /**  */
    @SerializedName("CVB_Carrier") @SerialName("CVB_Carrier")
    override val theCarrier: String = "",
    /**  */
    @SerializedName("CVB_Code") @SerialName("CVB_Code")
    override val theCode: String = "",
    /**  */
    @SerializedName("CVB_CreateDate") @SerialName("CVB_CreateDate")
    override val theCreateDate: String = "",
    /**  */
    @SerializedName("CVB_CreatorId") @SerialName("CVB_CreatorId")
    override val theCreatorId: String = "",
    /**  */
    @SerializedName("CVB_CreatorName_XX") @SerialName("CVB_CreatorName_XX")
    override val theCreatorNameXx: String = "",
    /**  */
    @SerializedName("CVB_DigitizeDate") @SerialName("CVB_DigitizeDate")
    override val theDigitizeDate: String = "",
    /**  */
    @SerializedName("CVB_DigitizePerson") @SerialName("CVB_DigitizePerson")
    override val theDigitizePerson: String = "",
    /**  */
    @SerializedName("CVB_DimensionX") @SerializedName("CVB_DimensionX")
    override val theDimensionX: Float? = null,
    /**  */
    @SerializedName("CVB_DimensionY") @SerialName("CVB_DimensionY")
    override val theDimensionY: Float? = null,
    /**  */
    @SerializedName("CVB_DimensionZ") @SerializedName("CVB_DimensionZ")
    override val theDimensionZ: Float? = null,
    /**  */
    @SerializedName("CVB_Edition") @SerialName("CVB_Edition")
    override val theEdition: String = "",
    /**  */
    @SerializedName("CVB_EnAuthor") @SerialName("CVB_EnAuthor")
    override val theEnAuthor: String = "",
    /**  */
    @SerializedName("CVB_EnWritName") @SerialName("CVB_EnWritName")
    override val theEnWritName: String = "",
    /**  */
    @SerializedName("CVB_FramedForm") @SerializedName("CVB_FramedForm")
    override val theFramedForm: String = "",
    /**  */
    @SerializedName("CVB_GalleryCode_XX") @SerialName("CVB_GalleryCode_XX")
    override val theGalleryCodeXx: String = "",
    /**  */
    @SerializedName("CVB_GalleryID") @SerialName("CVB_GalleryID")
    override val theGalleryId: String = "",
    /**  */
    @SerializedName("CVB_Height") @SerialName("CVB_Height")
    override val theHeight: Float? = null,
    /**  */
    @SerializedName("CVB_ImageUrl") @SerialName("CVB_ImageUrl")
    override val theImageUrl: String = "",
    /**  */
    @SerializedName("CVB_Inspector") @SerialName("CVB_Inspector")
    override val theInspector: String = "",
    /**  */
    @SerializedName("CVB_IsFrame") @SerialName("CVB_IsFrame")
    override val theIsFrame: String = "",
    /**  */
    @SerializedName("CVB_Kind") @SerialName("CVB_Kind")
    override val theKind: String = "",
    /**  */
    @SerializedName("CVB_KindChildren") @SerialName("CVB_KindChildren")
    override val theKindChildren: String = "",
    /**  */
    @SerializedName("CVB_KindMain_XX") @SerialName("CVB_KindMain_XX")
    override val theKindMainXx: String = "",
    /**  */
    @SerializedName("CVB_KindName_XX") @SerialName("CVB_KindName_XX")
    override val theKindNameXx: String = "",
    /**  */
    @SerializedName("CVB_KindRemark") @SerializedName("CVB_KindRemark")
    override val theKindRemark: String = "",
    /**  */
    @SerializedName("CVB_Length") @SerialName("CVB_Length")
    override val theLength: Float? = null,
    /**  */
    @SerializedName("CVB_Level") @SerialName("CVB_Level")
    override val theLevel: String = "",
    /**  */
    @SerializedName("CVB_Loc1") @SerializedName("CVB_Loc1")
    override val theLoc1: String = "",
    /**  */
    @SerializedName("CVB_Loc2") @SerializedName("CVB_Loc2")
    override val theLoc2: String = "",
    /**  */
    @SerializedName("CVB_Loc3") @SerializedName("CVB_Loc3")
    override val theLoc3: String = "",
    /**  */
    @SerializedName("CVB_Loc4") @SerializedName("CVB_Loc4")
    override val theLoc4: String = "",
    /**  */
    @SerializedName("CVB_MainQty") @SerializedName("CVB_MainQty")
    override val theMainQty: Float? = null,
    /**  */
    @SerializedName("CVB_MediaLength") @SerializedName("CVB_MediaLength")
    override val theMediaLength: Float? = null,
    /**  */
    @SerializedName("CVB_MountingMaterialH") @SerializedName("CVB_MountingMaterialH")
    override val theMountingMaterialH: Float? = null,
    /**  */
    @SerializedName("CVB_MountingMaterialL") @SerializedName("CVB_MountingMaterialL")
    override val theMountingMaterialL: Float? = null,
    /**  */
    @SerializedName("CVB_MountingMaterialW") @SerializedName("CVB_MountingMaterialW")
    override val theMountingMaterialW: Float? = null,
    /**  */
    @SerializedName("CVB_PartsQty") @SerializedName("CVB_PartsQty")
    override val thePartsQty: Float? = null,
    /**  */
    @SerializedName("CVB_ProtectiveMeasures") @SerializedName("CVB_ProtectiveMeasures")
    override val theProtectiveMeasures: String = "",
    /**  */
    @SerializedName("CVB_Recorder") @SerializedName("CVB_Recorder")
    override val theRecorder: String = "",
    /**  */
    @SerializedName("CVB_Remark") @SerializedName("CVB_Remark")
    override val theRemark: String = "",
    /**  */
    @SerializedName("CVB_ScreenProtect") @SerializedName("CVB_ScreenProtect")
    override val theScreenProtect: String = "",
    /**  */
    @SerializedName("CVB_Secondary") @SerializedName("CVB_Secondary")
    override val theSecondary: Float? = null,
    /**  */
    @SerializedName("CVB_Semicolon") @SerializedName("CVB_Semicolon")
    override val theSemicolon: String = "",
    /**  */
    @SerializedName("CVB_SerialNumber") @SerializedName("CVB_SerialNumber")
    override val theSerialNumber: String = "",
    /**  */
    @SerializedName("CVB_SizeDesc") @SerializedName("CVB_SizeDesc")
    override val theSizeDesc: String = "",
    /**  */
    @SerializedName("CVB_Source") @SerializedName("CVB_Source")
    override val theSource: String = "",
    /**  */
    @SerializedName("CVB_Source_XX") @SerializedName("CVB_Source_XX")
    override val theSourceXx: String = "",
    /**  */
    @SerializedName("CVB_Status") @SerializedName("CVB_Status")
    override val theStatus: String = "",
    /**  */
    @SerializedName("CVB_StatusName_XX") @SerializedName("CVB_StatusName_XX")
    override val theStatusNameXx: String = "",
    /**  */
    @SerializedName("CVB_StorageLocation") @SerializedName("CVB_StorageLocation")
    override val theStorageLocation: String = "",
    /**  */
    @SerializedName("CVB_UpdateDate") @SerializedName("CVB_UpdateDate")
    override val theUpdateDate: String = "",
    /**  */
    @SerializedName("CVB_UpdaterId") @SerializedName("CVB_UpdaterId")
    override val theUpdaterId: String = "",
    /**  */
    @SerializedName("CVB_UpdaterName_XX") @SerializedName("CVB_UpdaterName_XX")
    override val theUpdaterNameXx: String = "",
    /**  */
    @SerializedName("CVB_ViewDate") @SerializedName("CVB_ViewDate")
    override val theViewDate: String = "",
    /**  */
    @SerializedName("CVB_ViewReason") @SerializedName("CVB_ViewReason")
    override val theViewReason: String = "",
    /**  */
    @SerializedName("CVB_ViewReasonCode_XX") @SerializedName("CVB_ViewReasonCode_XX")
    override val theViewReasonCodeXx: String = "",
    /**  */
    @SerializedName("CVB_ViewReasonName_XX") @SerializedName("CVB_ViewReasonName_XX")
    override val theViewReasonNameXx: String = "",
    /**  */
    @SerializedName("CVB_Weight") @SerializedName("CVB_Weight")
    override val theWeight: Float? = null,
    /**  */
    @SerializedName("CVB_Width") @SerializedName("CVB_Width")
    override val theWidth: Float? = null,
    /**  */
    @SerializedName("CVB_WritName") @SerializedName("CVB_WritName")
    override val theWritName: String = "",
    /**  */
    @SerializedName("CVB_Years") @SerializedName("CVB_Years")
    override val theYears: String = "",
    /**  */
    @SerializedName("CVB_YYYY") @SerializedName("CVB_YYYY")
    override val theYyyy: Float? = null
) : MuseumGalleryItemDelegate

@Serializable
data class WtcMuseumAuthor(
    /**  */
    @SerializedName("CVA_AuthorID") @SerialName("CVA_AuthorID")
    override val theAuthorId: String = "",
    /**  */
    @SerializedName("CVA_BaseID") @SerialName("CVA_BaseID")
    override val theBaseId: String = "",
    /**  */
    @SerializedName("CVA_CreateDate") @SerialName("CVA_CreateDate")
    override val theCreateDate: String = "",
    /**  */
    @SerializedName("CVA_CreatorId") @SerialName("CVA_CreatorId")
    override val theCreatorId: String = "",
    /**  */
    @SerializedName("CVA_CreatorName_XX") @SerialName("CVA_CreatorName_XX")
    override val theCreatorNameXx: String = "",
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
    override val theIsProviderNameXx: String = "",
    /**  */
    @SerializedName("CVA_Provider") @SerialName("CVA_Provider")
    override val theProvider: String = "",
    /**  */
    @SerializedName("CVA_UpdateDate") @SerialName("CVA_UpdateDate")
    override val theUpdateDate: String = "",
    /**  */
    @SerializedName("CVA_UpdaterId") @SerialName("CVA_UpdaterId")
    override val theUpdaterId: String = "",
    /**  */
    @SerializedName("CVA_UpdaterName_XX") @SerialName("CVA_UpdaterName_XX")
    override val theUpdaterNameXx: String = ""
) : MuseumAuthorDelegate

@Serializable
data class WtcMuseumDetail1(
    /**  */
    @SerializedName("CVBD1_BaseDetail1ID") @SerialName("CVBD1_BaseDetail1ID")
    override val theBaseDetail1Id: String = "",
    /**  */
    @SerializedName("CVBD1_BaseID") @SerialName("CVBD1_BaseID")
    override val theBaseId: String = "",
    /**  */
    @SerializedName("CVBD1_CreateDate") @SerialName("CVBD1_CreateDate")
    override val theCreateDate: String = "",
    /**  */
    @SerializedName("CVBD1_CreatorId") @SerialName("CVBD1_CreatorId")
    override val theCreatorId: String = "",
    /**  */
    @SerializedName("CVBD1_CreatorName_XX") @SerialName("CVBD1_CreatorName_XX")
    override val theCreatorNameXx: String = "",
    /**  */
    @SerializedName("CVBD1_GalleryContentId") @SerialName("CVBD1_GalleryContentId")
    override val theGalleryContentId: String = "",
    /**  */
    @SerializedName("CVBD1_Remark") @SerialName("CVBD1_Remark")
    override val theRemark: String = "",
    /**  */
    @SerializedName("CVBD1_Semicolon") @SerialName("CVBD1_Semicolon")
    override val theSemicolon: String = "",
    /**  */
    @SerializedName("CVBD1_UpdateDate") @SerialName("CVBD1_UpdateDate")
    override val theUpdateDate: String = "",
    /**  */
    @SerializedName("CVBD1_UpdaterId") @SerialName("CVBD1_UpdaterId")
    override val theUpdaterId: String = "",
    /**  */
    @SerializedName("CVBD1_UpdaterName_XX") @SerialName("CVBD1_UpdaterName_XX")
    override val theUpdaterNameXx: String = ""
) : MuseumDetail1Delegate

@Serializable
data class WtcMuseumDetail2(
    /**  */
    @SerializedName("CVBD2_BaseDetail2ID") @SerialName("CVBD2_BaseDetail2ID")
    override val theBaseDetail2Id: String = "",
    /**  */
    @SerializedName("CVBD2_BaseID") @SerialName("CVBD2_BaseID")
    override val theBaseId: String = "",
    /**  */
    @SerializedName("CVBD2_CreateDate") @SerialName("CVBD2_CreateDate")
    override val theCreateDate: String = "",
    /**  */
    @SerializedName("CVBD2_CreatorId") @SerialName("CVBD2_CreatorId")
    override val theCreatorId: String = "",
    /**  */
    @SerializedName("CVBD2_CreatorName_XX") @SerialName("CVBD2_CreatorName_XX")
    override val theCreatorNameXx: String = "",
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
    @SerializedName("CVBD2_Signature1") @SerializedName("CVBD2_Signature1")
    override val theSignature1: String = "",
    /**  */
    @SerializedName("CVBD2_Signature2") @SerialName("CVBD2_Signature2")
    override val theSignature2: String = "",
    /**  */
    @SerializedName("CVBD2_Signature3") @SerialName("CVBD2_Signature3")
    override val theSignature3: String = "",
    /**  */
    @SerializedName("CVBD2_UpdateDate") @SerialName("CVBD2_UpdateDate")
    override val theUpdateDate: String = "",
    /**  */
    @SerializedName("CVBD2_UpdaterId") @SerialName("CVBD2_UpdaterId")
    override val theUpdaterId: String = "",
    /**  */
    @SerializedName("CVBD2_UpdaterName_XX") @SerialName("CVBD2_UpdaterName_XX")
    override val theUpdaterNameXx: String = ""
) : MuseumDetail2Delegate

@Serializable
data class WtcMuseumAttachFile(
    /**  */
    @SerializedName("CVAF_AttachFileId") @SerialName("CVAF_AttachFileId")
    override val theAttachFileId: String = "",
    /**  */
    @SerializedName("CVAF_CreateDate") @SerialName("CVAF_CreateDate")
    override val theCreateDate: String = "",
    /**  */
    @SerializedName("CVAF_CreatorCode_XX") @SerialName("CVAF_CreatorCode_XX")
    override val theCreatorCodeXx: String = "",
    /**  */
    @SerializedName("CVAF_CreatorId") @SerialName("CVAF_CreatorId")
    override val theCreatorId: String = "",
    /**  */
    @SerializedName("CVAF_CreatorName_XX") @SerialName("CVAF_CreatorName_XX")
    override val theCreatorNameXx: String = "",
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
    override val theSize: Float? = null,
    /**  */
    @SerializedName("CVAF_UpdateDate") @SerialName("CVAF_UpdateDate")
    override val theUpdateDate: String = "",
    /**  */
    @SerializedName("CVAF_UpdaterCode_XX") @SerialName("CVAF_UpdaterCode_XX")
    override val theUpdaterCodeXx: String = "",
    /**  */
    @SerializedName("CVAF_UpdaterId") @SerialName("CVAF_UpdaterId")
    override val theUpdaterId: String = "",
    /**  */
    @SerializedName("CVAF_UpdaterName_XX") @SerialName("CVAF_UpdaterName_XX")
    override val theUpdaterNameXx: String = ""
) : MuseumAttachFileDelegate

@Serializable
data class WtcMuseumDetailInfo(
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
) : MuseumDetailInfoDelegate

@Serializable
data class WtcMuseumGalleryDetailItem(
    /**  */
    @SerializedName("CVB_BaseID") @SerialName("CVB_BaseID")
    override val theBaseId: String = "",
    /**  */
    @SerializedName("CVB_AccessionNumber") @SerialName("CVB_AccessionNumber")
    override val theAccessionNumber: String = "",
    /**  */
    @SerializedName("CVB_AccessionNumber_XX") @SerialName("CVB_AccessionNumber_XX")
    override val theAccessionNumberXx: String = "",
    /**  */
    @SerializedName("CVB_AccessionNumber2") @SerialName("CVB_AccessionNumber2")
    override val theAccessionNumber2: String = "",
    /**  */
    @SerializedName("CVB_AuthCode") @SerialName("CVB_AuthCode")
    override val theAuthCode: String = "",
    /**  */
    @SerializedName("CVB_Author") @SerialName("CVB_Author")
    override val theAuthor: String = "",
    /**  */
    @SerializedName("CVB_BaseMaterial") @SerialName("CVB_BaseMaterial")
    override val theBaseMaterial: String = "",
    /**  */
    @SerializedName("CVB_Carrier") @SerialName("CVB_Carrier")
    override val theCarrier: String = "",
    /**  */
    @SerializedName("CVB_Code") @SerialName("CVB_Code")
    override val theCode: String = "",
    /**  */
    @SerializedName("CVB_CreateDate") @SerialName("CVB_CreateDate")
    override val theCreateDate: String = "",
    /**  */
    @SerializedName("CVB_CreatorId") @SerialName("CVB_CreatorId")
    override val theCreatorId: String = "",
    /**  */
    @SerializedName("CVB_CreatorName_XX") @SerialName("CVB_CreatorName_XX")
    override val theCreatorNameXx: String = "",
    /**  */
    @SerializedName("CVB_DigitizeDate") @SerialName("CVB_DigitizeDate")
    override val theDigitizeDate: String = "",
    /**  */
    @SerializedName("CVB_DigitizePerson") @SerialName("CVB_DigitizePerson")
    override val theDigitizePerson: String = "",
    /**  */
    @SerializedName("CVB_DimensionX") @SerializedName("CVB_DimensionX")
    override val theDimensionX: Float? = null,
    /**  */
    @SerializedName("CVB_DimensionY") @SerializedName("CVB_DimensionY")
    override val theDimensionY: Float? = null,
    /**  */
    @SerializedName("CVB_DimensionZ") @SerialName("CVB_DimensionZ")
    override val theDimensionZ: Float? = null,
    /**  */
    @SerializedName("CVB_Edition") @SerialName("CVB_Edition")
    override val theEdition: String = "",
    /**  */
    @SerializedName("CVB_EnAuthor") @SerialName("CVB_EnAuthor")
    override val theEnAuthor: String = "",
    /**  */
    @SerializedName("CVB_EnWritName") @SerialName("CVB_EnWritName")
    override val theEnWritName: String = "",
    /**  */
    @SerializedName("CVB_FramedForm") @SerializedName("CVB_FramedForm")
    override val theFramedForm: String = "",
    /**  */
    @SerializedName("CVB_GalleryCode_XX") @SerialName("CVB_GalleryCode_XX")
    override val theGalleryCodeXx: String = "",
    /**  */
    @SerializedName("CVB_GalleryID") @SerialName("CVB_GalleryID")
    override val theGalleryId: String = "",
    /**  */
    @SerializedName("CVB_Height") @SerialName("CVB_Height")
    override val theHeight: Float? = null,
    /**  */
    @SerializedName("CVB_ImageUrl") @SerialName("CVB_ImageUrl")
    override val theImageUrl: String = "",
    /**  */
    @SerializedName("CVB_Inspector") @SerialName("CVB_Inspector")
    override val theInspector: String = "",
    /**  */
    @SerializedName("CVB_IsFrame") @SerialName("CVB_IsFrame")
    override val theIsFrame: String = "",
    /**  */
    @SerializedName("CVB_Kind") @SerialName("CVB_Kind")
    override val theKind: String = "",
    /**  */
    @SerializedName("CVB_KindChildren") @SerialName("CVB_KindChildren")
    override val theKindChildren: String = "",
    /**  */
    @SerializedName("CVB_KindMain_XX") @SerialName("CVB_KindMain_XX")
    override val theKindMainXx: String = "",
    /**  */
    @SerializedName("CVB_KindName_XX") @SerialName("CVB_KindName_XX")
    override val theKindNameXx: String = "",
    /**  */
    @SerializedName("CVB_KindRemark") @SerializedName("CVB_KindRemark")
    override val theKindRemark: String = "",
    /**  */
    @SerializedName("CVB_Length") @SerialName("CVB_Length")
    override val theLength: Float? = null,
    /**  */
    @SerializedName("CVB_Level") @SerialName("CVB_Level")
    override val theLevel: String = "",
    /**  */
    @SerializedName("CVB_Loc1") @SerializedName("CVB_Loc1")
    override val theLoc1: String = "",
    /**  */
    @SerializedName("CVB_Loc2") @SerialName("CVB_Loc2")
    override val theLoc2: String = "",
    /**  */
    @SerializedName("CVB_Loc3") @SerializedName("CVB_Loc3")
    override val theLoc3: String = "",
    /**  */
    @SerializedName("CVB_Loc4") @SerializedName("CVB_Loc4")
    override val theLoc4: String = "",
    /**  */
    @SerializedName("CVB_MainQty") @SerializedName("CVB_MainQty")
    override val theMainQty: Float? = null,
    /**  */
    @SerializedName("CVB_MediaLength") @SerializedName("CVB_MediaLength")
    override val theMediaLength: Float? = null,
    /**  */
    @SerializedName("CVB_MountingMaterialH") @SerializedName("CVB_MountingMaterialH")
    override val theMountingMaterialH: Float? = null,
    /**  */
    @SerializedName("CVB_MountingMaterialL") @SerializedName("CVB_MountingMaterialL")
    override val theMountingMaterialL: Float? = null,
    /**  */
    @SerializedName("CVB_MountingMaterialW") @SerializedName("CVB_MountingMaterialW")
    override val theMountingMaterialW: Float? = null,
    /**  */
    @SerializedName("CVB_PartsQty") @SerializedName("CVB_PartsQty")
    override val thePartsQty: Float? = null,
    /**  */
    @SerializedName("CVB_ProtectiveMeasures") @SerializedName("CVB_ProtectiveMeasures")
    override val theProtectiveMeasures: String = "",
    /**  */
    @SerializedName("CVB_Recorder") @SerializedName("CVB_Recorder")
    override val theRecorder: String = "",
    /**  */
    @SerializedName("CVB_Remark") @SerializedName("CVB_Remark")
    override val theRemark: String = "",
    /**  */
    @SerializedName("CVB_ScreenProtect") @SerializedName("CVB_ScreenProtect")
    override val theScreenProtect: String = "",
    /**  */
    @SerializedName("CVB_Secondary") @SerializedName("CVB_Secondary")
    override val theSecondary: Float? = null,
    /**  */
    @SerializedName("CVB_Semicolon") @SerializedName("CVB_Semicolon")
    override val theSemicolon: String = "",
    /**  */
    @SerializedName("CVB_SerialNumber") @SerializedName("CVB_SerialNumber")
    override val theSerialNumber: String = "",
    /**  */
    @SerializedName("CVB_SizeDesc") @SerializedName("CVB_SizeDesc")
    override val theSizeDesc: String = "",
    /**  */
    @SerializedName("CVB_Source") @SerializedName("CVB_Source")
    override val theSource: String = "",
    /**  */
    @SerializedName("CVB_Source_XX") @SerializedName("CVB_Source_XX")
    override val theSourceXx: String = "",
    /**  */
    @SerializedName("CVB_Status") @SerializedName("CVB_Status")
    override val theStatus: String = "",
    /**  */
    @SerializedName("CVB_StatusName_XX") @SerializedName("CVB_StatusName_XX")
    override val theStatusNameXx: String = "",
    /**  */
    @SerializedName("CVB_StorageLocation") @SerializedName("CVB_StorageLocation")
    override val theStorageLocation: String = "",
    /**  */
    @SerializedName("CVB_UpdateDate") @SerializedName("CVB_UpdateDate")
    override val theUpdateDate: String = "",
    /**  */
    @SerializedName("CVB_UpdaterId") @SerializedName("CVB_UpdaterId")
    override val theUpdaterId: String = "",
    /**  */
    @SerializedName("CVB_UpdaterName_XX") @SerializedName("CVB_UpdaterName_XX")
    override val theUpdaterNameXx: String = "",
    /**  */
    @SerializedName("CVB_ViewDate") @SerializedName("CVB_ViewDate")
    override val theViewDate: String = "",
    /**  */
    @SerializedName("CVB_ViewReason") @SerializedName("CVB_ViewReason")
    override val theViewReason: String = "",
    /**  */
    @SerializedName("CVB_ViewReasonCode_XX") @SerializedName("CVB_ViewReasonCode_XX")
    override val theViewReasonCodeXx: String = "",
    /**  */
    @SerializedName("CVB_ViewReasonName_XX") @SerializedName("CVB_ViewReasonName_XX")
    override val theViewReasonNameXx: String = "",
    /**  */
    @SerializedName("CVB_Weight") @SerializedName("CVB_Weight")
    override val theWeight: Float? = null,
    /**  */
    @SerializedName("CVB_Width") @SerializedName("CVB_Width")
    override val theWidth: Float? = null,
    /**  */
    @SerializedName("CVB_WritName") @SerializedName("CVB_WritName")
    override val theWritName: String = "",
    /**  */
    @SerializedName("CVB_Years") @SerializedName("CVB_Years")
    override val theYears: String = "",
    /**  */
    @SerializedName("CVB_YYYY") @SerializedName("CVB_YYYY")
    override val theYyyy: Float? = null,

    /**  */
    @SerializedName("Children_Author") @SerialName("Children_Author")
    override val theChildrenAuthor: List<WtcMuseumAuthor> = listOf(),
    /**  */
    @SerializedName("Children_Detail1") @SerialName("Children_Detail1")
    override val theChildrenDetail1: List<WtcMuseumDetail1> = listOf(),
    /**  */
    @SerializedName("Children_Detail2") @SerialName("Children_Detail2")
    override val theChildrenDetail2: List<WtcMuseumDetail2> = listOf(),
    /**  */
    @SerializedName("Children_AttachFile") @SerialName("Children_AttachFile")
    override val theChildrenAttachFile: List<WtcMuseumAttachFile> = listOf(),
    /**  */
    @SerializedName("ViewReasonName") @SerialName("ViewReasonName")
    override val theViewReasonName: String = "",
    /**  */
    @SerializedName("IsLock") @SerialName("IsLock")
    override val theIsLock: String = "",
    /**  */
    @SerializedName("IsView") @SerialName("IsView")
    override val theIsView: String = "",
    /**  */
    @SerializedName("Detail") @SerialName("Detail")
    override val theDetail: List<WtcMuseumDetailInfo> = listOf(),
    /**  */
    @SerializedName("SemicolonCount") @SerialName("SemicolonCount")
    override val theSemicolonCount: Float? = null
) : MuseumGalleryDetailItemDelegate

@Serializable
data class WtcMuseumListResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcMuseumGalleryItem> = listOf(),
    @SerializedName("TotalCount") @SerialName("TotalCount") val theTotalCount: Float? = null
) : BaseRestfulApiResponse()

@Serializable
data class WtcMuseumDetailResponse(
    @SerializedName("Data") @SerialName("Data") val theData: List<WtcMuseumGalleryDetailItem> = listOf(),
    @SerializedName("TotalCount") @SerialName("TotalCount") val theTotalCount: Float? = null
) : BaseRestfulApiResponse()

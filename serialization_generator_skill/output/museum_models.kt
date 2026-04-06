/** added by elite_lin - 2026/04/01 */
interface MuseumSortItemDelegate {
    /**  */
    val theColumnName: String
    /**  */
    val theDirection: String
}


/** added by elite_lin - 2026/04/01 */
interface MuseumCvBaseQueryModelDelegate : KeywordQueryModelDelegate {
    /**  */
    val theBaseId: String
    /**  */
    val theYear: Int?
    /**  */
    val theSecondary: Int?
    /**  */
    val theAccessionNumber: String
    /**  */
    val theAccessionNumber2: String
    /**  */
    val theViewReasonCode: String
    /**  */
    val theAuthor: String
    /**  */
    val theStorageLocation: String
    /**  */
    val theKind: String
    /**  */
    val theKindChildren: String
    /**  */
    val theLevel: String
    /**  */
    val theBeginDate: String
    /**  */
    val theEndDate: String
    /**  */
    val theGalleryCode: String
    /**  */
    val theBaseDetail2Id: String
    /**  */
    val theSerialNumber: String
    /**  */
    val theGalleryId: String
    /**  */
    val theSemicolon: String
    /**  */
    val theStatus: String

//    /**  */
//    val thePageIndex: Float?
//    /**  */
//    val thePageSize: Float?
//    /**  */
//    val theSort: List<MuseumSortItemDelegate>
}


/** added by elite_lin - 2026/04/01 */
fun MuseumCvBaseQueryModelDelegate.buildList1QueryParameterExt() : String {
    val stringBuilder = StringBuilder()
        .append("?galleryId=${this@buildList1QueryParameterExt.theGalleryId}")
    return stringBuilder.toString()
}


/** added by elite_lin - 2026/04/01 */
interface MuseumCvBaseItemDelegate: CvBaseDisplayAccessionNumberDelegate {
    /**  */
    val theBaseId: String
    /** Ntcam only; Ntmofa does not have */
    val theYearStr: String
    /**  */
    val theYear: Int?
    /**  */
    val theSecondary: Int?
    /**  */
    val theAccessionNumber: String
    /**  */
    val theAccessionNumberXX: String
    /**  */
    val theAccessionNumber2: String

    /**  */
    val theViewDate: String
    /**  */
    val theViewReason: String
    /** XX */
    val theViewReasonCode: String
    /** XX */
    val theViewReasonName: String

    /**  */
    val theGalleryId: String
    /** XX */
    val theGalleryCode: String

    /**  */
    val theAuthor: String
    /**  */
    val theAuthCode: String
    /**  */
    val theEngAuthor: String

    /**  */
    val theWritName: String
    /**  */
    val theEngWritName: String

    /**  */
    val theSource: String
    /** Ntcam only; Ntmofa does not have */
    val theSourceXX: String
    /**  */
    val theSerialNumber: String

    /**  */
    val theKind: String
    /** XX */
    val theKindMain: String
    /** XX */
    val theKindName: String
    /**  */
    val theKindChildren: String
    /** Ntcam only; Ntmofa does not have */
    val theKindRemark: String

    /**  */
    val theInspector: String
    /**  */
    val theRecorder: String

    /**  */
    val theSemicolon: String
    /**  */
    val theStorageLocation: String

    /**  */
    val theStatus: String
    /** XX */
    val theStatusName: String

    /**  */
    val theLevel: String
    /**  */
    val theRemark: String
    /**  */
    val theIsFrame: String

    ///
//    /**  */
//    val theBaseMaterial: String
//    /**  */
//    val theCarrier: String
//    /**  */
//    val theCode: String
//    /**  */
//    val theDigitizeDate: String
//    /**  */
//    val theDigitizePerson: String
//    /**  */
//    val theDimensionX: Float?
//    /**  */
//    val theDimensionY: Float?
//    /**  */
//    val theDimensionZ: Float?
//    /**  */
//    val theEdition: String
//
//    /**  */
//    val theFramedForm: String
//
//    /**  */
//    val theImageUrl: String
//
//    /**  */
//    val theLocation1: String
//    /**  */
//    val theLocation2: String
//    /**  */
//    val theLocation3: String
//    /**  */
//    val theLocation4: String
//    /**  */
//    val theMainQty: Float?
//    /**  */
//    val theMediaLength: Float?
//    /**  */
//    val theMountingMaterialH: Float?
//    /**  */
//    val theMountingMaterialL: Float?
//    /**  */
//    val theMountingMaterialW: Float?
//    /**  */
//    val thePartsQty: Float?
//    /**  */
//    val theProtectiveMeasures: String
//    /**  */
//    val theScreenProtect: String
//
//    /**  */
//    val theSizeDesc: String
//    /**  */
//    val theLength: Float?
//    /**  */
//    val theWidth: Float?
//    /**  */
//    val theHeight: Float?
//    /**  */
//    val theWeight: Float?

}


/** added by elite_lin - 2026/04/04 */
data class MuseumCvBaseItemImpl(
    /** 流水號 */
    override val theBaseId: String = "",

    override val theYearStr: String = "",
    /** 會議年度 */
    override val theYear: Int? = 0,
    /** 會議次別 */
    override val theSecondary: Int? = 0,
    /** 登錄號 */
    override val theAccessionNumber: String = "",
    /** 登錄號 */
    override val theAccessionNumberXX: String = "",
    /** 整飭登錄號 */
    override val theAccessionNumber2: String = "",

    /** 檢視日期 */
    override val theViewDate: String = "",
    /** 檢視原由 */
    override val theViewReason: String = "",
    /** 檢視原由代碼 */
    override val theViewReasonCode: String = "",
    /** 檢視原由名稱 */
    override val theViewReasonName: String = "",

    /** 藏品流水號 */
    override val theGalleryId: String = "",
    /** 藏品登錄號 */
    override val theGalleryCode: String = "",

    /** 作者(代碼) */
    override val theAuthCode: String = "",
    /** 作者 */
    override val theAuthor: String = "",
    /** 作者(英文) */
    override val theEngAuthor: String = "",

    /** 作品 */
    override val theWritName: String = "",
    /** 作品(英文) */
    override val theEngWritName: String = "",

    /** 來源 */
    override val theSource: String = "",

    override val theSourceXX: String = "",
    /** 編號 (組件號) */
    override val theSerialNumber: String = "",

    /**
     * 檢視類型
     * * V：點檢單
     * * O：出庫單
     */
    override val theKind: String = "",
    // [start] added by elite_lin - 2025/06/10
    /**
     * 檢視類型 - 主類別
     * * 東方媒材類: A
     * * 西洋媒材類: B
     * * 版畫類: C
     * * 雕塑類: D
     * * 工藝美術類: E
     * * 觀念藝術類: F
     * * 應用美術類: G
     * * 影像類: H
     * * 陶藝類: I
     * * 當代藝術類: J
     * * 其它類: Z
     */
    override val theKindMain: String = "",
    // [end] added by elite_lin - 2025/06/10
    /** 檢視類型名稱 */
    override val theKindName: String = "",
    /** 檢視類型 - 子類別 */
    override val theKindChildren: String = "",

    override val theKindRemark: String = "",

    /** 檢視人員 */
    override val theInspector: String = "",
    /** 記錄者 */
    override val theRecorder: String = "",

    /** 組件分號 */
    override val theSemicolon: String = "",
    /** 庫藏位置 */
    override val theStorageLocation: String = "",
    /** 狀態: V: 檢視; N: 預設(填寫) */
    override val theStatus: String = "",
    /** 狀態 */
    override val theStatusName: String = "",
    /** 現況等級 */
    override val theLevel: String = "",
    /** 備註 */
    override val theRemark: String = "",
    /** 是否裝裱 */
    override val theIsFrame: String = "",

    override val theDisplayAccessionNumber: String = "",
) : MuseumCvBaseItemDelegate


/** added by elite_lin - 2026/04/01 */
interface MuseumCvAuthorItemDelegate {
    /**  */
    val theAuthorId: String
    /**  */
    val theBaseId: String
    /**  */
    val theDocuments: String
    /**  */
    val theDocuments2: String
    /**  */
    val theProvider: String
    /** XX */
    val theIsProviderName: String
    /**  */
    val theGuarantee: String
    /**  */
    val theExhibitRecord: String
    /**  */
    val theInstallation: String

}


/** added by elite_lin - 2026/04/01 */
interface MuseumCvBaseDetail1Delegate {
    /**  */
    val theBaseDetail1Id: String
    /**  */
    val theBaseId: String
    /**  */
    val theGalleryContentId: String
    /**  */
    val theSemicolon: String
    /**  */
    val theRemark: String

}


/** added by elite_lin - 2026/04/01 */
interface MuseumCvBaseDetail2Delegate {
    /**  */
    val theBaseDetail2Id: String
    /**  */
    val theBaseId: String
    /**  */
    val theIsBack: String
    /**  */
    val theIsFront: String
    /**  */
    val theIsOther: String
    /**  */
    val theIsPhoto: String
    /**  */
    val theSignature1: String
    /**  */
    val theSignature2: String
    /**  */
    val theSignature3: String

}


/** added by elite_lin - 2026/04/01 */
interface MuseumCvAttachFileItemDelegate {
    /**  */
    val theAttachFileId: String
    /**  */
    val theFileName: String
    /**  */
    val theFullName: String
    /**  */
    val theGroupCode: String
    /**  */
    val theIsLink: String
    /**  */
    val theMime: String
    /**  */
    val theObjectId: String
    /**  */
    val theRefId: String
    /**  */
    val theSize: Float?

}


/** added by elite_lin - 2026/04/01 */
interface MuseumCvBaseNestedDetailItemDelegate {
    /**  */
    val theBaseId: String
    /**  */
    val theAccessionNumber: String
    /**  */
    val theKind: String
    /**  */
    val theKindChildren: String
    /**  */
    val theNumber: String
    /**  */
    val theIsReView: String
    /**  */
    val theIsView: String
}


/** added by elite_lin - 2026/04/01 */
interface MuseumCvBaseItemExtDelegate : MuseumCvBaseItemDelegate {
    /**  */
    val theAuthors: List<MuseumCvAuthorItemDelegate>
    /**  */
    val theDetail1s: List<MuseumCvBaseDetail1Delegate>
    /**  */
    val theDetail2s: List<MuseumCvBaseDetail2Delegate>
    /**  */
    val theAttachedFiles: List<MuseumCvAttachFileItemDelegate>
    /**  */
    val theNestedDetails: List<MuseumCvBaseNestedDetailItemDelegate>
    /**  */
    val theViewReasonNameExt: String
    /**  */
    val theIsLockExt: String
    /**  */
    val theIsViewExt: String
    /** Ntcam used / Ntmofa does not have */
    val theSemicolonCountExt: Float?

}

///

interface ControlPropertyNestedDelegate {
    val theControlPropertyItemId: String
    val theRefCvBaseId: String
    val theControlPropertySortNo: Int
    val theCode: String
    val theCodeGroupId: String
    val theControlCode: String
    val theDIndex: Int?
    val theMIndex: Int?
    ///
    val theNodeId: String
    val theParentId: String
    val theSubject: String
    val theType: String
    val theUrl: String
    val theUrlAction: String
    val theFileMode: String
    val theWidth: Int
    val theHeight: Int
    val theItemLabel: String
    val theItemValue: String
    val theItemSort: Int
    val theRelactionClickValue: String
    val theRelactionControl: String
    val theRelactionControlName: String
    val theRelactionValue: String
    val theRows: Int
}


interface CvControlPropertyNestedDelegate : ControlPropertyNestedDelegate,
    QuestionOptionTypeDelegate, QuestionOptionLabelDelegate, DirtySyncDelegate {
    ///
//    val theControlPropertyItemId: String
//    val theRefCvBaseId: String

    val theRefCvControlGroupId: String
    val theControlGroupName: String

    val theRefCvControlGroupDetailId: String
    val theControlGroupDetailName: String
    val theControlGroupDetailSortNo: Int

    val theRefCvControlSettingId: String
    val theControlSettingName: String
    val theControlSettingSortNo: Int

    val theRefCvControlPropertyId: String
    val theRefCvCpId: String
//    val theControlPropertySortNo: Int

    ///
    val theIndex: Int
    val theRecordValue: String?
    val theRefSyControlPropertyItemId: String
    val theRefSyControlPropertyId: String
    ///
}

///

/** revision by elite_lin - 2025/08/22 */
interface CvControlGroupIdRenameDelegate : DirtySyncDelegate {
    val theRename: String
}


/** revision by elite_lin - 2025/08/22 */

// discard
interface CvControlGroupDelegate : SyncedAtDelegate {
    val theControlPropertyId: String
    val theControlPropertySortNo: Int
    val theRefCvControlSettingId: String
    val theRefCvCsId: String
    val theControlControlSettingSortNo: Int
    val theRefCvControlGroupDetailId: String
    val theControlGroupDetailName: String
    val theControlGroupDetailSortNo: Int
    val theRefCvControlGroupId: String
    val theControlGroupName: String
    val theRefCvBaseId: String
    val theRefSyControlPropertyId: String
    val theRefSyControlCodeId: String
    val theControlCode: String
    val theControlCodeName: String
    val theControlCodeSortNo: Int
    val theControlSettingName: String
    val theControlCodeGroupId: String
    val theControlCodeGroupName: String
    val theControlCodeGroupSortNo: Int
}


/** added by elite_lin - 2026/04/05 */
ok
interface MuseumCvControlGroupQueryDelegate : KeywordQueryModelDelegate {
    /**  */
    val theCloneId: String
    /**  */
    val theBaseId: String
    /**  */
    val theControlGroupId: String
    /**  */
    val theKind: String
    /**  */
    val theKindName: String
    /**  */
    val theName: String
    /**  */
    val theReName: String
    /**  */
    val theEnabled: String
    /**  */
    val theEnabledName: String
    /**  */
    override val theKeyword: String
    /**  */
    val theQueryLevel: String
    /**  */
    val thePageIndex: Float?
    /**  */
    val thePageSize: Float?
    /**  */
    val theSort: List<MuseumSortItemDelegate>
}


/** added by elite_lin - 2026/04/05 */
ok
interface MuseumCvControlGroupDelegate {
    /**  */
    val theControlGroupId: String
    /**  */
    val theRefCvBaseId: String
    /**  */
    val theRefSyControlGroupId: String

    /**  */
    val theName: String
    /**  */
    val theReName: String

    /**  */
    val theKind: String
    /**  */
    val theKindName: String

    /**  */
    val theEnabled: String
    /**  */
    val theEnabledName: String

    /**  */
    val theActInspector: String
    /**  */
    val theActRecorder: String

    /**  */
    val theChildrenControlGroupDetails: List<MuseumCvControlGroupDetailDelegate>
}


/** added by elite_lin - 2026/04/05 */

interface MuseumCvControlGroupDetailDelegate {
    /**  */
    val theControlGroupDetailId: String
    /** Reference to ``CvBase.CVB_BaseID */
    val theRefCvBaseId: String
    /** Reference to ``CvControlGroup.CVCG_CloneId */
    val theRefCvControlGroupId: String
    /** Reference to ``CvControlGroup.CVCG_CloneId */
    val theRefCvCgId: String
    /** Reference to ``SyControlGroupDetail.SYCGD_ControlGroupDetailId.`` */
    val theRefSyControlGroupDetailId: String
    val theControlGroupName: String
    val theName: String
    val theSortNo: Int?
    val theEnabled: String
    val theEnabledName: String
    
    /**  */
    val theChildrenControlSettings: List<MuseumCvControlSettingDelegate>
}


/** added by elite_lin - 2026/04/05 */
ok
interface MuseumCvControlSettingDelegate {
     val theControlSettingId: String
    /** Reference to ``CvBase.CVB_BaseID */
    val theRefCvBaseId: String
    /** Reference to ``CvControlGroupDetail.CVCGD_CloneId`` */
    val theRefCvControlGroupDetailId: String
    /** Reference to ``CvControlGroupDetail.CVCGD_CloneId`` */
    val theRefCvCgdId: String
    /** Reference to ``CvControlGroup.CVCG_CloneId`` */
    val theRefCvCgId: String
    /** Reference to ``SyControlSetting.SYCS_ControlSettingId`` */
    val theRefSyControlSettingId: String
    val theCvControlGroupDetailSortNo: Int
    val theControlGroupName: String
    val theControlGroupDetailName: String
    val theName: String
    val theRemark: String
    val theSortNo: Int?

    /**  */
    val theChildrenControlProperties: List<MuseumCvControlPropertyDelegate>
}


/** added by elite_lin - 2026/04/05 */
OK
interface MuseumCvControlPropertyDelegate {
    /**  */
    val theControlPropertyId: String

    /** Reference to ``CvBase.CVB_BaseID */
    val theRefCvBaseId: String

    /** Reference to ``CvControlGroup.CVCG_CloneId`` */
    val theRefCvControlGroupId: String

    /** Reference to ``CvControlGroupDetail.CVCGD_CloneId`` */
    val theRefCvControlGroupDetailId: String

    /** Reference to ``CvControlSetting.CVCS_CloneId */
    val theRefCvControlSettingId: String

    /** Reference to ``CvControlSetting.CVCS_CloneId */
    val theRefCvCsId: String

    /** Reference to ``SyControlProperty.SYCP_ControlPropertyId */
    val theRefSyControlPropertyId: String

    /** Reference to ``SyControlCode.SYCC_ControlCodeId */
    val theRefSyControlCodeId: String

    /**  */
    val theControlGroupName: String

    /**  */
    val theControlGroupDetailName: String
    /**  */
    val theControlGroupDetailSortNo: Int

    /**  */
    val theControlSettingName: String
    /**  */
    val theControlControlSettingSortNo: Int

    /**  */
    val theControlPropertySortNo: Int

    /**
     *  - [SyControlPropertyTypes]
     **/
    val theControlCode: String
    /**  */
    val theControlCodeName: String
    /**  */
    val theControlCodeSortNo: Int

    /**  */
    val theControlCodeGroupId: String
    /**  */
    val theControlCodeGroupName: String
    /**  */
    val theControlCodeGroupSortNo: Int

    /**  */
    val theChildrenControlCodes: List<MuseumCvControlCodeDelegate>
    /**  */
    val theChildrenControlCodeGroups: List<MuseumCvControlCodeGroupDelegate>
    /**  */
    val theChildrenControlPropertyItems: List<MuseumCvControlPropertyItemDelegate>
}


/** added by elite_lin - 2026/04/05 */
interface MuseumCvControlPropertyItemDelegate {
    /**  */
    val theCloneId: String
    /**  */
    val theBaseId: String
    /**  */
    val theCGDID: String
    /**  */
    val theCGDSortNo: Float?
    /**  */
    val theCGID: String
    /**  */
    val theCode: String
    /**  */
    val theCodeGroupId: String
    /**  */
    val theControlCode: String
    /**  */
    val theControlGroupDetailName: String
    /**  */
    val theControlGroupName: String
    /**  */
    val theControlPropertyId: String
    /**  */
    val theControlPropertyItemId: String
    /**  */
    val theControlSettingName: String
    /**  */
    val theCPCloneId: String
    /**  */
    val theCPID: String
    /**  */
    val theCPSortNo: Float?
    /**  */
    val theCSID: String
    /**  */
    val theCSSortNo: Float?
    /**  */
    val theDIndex: Float?
    /**  */
    val theFileMode: String
    /**  */
    val theHeight: Float?
    /**  */
    val theIndex: Float?
    /**  */
    val theItemLabel: String
    /**  */
    val theItemSort: Float?
    /**  */
    val theItemValue: String
    /**  */
    val theMIndex: Float?
    /**  */
    val theNodeId: String
    /**  */
    val theParentId: String
    /**  */
    val theRecordValue: String
    /**  */
    val theRelactionClickValue: String
    /**  */
    val theRelactionControl: String
    /**  */
    val theRelactionControlName: String
    /**  */
    val theRelactionValue: String
    /**  */
    val theRows: Float?
    /**  */
    val theSubject: String
    /**  */
    val theType: String
    /**  */
    val theUrl: String
    /**  */
    val theUrlAction: String
    /**  */
    val theWidth: Float?
}


/** added by elite_lin - 2026/04/05 */
ok
interface MuseumSyControlCodeDelegate {
    /**  */
    val theControlCodeId: String
    /**  */
    val theCode: String
    /**  */
    val theName: String
    /**  */
    val theIcon: String
    /**  */
    val theEnabled: String
    /**  */
    val theEnabledName: String
    /**  */
    val theSortNo: Int?
}


/** added by elite_lin - 2026/04/05 */
interface MuseumCvControlCodeGroupDelegate {
    /**  */
    val theCloneId: String
    /**  */
    val theControlCodeGroupId: String
    /**  */
    val theCPCloneId: String
    /**  */
    val theEnabled: String
    /**  */
    val theEnabledName: String
    /**  */
    val theName: String
    /**  */
    val theSortNo: Float?
    /**  */
    val theChildrenControlProperties: List<MuseumCvControlPropertyDelegate>
    /**  */
    val theChildrenControlPropertyItems: List<MuseumCvControlPropertyItemDelegate>
}


interface CvControlGroupDetailDelegate : SyncedAtDelegate {
    val theControlGroupDetailId: String
    val theRefCvControlGroupId: String
    val theRefCvCgId: String
    val theRefCvBaseId: String
    val theControlGroupName: String
    val theRefSyControlGroupDetailId: String
    val theName: String
    val theSortNo: Int
    val theEnabled: String
    val theEnabledName: String
}


interface CvControlSettingDelegate : SyncedAtDelegate {
    val theControlSettingId: String
    val theRefCvControlGroupDetailId: String
    val theRefCvCgdId: String
    val theRefCvCgId: String
    val theRefCvBaseId: String
    val theCvControlGroupDetailSortNo: Int
    val theControlGroupName: String
    val theControlGroupDetailName: String
    val theRefSyControlSettingId: String
    val theName: String
    val theRemark: String
    val theSortNo: Int
}


interface CvControlPropertyDelegate : SyncedAtDelegate {
    val theControlPropertyId: String
    val theControlPropertySortNo: Int
    val theRefCvControlSettingId: String
    val theRefCvCsId: String
    val theControlControlSettingSortNo: Int
    val theRefCvControlGroupDetailId: String
    val theControlGroupDetailName: String
    val theControlGroupDetailSortNo: Int
    val theRefCvControlGroupId: String
    val theControlGroupName: String
    val theRefCvBaseId: String
    val theRefSyControlPropertyId: String
    val theRefSyControlCodeId: String
    val theControlCode: String
    val theControlCodeName: String
    val theControlCodeSortNo: Int
    val theControlSettingName: String
    val theControlCodeGroupId: String
    val theControlCodeGroupName: String
    val theControlCodeGroupSortNo: Int
}



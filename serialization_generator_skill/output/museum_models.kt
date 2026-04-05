
interface MuseumSortItemDelegate {
    /**  */
    val theColumnName: String
    /**  */
    val theDirection: String
}

interface MuseumQueryModelDelegate {
    /**  */
    val theBaseId: String
    /**  */
    val theRecordId: String
    /**  */
    val theAccessionNumber: String
    /**  */
    val theAccessionNumber2: String
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
    val theYyyy: Float?
    /**  */
    val theSecondary: Float?
    /**  */
    val theBeginDate: String
    /**  */
    val theEndDate: String
    /**  */
    val theGalleryCode: String
    /**  */
    val theViewReasonCode: String
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
    /**  */
    val theForm: String
    /**  */
    val theRequestKey: String
    /**  */
    val theKeyword: String
    /**  */
    val theCreatorId: String
    /**  */
    val theCreatorCode: String
    /**  */
    val theCreatorName: String
    /**  */
    val theCreateDate: String
    /**  */
    val theCreateDateFrom: String
    /**  */
    val theCreateDateTo: String
    /**  */
    val theUpdaterId: String
    /**  */
    val theUpdaterCode: String
    /**  */
    val theUpdaterName: String
    /**  */
    val theUpdateDate: String
    /**  */
    val theUpdateDateFrom: String
    /**  */
    val theUpdateDateTo: String
    /**  */
    val theQueryLevel: String
    /**  */
    val theReportType: Float?
    /**  */
    val theReportId: String
    /**  */
    val theExportName: String
    /**  */
    val theReportTitle: String
    /**  */
    val theReportSourceType: Float?
    /**  */
    val theReportPrintMethod: String
    /**  */
    val theReportScheduleDate: String
    /**  */
    val theReportScheduleHour: Float?
    /**  */
    val theReportScheduleMin: Float?
    /**  */
    val theClientMessage: String
    /**  */
    val thePageIndex: Float?
    /**  */
    val thePageSize: Float?
    /**  */
    val theSort: List<MuseumSortItemDelegate>
}

interface MuseumGalleryItemDelegate {
    /**  */
    val theBaseId: String
    /**  */
    val theAccessionNumber: String
    /**  */
    val theAccessionNumberXx: String
    /**  */
    val theAccessionNumber2: String
    /**  */
    val theAuthCode: String
    /**  */
    val theAuthor: String
    /**  */
    val theBaseMaterial: String
    /**  */
    val theCarrier: String
    /**  */
    val theCode: String
    /**  */
    val theCreateDate: String
    /**  */
    val theCreatorId: String
    /**  */
    val theCreatorNameXx: String
    /**  */
    val theDigitizeDate: String
    /**  */
    val theDigitizePerson: String
    /**  */
    val theDimensionX: Float?
    /**  */
    val theDimensionY: Float?
    /**  */
    val theDimensionZ: Float?
    /**  */
    val theEdition: String
    /**  */
    val theEnAuthor: String
    /**  */
    val theEnWritName: String
    /**  */
    val theFramedForm: String
    /**  */
    val theGalleryCodeXx: String
    /**  */
    val theGalleryId: String
    /**  */
    val theHeight: Float?
    /**  */
    val theImageUrl: String
    /**  */
    val theInspector: String
    /**  */
    val theIsFrame: String
    /**  */
    val theKind: String
    /**  */
    val theKindChildren: String
    /**  */
    val theKindMainXx: String
    /**  */
    val theKindNameXx: String
    /**  */
    val theKindRemark: String
    /**  */
    val theLength: Float?
    /**  */
    val theLevel: String
    /**  */
    val theLoc1: String
    /**  */
    val theLoc2: String
    /**  */
    val theLoc3: String
    /**  */
    val theLoc4: String
    /**  */
    val theMainQty: Float?
    /**  */
    val theMediaLength: Float?
    /**  */
    val theMountingMaterialH: Float?
    /**  */
    val theMountingMaterialL: Float?
    /**  */
    val theMountingMaterialW: Float?
    /**  */
    val thePartsQty: Float?
    /**  */
    val theProtectiveMeasures: String
    /**  */
    val theRecorder: String
    /**  */
    val theRemark: String
    /**  */
    val theScreenProtect: String
    /**  */
    val theSecondary: Float?
    /**  */
    val theSemicolon: String
    /**  */
    val theSerialNumber: String
    /**  */
    val theSizeDesc: String
    /**  */
    val theSource: String
    /**  */
    val theSourceXx: String
    /**  */
    val theStatus: String
    /**  */
    val theStatusNameXx: String
    /**  */
    val theStorageLocation: String
    /**  */
    val theUpdateDate: String
    /**  */
    val theUpdaterId: String
    /**  */
    val theUpdaterNameXx: String
    /**  */
    val theViewDate: String
    /**  */
    val theViewReason: String
    /**  */
    val theViewReasonCodeXx: String
    /**  */
    val theViewReasonNameXx: String
    /**  */
    val theWeight: Float?
    /**  */
    val theWidth: Float?
    /**  */
    val theWritName: String
    /**  */
    val theYears: String
    /**  */
    val theYyyy: Float?
}

interface MuseumAuthorDelegate {
    /**  */
    val theAuthorId: String
    /**  */
    val theBaseId: String
    /**  */
    val theCreateDate: String
    /**  */
    val theCreatorId: String
    /**  */
    val theCreatorNameXx: String
    /**  */
    val theDocuments: String
    /**  */
    val theDocuments2: String
    /**  */
    val theExhibitRecord: String
    /**  */
    val theGuarantee: String
    /**  */
    val theInstallation: String
    /**  */
    val theIsProviderNameXx: String
    /**  */
    val theProvider: String
    /**  */
    val theUpdateDate: String
    /**  */
    val theUpdaterId: String
    /**  */
    val theUpdaterNameXx: String
}

interface MuseumDetail1Delegate {
    /**  */
    val theBaseDetail1Id: String
    /**  */
    val theBaseId: String
    /**  */
    val theCreateDate: String
    /**  */
    val theCreatorId: String
    /**  */
    val theCreatorNameXx: String
    /**  */
    val theGalleryContentId: String
    /**  */
    val theRemark: String
    /**  */
    val theSemicolon: String
    /**  */
    val theUpdateDate: String
    /**  */
    val theUpdaterId: String
    /**  */
    val theUpdaterNameXx: String
}

interface MuseumDetail2Delegate {
    /**  */
    val theBaseDetail2Id: String
    /**  */
    val theBaseId: String
    /**  */
    val theCreateDate: String
    /**  */
    val theCreatorId: String
    /**  */
    val theCreatorNameXx: String
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
    /**  */
    val theUpdateDate: String
    /**  */
    val theUpdaterId: String
    /**  */
    val theUpdaterNameXx: String
}

interface MuseumAttachFileDelegate {
    /**  */
    val theAttachFileId: String
    /**  */
    val theCreateDate: String
    /**  */
    val theCreatorCodeXx: String
    /**  */
    val theCreatorId: String
    /**  */
    val theCreatorNameXx: String
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
    /**  */
    val theUpdateDate: String
    /**  */
    val theUpdaterCodeXx: String
    /**  */
    val theUpdaterId: String
    /**  */
    val theUpdaterNameXx: String
}

interface MuseumDetailInfoDelegate {
    /**  */
    val theAccessionNumber: String
    /**  */
    val theKind: String
    /**  */
    val theKindChildren: String
    /**  */
    val theNumber: String
    /**  */
    val theBaseId: String
    /**  */
    val theIsReView: String
    /**  */
    val theIsView: String
}

interface MuseumGalleryDetailItemDelegate : MuseumGalleryItemDelegate {
    /**  */
    val theChildrenAuthor: List<MuseumAuthorDelegate>
    /**  */
    val theChildrenDetail1: List<MuseumDetail1Delegate>
    /**  */
    val theChildrenDetail2: List<MuseumDetail2Delegate>
    /**  */
    val theChildrenAttachFile: List<MuseumAttachFileDelegate>
    /**  */
    val theViewReasonName: String
    /**  */
    val theIsLock: String
    /**  */
    val theIsView: String
    /**  */
    val theDetail: List<MuseumDetailInfoDelegate>
    /**  */
    val theSemicolonCount: Float?
}

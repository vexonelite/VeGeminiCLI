

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WtcGalleryQueryScreen(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
    r36RfidViewModel: R36RfidViewModel,
) {
    Logger.getLogger("WtcGalleryQueryScreen").log(Level.INFO, "WtcGalleryQueryScreen - time: [${System.currentTimeMillis()}]")

    MuseumGalleryQueryScreen(
        navController = navController,
        galleryQueryParameters = rememberWtcGalleryQueryScreenParameters(
            navController = navController,
            apiViewModel = apiViewModel,
            r36RfidViewModel = r36RfidViewModel,
            uiUtility = apiViewModel,
            bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
        ),
        tag = "WtcGalleryQuery"
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberWtcGalleryQueryScreenParameters(
    navController: NavHostController,
    apiViewModel: MuseumApiModelDelegate,
    r36RfidViewModel: R36RfidViewModel,
    uiUtility: MuseumUiUtilityDelegate,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
): GalleryQueryParameterDelegate {
    return rememberGalleryQueryScreenParameters(
        navController = navController,
        apiViewModel = apiViewModel,
        uiUtility = uiUtility,
        rfidConfig = remember(r36RfidViewModel) { r36RfidViewModel.toRfidConfigExt() },
        bottomSheetScaffoldState = bottomSheetScaffoldState,
    ) {
        // 1-4
        theKeywordValue: TextFieldValue,
        theKeywordOnValueChange: (TextFieldValue) -> Unit,
        theKeywordHint: String,
        theKeywordOnFocusChanged: (FocusState) -> Unit,
        // 5-9
        theGalleryCodeValue: TextFieldValue,
        theGalleryCodeOnValueChange: (TextFieldValue) -> Unit,
        theGalleryCodeHint: String,
        theGalleryCodeOnFocusChanged: (FocusState) -> Unit,
        theGalleryCodeVisibility: Boolean,
        // 10-14
        theNameValue: TextFieldValue,
        theNameOnValueChange: (TextFieldValue) -> Unit,
        theNameHint: String,
        theNameOnFocusChanged: (FocusState) -> Unit,
        theNameVisibility: Boolean,
        // 15-16
        theClearButtonVisibility: Boolean,
        theOnClearButtonClick: () -> Unit,
        // 17-18
        theSearchTags: SnapshotStateList<String>,
        theOnSearchButtonClick: () -> Unit,
        // 19
        theItemClickCallback: HolderItemClickDelegate<GalleryItemDelegate>,
        // 20
        theListDataState: State<ListDataState<GalleryItemDelegate>>,
        // 21-23
        theHasScreenBeenInited: Boolean,
        theCoroutineScope: CoroutineScope,
        theLaunchedEffectBlock: suspend CoroutineScope.() -> Unit,
        //14
        theGalleryQueryDialogState: State<BuiltInDialogStateDelegate> ->

        BuiltInGalleryQueryParameter01(
            theKeywordValue = theKeywordValue,
            theKeywordOnValueChange = theKeywordOnValueChange,
            theKeywordHint = theKeywordHint,
            theKeywordOnFocusChanged = theKeywordOnFocusChanged,

            theGalleryCodeValue = theGalleryCodeValue,
            theGalleryCodeOnValueChange = theGalleryCodeOnValueChange,
            theGalleryCodeHint = theGalleryCodeHint,
            theGalleryCodeOnFocusChanged = theGalleryCodeOnFocusChanged,
            theGalleryCodeVisibility = theGalleryCodeVisibility,

            theNameValue = theNameValue,
            theNameOnValueChange = theNameOnValueChange,
            theNameHint = theNameHint,
            theNameOnFocusChanged = theNameOnFocusChanged,
            theNameVisibility = theNameVisibility,

            theClearButtonVisibility = theClearButtonVisibility,
            theOnClearButtonClick = theOnClearButtonClick,

            theSearchTags = theSearchTags,
            theOnSearchButtonClick = theOnSearchButtonClick,

            theItemClickCallback = theItemClickCallback,
            theListDataState = theListDataState,

            theHasScreenBeenInited = theHasScreenBeenInited,
            theCoroutineScope = theCoroutineScope,
            theLaunchedEffectBlock = theLaunchedEffectBlock,

            theGalleryQueryDialogState = theGalleryQueryDialogState,

        )
    }
}




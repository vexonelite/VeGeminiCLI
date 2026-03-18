

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WtcGalleryApplyScreen(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
) {
    Logger.getLogger("WtcGalleryApplyScreen").log(Level.INFO, "WtcGalleryApplyScreen - time: [${System.currentTimeMillis()}]")

    val bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    
    MuseumGalleryApplyScreen(
        navController = navController,
        galleryApplyQueryParameters = rememberWtcGalleryApplyQueryScreenParameters(
            navController = navController,
            apiViewModel = apiViewModel,
            uiUtility = apiViewModel,
            bottomSheetScaffoldState = bottomSheetScaffoldState,
        ),
        screenParameter = MuseumGalleryApplyScreenParameter(
            fontSize = 16.sp,
            chipColors = theBuiltInChipColors01(containerColor = Color.White),
            containerPaddingValues = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
            containerBackground = Grey94,
            bottomSheetScaffoldState = bottomSheetScaffoldState,
            tag = "NtcamGalleryQuery",
        )
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberWtcGalleryApplyQueryScreenParameters(
    navController: NavHostController,
    apiViewModel: MuseumApiModelDelegate,
    uiUtility: MuseumUiUtilityDelegate,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
): GalleryApplyQueryParameterDelegate {
    return rememberGalleryApplyQueryScreenParameters(
        navController = navController,
        apiViewModel = apiViewModel,
        uiUtility = uiUtility,
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
        theOptionValue: TextFieldValue,
        theOptionOnValueChange: (TextFieldValue) -> Unit,
        theOptionHint: String,
        // 17-19
        theClearButtonVisibility: Boolean,
        theOnClearButtonClick: () -> Unit,
        // 20-21
        theSearchTags: SnapshotStateList<String>,
        theOnSearchButtonClick: () -> Unit,
        // 22
        theItemClickCallback: HolderItemClickDelegate<GalleryApplyItemDelegate>,
        // 23
        theListDataState: State<ListDataState<GalleryApplyItemDelegate>>,
        // 24-26
        theHasScreenBeenInited: Boolean,
        theCoroutineScope: CoroutineScope,
        theLaunchedEffectBlock: suspend CoroutineScope.() -> Unit ->

        BuiltInGalleryApplyQueryParameter01(
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
            theOptionValue = theOptionValue,
            theOptionOnValueChange = theOptionOnValueChange,
            theOptionHint = theOptionHint,
            theClearButtonVisibility = theClearButtonVisibility,
            theOnClearButtonClick = theOnClearButtonClick,
            theSearchTags = theSearchTags,
            theOnSearchButtonClick = theOnSearchButtonClick,
            theItemClickCallback = theItemClickCallback,
            theListDataState = theListDataState,
            theHasScreenBeenInited = theHasScreenBeenInited,
            theCoroutineScope = theCoroutineScope,
            theLaunchedEffectBlock = theLaunchedEffectBlock,

        )
    }
}






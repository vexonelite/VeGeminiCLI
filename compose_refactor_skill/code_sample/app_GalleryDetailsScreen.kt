

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WtcGalleryDetailScreen(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
) {
    Logger.getLogger("WtcGalleryDetailScreen").log(Level.INFO, "WtcGalleryDetailScreen - time: [${System.currentTimeMillis()}]")

    MuseumGalleryDetailScreen(
        navController = navController,
        galleryDetailParameters = rememberWtcGalleryDetailScreenParameters(
            navController = navController,
            apiViewModel = apiViewModel,
            uiUtility = apiViewModel,
        ),
        tag = "WtcGalleryDetail"
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberWtcGalleryDetailScreenParameters(
    navController: NavHostController,
    apiViewModel: MuseumApiModelDelegate,
    uiUtility: MuseumUiUtilityDelegate,
): GalleryDetailParameterDelegate {
    return rememberGalleryDetailScreenParameters(
        navController = navController,
        apiViewModel = apiViewModel,
        uiUtility = uiUtility,
    ) {
        // 1-5
        theLocation1Value: TextFieldValue,
        theLocation1OnValueChange: (TextFieldValue) -> Unit,
        theLocation1Hint: String,
        theLocation1SelectAllOnFocus: Boolean,
        theLocation1OnFocusChanged: (FocusState) -> Unit,
        // 6-10
        theLocation2Value: TextFieldValue,
        theLocation2OnValueChange: (TextFieldValue) -> Unit,
        theLocation2Hint: String,
        theLocation2SelectAllOnFocus: Boolean,
        theLocation2OnFocusChanged: (FocusState) -> Unit,
        // 11-15
        theLocation3Value: TextFieldValue,
        theLocation3OnValueChange: (TextFieldValue) -> Unit,
        theLocation3Hint: String,
        theLocation3SelectAllOnFocus: Boolean,
        theLocation3OnFocusChanged: (FocusState) -> Unit,
        // 16-20
        thePlaceValue: TextFieldValue,
        thePlaceOnValueChange: (TextFieldValue) -> Unit,
        thePlaceHint: String,
        thePlaceSelectAllOnFocus: Boolean,
        thePlaceOnFocusChanged: (FocusState) -> Unit,
        // 21-25
        theRemarkValue: TextFieldValue,
        theRemarkOnValueChange: (TextFieldValue) -> Unit,
        theRemarkHint: String,
        theRemarkSelectAllOnFocus: Boolean,
        theRemarkOnFocusChanged: (FocusState) -> Unit,
        // 26
        theOnSaveButtonClick: () -> Unit,
        // 27-30
        theAreaHint: String,
        theAreaDropDownValue: TextFieldValue,
        onAreaDropDownMenuItemChange: (String) -> Unit,
        theAreaValue: TextFieldValue,
        // 31-36
        theAreaOnValueChange: (TextFieldValue) -> Unit,
        theAreaSelectAllOnFocus: Boolean,
        theAreaOnFocusChanged: (FocusState) -> Unit,
        theAreaFocusRequester: FocusRequester,
        theAreaOnDone: KeyboardActionScope.() -> Unit,
        theAreaItems: List<String>,
        // 37-40
        theStoreRoomListDataState: ListDataState<TextLabelDelegate>,
        theStoreRoomItemClickCallback: HolderItemClickDelegate<TextLabelDelegate>,
        theStoreRoomPickerDialogState: State<BuiltInDialogStateDelegate>,
        theStoreRoomIconClick: () -> Unit,
        // 41-45
        theScrollableThreshold: Int,
        theSelectedTabIndex: Int,
        theTabItems: List<TabItemDelegate>,
        theOnTabItemClick: (Int) -> Unit,
        theTabTextFontSize: TextUnit,
        // 46-47
        theItemClickCallback: HolderItemClickDelegate<ThreeTextsDelegate>,
        theListDataState: State<ListDataState<ThreeTextsDelegate>>,
        // 48-50
        theHasScreenBeenInited: Boolean,
        theCoroutineScope: CoroutineScope,
        theLaunchedEffectBlock: suspend CoroutineScope.() -> Unit,
        // 51-55
        theGalleryItem: RfGalleryItemDelegate,
        theScrollState: ScrollState,
        theFontSize: TextUnit,
        theReferencedWidth: Dp,
        theTopAppBarTitle: String,
        // 56-57
        theThreeTextsLabel: ThreeTextsDelegate,
        theGalleryDetailDialogState: State<BuiltInDialogStateDelegate> ->

        BuiltInGalleryDetailParameter01(
            theGalleryItem = theGalleryItem,
            theScrollState = theScrollState,
            theFontSize = theFontSize,
            theReferencedWidth = theReferencedWidth,
            theSelectedTabIndex = theSelectedTabIndex,
            theTabItems = theTabItems,
            theOnTabItemClick = theOnTabItemClick,
            theGalleryDetailDialogState = theGalleryDetailDialogState,
            theThreeTextsLabel = theThreeTextsLabel,
            theListDataState = theListDataState,
            theHasScreenBeenInited = theHasScreenBeenInited,
            theCoroutineScope = theCoroutineScope,
            theLaunchedEffectBlock = theLaunchedEffectBlock,
            theAreaHint = theAreaHint,
            theAreaDropDownValue = theAreaDropDownValue,
            onAreaDropDownMenuItemChange = onAreaDropDownMenuItemChange,
            theAreaValue = theAreaValue,
            theAreaOnValueChange = theAreaOnValueChange,
            theAreaSelectAllOnFocus = theAreaSelectAllOnFocus,
            theAreaOnFocusChanged = theAreaOnFocusChanged,
            theAreaFocusRequester = theAreaFocusRequester,
            theAreaOnDone = theAreaOnDone,
            theAreaItems = theAreaItems,
            theStoreRoomListDataState = theStoreRoomListDataState,
            theStoreRoomItemClickCallback = theStoreRoomItemClickCallback,
            theStoreRoomPickerDialogState = theStoreRoomPickerDialogState,
            theStoreRoomIconClick = theStoreRoomIconClick,
            theLocation1Value = theLocation1Value,
            theLocation1OnValueChange = theLocation1OnValueChange,
            theLocation1Hint = theLocation1Hint,
            theLocation1SelectAllOnFocus = theLocation1SelectAllOnFocus,
            theLocation1OnFocusChanged = theLocation1OnFocusChanged,
            theLocation2Value = theLocation2Value,
            theLocation2OnValueChange = theLocation2OnValueChange,
            theLocation2Hint = theLocation2Hint,
            theLocation2SelectAllOnFocus = theLocation2SelectAllOnFocus,
            theLocation2OnFocusChanged = theLocation2OnFocusChanged,
            theLocation3Value = theLocation3Value,
            theLocation3OnValueChange = theLocation3OnValueChange,
            theLocation3Hint = theLocation3Hint,
            theLocation3SelectAllOnFocus = theLocation3SelectAllOnFocus,
            theLocation3OnFocusChanged = theLocation3OnFocusChanged,
            thePlaceValue = thePlaceValue,
            thePlaceOnValueChange = thePlaceOnValueChange,
            thePlaceHint = thePlaceHint,
            thePlaceSelectAllOnFocus = thePlaceSelectAllOnFocus,
            thePlaceOnFocusChanged = thePlaceOnFocusChanged,
            theRemarkValue = theRemarkValue,
            theRemarkOnValueChange = theRemarkOnValueChange,
            theRemarkHint = theRemarkHint,
            theRemarkSelectAllOnFocus = theRemarkSelectAllOnFocus,
            theRemarkOnFocusChanged = theRemarkOnFocusChanged,
            theOnSaveButtonClick = theOnSaveButtonClick,
        )
    }
}





@Composable
fun WtcGalleryApplyDetailsScreen(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
    r36RfidViewModel: R36RfidViewModel,
) {
    Logger.getLogger("WtcGalleryApplyDetailsScreen").log(Level.INFO, "WtcGalleryApplyDetailsScreen - time: [${System.currentTimeMillis()}]")

    MuseumGalleryApplyDetailsScreen(
        navController = navController,
        galleryApplyDetailQueryParameters = rememberWtcGalleryApplyDetailQueryScreenParameters(
            navController = navController,
            apiViewModel = apiViewModel,
            r36RfidViewModel = r36RfidViewModel,
            uiUtility = apiViewModel,
        ),
        tag = "WtcGalleryApplyDetails"
    )

}


@Composable
fun rememberWtcGalleryApplyDetailQueryScreenParameters(
    navController: NavHostController,
    apiViewModel: MuseumApiModelDelegate,
    r36RfidViewModel: R36RfidViewModel,
    uiUtility: MuseumUiUtilityDelegate,
): GalleryApplyDetailQueryParameterDelegate {
    return rememberGalleryApplyDetailQueryScreenParameters(
        navController = navController,
        apiViewModel = apiViewModel,
        uiUtility = uiUtility,
        rfidConfig = remember(r36RfidViewModel) { r36RfidViewModel.toRfidConfigExt() },
        tabListFactory = WtcRfidTabListFactoryImpl(),
    ) {
        // 1-4
        theSelectedGalleryApply: RfGalleryApplyExt02Delegate,
        theGalleryApplyDetailDialogState: State<BuiltInDialogStateDelegate>,
        theOnReloadButtonClick: () -> Unit,
        theOnSaveButtonClick: () -> Unit,
        // 5-9
        theScrollableThreshold: Int,
        theSelectedTabIndex: Int,
        theTabItems: List<TabItemDelegate>,
        theOnTabItemClick: (Int) -> Unit,
        theTabTextFontSize: TextUnit,
        // 10-11
        theItemClickCallback: HolderItemClickDelegate<GalleryItemDelegate>,
        theListDataState: State<ListDataState<GalleryItemDelegate>>,
        // 12-14
        theHasScreenBeenInited: Boolean,
        theCoroutineScope: CoroutineScope,
        theLaunchedEffectBlock: suspend CoroutineScope.() -> Unit, ->

        BuiltInGalleryApplyDetailQueryParameter01(
            theSelectedGalleryApply = theSelectedGalleryApply,
            theGalleryApplyDetailDialogState = theGalleryApplyDetailDialogState,
            theOnReloadButtonClick = theOnReloadButtonClick,
            theOnSaveButtonClick = theOnSaveButtonClick,

            theScrollableThreshold = theScrollableThreshold,
            theSelectedTabIndex = theSelectedTabIndex,
            theTabItems = theTabItems,
            theOnTabItemClick = theOnTabItemClick,
            theTabTextFontSize = theTabTextFontSize,

            theItemClickCallback = theItemClickCallback,
            theListDataState = theListDataState,

            theHasScreenBeenInited = theHasScreenBeenInited,
            theCoroutineScope = theCoroutineScope,
            theLaunchedEffectBlock = theLaunchedEffectBlock,
        )
    }
}
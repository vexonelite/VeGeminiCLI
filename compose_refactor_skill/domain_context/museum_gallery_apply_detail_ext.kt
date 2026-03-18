

interface MuseumGalleryApplyDetailExtDelegate {

    val cachedGalleryApplyDetailList: List<RfGalleryApplyDetailDelegate>

    fun updateCachedGalleryApplyDetailList(galleryApplyDetailList: List<RfGalleryApplyDetailDelegate>)

    fun clearCachedGalleryApplyDetailList()

    val galleryApplyDetailSelectedTabIndexStateFlow: MutableStateFlow<Int?>

    val galleryApplyDetailListStateFlow: MutableStateFlow<List<GalleryApplyDetailItemDelegate>?>

    val galleryApplyDetailTabListStateFlow: MutableStateFlow<List<TabItemDelegate>?>

    fun clearGalleryApplyDetailMaps()

    fun reBuildGalleryApplyDetailDataSet(scope: CoroutineScope, galleryApplyDetailList: List<RfGalleryApplyDetailDelegate>, index: Int)

    fun updateGalleryApplyDetailItem(scope: CoroutineScope, newGalleryApplyDetail: GalleryApplyDetailItemDelegate, index: Int)

    fun updateGalleryApplyDetailItemByDevice(scope: CoroutineScope, galleryCode: String, index: Int)
}



class MuseumGalleryApplyDetailExtImpl(
    private val tabListFactory: MuseumRfidTabListFactoryDelegate
) : MuseumGalleryObjectDetailExtImpl<GalleryApplyDetailItemDelegate, RfGalleryApplyDetailDelegate>(),
    MuseumGalleryApplyDetailExtDelegate
{
    private val _cachedGalleryApplyDetailList: MutableList<RfGalleryApplyDetailDelegate> = mutableListOf()

    override val cachedGalleryApplyDetailList: List<RfGalleryApplyDetailDelegate> = _cachedGalleryApplyDetailList

    override fun updateCachedGalleryApplyDetailList(galleryApplyDetailList: List<RfGalleryApplyDetailDelegate>) {
        _cachedGalleryApplyDetailList.apply {
            clear()
            addAll(galleryApplyDetailList)
        }
    }

    override fun clearCachedGalleryApplyDetailList() {  _cachedGalleryApplyDetailList.clear() }
    // [end] added by elite_lin - 2025/04/25

    override val galleryApplyDetailSelectedTabIndexStateFlow: MutableStateFlow<Int?> = MutableStateFlow<Int?>(null)

    override val galleryApplyDetailTabListStateFlow: MutableStateFlow<List<TabItemDelegate>?> = MutableStateFlow<List<TabItemDelegate>?>(null)

    override val galleryApplyDetailListStateFlow: MutableStateFlow<List<GalleryApplyDetailItemDelegate>?> = galleryObjectDetailListStateFlow

    override fun clearGalleryApplyDetailMaps() { clearGalleryObjectDetailMaps() }

    override fun reBuildGalleryApplyDetailDataSet(
        scope: CoroutineScope, galleryApplyDetailList: List<RfGalleryApplyDetailDelegate>, index: Int
    ) {
        reBuildGalleryObjectDetailDataSet(scope, galleryApplyDetailList, index)
    }

    override fun updateGalleryApplyDetailItem(
        scope: CoroutineScope, newGalleryApplyDetail: GalleryApplyDetailItemDelegate, index: Int
    ) {
        updateGalleryObjectDetailItem(scope, newGalleryApplyDetail, newGalleryApplyDetail.theGalleryApplyDetailItem.theGalleryCode, index)
    }

    override fun updateGalleryApplyDetailItemByDevice(scope: CoroutineScope, galleryCode: String, index: Int) {
        updateGalleryObjectDetailItemByDevice(scope, galleryCode, index)
    }

    override fun apiItemToDelegate(item: RfGalleryApplyDetailDelegate): Pair<GalleryApplyDetailItemDelegate, String> {
        val galleryItemDelegate = GalleryApplyDetailItemImpl(theGalleryApplyDetailItem = item)
        return Pair<GalleryApplyDetailItemDelegate, String>(
            galleryItemDelegate, galleryItemDelegate.theGalleryApplyDetailItem.theGalleryCode
        )
    }

    override fun apiItemStatusFilter(item: RfGalleryApplyDetailDelegate, expectedStatus: String): Boolean =
        item.theStatus == expectedStatus


    override fun updateGalleryObjectDetailTabListStateFlow() {
        val sizeInfo: List<Int> = galleryObjectDetailTabListMap.getSizeInfoExt()
        val tabItems: List<TabItemDelegate> = tabListFactory.theGalleryApplyDetailTabList.mapIndexed { index: Int, tabItem: MuseumBadgetTabItemImpl ->
            tabItem.copy(theBadgetNumber = sizeInfo[index])
        }
        galleryApplyDetailTabListStateFlow.update { tabItems }
    }

    override fun updateGalleryObjectDetailItemByDevice(scope: CoroutineScope, galleryCode: String, tabIndex: Int) {
        val galleryApplyDetail: GalleryItemDelegate? = galleryObjectDetailMap[galleryCode]
        if (galleryApplyDetail !is GalleryApplyDetailItemImpl) {
            Logger.getLogger(getLogTag()).log(Level.WARNING, "updateGalleryObjectDetailItemByDevice() - galleryObjectDetailMap has no data for galleryCode: [$galleryCode]")
            return
        }

        Logger.getLogger(getLogTag()).log(Level.INFO, "updateGalleryObjectDetailItemByDevice() - galleryObjectDetailMap has data for galleryCode: [$galleryCode]")

        if (galleryApplyDetail.theRfidChecked) {
            Logger.getLogger(getLogTag()).log(Level.WARNING, "updateGalleryObjectDetailItemByDevice() - galleryApplyDetail [$galleryCode] already been checked by Device!!")
            return
        }

        val clonedOne: GalleryApplyDetailItemImpl = galleryApplyDetail.copy(theRfidChecked = true)
        updateGalleryObjectDetailItem(scope, clonedOne, clonedOne.theGalleryApplyDetailItem.theGalleryCode, tabIndex)
    }

}



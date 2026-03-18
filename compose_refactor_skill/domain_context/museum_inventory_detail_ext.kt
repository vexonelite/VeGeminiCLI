

interface MuseumInventoryDetailExtDelegate {

    val cachedInventoryDetailList: List<RfInventoryDetailDelegate>

    fun updateCachedInventoryDetailList(inventoryDetailList: List<RfInventoryDetailDelegate>)

    fun clearCachedInventoryDetailList()

    val inventoryDetailSelectedTabIndexStateFlow: MutableStateFlow<Int?>

    val inventoryDetailListStateFlow: MutableStateFlow<List<InventoryDetailItemDelegate>?>

    val inventoryDetailTabListStateFlow: MutableStateFlow<List<TabItemDelegate>?>

    //val inventoryDetailTabList: List<NtmofaBadgetTabItemImpl>     // revision by elite_lin - 2025/07/31

    fun clearInventoryDetailMaps()

    fun reBuildInventoryDetailDataSet(scope: CoroutineScope, inventoryDetailList: List<RfInventoryDetailDelegate>, index: Int)

    fun updateInventoryDetailItem(scope: CoroutineScope, newInventoryDetail: InventoryDetailItemDelegate, index: Int)

    fun updateInventoryDetailItemByDevice(scope: CoroutineScope, galleryCode: String, index: Int)
}


class MuseumInventoryDetailExtImpl(
    private val tabListFactory: MuseumRfidTabListFactoryDelegate    // added by elite_lin - 2025/07/31
) : MuseumGalleryObjectDetailExtImpl<InventoryDetailItemDelegate, RfInventoryDetailDelegate>(),
    MuseumInventoryDetailExtDelegate
{
    private val _cachedInventoryDetailList: MutableList<RfInventoryDetailDelegate> = mutableListOf()

    override val cachedInventoryDetailList: List<RfInventoryDetailDelegate> = _cachedInventoryDetailList

    override fun updateCachedInventoryDetailList(inventoryDetailList: List<RfInventoryDetailDelegate>) {
        _cachedInventoryDetailList.apply {
            clear()
            addAll(inventoryDetailList)
        }
    }

    override fun clearCachedInventoryDetailList() {  _cachedInventoryDetailList.clear() }

    override val inventoryDetailSelectedTabIndexStateFlow: MutableStateFlow<Int?> = MutableStateFlow<Int?>(null)

    override val inventoryDetailTabListStateFlow: MutableStateFlow<List<TabItemDelegate>?> = MutableStateFlow<List<TabItemDelegate>?>(null)

    override val inventoryDetailListStateFlow: MutableStateFlow<List<InventoryDetailItemDelegate>?> = galleryObjectDetailListStateFlow

    override fun clearInventoryDetailMaps() { clearGalleryObjectDetailMaps() }

    override fun reBuildInventoryDetailDataSet(
        scope: CoroutineScope, inventoryDetailList: List<RfInventoryDetailDelegate>, index: Int
    ) {
        reBuildGalleryObjectDetailDataSet(scope, inventoryDetailList, index)
    }

    override fun updateInventoryDetailItem(
        scope: CoroutineScope, newInventoryDetail: InventoryDetailItemDelegate, index: Int
    ) {
        updateGalleryObjectDetailItem(scope, newInventoryDetail, newInventoryDetail.theInventoryDetailItem.theGalleryCode, index)    // revision by elite_lin - 2025/12/11
    }

    override fun updateInventoryDetailItemByDevice(scope: CoroutineScope, galleryCode: String, index: Int) {
        updateGalleryObjectDetailItemByDevice(scope, galleryCode, index)
    }

    override fun apiItemToDelegate(item: RfInventoryDetailDelegate): Pair<InventoryDetailItemDelegate, String> {
        val inventoryDetailDelegate = InventoryDetailItemImpl(theInventoryDetailItem = item)
        return Pair<InventoryDetailItemDelegate, String>(
            inventoryDetailDelegate, inventoryDetailDelegate.theInventoryDetailItem.theGalleryCode   // revision by elite_lin - 2025/12/11
        )
    }

    override fun apiItemStatusFilter(item: RfInventoryDetailDelegate, expectedStatus: String): Boolean =
        item.theGalleryStatus == expectedStatus    // revision by elite_lin - 2025/12/11


    override fun updateGalleryObjectDetailTabListStateFlow() {
        val sizeInfo: List<Int> = galleryObjectDetailTabListMap.getSizeInfoExt()
        val tabItems: List<TabItemDelegate> = tabListFactory.theInventoryDetailTabList.mapIndexed { index: Int, tabItem: MuseumBadgetTabItemImpl ->
            tabItem.copy(theBadgetNumber = sizeInfo[index])
        }
        inventoryDetailTabListStateFlow.update { tabItems }
    }

    override fun updateGalleryObjectDetailItemByDevice(scope: CoroutineScope, galleryCode: String, tabIndex: Int) {
        val inventoryDetail: InventoryDetailItemDelegate? = galleryObjectDetailMap[galleryCode]
        if (inventoryDetail !is InventoryDetailItemImpl) {
            Logger.getLogger(getLogTag()).log(Level.WARNING, "updateGalleryObjectDetailItemByDevice() - galleryObjectDetailMap has no data for galleryCode: [$galleryCode]")
            return
        }

        Logger.getLogger(getLogTag()).log(Level.INFO, "updateGalleryObjectDetailItemByDevice() - galleryObjectDetailMap has data for galleryCode: [$galleryCode]")

        if (inventoryDetail.theHumanChecked) {
            Logger.getLogger(getLogTag()).log(Level.WARNING, "updateGalleryObjectDetailItemByDevice() - inventoryDetail [$galleryCode] already been checked by Human!!")
            return
        }
        if (inventoryDetail.theRfidChecked) {
            Logger.getLogger(getLogTag()).log(Level.WARNING, "updateGalleryObjectDetailItemByDevice() - inventoryDetail [$galleryCode] already been checked by Device!!")
            return
        }

        val clonedOne: InventoryDetailItemImpl = inventoryDetail.copy(theRfidChecked = true)
        updateGalleryObjectDetailItem(scope, clonedOne, clonedOne.theInventoryDetailItem.theGalleryCode, tabIndex)  // revision by elite_lin - 2025/12/11
    }
}

///

fun List<InventoryDetailItemDelegate>.updateItemExt(
    newInventoryDetail: InventoryDetailItemDelegate
): List<InventoryDetailItemDelegate> =
    this.map { theInventoryDetail: InventoryDetailItemDelegate ->
        if (theInventoryDetail.theIdentifier == newInventoryDetail.theIdentifier) {
            Logger.getLogger("List<InventoryDetailItemDelegate> Ktx").log(Level.INFO, "List<InventoryDetailItemDelegate>.updateItemExt() - update InventoryDetail : [${newInventoryDetail.theDescription}, ${newInventoryDetail.theIdentifier}] - on Thread [${Thread.currentThread().name}]")
            newInventoryDetail
        }
        else { theInventoryDetail }
    }



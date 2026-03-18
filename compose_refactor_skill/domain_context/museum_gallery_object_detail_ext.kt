

abstract class MuseumGalleryObjectDetailExtImpl<D, T> where D : IdentifierDelegate {

    private val galleryObjectDetailMapLock = ByteArray(0)

    protected val galleryObjectDetailMap: MutableMap<String, D> = mutableMapOf()

    protected val galleryObjectDetailTabListMap: MutableMap<Int, List<D>> = mutableMapOf()

    protected val galleryObjectDetailListStateFlow: MutableStateFlow<List<D>?> = MutableStateFlow<List<D>?>(null)

    private var reBuildGalleryObjectDetailDataSetJob: Job? = null


    protected fun clearGalleryObjectDetailMaps() {
        synchronized(galleryObjectDetailMapLock) {
            galleryObjectDetailMap.clear()
            galleryObjectDetailTabListMap.clear()
        }
    }

    private fun selectedTabIndexToGalleryObjectDetailList(tabIndex: Int) {
        galleryObjectDetailListStateFlow.value = null
        Logger.getLogger(getLogTag()).log(Level.INFO, "selectedTabIndexToGalleryObjectDetailList() - tabIndex: [$tabIndex] - on Thread [${Thread.currentThread().name}]")
        val galleryObjectDetailList: List<D>? = galleryObjectDetailTabListMap[tabIndex]
        if (null != galleryObjectDetailList) {
            Logger.getLogger(getLogTag()).log(Level.INFO, "selectedTabIndexToGalleryObjectDetailList() - galleryObjectDetailTabListMap has data for tabIndex: [$tabIndex], galleryObjectDetailList.size: [${galleryObjectDetailList.size}]")
            galleryObjectDetailListStateFlow.update { galleryObjectDetailList }
        }
        else {
            Logger.getLogger(getLogTag()).log(Level.INFO, "selectedTabIndexToGalleryObjectDetailList() - galleryObjectDetailTabListMap has no data for tabIndex: [$tabIndex]")
        }
    }

    protected abstract fun apiItemToDelegate(item: T): Pair<D, String>

    protected abstract fun apiItemStatusFilter(item: T, expectedStatus: String): Boolean

    protected abstract fun updateGalleryObjectDetailTabListStateFlow()

    protected abstract fun updateGalleryObjectDetailItemByDevice(scope: CoroutineScope, galleryCode: String, tabIndex: Int)

    protected fun reBuildGalleryObjectDetailDataSet(scope: CoroutineScope, galleryObjectDetailList: List<T>, tabIndex: Int) {
        if (galleryObjectDetailTabListMap.isNotEmpty()) {
            Logger.getLogger(getLogTag()).log(Level.INFO, "reBuildGalleryObjectDetailDataSet() - galleryObjectDetailTabListMap is present!")
            selectedTabIndexToGalleryObjectDetailList(tabIndex)
            return
        }

        reBuildGalleryObjectDetailDataSetJob?.cancelIfNeeded()
        reBuildGalleryObjectDetailDataSetJob = scope.launch(Dispatchers.Default) {
            Logger.getLogger(getLogTag()).log(Level.INFO, "reBuildGalleryObjectDetailDataSet() - galleryObjectDetailList.size: [${galleryObjectDetailList.size}] - on Thread [${Thread.currentThread().name}]")
            val detailMap = mutableMapOf<String, D>()

            galleryObjectDetailList.forEach { apiItem: T ->
                val (galleryObjectDetailDelegate, mapKey) = apiItemToDelegate(apiItem)
                detailMap[mapKey] = galleryObjectDetailDelegate
            }

            val detailTabListMap = mutableMapOf<Int, List<D>>()

            val tabIndex1List: List<D> = galleryObjectDetailList
                .filter { apiItem: T ->
                    apiItemStatusFilter(apiItem, "O")
                }
                .map { apiItem: T ->
                    val (galleryObjectDetailDelegate, mapKey) = apiItemToDelegate(apiItem)
                    galleryObjectDetailDelegate
                }

            val tabIndex0List: List<D> = galleryObjectDetailList
                .filter { apiItem: T ->
                    apiItemStatusFilter(apiItem, "I")
                }
                .map { apiItem: T ->
                    val (galleryObjectDetailDelegate, mapKey) = apiItemToDelegate(apiItem)
                    galleryObjectDetailDelegate
                }

            detailTabListMap[0] = tabIndex0List
            detailTabListMap[1] = tabIndex1List

            synchronized(galleryObjectDetailMapLock) {
                galleryObjectDetailMap.apply {
                    clear()
                    putAll(detailMap)
                }

                galleryObjectDetailTabListMap.apply {
                    clear()
                    putAll(detailTabListMap)
                }
            }
            Logger.getLogger(getLogTag()).log(Level.INFO, "reBuildGalleryObjectDetailDataSet - update galleryObjectDetailMap - size: [${galleryObjectDetailMap.size}]")

            updateGalleryObjectDetailTabListStateFlow()

            selectedTabIndexToGalleryObjectDetailList(tabIndex)
        }
    }

    ///

    protected fun updateGalleryObjectDetailItem(scope: CoroutineScope, newGalleryObjectDetail: D, mapKey: String, tabIndex: Int) {
        val indexSet = setOf<Int>(0, 1)
        if (tabIndex !in indexSet) {
            Logger.getLogger(getLogTag()).log(Level.WARNING, "updateGalleryObjectDetailItem() - tabIndex: [$tabIndex] does not belong to [$indexSet]!!")
            return
        }

        val tabIndex0List: List<D>? = galleryObjectDetailTabListMap[0]
        if (null == tabIndex0List) {
            Logger.getLogger(getLogTag()).log(Level.WARNING, "updateGalleryObjectDetailItem() - tabIndex0List is null!!")
            return
        }

        val tabIndex1List: List<D>? = galleryObjectDetailTabListMap[1]
        if (null == tabIndex1List) {
            Logger.getLogger(getLogTag()).log(Level.WARNING, "updateGalleryObjectDetailItem() - tabIndex1List is null!!")
            return
        }

        scope.launch(Dispatchers.Default) {
            val newTabIndex0List = tabIndex0List.updateItemGene(newGalleryObjectDetail)
            val newTabIndex1List = tabIndex1List.updateItemGene(newGalleryObjectDetail)
            synchronized(galleryObjectDetailMapLock) {
                galleryObjectDetailMap[mapKey] = newGalleryObjectDetail
                galleryObjectDetailTabListMap[0] = newTabIndex0List
                galleryObjectDetailTabListMap[1] = newTabIndex1List
                Logger.getLogger(getLogTag()).log(Level.INFO, "updateGalleryObjectDetailItem() - update GalleryObjectDetail : [${newGalleryObjectDetail.theIdentifier}] - on Thread [${Thread.currentThread().name}]")
            }

            val newInventoryDetailTabList = if (tabIndex == 0) { newTabIndex0List } else { newTabIndex1List }
            galleryObjectDetailListStateFlow.update {
                newInventoryDetailTabList
            }
        }
    }

}

///

fun <T> Map<Int, List<T>>.getSizeInfoExt(): List<Int> {
    val resultList = arrayOfNulls<Int>(this.size)
    this.forEach { entry: Map.Entry<Int, List<T>> -> resultList[entry.key] = entry.value.size }
    return resultList.map { it!! }
}



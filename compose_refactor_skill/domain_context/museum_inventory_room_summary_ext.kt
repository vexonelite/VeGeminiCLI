

interface MuseumInventoryRoomSummaryExtDelegate {

    val cachedInventoryRoomSummaryList: List<RfInventoryRoomSummaryDelegate>

    fun updateCachedInventoryRoomSummaryList(inventoryRoomSummaryList: List<RfInventoryRoomSummaryDelegate>)

    fun clearCachedInventoryRoomSummaryList()

    var selectedInventoryRoomSummary: RfInventoryRoomSummaryDelegate?

    val inventoryRoomSummarySelectedTabIndexStateFlow:  MutableStateFlow<Int?>
    val inventoryRoomSummaryListStateFlow: MutableStateFlow<List<InventoryRoomSummaryItemDelegate>?>
    val inventoryRoomSummaryTabListStateFlow: MutableStateFlow<List<TabItemDelegate>?>

    val inventoryRoomSummaryReloadEventStateFlow: MutableStateFlow<Boolean>

    fun clearInventoryRoomSummaryTabListMap()

    fun reBuildInventoryRoomSummaryTabListMap(scope: CoroutineScope, inventoryRoomSummaryList: List<RfInventoryRoomSummaryDelegate>, index: Int)
}



class MuseumInventoryRoomSummaryExtImpl(
    private val tabListFactory: MuseumRfidTabListFactoryDelegate
) : MuseumInventoryRoomSummaryExtDelegate {

    private val _cachedInventoryRoomSummaryList: MutableList<RfInventoryRoomSummaryDelegate> = mutableListOf()

    override val cachedInventoryRoomSummaryList: List<RfInventoryRoomSummaryDelegate> = _cachedInventoryRoomSummaryList

    override fun updateCachedInventoryRoomSummaryList(inventoryRoomSummaryList: List<RfInventoryRoomSummaryDelegate>) {
        _cachedInventoryRoomSummaryList.apply {
            clear()
            addAll(inventoryRoomSummaryList)
        }
    }

    override fun clearCachedInventoryRoomSummaryList() {  _cachedInventoryRoomSummaryList.clear() }


    override var selectedInventoryRoomSummary: RfInventoryRoomSummaryDelegate? = null


    private val inventoryRoomSummaryTabListMapLock = ByteArray(0)
    private val inventoryRoomSummaryTabListMap: MutableMap<Int, List<InventoryRoomSummaryItemDelegate>> = mutableMapOf()


    override val inventoryRoomSummarySelectedTabIndexStateFlow: MutableStateFlow<Int?> = MutableStateFlow<Int?>(null)
    override val inventoryRoomSummaryListStateFlow: MutableStateFlow<List<InventoryRoomSummaryItemDelegate>?> = MutableStateFlow<List<InventoryRoomSummaryItemDelegate>?>(null)
    override val inventoryRoomSummaryTabListStateFlow: MutableStateFlow<List<TabItemDelegate>?> = MutableStateFlow<List<TabItemDelegate>?>(null)

    private var reBuildInventoryRoomSummaryTabListMapJob: Job? = null

    override val inventoryRoomSummaryReloadEventStateFlow: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)


    override fun clearInventoryRoomSummaryTabListMap() {
        synchronized(inventoryRoomSummaryTabListMapLock) {
            inventoryRoomSummaryTabListMap.clear()
        }
    }

    override fun reBuildInventoryRoomSummaryTabListMap(
        scope: CoroutineScope, inventoryRoomSummaryList: List<RfInventoryRoomSummaryDelegate>, index: Int
    ) {
        if (inventoryRoomSummaryTabListMap.isNotEmpty()) {
            Logger.getLogger(getLogTag()).log(Level.INFO, "reBuildInventoryRoomSummaryTabListMap() - inventoryDetailTabListMap is present!")
            selectedTabIndexToInventoryRoomSummaryList(index)
            return
        }

        reBuildInventoryRoomSummaryTabListMapJob?.cancelIfNeeded()
        reBuildInventoryRoomSummaryTabListMapJob = scope.launch(Dispatchers.Default) {
            Logger.getLogger(getLogTag()).log(Level.INFO, "reBuildInventoryRoomSummaryTabListMap() - inventoryRoomSummaryList.size: [${inventoryRoomSummaryList.size}] - on Thread [${Thread.currentThread().name}]")

            val tabListMap = mutableMapOf<Int, List<InventoryRoomSummaryItemDelegate>>()

            val tabIndex2List: List<InventoryRoomSummaryItemDelegate> = inventoryRoomSummaryList
                .filter { inventoryRoomSummaryItem: RfInventoryRoomSummaryDelegate ->
                    inventoryRoomSummaryItem.theStatus == "2"   // revision by elite_lin - 2025/12/11
                }
                .map { inventoryRoomSummaryItem: RfInventoryRoomSummaryDelegate ->
                    InventoryRoomSummaryItemImpl(theInventoryRoomSummaryItem = inventoryRoomSummaryItem)
                }

            val tabIndex1List: List<InventoryRoomSummaryItemDelegate> = inventoryRoomSummaryList
                .filter { inventoryRoomSummaryItem: RfInventoryRoomSummaryDelegate ->
                    inventoryRoomSummaryItem.theStatus == "1"   // revision by elite_lin - 2025/12/11
                }
                .map { inventoryRoomSummaryItem: RfInventoryRoomSummaryDelegate ->
                    InventoryRoomSummaryItemImpl(theInventoryRoomSummaryItem = inventoryRoomSummaryItem)
                }

            val tabIndex0List: List<InventoryRoomSummaryItemDelegate> = inventoryRoomSummaryList
                .filter { inventoryRoomSummaryItem: RfInventoryRoomSummaryDelegate ->
                    (! ( (inventoryRoomSummaryItem.theStatus == "1") || (inventoryRoomSummaryItem.theStatus == "2") ) )   // revision by elite_lin - 2025/12/11
                }
                .map { inventoryRoomSummaryItem: RfInventoryRoomSummaryDelegate ->
                    InventoryRoomSummaryItemImpl(theInventoryRoomSummaryItem = inventoryRoomSummaryItem)
                }

            tabListMap[0] = tabIndex0List
            tabListMap[1] = tabIndex1List
            tabListMap[2] = tabIndex2List

            synchronized(inventoryRoomSummaryTabListMapLock) {
                inventoryRoomSummaryTabListMap.apply {
                    clear()
                    putAll(tabListMap)
                }
            }

            val sizeInfo: List<Int> = inventoryRoomSummaryTabListMap.getSizeInfoExt()

            val tabItems: List<TabItemDelegate> = tabListFactory.theInventoryRoomSummaryTabList.mapIndexed { index: Int, tabItem: MuseumBadgetTabItemImpl ->
                tabItem.copy(theBadgetNumber = sizeInfo[index])
            }

            inventoryRoomSummaryTabListStateFlow.update { tabItems }

            selectedTabIndexToInventoryRoomSummaryList(index)
        }
    }

    private fun selectedTabIndexToInventoryRoomSummaryList(index: Int) {
        inventoryRoomSummaryListStateFlow.value = null
        Logger.getLogger(getLogTag()).log(Level.INFO, "selectedTabIndexToInventoryRoomSummaryList - index: [$index] - on Thread [${Thread.currentThread().name}]")
        val inventoryRoomSummaryList: List<InventoryRoomSummaryItemDelegate>? = inventoryRoomSummaryTabListMap[index]
        if (null != inventoryRoomSummaryList) {
            Logger.getLogger(getLogTag()).log(Level.INFO, "selectedTabIndexToInventoryRoomSummaryList - inventoryRoomSummaryTabListMap has data for index: [$index], inventoryRoomSummaryList.size: [${inventoryRoomSummaryList.size}]")
            inventoryRoomSummaryListStateFlow.update { (inventoryRoomSummaryList) }
        }
        else {
            Logger.getLogger(getLogTag()).log(Level.INFO, "selectedTabIndexToInventoryRoomSummaryList - inventoryRoomSummaryTabListMap has no data for index: [$index]")
        }
    }
}



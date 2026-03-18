

interface MuseumInventoryEntryExtDelegate {

    val cachedInventoryItemList: List<RfInventoryDelegate>

    fun updateCachedInventoryItemList(inventoryItemList: List<RfInventoryDelegate>)

    fun clearCachedInventoryItemList()

    var selectedInventory: RfInventoryDelegate?

    val inventorySelectedTabIndexStateFlow: MutableStateFlow<Int?>
    val inventoryItemListStateFlow: MutableStateFlow<List<InventoryItemDelegate>?>
    val inventoryTabListStateFlow: MutableStateFlow<List<TabItemDelegate>?>

    val inventoryEntryReloadEventStateFlow: MutableStateFlow<Boolean>

    fun clearInventoryTabListMap()

    fun reBuildInventoryTabListMap(scope: CoroutineScope, inventoryList: List<RfInventoryDelegate>, index: Int)
}



class MuseumInventoryEntryExtImpl(
    private val tabListFactory: MuseumRfidTabListFactoryDelegate
) : MuseumInventoryEntryExtDelegate {

    private val _cachedInventoryItemList: MutableList<RfInventoryDelegate> = mutableListOf()

    override val cachedInventoryItemList: List<RfInventoryDelegate> = _cachedInventoryItemList

    override fun updateCachedInventoryItemList(inventoryItemList: List<RfInventoryDelegate>) {
        _cachedInventoryItemList.apply {
            clear()
            addAll(inventoryItemList)
        }
    }

    override fun clearCachedInventoryItemList() {  _cachedInventoryItemList.clear() }

    override var selectedInventory: RfInventoryDelegate? = null

    private val inventoryTabListMapLock = ByteArray(0)
    private val inventoryTabListMap: MutableMap<Int, List<InventoryItemDelegate>> = mutableMapOf()

    override val inventorySelectedTabIndexStateFlow: MutableStateFlow<Int?> = MutableStateFlow<Int?>(null)
    override val inventoryItemListStateFlow: MutableStateFlow<List<InventoryItemDelegate>?> = MutableStateFlow<List<InventoryItemDelegate>?>(null)
    override val inventoryTabListStateFlow: MutableStateFlow<List<TabItemDelegate>?> = MutableStateFlow<List<TabItemDelegate>?>(null)

    private var reBuildInventoryTabListMapJob: Job? = null

    override val inventoryEntryReloadEventStateFlow: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)


    override fun clearInventoryTabListMap() {
        synchronized(inventoryTabListMapLock) {
            inventoryTabListMap.clear()
        }
    }

    override fun reBuildInventoryTabListMap(
        scope: CoroutineScope, inventoryList: List<RfInventoryDelegate>, index: Int
    ) {
        if (inventoryTabListMap.isNotEmpty()) {
            Logger.getLogger(getLogTag()).log(Level.INFO, "reBuildInventoryTabListMap() - inventoryDetailTabListMap is present!")
            selectedTabIndexToInventoryList(index)
            return
        }

        reBuildInventoryTabListMapJob?.cancelIfNeeded()
        reBuildInventoryTabListMapJob = scope.launch(Dispatchers.Default) {
            Logger.getLogger(getLogTag()).log(Level.INFO, "reBuildInventoryTabListMap() - inventoryList.size: [${inventoryList.size}] - on Thread [${Thread.currentThread().name}]")

            val tabListMap = mutableMapOf<Int, List<InventoryItemDelegate>>()

            val tabIndex2List: List<InventoryItemDelegate> = inventoryList
                .filter { inventoryItem: RfInventoryDelegate ->
                    inventoryItem.theStatus == "2"  // revision by elite_lin - 2025/12/11
                }
                .map { inventoryItem: RfInventoryDelegate -> InventoryItemImpl(theDataObject = inventoryItem) }

            val tabIndex1List: List<InventoryItemDelegate> = inventoryList
                .filter { inventoryItem: RfInventoryDelegate ->
                    inventoryItem.theStatus == "1"   // revision by elite_lin - 2025/12/11
                }
                .map { inventoryItem: RfInventoryDelegate -> InventoryItemImpl(theDataObject = inventoryItem) }

            val tabIndex0List: List<InventoryItemDelegate> = inventoryList
                .filter { inventoryItem: RfInventoryDelegate ->
                    (! ( (inventoryItem.theStatus == "1") || (inventoryItem.theStatus == "2") ) )   // revision by elite_lin - 2025/12/11
                }
                .map { inventoryItem: RfInventoryDelegate -> InventoryItemImpl(theDataObject = inventoryItem) }

            tabListMap[0] = tabIndex0List
            tabListMap[1] = tabIndex1List
            tabListMap[2] = tabIndex2List

            synchronized(inventoryTabListMapLock) {
                inventoryTabListMap.apply {
                    clear()
                    putAll(tabListMap)
                }
            }

            val sizeInfo: List<Int> = inventoryTabListMap.getSizeInfoExt()

            val tabItems: List<TabItemDelegate> = tabListFactory.theInventoryEntryTabList.mapIndexed { index: Int, tabItem: MuseumBadgetTabItemImpl ->
                tabItem.copy(theBadgetNumber = sizeInfo[index])
            }

            inventoryTabListStateFlow.update { tabItems }

            selectedTabIndexToInventoryList(index)
        }
    }

    private fun selectedTabIndexToInventoryList(index: Int) {
        Logger.getLogger(getLogTag()).log(Level.INFO, "selectedTabIndexToInventoryList() - index: [$index] - on Thread [${Thread.currentThread().name}]")

        inventoryItemListStateFlow.value = null
        val inventoryList: List<InventoryItemDelegate>? = inventoryTabListMap[index]
        if (null != inventoryList) {
            Logger.getLogger(getLogTag()).log(Level.INFO, "selectedTabIndexToInventoryList() - inventoryTabListMap has data for index: [$index], inventoryList.size: [${inventoryList.size}]")
            inventoryItemListStateFlow.update { inventoryList }
        }
        else {
            Logger.getLogger(getLogTag()).log(Level.WARNING, "selectedTabIndexToInventoryList() - inventoryTabListMap has no data for index: [$index]")
        }
    }

}



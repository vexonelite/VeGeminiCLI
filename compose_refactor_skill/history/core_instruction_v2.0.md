
# Core Instruction: Museum Compose Refactor

You are a specialized Android agent. When refactoring or generating `ScreenParameter` files, you must follow these rules strictly:

### 1. No Hallucinations on Method Names
Before writing an API call, verify the method signature in `MuseumUiUtilityDelegate` and `MuseumRfGalleryApiExtDelegate`. 

### 2. Mandatory Lifecycle Collection
Never use `collectAsState()`. You must use `collectAsStateWithLifecycle()` to ensure resource efficiency and lifecycle awareness.

### 3. Factory-Correct Tab Initialization
When initializing `tabItems`, you must use the factory property that matches the specific screen context as defined in `WtcRfidTabListFactoryImpl`:
- Inventory Entry -> `theInventoryEntryTabList`.
- Inventory Room Summary -> `theInventoryRoomSummaryTabList`.
- Inventory Detail -> `theInventoryDetailTabList`.
- Gallery Apply Detail -> `theGalleryApplyDetailTabList`.
Always use the `.apply { addAll(...) }` pattern for initialization.

### 4. Custom State Utility Enforcement
Scan for and prioritize custom state wrappers. For integer states initialized from the ViewModel, use:
`var selectedTabIndex by rememberIntValueFrom { apiViewModel.someStateFlow.value }`


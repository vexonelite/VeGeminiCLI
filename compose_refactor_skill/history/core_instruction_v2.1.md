
# Core Instruction: Museum Compose Refactor (v2.1)

You are a specialized Android agent. You operate based on a "Work Order" system. 

### 0. Work Order Protocol (CRITICAL)
- **Primary Directive**: You must first read `current_task.md` to identify the specific Goal, Input files, and Target output for the current session.
- **Priority**: Instructions in `current_task.md` override general patterns if a specific conflict exists for a unique task.
- **Context Awareness**: Use the provided `domain_context/` and `templates/` to fulfill the Work Order while adhering to the following rules.

### 1. No Hallucinations on Method Names
Before writing an API call, verify the method signature in `MuseumUiUtilityDelegate` and `MuseumRfGalleryApiExtDelegate`. 

### 2. Mandatory Lifecycle Collection
Never use `collectAsState()`. You must use `collectAsStateWithLifecycle()`.

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

### 5. Architectural Integrity
- **Delegate-Only Access**: No `ViewModel` classes allowed in `compose_ui` module. Use `MuseumApiModelDelegate`.
- **Standard Pattern**: Every screen must have `remember[Name]Parameters` with a `parametersFactory`.
- **Naming**: Library uses `Museum` prefix; App uses `Wtc` prefix.

### 6. Design System Enforcement (NEW)
- **Component Priority**: You MUST prioritize using components defined in `./domain_context/compose_core/` over standard Material3/Material components.
- **Specific Mappings**:
    - For Dialogs: Use definitions in `dialog_definitions.kt`.
    - For Inputs: Use definitions in `text_field_definitions.kt`.
    - For Lists: Use `list_data_definitions.kt` to handle states (Loading, Available, etc.).
- **Style Consistency**: Colors and shapes MUST be derived from `color_definitions.kt` and `shape_definitions.kt`. Do not hardcode Hex colors.



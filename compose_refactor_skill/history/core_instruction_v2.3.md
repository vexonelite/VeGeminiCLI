
# Core Instruction Museum Compose Refactor (v2.3)

You are a specialized Android agent. You operate based on a Work Order system. 

### 0. Work Order Protocol (CRITICAL)
- Primary Directive You must first read `current_task.md` to identify the specific Goal, Input files, and Target output for the current session.
- Priority Instructions in `current_task.md` override general patterns if a specific conflict exists for a unique task.
- Context Awareness Use the provided `domain_context` and `templates` to fulfill the Work Order while adhering to the following rules.

### 1. Design System & Component Enforcement (NEW)
- Core Library Priority You MUST prioritize using components in `.domain_contextcompose_core` over standard Material3 components.
- Component Mapping
    - Buttons Use `built_in_button_row_definitions.kt`.
    - Dialogs Use `dialog_definitions.kt`.
    - Inputs Use `text_field_definitions.kt` (always use project-standard text wrappers).
    - Lists Use `list_data_definitions.kt` to handle `ListDataState` (LoadingAvailableUnavailable).
    - Layout Use `top_app_bar_definitions.kt` and `bottom_sheet_top_app_bar_list_screen.kt` for structure.
- Styling Colors and shapes MUST be derived from `color_definitions.kt` and `shape_definitions.kt`. Do not hardcode Hex values.

### 2. State Management & Lifecycle
- Wrappers Prioritize `remember_utils.kt` wrappers (e.g., `rememberIntValueFrom`, `rememberTextFieldValueFrom`).
- Lifecycle Never use `collectAsState()`. You must use `collectAsStateWithLifecycle()`.

### 3. Architectural Integrity
- Delegate-Only Access No `ViewModel` classes in `compose_ui` module. Use `MuseumApiModelDelegate`.
- Pattern Every screen must have `remember[Name]Parameters` with a `parametersFactory`.
- Naming Library uses `Museum` prefix; App uses `Wtc` prefix.

### 4. Technical Nuances
- **Pattern Alignment**: When refactoring, you MUST compare the source file in `srcProj` with its designated blueprint in `code_sample`. Follow the transformation logic (e.g., how StateFlows are converted to Delegates) shown in the sample.
- **Tab Initialization**: Use the specific factory from `WtcRfidTabListFactoryImpl` using the `.apply { addAll(...) }` pattern.
- **Legacy Preservation**: Maintain `// <editor-fold>` comments and do not consolidate multiple file outputs.



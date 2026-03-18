
# Core Instruction: Museum Compose Refactor (v2.4)

You are a specialized Android agent operating on a "Work Order" system. Your goal is to refactor legacy screens into the Project Delegate Architecture without altering the original UI logic or feature set.

### 0. Work Order Protocol (CRITICAL)
- **Primary Directive**: Always read `current_task.md` first.
- **Source of Truth**: The file in `srcProj` defines the **Required Features and UI Layout**. The `code_sample` only defines the **Architectural Style (How to write the code)**.
- **Conflict Resolution**: If a `code_sample` has a BottomSheet but the `srcProj` file does not, **DO NOT** add a BottomSheet. Maintain the original UI structure.

### 1. Structure & Feature Integrity
- **No Feature Creep**: Do not add search bars, BottomSheets, or API calls that do not exist in the original `srcProj` file.
- **Layout Preservation**: If the original screen is a simple list with RFID sensing, the refactored version must remain a simple list with RFID sensing.
- **Parameter Mirroring**: The `[Name]ParameterDelegate` must mirror the exact states and events used in the original ViewModel. Do not add "SearchValue" or "Query" parameters unless they exist in the source.

### 2. Design System & Component Enforcement
- **Library Priority**: Prioritize components in `./domain_context/compose_core/` (e.g., `BuiltInTextField01`, `BuiltInButtonRow01`).
- **Styling**: Use `color_definitions.kt` and `shape_definitions.kt`. No hardcoded hex values.
- **ListDataState**: Use `list_data_definitions.kt` to map the original list logic to `ListDataState` (Loading/Available/None).

### 3. State Management & Lifecycle
- **Wrappers**: Use `remember_utils.kt` (e.g., `rememberIntValueFrom`, `rememberTextFieldValueFrom`).
- **Lifecycle**: Strictly use `collectAsStateWithLifecycle()`.

### 4. Architectural Integrity
- **Delegate-Only Access**: Library module (`compose_ui`) must have **ZERO** knowledge of `ViewModel` classes. Access all data via `MuseumApiModelDelegate`.
- **Naming**: Library uses `Museum` prefix; App uses `Wtc` prefix.
- **Standard Factory**: Every screen must use the `remember[Name]Parameters { parametersFactory -> ... }` pattern.

### 5. Technical Nuances
- **Pattern Alignment**: Use `code_sample` only as a guide for *syntax* and *Delegate wrapping*. 
- **Tab Initialization**: Use `WtcRfidTabListFactoryImpl` with the `.apply { addAll(...) }` pattern.
- **Legacy Preservation**: Keep all `// <editor-fold>` comments and do not consolidate outputs.


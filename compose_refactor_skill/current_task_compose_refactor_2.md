
# Current Task: Batch Refactor Queue (RFID Screens)

## 1. Environment Configuration
- **Source Directory**: `./srcProj/`
- **Library Module Target**: `./destProj/compose_ui/`
- **App Module Target**: `./destProj/app/`

## 2. Processing Queue
| Priority | Source File | Status | Target Pattern |
| :--- | :--- | :--- | :--- |
| 1 | `RackScreen.kt` | [ ] | Pattern A (Search/List) |
| 2 | `RfidTagScreen.kt` | [ ] | Pattern A (Search/List) |
| 3 | `RfidTagReportAbnormalScreen.kt` | [ ] | Pattern B (Tabs/Details) |

## 3. Reference Mapping
* **Pattern A (Search/List)**: For screens with Restful API searching.
* **Pattern A-Lite (Pure RFID List)**: 
  - **Targets**: `RackScreen.kt`, `RfidTagScreen.kt`
  - **Reference**: Use `compose_ui_GalleryQueryScreen.kt` for Delegate wrapping logic ONLY.
  - **Constraint**: **NO BottomSheet**. **NO Search fields**. Keep the UI as a simple full-screen list for RFID sensing as seen in the source.

## 4. Execution Rules
1. **Atomic Refactor**: For each source file, generate both the `Museum[Name]Screen.kt` (Library) and `Wtc[Name]Screen.kt` (App) before moving to the next item.
2. **Delegate Transformation**: Extract logic from the source ViewModel into the `MuseumApiModelDelegate` pattern.
3. **State Management**: Replace all `collectAsState()` with `collectAsStateWithLifecycle()` and use `remember_utils.kt` wrappers.


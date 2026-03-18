
# Museum Project Context Map

## 🛠 Mandatory State Management Patterns
**Rule:** You MUST prioritize these custom wrappers over standard Compose state functions to ensure consistency and proper `remember` block usage.

| Default Compose Call | Required Project Standard | Data Source / Use Case |
| :--- | :--- | :--- |
| `mutableIntStateOf(x)` | `rememberIntValueFrom { x }` | Integer states from ViewModels/Delegates |
| `mutableStateOf(TextFieldValue())` | `rememberTextFieldValueFrom(x)` | All text inputs and search fields |
| `mutableStateOf(T)` | `rememberNonNullObjectFrom { T }` | Non-null objects (e.g., specific delegates) |
| `mutableStateOf(T?)` | `rememberNullableObjectFrom { T }` | Nullable objects |
| `mutableStateListOf<T>()` | `rememberMutableStateListOf<T>()` | Lists that must survive recomposition |

---

## 🏗 Dependency Injection & Tab Factories
- **Provider:** `WtcRfidTabListFactoryImpl`.
- **Mapping:**
    - `MuseumInventoryEntryExtDelegate` -> `theInventoryEntryTabList`
    - `MuseumInventoryRoomSummaryExtDelegate` -> `theInventoryRoomSummaryTabList`
    - `MuseumInventoryDetailExtDelegate` -> `theInventoryDetailTabList`
    - `MuseumGalleryApplyDetailExtDelegate` -> `theGalleryApplyDetailTabList`

---

## 📡 API & Utility Naming
- **Interface:** `MuseumUiUtilityDelegate`.
- **Inventory Rule:** Use `buildRfInventoryQueryModel` (with `Rf` prefix).
- **Gallery Rule:** Use `buildGalleryQueryModel`.

---

## 📅 Business Logic: The "5-Year Reload" Protocol
- **Trigger:** When `reloadData` is called and `apiViewModel.cachedKeyword` is empty.
- **Logic:** - `dateEndStr` = Today (`yyyy/MM/dd`).
    - `dateStartStr` = Today minus 5 years (`Calendar.YEAR, -5`).
    - Call `uiUtility.buildRfInventoryQueryModel` with these dates.



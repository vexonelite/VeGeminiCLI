# Current Task: Refactor [LegacyFileName].kt

## 1. Source Material
* **Source File:** `.\compose_refactor\srcProj\[LegacyFileName].kt`
* **Target App Module:** `.\compose_refactor\destProj\app\`
* **Target Library Module:** `.\compose_refactor\destProj\compose_ui\`

## 2. Reference & Few-Shot Learning
**CRITICAL:** Before generating code, analyze the following examples in the `.\code_sample\` folder to understand the transformation pattern:
* **Pattern A (Search/List):** Refer to `before_GalleryQueryScreen.kt` -> `compose_ui_GalleryQueryScreen.kt`.
* **Pattern B (Tabs/Details):** Refer to `before_GalleryDetailsScreen.kt` -> `compose_ui_GalleryDetailsScreen.kt`.
* **Pattern C (Updates/RFID):** Refer to `before_GalleryApplyDetailsScreen.kt` -> `app_GalleryApplyDetailsScreen.kt`.

## 3. Objective
Refactor the source file into two Kotlin files using the logic defined in `architecture_map.md` and the structural constraints in `constraints.md`.

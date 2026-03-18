
# Architecture Map: Library vs. App Separation

## 1. Module: `compose_ui` (The Library)
**Goal:** Pure UI structure and generic event handling.
* **Filename Prefix:** `Museum...` (e.g., `MuseumGalleryQueryScreen.kt`).
* **State Management:** * Defines a `ParameterDelegate` interface.
    * Implements a `BuiltIn...Parameter01` data class as a default.
    * Creates a generic `remember...ScreenParameters` function that accepts a `parametersFactory` lambda.
* **Constraints:**
    * ❌ NO concrete ViewModels (e.g., `ApiViewModel`). Use `MuseumApiModelDelegate`.
    * ❌ NO hardcoded navigation routes. Use `MuseumUiUtilityDelegate`.
    * ❌ NO concrete RFID hardware classes. Use `RfidDeviceConfigDelegate`.

## 2. Module: `app` (The Implementation)
**Goal:** Project-specific wiring and dependency injection.
* **Filename Prefix:** `Wtc...` (e.g., `WtcGalleryQueryScreen.kt`).
* **Logic Wiring:**
    * Obtains concrete ViewModels via Composable parameters.
    * Calls the Library's `remember...ScreenParameters` function.
    * Passes a `parametersFactory` that instantiates the `BuiltIn...` class with project-specific data.
* **Responsibilities:**
    * ✅ Converts concrete ViewModels to Delegates (e.g., `r36ViewModel.toRfidConfigExt()`).
    * ✅ Defines the specific `tag` for logging.
    * ✅ Maps Library actions to `navController` routes.

---

## 3. Transformation Logic (Before -> After)
1.  **Extract Data Class:** Move the `...Parameters` data class properties into an Interface in the Library.
2.  **Stateless UI:** Pass the Delegate into the Entry Composable instead of multiple parameters.
3.  **Hoisted State:** Wrap `LaunchedEffect` and `StateFlow` collectors inside the `remember` function in the Library, but trigger them using generic Delegates.


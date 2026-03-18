
# Current Task: Barcode System Migration (Screen A1)

## 1. Goal
Generate the "實績回報 (A1)" screen following the multi-page flow described in `draft_app_requirement.md` and the visual style in `stitch_app_01.png`.

## 2. Input References
- **Logic**: `draft_app_requirement.md` (Check Digit validation: E, V, M, O, P).
- **Visuals**: `stitch_app_01.png` (Main Menu and A1-Page 1) and `stitch_app_02.jpg` (A1-Page 2/Production Data).
- **Architecture**: `architecture_map.md` (Library vs. App module separation).

## 3. Specific UI Mapping (Using Compose Core)
- **Top Bar**: Implement using `top_app_bar_definitions.kt` with a back arrow.
- **Page 1 (Basic Info)**:
    - Date field: Use `rememberTextFieldValueFrom` (Auto-populate YYYYMMDDHHmm).
    - Employee ID: TextField with "Prefix: E" validation.
    - Unit Type: Use `text_field_definitions.kt` dropdown for "Prefix: V".
- **Page 2 (Production Data)**:
    - Follow the layout in `stitch_app_02.jpg` (leftmost screen).
    - Use `built_in_button_row_definitions.kt` for "Next (Enter)" and "Save (F4)" buttons.

## 4. Logic Constraints
- **Validation**: When a barcode is scanned/entered, check the first character against the required prefix (e.g., Worker = 'E').
- **Data Persistence**: Logic must support exporting to `A1.TXT` with comma separators.
- **State**: Use `rememberNonNullObjectFrom` to hold the A1 session data.

## 5. Output Target
1. `MuseumA1ReportScreen.kt` (Library - Module: `compose_ui`)
2. `WtcA1ReportScreen.kt` (App - Module: `app`)


./compose_refactor_skill/
│
├── 📜 draft_app_requirement.md      <-- requirement document
│
├── 📜 core_instruction_v2.3.md        <-- The "System Prompt" (High-level goals)
├── 📜 constraints.md                <-- The "Guardrails" (The "Do Nots")
│
├── 📂 domain_context/               <-- Knowledge Base (Reference code)
│   ├── 📂 compose_core/
│   │   ├── bottom_sheet_top_app_bar_list_screen.kt
│   │   ├── built_in_button_row_definitions.kt
│   │   ├── color_definitions.kt
│   │   ├── dialog_definitions.kt
│   │   ├── list_data_definitions.kt
│   │   ├── remember_utils.kt
│   │   ├── shape_definitions.kt
│   │   ├── text_field_definitions.kt
│   │   ├── text_label_definitions.kt
│   │   └── top_app_bar_definitions.kt
│   │
│   ├── core_models.kt         
│   ├── museum_models.kt        
│   ├── wtc_museum_models.kt    
│   ├── museum_ui.kt
│   ├── rfid_patterns.kt
│   ├── museum_search_Items_delegates.kt
│   ├── museum_gallery_object_detail_ext.kt
│   ├── museum_gallery_apply_detail_ext.kt
│   ├── museum_inventory_entry_ext.kt
│   ├── museum_inventory_room_summary_ext.kt
│   ├── museum_inventory_detail_ext.kt
│   ├── museum_rf_gallery_api_ext.kt
│   ├── api_viewmodel.kt
│   ├── context_map_v2.md
│   └── architecture_map.md           <-- Library vs. App module rules
│
├── 📂 templates/                    <-- Structure templates
│   ├── library_screen_tmpl.kt
│   └── app_screen_tmpl.kt
│
├── 📂 spec_images/                  <-- snapshots (stitch)
│
└── 📂 code_sample/             <-- Few-shot code samples


* ``context_map.md``
  - Architectural Source of Truth: Contains the mandatory mapping for state management, API naming conventions, and specific business logic protocols.

* ``core_models.kt``
  - ``sealed class FmApiResult<T> {}``
    - extension methods of ``FmApiResult``
  - ``enum class ListDataStateType {}``
  - ``sealed interface ListDataState<T> {}``

* ``museum_models.kt``
  interfaces, classes of Restful API

* ``wtc_museum_models.kt``
data classes that implement interface defined in ``museum_models.kt``.
each class represents the "Wtc" version of Restful API

* ``museum_ui.kt``
  interfaces, classes used in the app for UI display
  e.g., ``interface GalleryItemDelegate : TextLabelWrapperDelegate<RfGalleryItemDelegate>``

* ``rfid_patterns.kt``
  RFID relevant interfaces, classes

* ``museum_search_Items_delegates.kt``
  - ``interface MuseumUiUtilityDelegate {}``
  - ``interface MuseumSearchItemsDelegate : ISearchButtonDelete {}``
  - ``data class MuseumSearchItemsImpl()``

* ``museum_gallery_object_detail_ext.kt``

* ``museum_gallery_apply_detail_ext.kt``
  - ``interface MuseumGalleryApplyDetailExtDelegate {}``
  - ``class MuseumGalleryApplyDetailExtImpl(
    private val tabListFactory: MuseumRfidTabListFactoryDelegate
) : MuseumGalleryObjectDetailExtImpl<GalleryApplyDetailItemDelegate, RfGalleryApplyDetailDelegate>(),
    MuseumGalleryApplyDetailExtDelegate
{}``

* ``museum_inventory_entry_ext.kt``
  - ``interface MuseumInventoryEntryExtDelegate {}``
  - ``class MuseumInventoryEntryExtImpl(
    private val tabListFactory: MuseumRfidTabListFactoryDelegate
) : MuseumInventoryEntryExtDelegate {}``

* ``museum_inventory_room_summary_ext.kt``
  - ``interface MuseumInventoryRoomSummaryExtDelegate {}``
  - ``class MuseumInventoryRoomSummaryExtImpl(
    private val tabListFactory: MuseumRfidTabListFactoryDelegate
) : MuseumInventoryRoomSummaryExtDelegate {}``

* ``museum_inventory_detail_ext.kt``
  - ``interface MuseumInventoryDetailExtDelegate {}``
  - ``class MuseumInventoryDetailExtImpl(
    private val tabListFactory: MuseumRfidTabListFactoryDelegate    // added by elite_lin - 2025/07/31
) : MuseumGalleryObjectDetailExtImpl<InventoryDetailItemDelegate, RfInventoryDetailDelegate>(),
    MuseumInventoryDetailExtDelegate
{}``
  - ``fun List<InventoryDetailItemDelegate>.updateItemExt(
    newInventoryDetail: InventoryDetailItemDelegate
): List<InventoryDetailItemDelegate> {}``

* ``museum_rf_gallery_api_ext.kt``
  - ``interface MuseumRfGalleryApiExtDelegate {}``
    Restful API functions 
  - ``interface MuseumRfGalleryExtDelegate {}``
  - ``interface MuseumApiModelDelegate : CoroutineScopeExtDelegate, AccountAuthExtDelegate,
    MuseumRfGalleryApiExtDelegate, MuseumRfGalleryExtDelegate,
    MuseumInventoryEntryExtDelegate,
    MuseumInventoryRoomSummaryExtDelegate,
    MuseumInventoryDetailExtDelegate,
    MuseumGalleryApplyDetailExtDelegate``

* ``api_viewmodel.kt``
  - ``class ApiViewModel : ViewModel(), MuseumApiModelDelegate,
    CoroutineScopeExtDelegate by BuiltInCoroutineScopeImpl(),
    AccountAuthExtDelegate by BuiltInAccountAuthExtImpl(
        restfulApi = FmWtcMuseumApi.create(domain = theApiDomain()),
        authUrl = theApiDomain() + FmWtcMuseumApi.ACCOUNT_AUTH,
    ),
    MuseumRfGalleryApiExtDelegate by WtcRfGalleryApiExtImpl(),
    WtcRfGalleryExtDelegate by WtcRfGalleryExtImpl(),
    MuseumInventoryEntryExtDelegate by MuseumInventoryEntryExtImpl(WtcRfidTabListFactoryImpl()),
    MuseumInventoryRoomSummaryExtDelegate by MuseumInventoryRoomSummaryExtImpl(WtcRfidTabListFactoryImpl()),
    MuseumInventoryDetailExtDelegate by MuseumInventoryDetailExtImpl(WtcRfidTabListFactoryImpl()),
    MuseumGalleryApplyDetailExtDelegate by MuseumGalleryApplyDetailExtImpl(WtcRfidTabListFactoryImpl()),
    WtcEmployeeExtDelegate by WtcEmployeeExtImpl(),
    AudioPlayerControlsDelegate by ExoPlayerControlsImpl(),
    MuseumUiUtilityDelegate
{}``
  - ``class WtcRfidTabListFactoryImpl : MuseumRfidTabListFactoryDelegate {}``

* ``compose_core/`` 
  * ``bottom_sheet_top_app_bar_list_screen.kt``
    interfaces, classes, Jetpack Composable functions that build a complex UI  
  - ``built_in_button_row_definitions.kt``
    customized button composable functions
  - ``color_definitions.kt``
      define colors
  - ``dialog_definitions.kt``
    dialog composable functions
  - ``list_data_definitions.kt``
    ``LazyColum`` / LazyRow Relevent composable functions, 
    display one of states from ``Init<T>``, ``Loading<T>``, ``Available<T>``, ``Unavailable<T>``, ``None<T>`` based on the given ``sealed interface ListDataState<T> {}``
  - ``remember_utils.kt``
    - **Mandatory State Standards:** Contains the required project wrappers for state initialization. 
    - ALWAYS check this file before using standard `mutableStateOf` variants.
  - ``shape_definitions.kt``
    shape (circle, RoundedCorner) composable functions
  - ``text_field_definitions.kt``
    customized ``TextField`` composable functions, includes Drop Down Menu
  - ``text_label_definitions.kt``
    text Label relevant composable functions (some are items for the ``LazyColumn``)
  - ``top_app_bar_definitions.kt``
    a composable function that has ``TopAppBar`` on the top.

* ``code_sample/`` 
  ``before_``, ``compose_ui_``, ``app_``  kotlin files
  e.g.,  ``before_GalleryApplyDetailsScreen.kt``
  --> ``app_GalleryApplyDetailsScreen.kt`` and ``compose_ui_GalleryApplyDetailsScreen.kt``



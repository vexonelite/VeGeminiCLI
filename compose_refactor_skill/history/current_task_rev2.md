
# Current Task: Batch Refactor Queue

## 1. Environment Configuration

* Source Directory: ``.\compose_refactor\srcProj\``
* App Module Target: ``.\compose_refactor\destProj\app\``
* Library Module Target: ``.\compose_refactor\destProj\compose_ui\``

## 2. Processing Queue (Status: In-Progress)

Priority | Source File | Status
--- | --- | --- | ---
1 | ``InventoryScreen.kt`` | [] Pending
2 | ``InventoryRoomSummaryScreen.kt`` | [] Pending
3 | ``InventoryDetailsScreen.kt`` | [] Pending

## 3. Reference Mapping (Few-Shot Training)
CRITICAL: For each item in the queue, use the corresponding blueprint from ```.\code_sample\``:

* **Pattern A (Search/List)**: Follow
  * ``before_GalleryQueryScreen.kt`` -> ``compose_ui_GalleryQueryScreen.kt``
  * ``before_GalleryApplyScreen.kt`` --> ``compose_ui_GalleryApplyScreen.kt``
* **Pattern B (Tabs/Details)**: Follow ``before_GalleryDetailsScreen.kt`` -> ``compose_ui_GalleryDetailsScreen.kt``.

* **Pattern C (Tabs/List/Updates/RFID)**: 
  The file offers 1) Tabs/List display; 2) batch status updates;: 
  * ``before_GalleryApplyDetailsScreen.kt`` -> ``app_GalleryApplyDetailsScreen.kt``.

## 4. Batch Execution Instructions
1. **Atomic Processing**: You must finish generating and writing both the Library and App files for one queue item before starting the next.

2. **Constraint Adherence**: Check every generated file against ``architecture_map.md`` and ``constraints.md`` to ensure zero ViewModel leakage in the library.

3. **Logging**: Preserve legacy ``// <editor-fold>`` comments and maintain consistent logging prefixes (Museum for library, Wtc for app).

4. **Token Limit Management**: If you reach an output limit, save your current progress to the disk, update the "Status" in this file, and wait for a command to resume the next item in the queue.



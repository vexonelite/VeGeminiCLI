
* Strict Separation: NEVER allow ``ViewModel`` (e.g., ``ApiViewModel``, ``C72RfidViewModel``, or ``R36RfidViewModel``) to appear in the ``compose_ui`` module. All logic must be accessed via ``MuseumApiModelDelegate``. 
* Remember Parameters Rule: Every screen MUST have a ``remember[Name]Parameters`` function to host event logic.
* The Parameter Factory Rule: Every library ``remember`` function MUST use a ``parametersFactory`` lambda to instantiate the state holder.
* Naming Convention:
  * Library: Use ``Museum`` prefix (e.g., ``MuseumGalleryDetailScreen``).
  * App: Use ``Wtc`` prefix (e.g., ``WtcGalleryDetailScreen``).
* Dialogue Ownership: State for dialogs (e.g., ``theStoreRoomPickerDialogState``) must be hoisted into the ``ParameterDelegate``.
* Reference Preservation: Do not remove legacy ``// <editor-fold>`` comments; preserve them in the refactored output to maintain organizational structure.
* Non-consolidate Rule: Do not consolidate outputs; if the task requires two files, you must output two distinct code blocks.

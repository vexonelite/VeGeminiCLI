

| Feature | ChainWay C72 | CipherLab R36 | BlueBird (Sled) | 
| :--- | :--- | :--- | :--- |
| Core Reader Class | ``RFIDWithUHFUART`` | ``RfidManager`` | ``Reader`` |
| Data Extraction | ``barcodeData`` | ``onDecodeComplete`` | ``msg.obj`` (Parsed via ``;``) |
| UHF Delegate | ``C72UhfTagDelegate`` | ``R36UhfTagDelegate`` | ``BlueBirdUhfTagDelegate`` |
| Barcode Delegate | ``C72BarcodeEntityDelegate`` | ``R36BarcodeEntityDelegate`` | ``BlueBirdBarcodeDelegate`` || Write EPC | ``writeData`` (Offset 2) | ``RFIDDirectWriteTagByEPC`` | ``RF_WriteTagID`` (Index 1) | 
| Event Dispatcher | ``KcEventBus`` (SharedFlow) | ``KcEventBus`` (SharedFlow) | ``SharedFlow<Int>`` (Manual) | 
| Anti-Sticky Solution | Event Bus Subscription | Event Bus Subscription | ``SharedFlow.emit()`` |



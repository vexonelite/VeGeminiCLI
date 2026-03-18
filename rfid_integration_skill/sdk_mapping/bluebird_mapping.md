

This document provides the translation bridge from the BlueBird Android SDK to the internal **WTC Framework**.

| Feature | BlueBird SDK (Java) | WTC Framework (Kotlin) | Notes |
| :--- | :--- | :--- | :--- |
| Connectivity | ``SD_Open()``, ``SD_Connect()`` |  ``createRfidInstanceIfNeeded()`` | Ensure ``SD_Wakeup()`` is called
| Power Get | ``RF_GetRadioPowerState()`` | ``getTxPower(): FmApiResult<Int>`` | Returns Int (dBm) | 
| Power Set | ``RF_SetRadioPowerState(int)`` | ``setTxPower(Int): FmApiResult<Int>`` | Typically range 5-30 | 
| Inventory | ``RF_PerformInventory()`` | ``startReadTags()`` / ``SharedFlow`` | Events via ``sdkHandler`` | 
| Tag Read | ``RF_READ(mem, pos, len, pw, sel)`` | ``readTagExt(): FmApiResult<T>`` | Bridge via ``FmApiResult`` | 
| Tag Write | ``RF_WRITE(...)`` / ``RF_WriteTagID(...)`` | ``writeTagExt(): FmApiResult<Int>`` | Use ``RF_WriteTagID`` for EPC | 


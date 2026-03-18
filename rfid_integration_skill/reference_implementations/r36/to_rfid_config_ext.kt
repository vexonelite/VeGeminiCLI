

fun R36RfidViewModel.toRfidConfigExt(): RfidDeviceConfigDelegate = RfidDeviceConfigImpl(
    uhfTxPowerStateFlow = uhfTxPowerStateFlow,
    resetUhfTxPowerStateFlow = { uhfTxPowerStateFlow.value = null },
    uhfTagReadResultStateFlow = uhfTagReadResultStateFlow,
    resetUhfTagReadResultStateFlow = { uhfTagReadResultStateFlow.value = null },
    barcodeReadResultStateFlow = barcodeReadResultStateFlow,
    resetBarcodeReadResultStateFlow = { barcodeReadResultStateFlow.value = null },
    triggerEventFlow = MutableSharedFlow(), // Placeholder as R36 might not support it directly here
    uhfWriteTagStateFlow = uhfWriteTagStateFlow,
    resetUhfWriteTagStateFlow = { uhfWriteTagStateFlow.value = null },
    compoundedWriteTagByEpc = { currentEpc, newEpc -> compoundedDirectWriteTagByEpc(currentEpc, newEpc) },
    galleryCodeToEpcHexString = { it.theWtcGalleryCodeToEpcHexStringExt() },
    parseGalleryTagNo = { uhfTag: RfidUhfTagDelegate -> uhfTag.parseEPCtoGalleryTagNoExt() },
    getBarcodeData = { barcodeEntity: BarcodeEntityDelegate -> barcodeEntity.getBarcodeData() },
    onGetTxPower = { getTxPower() },
    onSetTxPower = { setTxPower(it) },
    hasRfidInstance = { hasRfidInstance() }
)



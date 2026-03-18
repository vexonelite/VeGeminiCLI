

/**
 * theType:
 * - 0: Normal scan (Press Trigger Key to receive the data);
 * - 1: Inventory EPC;
 * - 2: Inventory ECP TID;
 * - 3: Reader tag;
 * - 5: Write tag;
 * - 6: Lock tag;
 * - 7: Kill tag;
 * - 8: Authenticate tag;
 * - 9: Untraceable tag
 *
 * theResponse:
 * - 0: RESPONSE_OPERATION_SUCCESS;
 * - 1: RESPONSE_OPERATION_FINISH;
 * - 2: RESPONSE_OPERATION_TIMEOUT_FAIL;
 * - 6: RESPONSE_PASSWORD_FAIL;
 * - 7: RESPONSE_OPERATION_FAIL;
 * - 251: DEVICE_BUSY
 */
interface R36UhfTagDelegate : RfidUhfTagDelegate {
    val theType: Int
    val theResponse: Int
    val theReadData: String
    val theRSSI: Double
    val theEPCLength: Int
    val theTidLength: Int
    val theReadDataLength: Int
}


internal data class UhfTagDelegateImpl(
    override val theType: Int,
    override val theResponse: Int,
    override val thePC: String,
    override val theEPC: String,
    override val theTid: String,
    override val theReadData: String,
    override val theRSSI: Double,
    override val theEPCLength: Int,
    override val theTidLength: Int,
    override val theReadDataLength: Int,
    override val theRssiInDouble: Double = theRSSI,    
): R36UhfTagDelegate {
    override fun toString(): String =
        "UhfTagDelegateImpl: { type: [$theType], response: [$theResponse], dataRssi: [$theRSSI], pc: [$thePC], epc: [$theEPC], epcLength: [$theEPCLength], tid: [$theTid], tidLength: [$theTidLength], readData: [$theReadData], readDataLength: [$theReadDataLength]"

}


internal fun Intent.buildUhfTagDelegate(): R36UhfTagDelegate? =
    try {
        val type = this.getIntExtra(GeneralString.EXTRA_DATA_TYPE, -1)
        val response = this.getIntExtra(GeneralString.EXTRA_RESPONSE, -1)
        val dataRssi = this.getDoubleExtra(GeneralString.EXTRA_DATA_RSSI, 0.0)
        val pc = this.getStringExtra(GeneralString.EXTRA_PC) ?: ""
        val epc = this.getStringExtra(GeneralString.EXTRA_EPC) ?: ""
        val epcLength = this.getIntExtra(GeneralString.EXTRA_EPC_LENGTH, 0)
        val tid = this.getStringExtra(GeneralString.EXTRA_TID) ?: ""
        val tidLength = this.getIntExtra(GeneralString.EXTRA_TID_LENGTH, 0)
        val readData = this.getStringExtra(GeneralString.EXTRA_ReadData) ?: ""
        val readDataLength = this.getIntExtra(GeneralString.EXTRA_ReadData_LENGTH, 0)

        Logger.getLogger("R36 Intent Ktx").log(Level.INFO, "R36 Intent Ktx - buildUhfTagDelegate() - " +
                "type: [$type], response: [$response], dataRssi: [$dataRssi], pc: [$pc], epc: [$epc], epcLength: [$epcLength], tid: [$tid], tidLength: [$tidLength], readData: [$readData], readDataLength: [$readDataLength]")

        UhfTagDelegateImpl(
            theType  = type,
            theResponse = response,
            thePC = pc,
            theEPC = epc,
            theTid = tid,
            theReadData = readData,
            theRSSI = dataRssi,
            theEPCLength = epcLength,
            theTidLength = tidLength,
            theReadDataLength = readDataLength
        )
    }
    catch (cause: Throwable) {
        Logger.getLogger("Intent Ktx").log(Level.INFO, "error on buildUhfTagDelegate()", cause)
        null
    }


interface R36UhfTagListDelegate: TextLabelDelegate {
    val theUhfTag: R36UhfTagDelegate
}


internal data class UhfTagListDelegateImpl(
    override val theUhfTag: R36UhfTagDelegate,
    override val theCellType: Int,
    override val theIdentifier: String = theUhfTag.theEPC,
    override val theDescription: String = theUhfTag.theEPC + "_" + theUhfTag.theRSSI,
    override val theAction: String = theDescription
): R36UhfTagListDelegate



interface R36BarcodeEntityDelegate : BarcodeEntityDelegate


fun R36BarcodeEntityDelegate.parseRawBarcodeDataExt(): Triple<String, String, String> =
    if (theRawBarcodeData.isEmpty()) { Triple<String, String, String>("", "", "") }     
    else {
        val barcodeDataSplitList: List<String> = theRawBarcodeData.trim()   
            //.replace(" ", "-")
            .split(":".toRegex())
            .dropLastWhile { it.isEmpty() }
        val first = if (barcodeDataSplitList.isNotEmpty()) { barcodeDataSplitList[0] } else { "" }
        val second = if (barcodeDataSplitList.size >= 2) { barcodeDataSplitList[1] } else { "" }
        val third = if (barcodeDataSplitList.size >= 3) { barcodeDataSplitList[2] } else { "" }
        Triple<String, String, String>(first, second, third)
    }


internal data class BarcodeEntityDelegateImpl (
    override val theRawBarcodeData: String,     
): R36BarcodeEntityDelegate {

    override fun getBarcodeData(): String {
        val (type, barcode, number) = this.parseRawBarcodeDataExt()
        return barcode
    }

}


data class UhfReaderOpenResult(
    val serviceVersion: String = "",
    val apiVersion: String = "",
)


data class BarcodeDecoderOpenResult(
    val serviceVersion: String = "",
)


interface DeviceInfoDelegate {
    val theSerialNumber: String
    val theRegion: String
    val theKernelVersion: String
    val theUserVersion: String
    val theRFIDModuleVersion: String
}


internal data class DeviceInfoImpl(
    override val theSerialNumber: String = "",
    override val theRegion: String = "",
    override val theKernelVersion: String = "",
    override val theUserVersion: String = "",
    override val theRFIDModuleVersion: String = ""
) : DeviceInfoDelegate



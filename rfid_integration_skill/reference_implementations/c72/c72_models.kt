

interface C72UhfTagDelegate : RfidUhfTagDelegate {
    val theEpcBytes: ByteArray
    val theUser: String
    val theRSSI: String
    val theAnt: String
    val theReserved: String
    val theRemain: Int
    val theIndex: Int
    val theCount: Int
}


internal class UhfTagDelegateImpl(
    override val theEpcBytes: ByteArray,
    override val thePC: String,
    override val theEPC: String,
    override val theTid: String,
    override val theUser: String,
    override val theRSSI: String,
    override val theAnt: String,
    override val theReserved: String,
    override val theRemain: Int,
    override val theIndex: Int,
    override val theCount: Int,
    override val theRssiInDouble: Double = theRSSI.parseDoubleSafely() ?: 0.0,  
): C72UhfTagDelegate


fun UHFTAGInfo.buildUhfTagDelegate(): C72UhfTagDelegate =
    UhfTagDelegateImpl(
        this.epcBytes ?: ByteArray(0),
        this.pc ?: "",
        this.epc ?: "",
        this.tid ?: "",
        this.user ?: "",
        this.rssi ?: "",
        this.ant ?: "",
        this.reserved ?: "",
        this.remain,
        this.index,
        this.count
    )


interface C72UhfTagListDelegate: TextLabelDelegate {
    val theUhfTag: C72UhfTagDelegate
}


internal data class UhfTagListDelegateImpl(
    override val theUhfTag: C72UhfTagDelegate,
    override val theCellType: Int,
    override val theIdentifier: String = theUhfTag.theEPC,
    override val theDescription: String = theUhfTag.theEPC + "_" + theUhfTag.theRSSI,
    override val theAction: String = theDescription
): C72UhfTagListDelegate


interface C72BarcodeEntityDelegate : BarcodeEntityDelegate {
    val theResultCode: Int
    val theBarcodeSymbology: Int
    val theBarcodeName: String
    val theBarcodeBytesData: ByteArray
    val theDecodeTime: Int
    val thePrefix: ByteArray
    val theAimId: String
    val theErrorCode: Int
}


fun C72BarcodeEntityDelegate.showResultCode(): String =
    when(theResultCode) {
        BarcodeStatusCodes.DECODE_TIMEOUT -> { "Timeout" }
        BarcodeStatusCodes.DECODE_SUCCESS -> { "Success" }
        BarcodeStatusCodes.DECODE_CANCEL -> { "Cancel" }
        BarcodeStatusCodes.DECODE_FAILURE -> { "Failure" }
        BarcodeStatusCodes.DECODE_ENGINE_ERROR -> { "Engine Error" }
        else -> { "Unknown" }
    }


internal class BarcodeEntityDelegateImpl (
    override val theResultCode: Int,
    override val theBarcodeSymbology: Int = -1,
    override val theBarcodeName: String,
    override val theBarcodeBytesData: ByteArray,
    override val theRawBarcodeData: String,     
    override val theDecodeTime: Int,
    override val thePrefix: ByteArray,
    override val theAimId: String,
    override val theErrorCode: Int,
): C72BarcodeEntityDelegate {

    override fun getBarcodeData(): String {
        return theRawBarcodeData
    }

}


fun BarcodeEntity.buildBarcodeEntityDelegate(): C72BarcodeEntityDelegate =
    BarcodeEntityDelegateImpl(
        this.resultCode,
        this.barcodeSymbology,
        this.barcodeName ?: "",
        this.barcodeBytesData ?: ByteArray(0),
        this.barcodeData ?: "",
        this.decodeTime,
        this.prefix ?: ByteArray(0),
        this.aimId ?: "",
        this.errCode,
    )


data class UhfReaderOpenResult(
    val version: String?,
    val error: FmRuntimeException?
)


data class BarcodeDecoderOpenResult(
    val result: Int?,
    val error: FmRuntimeException?
)


interface UhfLocationResultDelegate {
    val theLocationValue: Int
    val theFlag: Boolean
}


internal data class UhfLocationResultImpl(
    override val theLocationValue: Int,
    override val theFlag: Boolean,
) : UhfLocationResultDelegate {
    override fun toString(): String {
        return "UhfLocationResultImpl { locationValue: [$theLocationValue], flag: [$theFlag] }"
    }
}





@Throws(FmRuntimeException::class)
fun RFIDWithUHFUART.initExt(): String =
    try {
        this.init()
        Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.INFO, "RFIDWithUHFUART.initExt() - On Thread: [" + Thread.currentThread().name + "]")
        this.version
    }
    catch (cause: Throwable) {
        Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.SEVERE, "error on RFIDWithUHFUART.initExt() - On Thread: [" + Thread.currentThread().name + "]", cause)
        throw FmRuntimeException(cause, C72ErrorCodes.INITIALIZATION_FAILURE)
    }


fun RFIDWithUHFUART.startInventoryTagExt(): FmApiResult<Int> =
    try {
        val result: Boolean = this.startInventoryTag()
        if (result) {
            FmApiResult.Success(1)
        }
        else {
            Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.WARNING, "startInventoryTagExt() - RFIDWithUHFUART.startInventoryTag() returns false!!")
            FmApiResult.Error(FmRuntimeException("FIDWithUHFUART. startInventoryTagExt() - RFIDWithUHFUART.startInventoryTag() returns false!!", C72ErrorCodes.START_INVENTORY_ERROR))
        }
    }
    catch (cause: Throwable) {
        Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.SEVERE, "startInventoryTagExt() - error on RFIDWithUHFUART.startInventoryTag()", cause)
        FmApiResult.Error(FmRuntimeException("RFIDWithUHFUART. startInventoryTagExt() - error on RFIDWithUHFUART.startInventoryTag()!!", cause, C72ErrorCodes.START_INVENTORY_ERROR))
    }


fun RFIDWithUHFUART.stopInventoryExt(): FmApiResult<Int> =
    try {
        val result: Boolean = this.stopInventory()
        if (result) {
            FmApiResult.Success(1)
        }
        else {
            Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.WARNING, "stopInventoryExt() - RFIDWithUHFUART.stopInventory() returns false!!")
            FmApiResult.Error(FmRuntimeException("RFIDWithUHFUART.stopInventoryExt() - RFIDWithUHFUART.stopInventory() returns false!!", C72ErrorCodes.STOP_INVENTORY_ERROR))
        }
    }
    catch (cause: Throwable) {
        Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.SEVERE, "stopInventoryExt() - error on RFIDWithUHFUART.stopInventory()", cause)
        FmApiResult.Error(FmRuntimeException("RFIDWithUHFUART.stopInventoryExt() - error on RFIDWithUHFUART.stopInventory()!!", cause, C72ErrorCodes.STOP_INVENTORY_ERROR))
    }


fun RFIDWithUHFUART.startLocationExt(
    context: Context,
    epc: String,
    locationCallback: IUHFLocationCallback,
    var3: Int = IUHF.Bank_EPC,
    var4: Int = 32
): FmApiResult<Int> =
    try {
        val result: Boolean = this.startLocation(context, epc, var3, var4, locationCallback)
        if (result) {
            FmApiResult.Success(1)
        }
        else {
            Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.WARNING, "startLocationExt() - RFIDWithUHFUART.startLocation() returns false!!")
            FmApiResult.Error(FmRuntimeException("RFIDWithUHFUART.startLocationExt() - RFIDWithUHFUART.startLocation() returns false!!", C72ErrorCodes.START_LOCATION_ERROR))
        }
    }
    catch (cause: Throwable) {
        Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.SEVERE, "startLocationExt() - error on RFIDWithUHFUART.startLocation()", cause)
        FmApiResult.Error(FmRuntimeException("RFIDWithUHFUART.startLocationExt() - error on RFIDWithUHFUART.startLocation()!!", cause, C72ErrorCodes.START_LOCATION_ERROR))
    }


fun RFIDWithUHFUART.stopLocationExt(): FmApiResult<Int> =
    try {
        val result: Boolean = this.stopLocation()
        if (result) {
            FmApiResult.Success(1)
        }
        else {
            Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.WARNING, "stopLocationExt() - RFIDWithUHFUART.stopLocation() returns false!!")
            FmApiResult.Error(FmRuntimeException("RFIDWithUHFUART.stopLocationExt() - RFIDWithUHFUART.stopLocation() returns false!!", C72ErrorCodes.STOP_LOCATION_ERROR))
        }
    }
    catch (cause: Throwable) {
        Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.SEVERE, "stopLocationExt() - error on RFIDWithUHFUART.stopLocation()", cause)
        FmApiResult.Error(FmRuntimeException("RFIDWithUHFUART.stopLocationExt() - error on RFIDWithUHFUART.stopLocation()!!", cause, C72ErrorCodes.STOP_LOCATION_ERROR))
    }


fun RFIDWithUHFUART.setTxPowerExt(value: Int): FmApiResult<Int> =
    try {
        val result: Boolean = this.setPower(value)
        if (result) {
            FmApiResult.Success(1)
        }
        else {
            Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.WARNING, "setTxPowerExt() - RFIDWithUHFUART.setPower() returns false!!")
            FmApiResult.Error(FmRuntimeException("RFIDWithUHFUART.setTxPowerExt() - RFIDWithUHFUART.setPower() returns false!!", C72ErrorCodes.SET_TX_POWER_ERROR))
        }
    }
    catch (cause: Throwable) {
        Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.SEVERE, "setTxPowerExt() - error on RFIDWithUHFUART.setPower()", cause)
        FmApiResult.Error(FmRuntimeException("RFIDWithUHFUART.setTxPowerExt() - error on RFIDWithUHFUART.setPower()!!", cause, C72ErrorCodes.SET_TX_POWER_ERROR))
    }


fun RFIDWithUHFUART.getTxPowerExt(): FmApiResult<Int> =
    try {
        val result: Int = this.getPower()
        if (result != -1) {
            FmApiResult.Success(result)
        }
        else {
            Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.WARNING, "getTxPowerExt() - RFIDWithUHFUART.getPower() returns -1!!")
            FmApiResult.Error(FmRuntimeException("RFIDWithUHFUART.getTxPowerExt() - RFIDWithUHFUART.getPower() returns -1!!", C72ErrorCodes.GET_TX_POWER_ERROR))
        }
    }
    catch (cause: Throwable) {
        Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.SEVERE, "getTxPowerExt() - error on RFIDWithUHFUART.getPower()", cause)
        FmApiResult.Error(FmRuntimeException("RFIDWithUHFUART.getTxPowerExt() - error on RFIDWithUHFUART.getPower()!!", cause, C72ErrorCodes.GET_TX_POWER_ERROR))
    }


/**
 * If The UhfBank.EPC is "EPC", the [startIndex] (offset) would be 2!!
 */
fun RFIDWithUHFUART.writeDataWithIn32BytesExt(
    dataInHexFormat: String,
    pwdInHexFormat: String = DEFAULT_UHF_WRITE_PWD,
    bank: UhfBank = UhfBank.EPC,
    startIndex: Int = 2,
): FmApiResult<Int> {
    Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.INFO, "writeDataWithIn32BytesExt() - dataInHexFormat: [$dataInHexFormat], pwdInHexFormat: [$pwdInHexFormat], bank: [$bank], startIndex: [$startIndex]")

    if (dataInHexFormat.length % 4 != 0) {
        Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.WARNING, "writeDataWithIn32BytesExt() - dataInHexFormat.length % 4 != 0!!")
        return FmApiResult.Error(
            FmRuntimeException("RFIDWithUHFUART.writeDataWithIn32BytesExt(): [dataInHexFormat.length % 4 != 0]!!", BaseErrorCodes.ILLEGAL_ARGUMENT_ERROR)
        )
    }

    if (!dataInHexFormat.isHexFormat()) {
        Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.WARNING, "writeDataWithIn32BytesExt() - dataInHexFormat is not Hex Format!!")
        return FmApiResult.Error(
            FmRuntimeException("RFIDWithUHFUART.writeDataWithIn32BytesExt(): [dataInHexFormat is not Hex Format]!!", BaseErrorCodes.ILLEGAL_ARGUMENT_ERROR)
        )
    }

    if (!pwdInHexFormat.isHexFormat()) {
        Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.WARNING, "writeDataWithIn32BytesExt() - pwdInHexFormat is not Hex Format!!")
        return FmApiResult.Error(
            FmRuntimeException("RFIDWithUHFUART.writeDataWithIn32BytesExt(): [pwdInHexFormat is not Hex Format]!!", BaseErrorCodes.ILLEGAL_ARGUMENT_ERROR)
        )
    }

    val dataLengthInWords = dataInHexFormat.length / 4

    return try {
        val result: Boolean = this.writeData(
            pwdInHexFormat, bank.toInt(), startIndex, dataLengthInWords, dataInHexFormat
        )
        if (result) {
            FmApiResult.Success(1)
        }
        else {
            Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.WARNING, "writeDataWithIn32BytesExt() - RFIDWithUHFUART.writeData() returns false!!")
            FmApiResult.Error(FmRuntimeException("RFIDWithUHFUART.writeDataWithIn32BytesExt - RFIDWithUHFUART.writeData() returns false!!", C72ErrorCodes.WRITE_TAG_ERROR))
        }
    }
    catch (cause: Throwable) {
        Logger.getLogger("RFIDWithUHFUART Ktx").log(Level.SEVERE, "writeDataWithIn32BytesExt() - error on RFIDWithUHFUART.writeData()", cause)
        FmApiResult.Error(FmRuntimeException("RFIDWithUHFUART.writeDataWithIn32BytesExt() - error on RFIDWithUHFUART.writeData()!!", cause, C72ErrorCodes.WRITE_TAG_ERROR))
    }
}



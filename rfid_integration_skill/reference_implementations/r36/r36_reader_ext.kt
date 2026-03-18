

internal fun RfidManager.getDeviceInfoExt(): FmApiResult<DeviceInfoDelegate> =
    try {
        val info: DeviceInfo = this.GetDeviceInfo()
        val lastErrorMessage: String = this.GetLastError()
        val deviceInfoDelegate = DeviceInfoImpl(
            theSerialNumber = info.SerialNumber,
            theRegion = info.Region,
            theKernelVersion = info.KernelVersion,
            theUserVersion = info.UserVersion,
            theRFIDModuleVersion = info.RFIDModuleVersion)
        FmApiResult.Success(deviceInfoDelegate)

    }
    catch (cause: Throwable) {
        Logger.getLogger("RfidManager Ktx").log(Level.SEVERE, "error on getDeviceInfoExt():", cause)
        FmApiResult.Error(FmRuntimeException(cause, R36ErrorCodes.OPERATION_GET_ERROR))
    }


internal fun RfidManager.setTxPowerExt(value: Int): FmApiResult<Int> =
    try {
        val result = this.SetTxPower(value)
        if (result != ClResult.S_OK.ordinal) {
            val lastErrorMessage: String = this.GetLastError()
            Logger.getLogger("RfidManager Ktx").log(Level.WARNING, "Fail to setTxPowerExt(): [$lastErrorMessage]")
            FmApiResult.Error(FmRuntimeException("Fail to setTxPowerExt(): [$lastErrorMessage]", R36ErrorCodes.SET_TX_POWER_ERROR))
        }
        else {
            Logger.getLogger("RfidManager Ktx").log(Level.INFO, "setTxPowerExt() - done!")
            FmApiResult.Success(1)
        }
    }
    catch (cause: Throwable) {
        Logger.getLogger("RfidManager Ktx").log(Level.SEVERE, "error on setTxPowerExt():", cause)
        FmApiResult.Error(FmRuntimeException("error on setTxPowerExt()", cause, R36ErrorCodes.SET_TX_POWER_ERROR))
    }


internal fun RfidManager.getTxPowerExt(): FmApiResult<Int> =
    try {
        val result: Int = this.GetTxPower()
        if (result == -1) {
            val lastErrorMessage: String = this.GetLastError()
            Logger.getLogger("RfidManager Ktx").log(Level.WARNING, "Fail to getTxPowerExt(): [$lastErrorMessage]")
            FmApiResult.Error(FmRuntimeException("Fail to getTxPowerExt(): [$lastErrorMessage]", R36ErrorCodes.GET_TX_POWER_ERROR))
        }
        else {
            Logger.getLogger("RfidManager Ktx").log(Level.INFO, "getTxPowerExt() - [$result]!")
            FmApiResult.Success(result)
        }
    }
    catch (cause: Throwable) {
        Logger.getLogger("RfidManager Ktx").log(Level.SEVERE, "error on getTxPowerExt():", cause)
        FmApiResult.Error(FmRuntimeException("error on getTxPowerExt()", cause, R36ErrorCodes.GET_TX_POWER_ERROR))
    }


internal fun RfidManager.directWriteTagByEpcExt(
    currentEpcInHexFormat: String,
    newEpcInHexFormat: String,
    pwdInHexFormat: String? = null,
    //epcByteArray: ByteArray = byteArrayOf(),
    //dataByteArray: ByteArray = byteArrayOf(),
    //pwdByteArray: ByteArray? = null,
    memoryBank: RFIDMemoryBank = RFIDMemoryBank.EPC,
    startIndex: Int = 2 * 2,
    retry: Int = 3,
): FmApiResult<DeviceResponse> {
    if (currentEpcInHexFormat.length % 4 != 0) {
        Logger.getLogger("RfidManager Ktx").log(Level.WARNING, "directWriteTagByEpcExt() - currentEpcInHexFormat.length % 4 != 0!!")
        return FmApiResult.Error(
            FmRuntimeException("RfidManager.directWriteTagByEpcExt(): [currentEpcInHexFormat.length % 4 != 0]!!", BaseErrorCodes.ILLEGAL_ARGUMENT_ERROR)
        )
    }

    if (!currentEpcInHexFormat.isHexFormat()) {
        Logger.getLogger("RfidManager Ktx").log(Level.WARNING, "directWriteTagByEpcExt() - currentEpcInHexFormat is not Hex Format!!")
        return FmApiResult.Error(
            FmRuntimeException("RfidManager.directWriteTagByEpcExt(): [currentEpcInHexFormat is not Hex Format]!!", BaseErrorCodes.ILLEGAL_ARGUMENT_ERROR)
        )
    }

    if (newEpcInHexFormat.length % 4 != 0) {
        Logger.getLogger("RfidManager Ktx").log(Level.WARNING, "directWriteTagByEpcExt() - newEpcInHexFormat.length % 4 != 0!!")
        return FmApiResult.Error(
            FmRuntimeException("RfidManager.directWriteTagByEpcExt(): [newEpcInHexFormat.length % 4 != 0]!!", BaseErrorCodes.ILLEGAL_ARGUMENT_ERROR)
        )
    }

    if (!newEpcInHexFormat.isHexFormat()) {
        Logger.getLogger("RfidManager Ktx").log(Level.WARNING, "directWriteTagByEpcExt() - newEpcInHexFormat is not Hex Format!!")
        return FmApiResult.Error(
            FmRuntimeException("RfidManager.directWriteTagByEpcExt(): [newEpcInHexFormat is not Hex Format]!!", BaseErrorCodes.ILLEGAL_ARGUMENT_ERROR)
        )
    }

    if (null != pwdInHexFormat) {
        if (!pwdInHexFormat.isHexFormat()) {
            Logger.getLogger("RfidManager Ktx").log(Level.WARNING, "directWriteTagByEpcExt() - pwdInHexFormat is not Hex Format!!")
            return FmApiResult.Error(
                FmRuntimeException("RfidManager.directWriteTagByEpcExt(): [pwdInHexFormat is not Hex Format]!!", BaseErrorCodes.ILLEGAL_ARGUMENT_ERROR)
            )
        }
    }

    val epcByteArray = currentEpcInHexFormat.r36StringToByteArrayExt()
    val dataByteArray = newEpcInHexFormat.r36StringToByteArrayExt()
    val pwdByteArray = pwdInHexFormat?.r36StringToByteArrayExt()

    return try {
        val result: DeviceResponse? = this.RFIDDirectWriteTagByEPC(pwdByteArray, epcByteArray, memoryBank, startIndex, retry, dataByteArray)
        if (null == result) {
            val lastErrorMessage: String = this.GetLastError()
            Logger.getLogger("RfidManager Ktx").log(Level.WARNING, "RfidManager Ktx - Fail to RFIDDirectWriteTagByEPC(): [$lastErrorMessage]")
            FmApiResult.Error(FmRuntimeException("RfidManager Ktx - Fail to RFIDDirectWriteTagByEPC(): [$lastErrorMessage]", R36ErrorCodes.WRITE_TAG_ERROR))
        }
        else {
            when(result) {
                DeviceResponse.OperationSuccess -> {
                    Logger.getLogger("RfidManager Ktx").log(Level.INFO, "RfidManager Ktx - RFIDDirectWriteTagByEPC(): [OperationSuccess]")
                }
                DeviceResponse.OperationFail -> {
                    Logger.getLogger("RfidManager Ktx").log(Level.WARNING, "RfidManager Ktx - RFIDDirectWriteTagByEPC(): [OperationFail]")

                }
                DeviceResponse.OperationFinish -> {
                    Logger.getLogger("RfidManager Ktx").log(Level.INFO, "RfidManager Ktx - RFIDDirectWriteTagByEPC(): [OperationFinish]")

                }
                DeviceResponse.DeviceTimeOut -> {
                    Logger.getLogger("RfidManager Ktx").log(Level.WARNING, "RfidManager Ktx - RFIDDirectWriteTagByEPC(): [DeviceTimeOut]")

                }
                DeviceResponse.DeviceBusy -> {
                    Logger.getLogger("RfidManager Ktx").log(Level.WARNING, "RfidManager Ktx - RFIDDirectWriteTagByEPC(): [DeviceBusy]")


                }
                DeviceResponse.TagLock -> {
                    Logger.getLogger("RfidManager Ktx").log(Level.WARNING, "RfidManager Ktx - RFIDDirectWriteTagByEPC(): [TagLock]")

                }
            }
            FmApiResult.Success(result)
        }
    }
    catch (cause: Throwable) {
        Logger.getLogger("RfidManager Ktx").log(Level.SEVERE, "RfidManager Ktx - error on RFIDDirectWriteTagByEPC():", cause)
        FmApiResult.Error(FmRuntimeException("RfidManager Ktx - error on RFIDDirectWriteTagByEPC()", cause, R36ErrorCodes.WRITE_TAG_ERROR))
    }
}



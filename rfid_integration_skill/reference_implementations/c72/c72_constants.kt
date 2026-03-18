

object C72ErrorCodes {
    const val CREATION_FAILURE = "79999"
    const val INITIALIZATION_FAILURE = "79998"
    const val DECODER_OPEN_FAILURE = "79997"
    const val GET_TX_POWER_ERROR = "79996"
    const val SET_TX_POWER_ERROR = "79995"
    const val START_INVENTORY_ERROR = "79994"
    const val STOP_INVENTORY_ERROR = "79993"
    const val START_LOCATION_ERROR = "79992"
    const val STOP_LOCATION_ERROR = "79991"
    const val WRITE_TAG_ERROR = "79990"
}


object BarcodeStatusCodes {
    const val DECODE_TIMEOUT = 0
    const val DECODE_SUCCESS = 1
    const val DECODE_CANCEL = -1
    const val DECODE_FAILURE = -2
    const val DECODE_ENGINE_ERROR = -3
}


object C72KeyCodes {
    const val SCAN_BUTTONS = 139
    const val BOTTOM_TRIGGER = 293
}


enum class UhfTagReadState {
    START,
    STOP,
}


const val DEFAULT_UHF_WRITE_PWD = "00000000"


enum class UhfBank : AnyToIntDelegate {
    RESERVED {
        override fun toInt(): Int = 0
    },
    EPC {
        override fun toInt(): Int = 1
    },
    TID {
        override fun toInt(): Int = 2
    },
    USER {
        override fun toInt(): Int = 3
    },
}



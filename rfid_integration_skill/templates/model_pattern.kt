

// Define specific BlueBird delegates to match the C72/R36 pattern
interface BlueBirdUhfTagDelegate : RfidUhfTagDelegate {
    val rawRssi: String
}

internal data class BlueBirdUhfTagImpl(
    override val theEPC: String,
    override val rawRssi: String,
    override val thePC: String = "",
    override val theTid: String = "",
    override val theRssiInDouble: Double = rawRssi.toDoubleOrNull() ?: 0.0
) : BlueBirdUhfTagDelegate



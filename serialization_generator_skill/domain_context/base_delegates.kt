
interface KeywordQueryModelDelegate {
    /**  */
    val theKeyword: String
}


@Serializable
abstract class BaseRestfulApiResponse (
    @SerializedName("Success") @SerialName("Success") val isSuccess: Boolean = false,
    @SerializedName("Code") @SerialName("Code") val theStatusCode: String = "",
    @SerializedName("Message") @SerialName("Message") val theMessage: String = "",
    @SerializedName("DataTime") @SerialName("DataTime") val theDataTime: String = "",
) : FmApiResponseDelegate {

    override fun hasSuccessfulResult(): Boolean = isSuccess &&
            ( (theStatusCode == FmRestfulApiConstants.CODE_SUCCESS) ||
                    (theStatusCode == FmRestfulApiConstants.CODE_SAVE_SUCCESS) ||
                    (theStatusCode == FmRestfulApiConstants.CODE_DELETE_SUCCESS))

    override fun buildFmApiErrorException(): ApiErrorException = ApiErrorException(theMessage, theStatusCode)
}



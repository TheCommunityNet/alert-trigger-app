package wiki.comnet.alerttrigger.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpRequest(
    @SerialName("otp_token") val otpToken: String,
    @SerialName("device_id") val deviceId: String,
)

@Serializable
data class VerifyOtpData(
    @SerialName("access_token") val accessToken: String,
)
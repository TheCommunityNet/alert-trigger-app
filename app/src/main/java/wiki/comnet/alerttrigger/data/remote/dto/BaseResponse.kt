package wiki.comnet.alerttrigger.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: String? = null,
)
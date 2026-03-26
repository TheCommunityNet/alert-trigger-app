package wiki.comnet.alerttrigger.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserMessageData(
    val message: String,
)

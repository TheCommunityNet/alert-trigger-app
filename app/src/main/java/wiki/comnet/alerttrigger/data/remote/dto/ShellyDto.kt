package wiki.comnet.alerttrigger.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ShellyData(
    val id: String,
    val name: String,
)
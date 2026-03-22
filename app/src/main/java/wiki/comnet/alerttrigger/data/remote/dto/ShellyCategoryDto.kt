package wiki.comnet.alerttrigger.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ShellyCategoryData(
    val value: String,
    val label: String,
)

@Serializable
data class TriggerByCategoryRequest(
    val category: String,
)

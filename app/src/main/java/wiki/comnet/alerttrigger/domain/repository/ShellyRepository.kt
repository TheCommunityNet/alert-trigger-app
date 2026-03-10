package wiki.comnet.alerttrigger.domain.repository

import wiki.comnet.alerttrigger.domain.model.Shelly

interface ShellyRepository {
    suspend fun fetchAndCacheShellies(): Result<List<Shelly>>
    suspend fun getCachedShellies(): List<Shelly>
    suspend fun toggleShellyAlert(shellyId: String): Result<String>
    suspend fun toggleAllAlerts(): Result<String>
}

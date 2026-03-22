package wiki.comnet.alerttrigger.domain.repository

import wiki.comnet.alerttrigger.domain.model.Shelly
import wiki.comnet.alerttrigger.domain.model.ShellyCategory

interface ShellyRepository {
    suspend fun fetchAndCacheShellies(): Result<List<Shelly>>
    suspend fun getCachedShellies(): List<Shelly>
    suspend fun fetchAndCacheCategories(): Result<List<ShellyCategory>>
    suspend fun getCachedCategories(): List<ShellyCategory>
    suspend fun toggleShellyAlert(shellyId: String): Result<String>
    suspend fun toggleAllAlerts(): Result<String>
    suspend fun triggerByCategory(category: String): Result<String>
}

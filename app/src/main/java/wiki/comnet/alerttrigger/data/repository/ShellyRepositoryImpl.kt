package wiki.comnet.alerttrigger.data.repository

import wiki.comnet.alerttrigger.data.local.db.ShellyDao
import wiki.comnet.alerttrigger.data.local.db.ShellyEntity
import wiki.comnet.alerttrigger.data.remote.ApiService
import wiki.comnet.alerttrigger.domain.model.Shelly
import wiki.comnet.alerttrigger.domain.repository.ShellyRepository

class ShellyRepositoryImpl(
    private val apiService: ApiService,
    private val shellyDao: ShellyDao,
) : ShellyRepository {
    override suspend fun fetchAndCacheShellies(): Result<List<Shelly>> {
        return try {
            val response = apiService.getShellies()
            if (response.success) {
                val entities = response.data?.map { ShellyEntity(it.id, it.name) } ?: emptyList()
                shellyDao.deleteAll()
                shellyDao.insertAll(entities)
                Result.success(response.data?.map { Shelly(it.id, it.name) } ?: emptyList())
            } else {
                Result.failure(Exception(response.error ?: "Failed to fetch shellies"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCachedShellies(): List<Shelly> {
        return shellyDao.getAll().map { Shelly(it.id, it.name) }
    }

    override suspend fun toggleShellyAlert(shellyId: String): Result<String> {
        return try {
            apiService.toggleShellyAlert(shellyId)
            Result.success("Success")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleAllAlerts(): Result<String> {
        return try {
            apiService.toggleAllAlerts()
            Result.success("Success")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
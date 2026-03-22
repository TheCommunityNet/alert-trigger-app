package wiki.comnet.alerttrigger.data.repository

import wiki.comnet.alerttrigger.data.local.db.ShellyCategoryDao
import wiki.comnet.alerttrigger.data.local.db.ShellyCategoryEntity
import wiki.comnet.alerttrigger.data.local.db.ShellyDao
import wiki.comnet.alerttrigger.data.local.db.ShellyEntity
import wiki.comnet.alerttrigger.data.remote.ApiService
import wiki.comnet.alerttrigger.domain.model.Shelly
import wiki.comnet.alerttrigger.domain.model.ShellyCategory
import wiki.comnet.alerttrigger.domain.repository.ShellyRepository

class ShellyRepositoryImpl(
    private val apiService: ApiService,
    private val shellyDao: ShellyDao,
    private val shellyCategoryDao: ShellyCategoryDao,
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

    override suspend fun fetchAndCacheCategories(): Result<List<ShellyCategory>> {
        return try {
            val response = apiService.getShellyCategories()
            if (response.success) {
                val entities = response.data?.map { ShellyCategoryEntity(it.value, it.label) }
                    ?: emptyList()
                shellyCategoryDao.deleteAll()
                shellyCategoryDao.insertAll(entities)
                Result.success(
                    response.data?.map { ShellyCategory(it.value, it.label) } ?: emptyList(),
                )
            } else {
                Result.failure(Exception(response.error ?: "Failed to fetch categories"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCachedCategories(): List<ShellyCategory> {
        return shellyCategoryDao.getAll().map { ShellyCategory(it.value, it.label) }
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

    override suspend fun triggerByCategory(category: String): Result<String> {
        return try {
            apiService.triggerByCategory(category)
            Result.success("Success")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
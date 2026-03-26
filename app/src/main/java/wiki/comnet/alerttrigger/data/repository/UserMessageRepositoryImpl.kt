package wiki.comnet.alerttrigger.data.repository

import wiki.comnet.alerttrigger.data.local.prefs.PrefsManager
import wiki.comnet.alerttrigger.data.remote.ApiService
import wiki.comnet.alerttrigger.domain.repository.UserMessageRepository

class UserMessageRepositoryImpl(
    private val apiService: ApiService,
    private val prefsManager: PrefsManager,
) : UserMessageRepository {

    override suspend fun getCachedMessage(): String =
        prefsManager.getUserMessage().orEmpty()

    override suspend fun fetchAndCacheMessage(): Result<String> {
        return try {
            val response = apiService.getUserMessage()
            if (response.success) {
                val message = response.data?.message.orEmpty()
                prefsManager.saveUserMessage(message)
                Result.success(message)
            } else {
                Result.failure(Exception(response.error ?: "Failed to fetch message"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

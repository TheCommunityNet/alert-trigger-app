package wiki.comnet.alerttrigger.domain.repository

interface UserMessageRepository {
    suspend fun getCachedMessage(): String
    suspend fun fetchAndCacheMessage(): Result<String>
}

package wiki.comnet.alerttrigger.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    fun isLogin(): Flow<Boolean>

    suspend fun load()

    suspend fun verifyOtp(deviceId: String, otpToken: String): Result<String>
}
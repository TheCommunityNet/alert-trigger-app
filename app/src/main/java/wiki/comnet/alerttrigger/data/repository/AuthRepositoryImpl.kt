package wiki.comnet.alerttrigger.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import wiki.comnet.alerttrigger.data.local.prefs.PrefsManager
import wiki.comnet.alerttrigger.data.remote.ApiService
import wiki.comnet.alerttrigger.data.remote.dto.VerifyOtpRequest
import wiki.comnet.alerttrigger.domain.repository.AccessTokenRepository
import wiki.comnet.alerttrigger.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val accessTokenRepository: AccessTokenRepository,
    private val prefsManager: PrefsManager,
) : AuthRepository {

    override fun isLogin(): Flow<Boolean> = flow {
        accessTokenRepository.accessToken().collect {
            emit(it != null)
        }
    }

    override suspend fun load() {
        val token = prefsManager.getToken()
        accessTokenRepository.setAccessToken(token)
    }

    override suspend fun verifyOtp(
        deviceId: String,
        otpToken: String,
    ): Result<String> {
        return try {
            val response = apiService.verifyOtp(VerifyOtpRequest(otpToken, deviceId))
            if (response.success && response.data != null) {
                val token = response.data.accessToken
                prefsManager.saveToken(token)
                accessTokenRepository.setAccessToken(token)
                Result.success(token)
            } else {
                Result.failure(Exception(response.error ?: "Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
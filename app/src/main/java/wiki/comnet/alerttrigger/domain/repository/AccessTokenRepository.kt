package wiki.comnet.alerttrigger.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface AccessTokenRepository {
    fun accessToken(): StateFlow<String?>

    fun setAccessToken(token: String?)
}
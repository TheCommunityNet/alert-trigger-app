package wiki.comnet.alerttrigger.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import wiki.comnet.alerttrigger.domain.repository.AccessTokenRepository

class AccessTokenRepositoryImpl : AccessTokenRepository {
    private val _accessToken = MutableStateFlow<String?>(null)

    override fun accessToken() = _accessToken.asStateFlow()

    override fun setAccessToken(token: String?) {
        _accessToken.value = token
    }
}
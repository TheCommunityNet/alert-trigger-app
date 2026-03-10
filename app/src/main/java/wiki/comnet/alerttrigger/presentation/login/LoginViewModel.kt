package wiki.comnet.alerttrigger.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import wiki.comnet.alerttrigger.domain.repository.AuthRepository
import wiki.comnet.alerttrigger.domain.repository.ShellyRepository

//import wiki.comnet.alert_trigger.domain.repository.ShellyRepository
//import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoginSuccess: Boolean = false,
)


class LoginViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(otpToken: String) {
        if (otpToken.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "OTP and Device ID are required")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)

            val loginResult = authRepository.verifyOtp("test123", otpToken)
            if (loginResult.isFailure) {
                _uiState.value = LoginUiState(
                    error = loginResult.exceptionOrNull()?.message ?: "Login failed"
                )
                return@launch
            }

            _uiState.value = LoginUiState(isLoginSuccess = true)
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

package wiki.comnet.alerttrigger.presentation.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import wiki.comnet.alerttrigger.common.FlowResult
import wiki.comnet.alerttrigger.domain.repository.AuthRepository

class RootViewModel(
    private val authRepository: AuthRepository,
): ViewModel() {
    private val _isSetupFinished = MutableStateFlow(false)
    private val _isLogin = MutableStateFlow(false)

    val isSetupFinished = _isSetupFinished.asStateFlow()
    val isLogin = _isLogin.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.load()
            _isSetupFinished.value = true
        }
        viewModelScope.launch {
            authRepository.isLogin().collect {
                _isLogin.value = it
            }
        }
    }
}
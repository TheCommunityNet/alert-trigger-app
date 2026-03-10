package wiki.comnet.alerttrigger.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import wiki.comnet.alerttrigger.domain.model.Shelly
import wiki.comnet.alerttrigger.domain.repository.ShellyRepository

data class HomeUiState(
    val shellies: List<Shelly> = emptyList(),
    val isLoading: Boolean = false,
    val snackbarMessage: String? = null
)


class HomeViewModel constructor(
    private val shellyRepository: ShellyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadShellies()
        viewModelScope.launch {
            shellyRepository.fetchAndCacheShellies()
            loadShellies()
        }
    }

    fun loadShellies() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val shellies = shellyRepository.getCachedShellies()
            _uiState.value = _uiState.value.copy(shellies = shellies, isLoading = false)
        }
    }

    fun toggleShelly(shellyId: String, shellyName: String) {
        viewModelScope.launch {
            val result = shellyRepository.toggleShellyAlert(shellyId)
            val message = result.fold(
                onSuccess = { action -> "$shellyName: $action" },
                onFailure = { e -> "$shellyName: ${e.message}" }
            )
            _uiState.value = _uiState.value.copy(snackbarMessage = message)
        }
    }

    fun toggleAll() {
        viewModelScope.launch {
            val result = shellyRepository.toggleAllAlerts()
            val message = result.fold(
                onSuccess = { summary -> summary },
                onFailure = { e -> e.message ?: "Toggle all failed" }
            )
            _uiState.value = _uiState.value.copy(snackbarMessage = message)
        }
    }

    fun clearSnackbar() {
        _uiState.value = _uiState.value.copy(snackbarMessage = null)
    }
}

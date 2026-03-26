package wiki.comnet.alerttrigger.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import wiki.comnet.alerttrigger.domain.model.ShellyCategory
import wiki.comnet.alerttrigger.domain.repository.ShellyRepository
import wiki.comnet.alerttrigger.domain.repository.UserMessageRepository

data class HomeUiState(
    val categories: List<ShellyCategory> = emptyList(),
    val userMessage: String = "",
    val isLoading: Boolean = false,
    val snackbarMessage: String? = null
)


class HomeViewModel constructor(
    private val shellyRepository: ShellyRepository,
    private val userMessageRepository: UserMessageRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadHomeData()
        }
    }

    fun loadHomeData() {
        viewModelScope.launch {
            val cachedMessage = userMessageRepository.getCachedMessage()
            val cachedCategories = shellyRepository.getCachedCategories()
            val hasCache =
                cachedMessage.isNotBlank() || cachedCategories.isNotEmpty()
            _uiState.value = _uiState.value.copy(
                userMessage = cachedMessage,
                categories = cachedCategories,
                isLoading = !hasCache,
            )
            shellyRepository.fetchAndCacheCategories()
            userMessageRepository.fetchAndCacheMessage().fold(
                onSuccess = { message ->
                    _uiState.value = _uiState.value.copy(userMessage = message)
                },
                onFailure = {
                    _uiState.value = _uiState.value.copy(
                        userMessage = userMessageRepository.getCachedMessage(),
                    )
                },
            )
            _uiState.value = _uiState.value.copy(
                categories = shellyRepository.getCachedCategories(),
                userMessage = userMessageRepository.getCachedMessage(),
                isLoading = false,
            )
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

    fun triggerCategory(categoryValue: String, categoryLabel: String) {
        viewModelScope.launch {
            val result = shellyRepository.triggerByCategory(categoryValue)
            val message = result.fold(
                onSuccess = { "$categoryLabel: $it" },
                onFailure = { e -> "$categoryLabel: ${e.message}" },
            )
            _uiState.value = _uiState.value.copy(snackbarMessage = message)
        }
    }

    fun clearSnackbar() {
        _uiState.value = _uiState.value.copy(snackbarMessage = null)
    }
}

package org.mireiavh.finalprojectt.infrastructure.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mireiavh.finalprojectt.domain.data.UniverseRepository
import org.mireiavh.finalprojectt.domain.model.Universe

data class UniverseUiState(
    val universes: List<Universe> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class UniverseViewModel(
    private val repository: UniverseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UniverseUiState(isLoading = true))
    val uiState: StateFlow<UniverseUiState> = _uiState

    private val _selectedUniverse = MutableStateFlow<Universe?>(null)
    val selectedUniverse: StateFlow<Universe?> = _selectedUniverse

    init {
        fetchUniverses()
    }

    fun fetchUniverses() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val universesList = repository.getUniverse()
                _uiState.update { it.copy(universes = universesList, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
            }
        }
    }

    fun selectUniverse(universe: Universe) {
        _selectedUniverse.value = universe
    }
}

class UniverseViewModelFactory(
    private val repository: UniverseRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UniverseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UniverseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

package org.mireiavh.finalprojectt.infrastructure.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mireiavh.finalprojectt.domain.data.ManualRepository
import org.mireiavh.finalprojectt.domain.model.Manual

data class ManualsUiState(
    val manuals: List<Manual> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class ManualViewModel(
    private val repository: ManualRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ManualsUiState(isLoading = true))
    val uiState: StateFlow<ManualsUiState> = _uiState

    private val _selectedManual = MutableStateFlow<Manual?>(null)
    val selectedManual: StateFlow<Manual?> = _selectedManual

    init {
        fetchManuals()
    }

    fun fetchManuals() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val manualsList = repository.getManuals()
                _uiState.update { it.copy(manuals = manualsList, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
            }
        }
    }

    fun selectManual(manual: Manual) {
        _selectedManual.value = manual
    }

}

class ManualViewModelFactory(
    private val repository: ManualRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ManualViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ManualViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

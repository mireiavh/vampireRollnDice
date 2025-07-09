package org.mireiavh.finalproject.infrastructure.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mireiavh.finalproject.domain.data.ManualRepository
import org.mireiavh.finalproject.domain.model.Manual

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
}

package org.mireiavh.finalprojectt.infrastructure.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mireiavh.finalprojectt.domain.model.Character
import org.mireiavh.finalprojectt.infrastructure.GlobalCharacterRepository
import org.mireiavh.finalprojectt.infrastructure.FirebaseGlobalCharacterRepository

data class GlobalCharactersUiState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class GlobalCharacterViewModel(
    private val repository: GlobalCharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GlobalCharactersUiState(isLoading = true))
    val uiState: StateFlow<GlobalCharactersUiState> = _uiState

    private val _selectedCharacter = MutableStateFlow<Character?>(null)
    val selectedCharacter: StateFlow<Character?> = _selectedCharacter

    init {
        fetchCharacters()
    }

    fun fetchCharacters() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val characterList = repository.getAllCharacters()
                _uiState.update { it.copy(characters = characterList, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
            }
        }
    }

    fun selectCharacter(character: Character) {
        _selectedCharacter.value = character
    }
}

class GlobalCharacterViewModelFactory(
    private val repository: FirebaseGlobalCharacterRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GlobalCharacterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GlobalCharacterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

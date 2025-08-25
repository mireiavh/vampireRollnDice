package org.mireiavh.finalprojectt.infrastructure.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.mireiavh.finalprojectt.domain.data.CharacterRepository
import org.mireiavh.finalprojectt.domain.model.Character
import java.util.UUID

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _characterList = MutableStateFlow<List<Character>>(emptyList())
    val characterList: StateFlow<List<Character>> = _characterList.asStateFlow()

    private val _selectedCharacter = MutableStateFlow<Character?>(null)
    val selectedCharacter: StateFlow<Character?> = _selectedCharacter.asStateFlow()

    private val _editableCharacter = MutableStateFlow(Character())
    val editableCharacter: StateFlow<Character> = _editableCharacter.asStateFlow()

    fun loadCharacters(userId: String) {
        viewModelScope.launch {
            _characterList.value = repository.getCharactersForUser(userId)
        }
    }

    fun deleteCharacter(userId: String, character: Character) {
        viewModelScope.launch {
            repository.deleteCharacter(userId, character.id)
            _characterList.value = _characterList.value.filter { it.id != character.id }
        }
    }

    fun saveCharacter(userId: String, character: Character) {
        viewModelScope.launch {
            repository.saveCharacter(userId, character)
            loadCharacters(userId)
        }
    }

    fun selectCharacter(character: Character) {
        _selectedCharacter.value = character
        _editableCharacter.value = character
    }

    fun updateEditableCharacter(updated: Character) {
        _editableCharacter.value = updated
    }

    fun createNewCharacter() {
        _editableCharacter.value = Character(id = "")
    }
}

class CharacterViewModelFactory(
    private val repository: CharacterRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

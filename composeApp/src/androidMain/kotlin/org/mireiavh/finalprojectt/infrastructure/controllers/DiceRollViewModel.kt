package org.mireiavh.finalprojectt.infrastructure.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.mireiavh.finalprojectt.domain.data.DiceRollRepository
import org.mireiavh.finalprojectt.domain.model.DiceRoll
import org.mireiavh.finalprojectt.domain.model.DiceSummary

class DiceRollViewModel(
    private val repository: DiceRollRepository
) : ViewModel() {

    private val _currentRoll = MutableStateFlow<DiceRoll?>(null)
    val currentRoll: StateFlow<DiceRoll?> = _currentRoll

    fun rollDice(title: String, manualId: String, normalCount: Int, angerCount: Int) {
        val normalDice = List(normalCount) { rollNormalDie() }
        val angerDice = List(angerCount) { rollAngerDie() }

        val summary = DiceSummary(
            successes = normalDice.count { it == "success" } + angerDice.count { it == "success" },
            greatSuccesses = normalDice.count { it == "great_success" } + angerDice.count { it == "great_success" },
            skulls = angerDice.count { it == "skull" }
        )

        _currentRoll.value = DiceRoll(
            title = title,
            normalDice = normalDice,
            angerDice = angerDice,
            summary = summary
        )
    }

    fun saveRoll(userId: String) {
        viewModelScope.launch {
            currentRoll.value?.let {
                repository.saveDiceRoll(userId, it)
            }
        }
    }

    private fun rollNormalDie(): String {
        return listOf("neutral", "success", "great_success").random()
    }

    private fun rollAngerDie(): String {
        return listOf("neutral", "success", "great_success", "skull").random()
    }

}

class DiceRollViewModelFactory(
    private val repository: DiceRollRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiceRollViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DiceRollViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
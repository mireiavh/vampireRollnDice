package org.mireiavh.finalprojectt.infrastructure.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.mireiavh.finalprojectt.domain.data.DiceRepository
import org.mireiavh.finalprojectt.domain.model.DiceRoll
import org.mireiavh.finalprojectt.domain.model.DiceSummary
import java.util.UUID

class DiceRollViewModel(
    private val repository: DiceRepository
) : ViewModel() {

    private val _currentRoll = MutableStateFlow<DiceRoll?>(null)
    val currentRoll: StateFlow<DiceRoll?> = _currentRoll

    private val _allRolls = MutableStateFlow<List<DiceRoll>>(emptyList())
    val allRolls: StateFlow<List<DiceRoll>> = _allRolls

    fun rollDice(title: String, manualId: String, normalCount: Int, angerCount: Int, userId: String) {
        val normalDice = List(normalCount) { rollNormalDie() }
        val angerDice = List(angerCount) { rollAngerDie() }

        val summary = DiceSummary(
            successes = normalDice.count { it == "success" } + angerDice.count { it == "success" },
            greatSuccesses = normalDice.count { it == "great_success" } + angerDice.count { it == "great_success" },
            skulls = angerDice.count { it == "skull" }
        )

        val roll = DiceRoll(
            id = UUID.randomUUID().toString(),
            title = title,
            normalDice = normalDice,
            angerDice = angerDice,
            summary = summary,
            userId = userId
        )

        _currentRoll.value = roll
        _allRolls.value = _allRolls.value + roll
    }

    fun saveRoll(userId: String) {
        viewModelScope.launch {
            currentRoll.value?.let { roll ->
                repository.saveDiceRoll(userId, roll)
            }
        }
    }

    fun loadRolls(userId: String) {
        viewModelScope.launch {
            val rolls = repository.getDiceRollsForUser(userId)
            _allRolls.value = rolls
        }
    }

    fun deleteRoll(roll: DiceRoll) {
        viewModelScope.launch {
            repository.deleteDiceRoll(roll.userId, roll.id)
            _allRolls.value = _allRolls.value.filter { it.id != roll.id }
        }
    }

    fun deleteAllRoll(userId: String) {
        viewModelScope.launch {
            repository.deleteAllDiceRollsForUser(userId)
            _allRolls.value = emptyList()
        }
    }

    fun rerollSelectedDice(selectedIndices: List<Int>, originalRoll: DiceRoll) {
        val newNormalDice = originalRoll.normalDice.mapIndexed { index, value ->
            if (index in selectedIndices) rollNormalDie() else value
        }

        val summary = DiceSummary(
            successes = newNormalDice.count { it == "success" } + originalRoll.angerDice.count { it == "success" },
            greatSuccesses = newNormalDice.count { it == "great_success" } + originalRoll.angerDice.count { it == "great_success" },
            skulls = originalRoll.angerDice.count { it == "skull" }
        )

        val newRoll = DiceRoll(
            id = UUID.randomUUID().toString(),
            title = originalRoll.title ?: "Relanzamiento",
            normalDice = newNormalDice,
            angerDice = originalRoll.angerDice,
            summary = summary,
            userId = originalRoll.userId
        )

        _allRolls.value = _allRolls.value + newRoll

        viewModelScope.launch {
            repository.saveDiceRoll(originalRoll.userId, newRoll)
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
    private val repository: DiceRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiceRollViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DiceRollViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

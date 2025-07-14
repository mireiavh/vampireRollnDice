package org.mireiavh.finalprojectt.domain.data

import org.mireiavh.finalprojectt.domain.model.DiceRoll

interface DiceRollRepository {
    suspend fun saveDiceRoll(userId: String, roll: DiceRoll)
}
package org.mireiavh.finalprojectt.domain.data

import org.mireiavh.finalprojectt.domain.model.DiceRoll

interface DiceRepository {
    suspend fun saveDiceRoll(userId: String, roll: DiceRoll)
    suspend fun getDiceRollsForUser(userId: String): List<DiceRoll>
    suspend fun deleteAllDiceRollsForUser(userId: String)
    suspend fun deleteDiceRoll(userId: String, rollId: String)
}
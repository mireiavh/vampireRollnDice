package org.mireiavh.finalprojectt.domain.data

import org.mireiavh.finalprojectt.domain.model.Character

interface CharacterRepository {
    suspend fun getCharactersForUser(userId: String): List<Character>
    suspend fun deleteCharacter(userId: String, characterId: String)
    suspend fun saveCharacter(userId: String, character: Character)
}

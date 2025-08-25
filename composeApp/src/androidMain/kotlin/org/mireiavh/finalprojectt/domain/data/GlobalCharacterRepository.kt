package org.mireiavh.finalprojectt.infrastructure

import org.mireiavh.finalprojectt.domain.model.Character

interface GlobalCharacterRepository {
    suspend fun getAllCharacters(): List<Character>
}

package org.mireiavh.finalprojectt.infrastructure.repositories

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.mireiavh.finalprojectt.domain.data.CharacterRepository
import org.mireiavh.finalprojectt.domain.model.Character

class FirebaseCharacterRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : CharacterRepository {

    override suspend fun getCharactersForUser(userId: String): List<Character> {
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("characters")
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                val character = doc.toObject(Character::class.java)
                character?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun deleteCharacter(userId: String, characterId: String) {
        firestore.collection("users")
            .document(userId)
            .collection("characters")
            .document(characterId)
            .delete()
            .await()
    }

    override suspend fun saveCharacter(userId: String, character: Character) {
        val docRef = if (character.id.isBlank()) {
            firestore.collection("users")
                .document(userId)
                .collection("characters")
                .document()
        } else {
            firestore.collection("users")
                .document(userId)
                .collection("characters")
                .document(character.id)
        }

        docRef.set(character.copy(id = docRef.id)).await()
    }
}

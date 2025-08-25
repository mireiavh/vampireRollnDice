package org.mireiavh.finalprojectt.infrastructure

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.mireiavh.finalprojectt.domain.model.Character
import android.util.Log

class FirebaseGlobalCharacterRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : GlobalCharacterRepository {

    override suspend fun getAllCharacters(): List<Character> {
        return try {
            val snapshot = firestore.collection("globnal_characters")
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toCharacter()?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            Log.e("FirebaseRepo", "Error loading characters", e)
            emptyList()
        }
    }
}

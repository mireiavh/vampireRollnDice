package org.mireiavh.finalprojectt.infrastructure.repositories

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.mireiavh.finalprojectt.domain.data.DiceRepository
import org.mireiavh.finalprojectt.domain.model.DiceRoll

class FirebaseDiceRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
): DiceRepository {

    override suspend fun saveDiceRoll(userId: String, roll: DiceRoll) {
        firestore.collection("users")
            .document(userId)
            .collection("dice_rolls")
            .document(roll.id)
            .set(roll)
            .await()
    }

    override suspend fun getDiceRollsForUser(userId: String): List<DiceRoll> {
        val snapshot = firestore.collection("users")
            .document(userId)
            .collection("dice_rolls")
            .get()
            .await()

        return snapshot.documents.mapNotNull { it.toObject(DiceRoll::class.java) }
    }

    override suspend fun deleteDiceRoll(userId: String, rollId: String) {
        val documentRef = firestore.collection("users")
            .document(userId)
            .collection("dice_rolls")
            .document(rollId)
        documentRef.delete().await()
    }

    override suspend fun deleteAllDiceRollsForUser(userId: String) {
        val collection = firestore.collection("users")
            .document(userId)
            .collection("dice_rolls")

        val snapshot = collection.get().await()
        for (document in snapshot.documents) {
            document.reference.delete().await()
        }
    }
}
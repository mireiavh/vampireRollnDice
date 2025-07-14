package org.mireiavh.finalprojectt.infrastructure

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.mireiavh.finalprojectt.domain.data.DiceRollRepository
import org.mireiavh.finalprojectt.domain.model.DiceRoll

class FirebaseDiceRollRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
): DiceRollRepository {

    override suspend fun saveDiceRoll(userId: String, roll: DiceRoll) {
        firestore.collection("users")
            .document(userId)
            .collection("dice_rolls")
            .add(roll)
            .await()
    }
}
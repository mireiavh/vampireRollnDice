package org.mireiavh.finalprojectt.infrastructure

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.mireiavh.finalprojectt.domain.data.UniverseRepository
import org.mireiavh.finalprojectt.domain.model.Universe
import org.mireiavh.finalprojectt.infrastructure.dtos.UniverseDto

class FirebaseUniverseRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : UniverseRepository {

    override suspend fun getUniverse(): List<Universe> {
        return try {
            val snapshot = firestore.collection("universe").get().await()
            snapshot.documents.mapNotNull { doc ->
                val dto = doc.toObject(UniverseDto::class.java)?.copy(id = doc.id)
                    ?: return@mapNotNull null

                Universe(
                    id = dto.id,
                    title = dto.title,
                    description = dto.description,
                    poster = dto.poster
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
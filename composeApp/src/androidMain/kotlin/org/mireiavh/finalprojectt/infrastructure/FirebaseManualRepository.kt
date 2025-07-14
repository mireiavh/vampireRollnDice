package org.mireiavh.finalprojectt.infrastructure

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.mireiavh.finalprojectt.domain.data.ManualRepository
import org.mireiavh.finalprojectt.infrastructure.dtos.ManualDto
import org.mireiavh.finalprojectt.domain.model.Manual
import org.mireiavh.finalprojectt.domain.model.toLanguageType
import org.mireiavh.finalprojectt.domain.model.toTagType

class FirebaseManualRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ManualRepository {

    override suspend fun getManuals(): List<Manual> {
        return try {
            val snapshot = firestore.collection("manuals").get().await()
            snapshot.documents.mapNotNull { doc ->
                val dto = doc.toObject(ManualDto::class.java)?.copy(id = doc.id) ?: return@mapNotNull null

                Manual(
                    id = dto.id,
                    title = dto.title,
                    description = dto.description,
                    poster = dto.poster,
                    author = dto.author,
                    releaseYear = dto.releaseYear,
                    system = dto.system,
                    tags = dto.tags.mapNotNull { it.toTagType() },
                    language = dto.language.mapNotNull { it.toLanguageType() }
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
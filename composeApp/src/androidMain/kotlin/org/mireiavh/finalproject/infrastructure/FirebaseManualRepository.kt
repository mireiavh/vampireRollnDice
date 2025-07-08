package org.mireiavh.finalproject.infrastructure

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.mireiavh.finalproject.data.ManualRepository
import org.mireiavh.finalproject.model.Manual
import org.mireiavh.finalproject.model.toLanguageType
import org.mireiavh.finalproject.model.toTagType

class FirebaseManualRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ManualRepository {

    override suspend fun getManuals(): List<Manual> {
        return try {
            val snapshot = firestore.collection("manuals").get().await()
            snapshot.documents.mapNotNull { doc ->
                val dto = doc.toObject(ManualDto::class.java) ?: return@mapNotNull null

                Manual(
                    id = dto.id,
                    title = dto.title,
                    description = dto.description,
                    poster = dto.poster,
                    author = dto.author,
                    releaseYear = dto.releaseYear,
                    system = dto.system,
                    isOfficial = dto.isOfficial,
                    tags = dto.tags.mapNotNull { it.toTagType() },
                    language = dto.language.mapNotNull { it.toLanguageType() }
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
package org.mireiavh.finalproject.domain

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.mireiavh.finalproject.data.ManualRepository
import org.mireiavh.finalproject.model.Manual

/*
class ManualRepositoryImpl(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ManualRepository {

    private val collection = firestore.collection("manuals")

    override suspend fun getManuals(): List<Manual> {
        return try {
            val snapshot = collection.get().await()
            snapshot.documents.mapNotNull { it.toObject(Manual::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

}

 */
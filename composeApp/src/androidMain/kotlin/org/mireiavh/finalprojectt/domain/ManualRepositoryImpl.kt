package org.mireiavh.finalprojectt.domain

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
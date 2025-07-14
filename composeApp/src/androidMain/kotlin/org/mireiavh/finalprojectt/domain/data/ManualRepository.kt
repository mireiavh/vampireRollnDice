package org.mireiavh.finalprojectt.domain.data

import org.mireiavh.finalprojectt.domain.model.Manual

interface ManualRepository {
    suspend fun getManuals(): List<Manual>

}
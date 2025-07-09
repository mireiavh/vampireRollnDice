package org.mireiavh.finalproject.domain.data

import org.mireiavh.finalproject.domain.model.Manual

interface ManualRepository {
    suspend fun getManuals(): List<Manual>

}
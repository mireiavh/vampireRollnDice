package org.mireiavh.finalproject.data

import org.mireiavh.finalproject.model.Manual

interface ManualRepository {
    suspend fun getManuals(): List<Manual>

}
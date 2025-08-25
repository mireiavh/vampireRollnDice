package org.mireiavh.finalprojectt.domain.data

import org.mireiavh.finalprojectt.domain.model.Universe

interface UniverseRepository {
    suspend fun getUniverse(): List<Universe>
}
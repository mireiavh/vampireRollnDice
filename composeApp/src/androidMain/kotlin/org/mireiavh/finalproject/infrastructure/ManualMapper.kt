package org.mireiavh.finalproject.infrastructure

import org.mireiavh.finalproject.model.Manual
import org.mireiavh.finalproject.model.toLanguageType
import org.mireiavh.finalproject.model.toTagType

fun ManualDto.toManual(): Manual {
    return Manual(
        id = this.id,
        title = this.title,
        description = this.description,
        poster = this.poster,
        author = this.author,
        releaseYear = this.releaseYear,
        tags = this.tags.mapNotNull { it.toTagType() },
        language = this.language.mapNotNull { it.toLanguageType() },
        system = this.system,
        isOfficial = this.isOfficial
    )
}

fun Manual.toDto(): ManualDto {
    return ManualDto(
        id = this.id,
        title = this.title,
        description = this.description,
        poster = this.poster,
        author = this.author,
        releaseYear = this.releaseYear,
        tags = this.tags.map { it.name },
        language = this.language.map { it.name },
        system = this.system,
        isOfficial = this.isOfficial
    )
}
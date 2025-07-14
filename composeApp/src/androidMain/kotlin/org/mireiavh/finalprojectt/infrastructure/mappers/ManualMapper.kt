package org.mireiavh.finalprojectt.infrastructure.mappers

import org.mireiavh.finalprojectt.infrastructure.dtos.ManualDto
import org.mireiavh.finalprojectt.domain.model.Manual
import org.mireiavh.finalprojectt.domain.model.toLanguageType
import org.mireiavh.finalprojectt.domain.model.toTagType

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
        system = this.system
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
        system = this.system
    )
}
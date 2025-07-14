package org.mireiavh.finalprojectt.infrastructure.dtos

import androidx.annotation.Keep

@Keep
data class ManualDto(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var poster: String = "",
    var author: List<String> = emptyList(),
    var releaseYear: Int = 0,
    var tags: List<String> = emptyList(),
    var language: List<String> = emptyList(),
    var system: String = ""
)

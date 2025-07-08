package org.mireiavh.finalproject.infrastructure


data class ManualDto(
    var id: Number = 0,
    var title: String = "",
    var description: String = "",
    var poster: String = "",
    var author: List<String> = emptyList(),
    var releaseYear: Int = 0,
    var tags: List<String> = emptyList(),
    var language: List<String> = emptyList(),
    var system: String = "",
    var isOfficial: Boolean = false
)

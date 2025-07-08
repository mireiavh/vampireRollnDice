package org.mireiavh.finalproject.model

import androidx.annotation.StringRes
import org.mireiavh.finalproject.R
import java.io.Serializable

data class Manual(
    var id: Number = 0,
    var title: String = "",
    var description: String = "",
    var poster: String = "",
    var author: List<String> = emptyList(),
    var releaseYear: Int = 0,
    var tags: List<TagType> = emptyList(),
    var language: List<LanguageType> = emptyList(),
    var system: String = "",
    var isOfficial: Boolean = false
): Serializable

enum class TagType {
    MASTER,
    PLAYER,
    FREE,
    NEW
}

enum class LanguageType {
    ENGLISH,
    SPANISH
}

@StringRes
fun TagType.getLabelRes(): Int {
    return when (this) {
        TagType.MASTER -> R.string.master_tag_manual_text
        TagType.PLAYER -> R.string.player_tag_manual_text
        TagType.FREE -> R.string.free_tag_manual_text
        TagType.NEW -> R.string.new_tag_manual_text
    }
}

@StringRes
fun LanguageType.getLabelRes(): Int {
    return when (this) {
        LanguageType.ENGLISH -> R.string.english_tag_es
        LanguageType.SPANISH -> R.string.spanish_tag_es
    }
}


fun String.toTagType(): TagType? {
    return when (this.uppercase()) {
        "MASTER" -> TagType.MASTER
        "PLAYER" -> TagType.PLAYER
        "FREE" -> TagType.FREE
        "NEW" -> TagType.NEW
        else -> null
    }
}

fun String.toLanguageType(): LanguageType? {
    return when (this.uppercase()) {
        "ENGLISH" -> LanguageType.ENGLISH
        "SPANISH", "ESPAÑOL" -> LanguageType.SPANISH
        else -> null
    }
}
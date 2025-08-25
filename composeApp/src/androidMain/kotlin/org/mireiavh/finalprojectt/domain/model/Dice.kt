package org.mireiavh.finalprojectt.domain.model

import java.io.Serializable

data class DiceRoll(
    val id: String = "",
    val title: String = "",
    val normalDice: List<String> = emptyList(),
    val angerDice: List<String> = emptyList(),
    val summary: DiceSummary = DiceSummary(),
    val timestamp: Long = System.currentTimeMillis(),
    val userId: String = ""
) : Serializable

data class DiceSummary(
    val successes: Int = 0,
    val greatSuccesses: Int = 0,
    val skulls: Int = 0
) : Serializable
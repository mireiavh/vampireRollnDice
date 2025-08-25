package org.mireiavh.finalprojectt.domain.model

import java.io.Serializable

data class Character(
    var id: String = "",
    var name: String = "",
    var ilustration: String = "",
    var largeIlustration: String = "",
    var concept: ConceptType = ConceptType.NONE,
    var predator: PredatorType = PredatorType.NONE,
    var chronicle: String = "",
    var ambition: String = "",
    var clan: ClanType = ClanType.NONE,
    var sire: String = "",
    var desire: String = "",
    var generation: GenerationType = GenerationType.NONE,

    var physical: Attributes = Attributes(),
    var social: Attributes = Attributes(),
    var mental: Attributes = Attributes(),

    var skills: List<Skill> = emptyList(),

    var disciplines: List<DisciplineType> = emptyList(),
    var resonance: ResonanceType = ResonanceType.NONE,
    var merits: List<MeritType> = emptyList(),
    var flaws: List<FlawType> = emptyList(),

    var totalXP: Int = 0,
    var spentXP: Int = 0,
    var trueAge: Int = 0,
    var apparentAge: Int = 0,
    var birthDate: String = "",
    var deathDate: String = "",
    var appearance: String = "",
    var notableTraits: String = "",
    var backstory: String = "",
    var notes: String = ""
) : Serializable

data class Attributes(
    var first: Int = 1,
    var second: Int = 1,
    var third: Int = 1
) : Serializable


enum class ConceptType {
    NONE, ERUDITE, VETERAN, RESEARCHER, SOCIALITE, EXECUTIVE, ARTIST
}

enum class PredatorType {
    NONE, BAGGER, CLEAVER, CONSENSUALIST, ALLEY_CAT, FARMER,
    OSIRIS, SCENE_QUEEN, SANDMAN, BLOOD_LEECH, SIREN
}

enum class ClanType {
    NONE, BRUJAH, GANGREL, MALKAVIAN, NOSFERATU, TOREADOR,
    TREMERE, VENTRUE, CAITIFF, THIN_BLOOD
}

enum class GenerationType {
    NONE, GEN_16, GEN_15, GEN_14, GEN_13, GEN_12, GEN_11, GEN_10
}

enum class ResonanceType {
    NONE, COLERIC, MELANCHOLIC, PHLEGMATIC, SANGUINE, ANIMAL_BLOOD
}

enum class DisciplineType {
    NONE, ANIMALISM, AUSPEX, CLARITY, DOMINATE, FORTITUDE,
    OBFUSCATE, POTENCE, PRESENCE, PROTEAN,
    BLOOD_SORCERY, RITUALS, BLOOD_ALCHEMY
}

enum class MeritType {
    NONE, DAY_DRINKER, CAMARILLA_CONTACT, ANARCH_COMRADE,
    THIN_BLOOD_ALCHEMIST, BOUNDING_BLOOD, VAMPIRIC_RESILIENCE, LIVED
}

enum class FlawType {
    NONE, DEAD_FLESH, VITAE_DEPENDENCY, MILK_TEETH, MORTAL_FRAGILITY,
    CLAN_CURSE, MARKED_BY_CAMARILLA, ANARCH_REJECTED, BESTIAL_TEMPER
}

data class Skill(
    val name: String,
    val category: SkillCategory,
    var level: Int = 0
) : Serializable

enum class SkillCategory {
    FIREARMS, CRAFTS, ATHLETICS, DRIVE, LARCENY,
    BRAWL, MELEE, STEALTH, SURVIVAL,
    STREETWISE, ETIQUETTE, PERFORMANCE, INTIMIDATION, LEADERSHIP,
    INSIGHT, PERSUASION, SUBTERFUGE, ANIMAL_HANDLING,
    ACADEMICS, SCIENCE, AWARENESS, FINANCE,
    INVESTIGATION, MEDICINE, OCCULT, POLITICS, TECHNOLOGY
}

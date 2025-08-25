package org.mireiavh.finalprojectt.infrastructure

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import org.mireiavh.finalprojectt.domain.model.*

fun DocumentSnapshot.toCharacter(): Character? {
    return try {
        val id = this.id
        val name = getString("name") ?: ""
        val ilustration = getString("ilustration") ?: ""
        val largeIlustration = getString("largeIlustration") ?: ""

        val conceptStr = getString("concept") ?: "NONE"
        val concept = ConceptType.valueOf(conceptStr)

        val predator = PredatorType.valueOf(getString("predator") ?: "NONE")
        val clan = ClanType.valueOf(getString("clan") ?: "NONE")
        val generation = GenerationType.valueOf(getString("generation") ?: "NONE")
        val resonance = ResonanceType.valueOf(getString("resonance") ?: "NONE")

        val disciplinesStrList = get("disciplines") as? List<*> ?: emptyList<Any>()
        val disciplines = disciplinesStrList.mapNotNull {
            (it as? String)?.let { str -> DisciplineType.valueOf(str) }
        }

        val meritsStrList = get("merits") as? List<*> ?: emptyList<Any>()
        val merits = meritsStrList.mapNotNull {
            (it as? String)?.let { str -> MeritType.valueOf(str) }
        }

        val flawsStrList = get("flaws") as? List<*> ?: emptyList<Any>()
        val flaws = flawsStrList.mapNotNull {
            (it as? String)?.let { str -> FlawType.valueOf(str) }
        }

        val skillsMapList = get("skills") as? List<*> ?: emptyList<Any>()
        val skills = skillsMapList.mapNotNull { skillAny ->
            (skillAny as? Map<*, *>)?.let { skillMap ->
                Skill(
                    name = skillMap["name"] as? String ?: "",
                    category = SkillCategory.valueOf(skillMap["category"] as? String ?: "FIREARMS"),
                    level = (skillMap["level"] as? Long)?.toInt() ?: 0
                )
            }
        }

        fun mapAttributes(key: String): Attributes {
            val map = get(key)
            return if (map is Map<*, *>) {
                Attributes(
                    first = (map["first"] as? Long)?.toInt() ?: 1,
                    second = (map["second"] as? Long)?.toInt() ?: 1,
                    third = (map["third"] as? Long)?.toInt() ?: 1
                )
            } else {
                Attributes()
            }
        }

        val physical = mapAttributes("physical")
        val social = mapAttributes("social")
        val mental = mapAttributes("mental")

        val totalXP = (getLong("totalXP") ?: 0L).toInt()
        val spentXP = (getLong("spentXP") ?: 0L).toInt()
        val trueAge = (getLong("trueAge") ?: 0L).toInt()
        val apparentAge = (getLong("apparentAge") ?: 0L).toInt()

        val birthDate = getString("birthDate") ?: ""
        val deathDate = getString("deathDate") ?: ""
        val appearance = getString("appearance") ?: ""
        val notableTraits = getString("notableTraits") ?: ""
        val backstory = getString("backstory") ?: ""
        val notes = getString("notes") ?: ""

        val sire = getString("sire") ?: ""
        val desire = getString("desire") ?: ""

        Character(
            id = id,
            name = name,
            ilustration = ilustration,
            largeIlustration = largeIlustration,
            concept = concept,
            predator = predator,
            clan = clan,
            generation = generation,
            resonance = resonance,
            disciplines = disciplines,
            merits = merits,
            flaws = flaws,
            skills = skills,
            physical = physical,
            social = social,
            mental = mental,
            totalXP = totalXP,
            spentXP = spentXP,
            trueAge = trueAge,
            apparentAge = apparentAge,
            birthDate = birthDate,
            deathDate = deathDate,
            appearance = appearance,
            notableTraits = notableTraits,
            backstory = backstory,
            notes = notes,
            sire = sire,
            desire = desire
        )
    } catch (e: Exception) {
        Log.e("FirebaseExtensions", "Error mapping DocumentSnapshot to Character", e)
        null
    }
}

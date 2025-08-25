package org.mireiavh.finalprojectt

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mireiavh.finalprojectt.domain.model.Character
import org.mireiavh.finalprojectt.domain.model.Manual
import org.mireiavh.finalprojectt.domain.model.TagType
import org.mireiavh.finalprojectt.domain.model.LanguageType
import org.mireiavh.finalprojectt.infrastructure.repositories.FirebaseCharacterRepository
import org.mireiavh.finalprojectt.infrastructure.repositories.FirebaseManualRepository
import org.mireiavh.finalprojectt.infrastructure.viewmodels.CharacterViewModel
import org.mireiavh.finalprojectt.infrastructure.viewmodels.ManualViewModel
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.tasks.await
import org.mireiavh.finalprojectt.domain.data.CharacterRepository
import org.mireiavh.finalprojectt.domain.data.ManualRepository
import org.mireiavh.finalprojectt.domain.model.ClanType
import org.mireiavh.finalprojectt.domain.model.ConceptType
import org.mireiavh.finalprojectt.domain.model.DisciplineType
import org.mireiavh.finalprojectt.domain.model.FlawType
import org.mireiavh.finalprojectt.domain.model.GenerationType
import org.mireiavh.finalprojectt.domain.model.MeritType
import org.mireiavh.finalprojectt.domain.model.PredatorType
import org.mireiavh.finalprojectt.domain.model.ResonanceType
import org.mireiavh.finalprojectt.domain.model.getLabelRes
import org.mireiavh.finalprojectt.domain.model.toLanguageType
import org.mireiavh.finalprojectt.domain.model.toTagType
import org.mireiavh.finalprojectt.navigationManagers.AuthManager

@OptIn(ExperimentalCoroutinesApi::class)
class AppUnitTests {

    private lateinit var mockFirestore: FirebaseFirestore
    private lateinit var mockAuthManager: AuthManager
    private lateinit var mockCharacterRepository: CharacterRepository
    private lateinit var mockManualRepository: ManualRepository

    @Before
    fun setup() {
        mockFirestore = mockk(relaxed = true)
        mockAuthManager = mockk(relaxed = true)
        mockCharacterRepository = mockk(relaxed = true)
        mockManualRepository = mockk(relaxed = true)

        mockkStatic(FirebaseFirestore::class)
        every { FirebaseFirestore.getInstance() } returns mockFirestore
    }

    @Test
    fun testAuthManagerSignInIntent() {
        val authManager = AuthManager(mockk())
        val intent = authManager.getSignInIntent()

        assertNotNull(intent)
        assertEquals("com.google.android.gms.auth.api.signin.GoogleSignInIntent", intent.action)
    }

    @Test
    fun testManualMapping() {
        val testString = "MASTER"
        val tagType = testString.toTagType()

        assertEquals(TagType.MASTER, tagType)

        val invalidString = "INVALID"
        val nullResult = invalidString.toTagType()

        assertNull(nullResult)
    }

    @Test
    fun testLanguageTypeLabelResources() {
        val spanishLabelRes = LanguageType.SPANISH.getLabelRes()
        val englishLabelRes = LanguageType.ENGLISH.getLabelRes()

        assertTrue(spanishLabelRes > 0)
        assertTrue(englishLabelRes > 0)
    }

    @Test
    fun testCharacterDefaultValues() {
        val character = Character()

        assertEquals("", character.id)
        assertEquals("", character.name)
        assertEquals(1, character.physical.first)
        assertEquals(0, character.skills.size)
        assertEquals(0, character.totalXP)
    }

    @Test
    fun testDiceRollSummaryCalculation() {
        val diceRoll = org.mireiavh.finalprojectt.domain.model.DiceRoll(
            summary = org.mireiavh.finalprojectt.domain.model.DiceSummary(
                successes = 3,
                greatSuccesses = 1,
                skulls = 2
            )
        )

        assertEquals(3, diceRoll.summary.successes)
        assertEquals(1, diceRoll.summary.greatSuccesses)
        assertEquals(2, diceRoll.summary.skulls)
    }

    @Test
    fun testManualViewModelInitialization() = runTest {
        val manualViewModel = ManualViewModel(mockManualRepository)

        assertNotNull(manualViewModel)
        assertNotNull(manualViewModel.uiState)
    }

    @Test
    fun testCharacterViewModelInitialization() = runTest {
        val characterViewModel = CharacterViewModel(mockCharacterRepository)

        assertNotNull(characterViewModel)
    }

    @Test
    fun testManualRepositoryEmptyResponse() = runTest {
        val manualRepository = FirebaseManualRepository(mockFirestore)

        coEvery { mockFirestore.collection("manuals").get().await() } returns mockk {
            every { documents } returns emptyList()
        }

        val manuals = manualRepository.getManuals()

        assertEquals(0, manuals.size)
    }

    @Test
    fun testCharacterRepositoryEmptyResponse() = runTest {
        val characterRepository = FirebaseCharacterRepository(mockFirestore)
        val testUserId = "test-user-123"

        coEvery {
            mockFirestore.collection("users")
                .document(testUserId)
                .collection("characters")
                .get()
                .await()
        } returns mockk {
            every { documents } returns emptyList()
        }

        val characters = characterRepository.getCharactersForUser(testUserId)

        assertEquals(0, characters.size)
    }

    @Test
    fun testAuthManagerCurrentUser() {
        every { mockAuthManager.getCurrentUser() } returns mockk()

        val currentUser = mockAuthManager.getCurrentUser()

        assertNotNull(currentUser)
    }

    @Test
    fun testTagTypeMapping() {
        val testCases = mapOf(
            "MASTER" to TagType.MASTER,
            "PLAYER" to TagType.PLAYER,
            "FREE" to TagType.FREE,
            "NEW" to TagType.NEW,
            "invalid" to null
        )

        testCases.forEach { (input, expected) ->
            assertEquals(expected, input.toTagType())
        }
    }

    @Test
    fun testLanguageTypeMapping() {
        val testCases = mapOf(
            "ENGLISH" to LanguageType.ENGLISH,
            "SPANISH" to LanguageType.SPANISH
        )

        testCases.forEach { (input, expected) ->
            assertEquals(expected, input.toLanguageType())
        }
    }

    @Test
    fun testCharacterAttributesInitialization() {
        val character = Character()

        assertEquals(1, character.physical.first)
        assertEquals(1, character.physical.second)
        assertEquals(1, character.physical.third)

        assertEquals(1, character.social.first)
        assertEquals(1, character.social.second)
        assertEquals(1, character.social.third)

        assertEquals(1, character.mental.first)
        assertEquals(1, character.mental.second)
        assertEquals(1, character.mental.third)
    }

    @Test
    fun testEnumValuesPresence() {
        assertTrue(ClanType.values().isNotEmpty())
        assertTrue(DisciplineType.values().isNotEmpty())
        assertTrue(PredatorType.values().isNotEmpty())
        assertTrue(ConceptType.values().isNotEmpty())
        assertTrue(GenerationType.values().isNotEmpty())
        assertTrue(ResonanceType.values().isNotEmpty())
        assertTrue(MeritType.values().isNotEmpty())
        assertTrue(FlawType.values().isNotEmpty())
    }

    @Test
    fun testSerializableModels() {
        val character = Character()
        val manual = Manual()
        val diceRoll = org.mireiavh.finalprojectt.domain.model.DiceRoll()

        assertTrue(character is java.io.Serializable)
        assertTrue(manual is java.io.Serializable)
        assertTrue(diceRoll is java.io.Serializable)
    }

    @Test
    fun testManualDtoStructure() {
        val dto = org.mireiavh.finalprojectt.infrastructure.dtos.ManualDto(
            id = "test-id",
            title = "Test Manual",
            description = "Test Description",
            poster = "test-poster.jpg",
            author = listOf("Author 1", "Author 2"),
            releaseYear = 2024,
            tags = listOf("MASTER", "PLAYER"),
            language = listOf("ENGLISH", "SPANISH"),
            system = "Test System"
        )

        assertEquals("test-id", dto.id)
        assertEquals("Test Manual", dto.title)
        assertEquals(2, dto.author.size)
        assertEquals(2024, dto.releaseYear)
    }
}
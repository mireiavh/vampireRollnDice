import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import org.mireiavh.finalprojectt.R
import org.mireiavh.finalprojectt.domain.model.*
import org.mireiavh.finalprojectt.infrastructure.viewmodels.CharacterViewModel
import org.mireiavh.finalprojectt.utils.DarkBrown
import org.mireiavh.finalprojectt.utils.Peach
import org.mireiavh.finalprojectt.utils.customs.CustomDetailButton
import org.mireiavh.finalprojectt.utils.customs.EnumDropdown

@Composable
fun CreateCharacterSection(
    navController: NavController,
    viewModel: CharacterViewModel,
    onCharacterSaved: () -> Unit
) {
    val editableCharacter by viewModel.editableCharacter.collectAsState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    var name by remember { mutableStateOf(editableCharacter.name) }
    var clan by remember { mutableStateOf(editableCharacter.clan) }
    var predator by remember { mutableStateOf(editableCharacter.predator) }
    var concept by remember { mutableStateOf(editableCharacter.concept) }
    var chronicle by remember { mutableStateOf(editableCharacter.chronicle) }
    var ambition by remember { mutableStateOf(editableCharacter.ambition) }
    var sire by remember { mutableStateOf(editableCharacter.sire) }
    var desire by remember { mutableStateOf(editableCharacter.desire) }
    var generation by remember { mutableStateOf(editableCharacter.generation) }

    var totalXP by remember { mutableStateOf(editableCharacter.totalXP.toString()) }
    var spentXP by remember { mutableStateOf(editableCharacter.spentXP.toString()) }
    var trueAge by remember { mutableStateOf(editableCharacter.trueAge.toString()) }
    var apparentAge by remember { mutableStateOf(editableCharacter.apparentAge.toString()) }

    var birthDate by remember { mutableStateOf(editableCharacter.birthDate) }
    var deathDate by remember { mutableStateOf(editableCharacter.deathDate) }
    var appearance by remember { mutableStateOf(editableCharacter.appearance) }
    var notableTraits by remember { mutableStateOf(editableCharacter.notableTraits) }
    var backstory by remember { mutableStateOf(editableCharacter.backstory) }
    var notes by remember { mutableStateOf(editableCharacter.notes) }

    LaunchedEffect(editableCharacter) {
        name = editableCharacter.name
        clan = editableCharacter.clan
        predator = editableCharacter.predator
        concept = editableCharacter.concept
        chronicle = editableCharacter.chronicle
        ambition = editableCharacter.ambition
        sire = editableCharacter.sire
        desire = editableCharacter.desire
        generation = editableCharacter.generation

        totalXP = editableCharacter.totalXP.toString()
        spentXP = editableCharacter.spentXP.toString()
        trueAge = editableCharacter.trueAge.toString()
        apparentAge = editableCharacter.apparentAge.toString()

        birthDate = editableCharacter.birthDate
        deathDate = editableCharacter.deathDate
        appearance = editableCharacter.appearance
        notableTraits = editableCharacter.notableTraits
        backstory = editableCharacter.backstory
        notes = editableCharacter.notes
    }

    val scrollState = rememberScrollState()


    Scaffold(
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Peach.copy(alpha = 0.5f))
        ) {

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                EnumDropdown(
                    label = "Clan",
                    options = ClanType.values().toList(),
                    selectedOption = clan,
                    onOptionSelected = { clan = it }
                )

                EnumDropdown(
                    label = "Predator",
                    options = PredatorType.values().toList(),
                    selectedOption = predator,
                    onOptionSelected = { predator = it }
                )

                EnumDropdown(
                    label = "Concepto",
                    options = ConceptType.values().toList(),
                    selectedOption = concept,
                    onOptionSelected = { concept = it }
                )

                TextField(
                    value = chronicle,
                    onValueChange = { chronicle = it },
                    label = { Text("Crónica") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = ambition,
                    onValueChange = { ambition = it },
                    label = { Text("Ambición") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = sire,
                    onValueChange = { sire = it },
                    label = { Text("Sire") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = desire,
                    onValueChange = { desire = it },
                    label = { Text("Deseo") },
                    modifier = Modifier.fillMaxWidth()
                )

                EnumDropdown(
                    label = "Generación",
                    options = GenerationType.values().toList(),
                    selectedOption = generation,
                    onOptionSelected = { generation = it }
                )

                TextField(
                    value = totalXP,
                    onValueChange = { totalXP = it.filter { c -> c.isDigit() } },
                    label = { Text("XP Total") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = spentXP,
                    onValueChange = { spentXP = it.filter { c -> c.isDigit() } },
                    label = { Text("XP Gastada") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = trueAge,
                    onValueChange = { trueAge = it.filter { c -> c.isDigit() } },
                    label = { Text("Edad Real") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = apparentAge,
                    onValueChange = { apparentAge = it.filter { c -> c.isDigit() } },
                    label = { Text("Edad Aparente") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = birthDate,
                    onValueChange = { birthDate = it },
                    label = { Text("Fecha de Nacimiento") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = deathDate,
                    onValueChange = { deathDate = it },
                    label = { Text("Fecha de Muerte") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = appearance,
                    onValueChange = { appearance = it },
                    label = { Text("Apariencia") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = notableTraits,
                    onValueChange = { notableTraits = it },
                    label = { Text("Rasgos Notables") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = backstory,
                    onValueChange = { backstory = it },
                    label = { Text("Historia") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notas") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomDetailButton(
                    text = "Guardar",
                    onClick = {
                        val updatedCharacter = editableCharacter.copy(
                            name = name,
                            clan = clan,
                            predator = predator,
                            concept = concept,
                            chronicle = chronicle,
                            ambition = ambition,
                            sire = sire,
                            desire = desire,
                            generation = generation,
                            totalXP = totalXP.toIntOrNull() ?: 0,
                            spentXP = spentXP.toIntOrNull() ?: 0,
                            trueAge = trueAge.toIntOrNull() ?: 0,
                            apparentAge = apparentAge.toIntOrNull() ?: 0,
                            birthDate = birthDate,
                            deathDate = deathDate,
                            appearance = appearance,
                            notableTraits = notableTraits,
                            backstory = backstory,
                            notes = notes
                        )
                        viewModel.updateEditableCharacter(updatedCharacter)
                        viewModel.saveCharacter(userId, updatedCharacter)
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}


package org.mireiavh.finalprojectt.ui.homeSectionUI

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.mireiavh.finalprojectt.domain.model.Character
import org.mireiavh.finalprojectt.domain.model.Manual
import org.mireiavh.finalprojectt.domain.model.Universe
import org.mireiavh.finalprojectt.infrastructure.viewmodels.GlobalCharacterViewModel
import org.mireiavh.finalprojectt.infrastructure.viewmodels.ManualViewModel
import org.mireiavh.finalprojectt.infrastructure.viewmodels.UniverseViewModel
import org.mireiavh.finalprojectt.utils.DeepRed
import org.mireiavh.finalprojectt.utils.Peach
import org.mireiavh.finalprojectt.utils.customs.SearchBasicTextField

@Composable
fun HomeMenuSection(
    manualViewModel: ManualViewModel,
    universeViewModel: UniverseViewModel,
    globalCharacterViewModel: GlobalCharacterViewModel,
    onMoreInfoClick: (Manual) -> Unit,
    onMoreInfoUniverseClick: (Universe) -> Unit,
    onMoreInfoCharacterClick: (Character) -> Unit,
    navController: NavController
) {
    val searchQuery = remember { mutableStateOf("") }
    val selectedSection = remember { mutableStateOf("Manuales") }
    val uiState by manualViewModel.uiState.collectAsState()


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        SearchBasicTextField(value = searchQuery.value, onValueChange = { searchQuery.value = it },
            modifier = Modifier.height(50.dp).padding(horizontal = 8.dp), labelText = "Buscar por nombre",
            labelColor = Peach, borderColor = Peach, shape = RoundedCornerShape(2.dp), heightDp = 50
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row (modifier = Modifier.padding(horizontal = 8.dp)) {
            listOf("Manuales", "Personajes", "Universo").forEach { section ->
                Button(
                    onClick = { selectedSection.value = section
                        searchQuery.value = "" },
                    modifier = Modifier.padding(end = 4.dp).wrapContentWidth(),
                    shape = RoundedCornerShape(2.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedSection.value == section) DeepRed else Color.Transparent,
                        contentColor = Color.White)
                ) { Text(text = section, color = if (selectedSection.value == section) Color.White else Color.Gray) }
            }
        }

        when (selectedSection.value) {
            "Manuales" -> ManualsSection(manualViewModel, searchQuery.value, onMoreInfoClick)
            "Personajes" -> GlobalCharacterSection(globalCharacterViewModel, searchQuery.value, onMoreInfoCharacterClick)
            "Universo" -> UniverseSection(universeViewModel, searchQuery.value, onMoreInfoUniverseClick)
        }

    }

}
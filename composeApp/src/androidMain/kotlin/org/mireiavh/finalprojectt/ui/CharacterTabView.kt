package org.mireiavh.finalprojectt.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import org.mireiavh.finalprojectt.R
import org.mireiavh.finalprojectt.infrastructure.viewmodels.CharacterViewModel
import org.mireiavh.finalprojectt.utils.DarkBrown
import org.mireiavh.finalprojectt.utils.White
import org.mireiavh.finalprojectt.utils.customs.CharacterListContent
import org.mireiavh.finalprojectt.utils.customs.CustomAlertDialog
import org.mireiavh.finalprojectt.utils.customs.CustomDetailButton
import org.mireiavh.finalprojectt.utils.customs.CustomSortDropdown
import org.mireiavh.finalprojectt.utils.customs.CustomTitleText2
import org.mireiavh.finalprojectt.utils.customs.SearchBasicTextField

@Composable
fun CharacterTabView(
    viewModel: CharacterViewModel,
    navController: NavController,
    onNavigateToCreate: () -> Unit
) {
    val searchQuery = remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var sortOption by remember { mutableStateOf(CharacterSortOption.ORIGINAL) }

    val characters by viewModel.characterList.collectAsState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    LaunchedEffect(userId) {
        if (userId.isNotBlank()) {
            viewModel.loadCharacters(userId)
        }
    }

    val filteredSortedCharacters = characters
        .filter { it.name.contains(searchQuery.value, ignoreCase = true) }
        .let { list ->
            when (sortOption) {
                CharacterSortOption.NAME_ASC -> list.sortedBy { it.name.lowercase() }
                CharacterSortOption.NAME_DESC -> list.sortedByDescending { it.name.lowercase() }
                CharacterSortOption.ORIGINAL -> list
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBrown)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ddddddd),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().graphicsLayer { alpha = 0.4f },
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            DarkBrown,
                            DarkBrown.copy(alpha = 0f),
                            DarkBrown
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Listado de personajes", color = White)

                Spacer(modifier = Modifier.width(20.dp))

                CustomSortDropdown(
                    selectedOption = sortOption,
                    onOptionSelected = { sortOption = it }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            SearchBasicTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                labelText = stringResource(id = R.string.search_bar_text_es)
            )

            Spacer(modifier = Modifier.height(16.dp))

            CharacterListContent(
                characters = filteredSortedCharacters,
                onEditClick = { character ->
                    viewModel.selectCharacter(character)
                    onNavigateToCreate()
                },
                onDeleteClick = { character ->
                    viewModel.deleteCharacter(userId, character)
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, bottom = 16.dp)
        ) {
            CustomDetailButton(
                onClick = { showDialog = true },
                text = stringResource(id = R.string.create_ch_text_es),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(0.7f)
            )
        }

        if (showDialog) {
            CustomAlertDialog(
                showDialog = showDialog,
                onDismiss = { showDialog = false },
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

enum class CharacterSortOption(val label: String) {
    ORIGINAL("Orden original"),
    NAME_ASC("Orden por: A-Z"),
    NAME_DESC("Orden por: Z-A")
}


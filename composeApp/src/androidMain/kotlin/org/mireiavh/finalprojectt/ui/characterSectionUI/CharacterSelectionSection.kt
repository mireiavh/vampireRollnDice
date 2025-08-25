package org.mireiavh.finalprojectt.ui.characterSectionUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.mireiavh.finalprojectt.R
import org.mireiavh.finalprojectt.infrastructure.viewmodels.CharacterViewModel
import org.mireiavh.finalprojectt.infrastructure.viewmodels.GlobalCharacterViewModel
import org.mireiavh.finalprojectt.utils.DarkBrown
import org.mireiavh.finalprojectt.utils.DeepRed
import org.mireiavh.finalprojectt.utils.Peach
import org.mireiavh.finalprojectt.utils.customs.CustomDiceInput

@Composable
fun CharacterSelectionSection(
    globalViewModel: GlobalCharacterViewModel,
    userViewModel: CharacterViewModel,
    userId: String,
    onCharacterSelected: () -> Unit
) {
    val uiState by globalViewModel.uiState.collectAsState()
    val globalCharacters = uiState.characters

    LaunchedEffect(Unit) {
        globalViewModel.fetchCharacters()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBrown)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ddddddd),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.4f },
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

        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val cardHeight = maxHeight * 0.80f

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(globalCharacters, key = { it.id }) { character ->

                    var editableName by rememberSaveable(character.id) {
                        mutableStateOf(character.name)
                    }

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = DeepRed.copy(alpha = 0.9f)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .height(cardHeight)
                            .width(cardHeight * 0.60f)
                            .clickable {
                                val clonedCharacter =
                                    character.copy(id = "", name = editableName)
                                userViewModel.saveCharacter(userId, clonedCharacter)
                                onCharacterSelected()
                            }
                            .border(1.dp, Peach, RoundedCornerShape(16.dp))
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(character.largeIlustration),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            CustomDiceInput(
                                value = editableName,
                                onValueChange = { editableName = it },
                                title = "Indica un nombre personalizado",
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text("Pertenece al clan de los ${character.clan}")
                        }
                    }
                }
            }
        }
    }
}

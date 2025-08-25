package org.mireiavh.finalprojectt.utils.customs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import org.mireiavh.finalprojectt.R
import org.mireiavh.finalprojectt.domain.model.Character
import org.mireiavh.finalprojectt.utils.DeepRed
import org.mireiavh.finalprojectt.utils.Peach

@Composable
fun CharacterListContent(
    characters: List<Character>,
    onEditClick: (Character) -> Unit,
    onDeleteClick: (Character) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AnimatedCharacterList(
            characters = characters,
            onEditClick = onEditClick,
            onDeleteClick = onDeleteClick
        )
    }
}

@Composable
fun AnimatedCharacterList(
    characters: List<Character>,
    onEditClick: (Character) -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Character) -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(characters) {
        visible = false
        delay(100)
        visible = true
    }

    AnimatedVisibility(
        visible = visible && characters.isNotEmpty(),
        enter = fadeIn(animationSpec = tween(700)) + slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(700)
        ),
        exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(500)
        ),
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(characters) { character ->
                CharacterCard(
                    character = character,
                    onEditClick = { onEditClick(character) },
                    onDeleteClick = { onDeleteClick(character) }
                )
            }
        }
    }
}


@Composable
fun CharacterCard(
    character: Character,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(8.dp)
            .border(0.5.dp, Peach, shape = RoundedCornerShape(2.dp)),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(2.dp),
        colors = CardDefaults.cardColors(containerColor = DeepRed)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                AsyncImage(
                    model = character.ilustration,
                    contentDescription = character.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(width = 95.dp, height = 140.dp)
                        .border(0.8.dp, Peach, shape = RoundedCornerShape(2.dp))
                        .clip(RoundedCornerShape(2.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Clan: ${character.clan.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Generación: ${character.generation.name.replace("GEN_", "Gen ")}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }

            var expanded by remember { mutableStateOf(false) }

            IconButton(
                onClick = { expanded = true },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Más opciones",
                    tint = Color.White
                )
            }

            EditDeleteMenu(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
fun CustomAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    navController: NavController
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            containerColor = DeepRed.copy(alpha = 0.7f),
            shape = RoundedCornerShape(2.dp),
            tonalElevation = 0.dp,
            modifier = Modifier.border(1.dp, Peach, RoundedCornerShape(2.dp)),
            title = {
                CustomTitleText2(
                    title = stringResource(id = R.string.create_ch_dialog_text_es),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CustomDialogTextButtom(
                        onClick = {
                            onDismiss()
                            navController.navigate("globalCharacterSelection")
                        },
                        text = stringResource(id = R.string.create_existed_ch_dialog_text_es)
                    )

                    CustomDialogTextButtom(
                        onClick = {
                            onDismiss()
                            navController.navigate("characterCreation")
                        },
                        text = stringResource(id = R.string.create_custom_ch_dialog_text_es)
                    )
                }
            },
            confirmButton = {},
            dismissButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    CustomDetailButton(
                        onClick = onDismiss,
                        text = stringResource(id = R.string.cancel_text_es)
                    )
                }
            }
        )
    }
}

@Composable
fun EditDeleteMenu(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        IconButton(onClick = { onExpandedChange(true) }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Opciones",
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier.background(DeepRed.copy(alpha = 0.9f))
        ) {
            DropdownMenuItem(
                text = { Text("Editar", color = Peach) },
                onClick = {
                    onExpandedChange(false)
                    onEditClick()
                }
            )
            DropdownMenuItem(
                text = { Text("Eliminar", color = Peach) },
                onClick = {
                    onExpandedChange(false)
                    onDeleteClick()
                }
            )
        }
    }
}



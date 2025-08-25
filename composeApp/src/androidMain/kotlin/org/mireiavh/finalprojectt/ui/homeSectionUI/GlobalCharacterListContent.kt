package org.mireiavh.finalprojectt.ui.homeSectionUI

import androidx.compose.runtime.Composable
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.mireiavh.finalprojectt.domain.model.Character
import org.mireiavh.finalprojectt.utils.DeepRed
import org.mireiavh.finalprojectt.utils.LightBrown
import org.mireiavh.finalprojectt.utils.Peach

@Composable
fun GlobalCharacterListContent(
    globalCharacters: List<Character>,
    onCharacterClick: (Character) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AnimatedCharacterList(
            characters = globalCharacters,
            onMoreInfoClick = { character -> onCharacterClick(character) }
        )
    }
}

@Composable
fun AnimatedCharacterList(
    characters: List<Character>,
    modifier: Modifier = Modifier,
    onMoreInfoClick: (Character) -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(characters) {
        visible = false
        kotlinx.coroutines.delay(100)
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
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(700)) + slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight },
                        animationSpec = tween(700)
                    ),
                    exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(
                        targetOffsetY = { fullHeight -> fullHeight },
                        animationSpec = tween(500)
                    )
                ) {
                    CharacterCard(
                        character = character,
                        onMoreInfoClick = { onMoreInfoClick(character) }
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterCard(
    character: Character,
    onMoreInfoClick: () -> Unit
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
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = character.ilustration,
                contentDescription = character.name,
                modifier = Modifier
                    .size(width = 95.dp, height = 140.dp)
                    .border(0.8.dp, Peach, shape = RoundedCornerShape(2.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onMoreInfoClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(2.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightBrown,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Más información")
                }
            }
        }
    }
}

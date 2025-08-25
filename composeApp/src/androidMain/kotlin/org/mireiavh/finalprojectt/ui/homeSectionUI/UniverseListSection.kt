package org.mireiavh.finalprojectt.utils.customs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import org.mireiavh.finalprojectt.domain.model.Universe
import org.mireiavh.finalprojectt.utils.DeepRed
import org.mireiavh.finalprojectt.utils.LightBrown
import org.mireiavh.finalprojectt.utils.Peach

@Composable
fun UniverseListContent(
    universes: List<Universe>,
    onMoreInfoClick: (Universe) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AnimatedUniverseList(
            universes = universes,
            onMoreInfoClick = { universe ->
                onMoreInfoClick(universe)
            }
        )
    }
}

@Composable
fun AnimatedUniverseList(
    universes: List<Universe>,
    modifier: Modifier = Modifier,
    onMoreInfoClick: (Universe) -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(universes) {
        visible = false
        delay(100)
        visible = true
    }

    AnimatedVisibility(
        visible = visible && universes.isNotEmpty(),
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
            items(universes) { universe ->
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
                    UniverseCard(
                        universe = universe,
                        onMoreInfoClick = { onMoreInfoClick(universe) }
                    )
                }
            }
        }
    }
}

@Composable
fun UniverseCard(
    universe: Universe,
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
                model = universe.poster,
                contentDescription = universe.title,
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
                Spacer(modifier = Modifier.height(10.dp))
                CustomTitleText2(universe.title, TextAlign.Left)
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { onMoreInfoClick() },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(2.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightBrown,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Más información",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                }
            }
        }
    }
}

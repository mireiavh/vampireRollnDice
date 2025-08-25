package org.mireiavh.finalprojectt.ui.homeSectionUI

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.mireiavh.finalprojectt.domain.model.Manual
import org.mireiavh.finalprojectt.utils.customs.AnimatedManualList
import org.mireiavh.finalprojectt.domain.model.TagType
import org.mireiavh.finalprojectt.utils.DeepRed
import org.mireiavh.finalprojectt.utils.Peach
import org.mireiavh.finalprojectt.utils.customs.TagCard

@Composable
fun ManualListContent(
    manuals: List<Manual>,
    onMoreInfoClick: (Manual) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        FreeManualsRow(
            manuals = manuals,
            onMoreInfoClick = onMoreInfoClick
        )

        AnimatedManualList(
            manuals = manuals,
            onMoreInfoClick = { onMoreInfoClick(it) }
        )
    }
}

@Composable
fun FreeManualsRow(
    manuals: List<Manual>,
    onMoreInfoClick: (Manual) -> Unit
) {
    val freeManuals = manuals.filter { it.tags.contains(TagType.FREE) }

    if (freeManuals.isNotEmpty()) {
        var visible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            visible = true
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Manuales gratuitos",
                style = MaterialTheme.typography.body1,
                color = Color.White,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 4.dp)
            )

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(700)) + slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(700)
                ),
                exit = fadeOut(animationSpec = tween(500)) + slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(500)
                )
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                ) {
                    items(freeManuals) { manual ->
                        FreeManualCard(manual = manual, onClick = { onMoreInfoClick(manual) })
                    }
                }
            }
        }
    }
}


@Composable
fun FreeManualCard(
    manual: Manual,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(215.dp)
            .padding(8.dp)
            .clickable { onClick() }
            .border(0.5.dp, Peach, shape = RoundedCornerShape(2.dp)),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(2.dp),
        colors = CardDefaults.cardColors(containerColor = DeepRed)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = manual.poster,
                contentDescription = manual.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
                    .border(0.8.dp, Peach, shape = RoundedCornerShape(2.dp))
                    .clip(RoundedCornerShape(2.dp))
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                manual.tags
                    .filter { it == TagType.FREE }
                    .forEach { tag ->
                        TagCard(tag = tag)
                    }
            }
        }
    }
}



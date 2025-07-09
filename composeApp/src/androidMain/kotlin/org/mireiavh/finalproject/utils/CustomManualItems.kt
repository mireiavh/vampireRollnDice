package org.mireiavh.finalproject.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.mireiavh.finalproject.R
import org.mireiavh.finalproject.domain.model.Manual
import org.mireiavh.finalproject.domain.model.TagType
import org.mireiavh.finalproject.domain.model.getLabelRes

@Composable
fun ManualListContent(
    manuals: List<Manual>,
    searchQuery: MutableState<String>
) {
    val filteredManuals = manuals.filter {
        it.title.contains(searchQuery.value, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        AnimatedManualList(manuals = filteredManuals)
    }
}

@Composable
fun ManualCard(
    manual: Manual
) {
    val dummyTags = manual.tags
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(8.dp)
            .border(0.2.dp, Peach, shape = RoundedCornerShape(2.dp)),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(2.dp),
        colors = CardDefaults.cardColors(containerColor = DeepRed)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.rectangle),
                contentDescription = manual.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(width = 100.dp, height = 140.dp)
                    .border(0.4.dp, Peach, shape = RoundedCornerShape(2.dp))
                    .clip(RoundedCornerShape(2.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    dummyTags.forEach { tag ->
                        TagCard(tag = tag)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                CustomTitleText2(manual.title, TextAlign.Left)
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.2.dp, Peach, shape = RoundedCornerShape(2.dp)),
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


@Composable
fun TagCard(tag: TagType) {
    val tagLabel = stringResource(id = tag.getLabelRes())

    val backgroundColor = when (tag) {
        TagType.MASTER, TagType.PLAYER -> LightBrown
        TagType.NEW -> Crimson
        TagType.FREE -> Color(0xFF2E7D32) //verde
    }
    Card(
        shape = RoundedCornerShape(2.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Text(
            text = tagLabel,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun AnimatedManualList(
    manuals: List<Manual>,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = manuals.isNotEmpty(),
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
            items(manuals) { manual ->
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
                    ManualCard(manual = manual, )
                }
            }
        }
    }
}


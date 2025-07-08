package org.mireiavh.finalproject.utils

import android.media.Image
import android.nfc.Tag
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.mireiavh.finalproject.R
import org.mireiavh.finalproject.infrastructure.ManualViewModel
import org.mireiavh.finalproject.model.Manual
import org.mireiavh.finalproject.model.TagType
import org.mireiavh.finalproject.model.getLabelRes


@Composable
fun ManualListScreen(viewModel: ManualViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery = remember { mutableStateOf("") }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.errorMessage != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${uiState.errorMessage}")
            }
        }

        else -> {
            val filteredManuals = uiState.manuals.filter {
                it.title.contains(searchQuery.value, ignoreCase = true)
            }
            Column(modifier = Modifier.fillMaxSize()) {
                OutlinedTextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    label = { Text("Buscar recursos...", color = Color.White) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = LightBrown
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = LightBrown,
                        focusedBorderColor = DeepRed,
                        cursorColor = Peach,
                        focusedLabelColor = Peach,
                        unfocusedLabelColor = Color.White,
                        unfocusedTextColor = Color.Gray,
                        focusedTextColor = Color.White,
                        unfocusedContainerColor = DarkBrown,
                        focusedContainerColor = DarkBrown
                    )
                )
                AnimatedVisibility(
                    visible = filteredManuals.isNotEmpty(),
                    enter = fadeIn(animationSpec = tween(durationMillis = 700)) + slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight }, // comienza debajo del contenido (altura completa)
                        animationSpec = tween(durationMillis = 700)
                    ),
                    exit = fadeOut(animationSpec = tween(durationMillis = 500)) + slideOutVertically(
                        targetOffsetY = { fullHeight -> fullHeight }, // sale hacia abajo
                        animationSpec = tween(durationMillis = 500)
                    ),
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredManuals) { manual ->
                            AnimatedVisibility(
                                visible = filteredManuals.isNotEmpty(),
                                enter = fadeIn(animationSpec = tween(durationMillis = 700)) + slideInVertically(
                                    initialOffsetY = { fullHeight -> fullHeight }, // comienza debajo del contenido (altura completa)
                                    animationSpec = tween(durationMillis = 700)
                                ),
                                exit = fadeOut(animationSpec = tween(durationMillis = 500)) + slideOutVertically(
                                    targetOffsetY = { fullHeight -> fullHeight }, // sale hacia abajo
                                    animationSpec = tween(durationMillis = 500)
                                ),
                                modifier = Modifier.fillMaxSize()
                            ){
                                ManualCard(manual = manual)
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ManualCard(manual: Manual) {
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
                    onClick = { /* Acción */ },
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


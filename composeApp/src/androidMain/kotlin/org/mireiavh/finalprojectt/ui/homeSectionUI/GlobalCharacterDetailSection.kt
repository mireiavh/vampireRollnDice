package org.mireiavh.finalprojectt.ui.characterSectionUI

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.mireiavh.finalprojectt.infrastructure.viewmodels.GlobalCharacterViewModel
import org.mireiavh.finalprojectt.utils.DarkBrown
import org.mireiavh.finalprojectt.utils.Peach
import org.mireiavh.finalprojectt.utils.customs.CustomDivider

@Composable
fun GlobalCharacterDetailSection(viewModel: GlobalCharacterViewModel) {
    val selectedCharacter by viewModel.selectedCharacter.collectAsState()

    selectedCharacter?.let { character ->
        LazyColumn(modifier = Modifier.fillMaxSize().background(DarkBrown)) {
            item {
                Box(modifier = Modifier.fillMaxWidth().height(400.dp)) {
                    if (character.largeIlustration.isNotBlank()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(character.largeIlustration)
                                .crossfade(true)
                                .build(),
                            contentDescription = character.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            alpha = 0.5f,
                            onError = { error ->
                                Log.e("CharacterDetail", "Error loading bg image: ${error.result.throwable}")
                            }
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .align(Alignment.BottomCenter)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, DarkBrown),
                                    )
                                )
                        )

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(character.largeIlustration)
                                .crossfade(true)
                                .build(),
                            contentDescription = character.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(width = 200.dp, height = 300.dp)
                                .border(2.dp, Peach, shape = RoundedCornerShape(8.dp))
                                .clip(RoundedCornerShape(8.dp))
                                .align(Alignment.BottomCenter),
                            onError = { error ->
                                Log.e("CharacterDetail", "Error loading image: ${error.result.throwable}")
                            }
                        )
                    } else {
                        Text("No hay ilustración disponible", color = Color.Gray)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    DrawerText(character.name)
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    SectionHeader("Descripción")
                    DrawerText(character.backstory)
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                CustomDivider()
            }
        }
    } ?: Text("Error de carga.", color = Color.White)
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        color = Color.Gray,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun DrawerText(content: String) {
    Text(
        text = content,
        color = Color.White,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

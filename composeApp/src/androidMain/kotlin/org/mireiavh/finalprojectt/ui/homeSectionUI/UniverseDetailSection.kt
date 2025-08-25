package org.mireiavh.finalprojectt.ui.homeSectionUI

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.mireiavh.finalprojectt.infrastructure.viewmodels.UniverseViewModel
import org.mireiavh.finalprojectt.utils.DarkBrown
import org.mireiavh.finalprojectt.utils.DeepRed
import org.mireiavh.finalprojectt.utils.Peach
import org.mireiavh.finalprojectt.utils.customs.CustomDivider

@Composable
fun UniverseDetailSection(
    viewModel: UniverseViewModel
) {
    val universe by viewModel.selectedUniverse.collectAsState()

    universe?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBrown)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                ) {
                    if (universe!!.poster.isNotBlank()) {

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(universe!!.poster)
                                .crossfade(true)
                                .build(),
                            contentDescription = universe!!.title,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            alpha = 0.5f,
                            onError = { error ->
                                Log.e("ManualDetail", "Error loading image: ${error.result.throwable}")
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
                                .data(universe!!.poster)
                                .crossfade(true)
                                .build(),
                            contentDescription = universe!!.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(width = 200.dp, height = 300.dp)
                                .border(2.dp, Peach, shape = RoundedCornerShape(4.dp))
                                .clip(RoundedCornerShape(4.dp))
                                .align(Alignment.BottomCenter),
                            onError = { error ->
                                Log.e("ManualDetail", "Error loading image: ${error.result.throwable}")
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

                    } else {
                        Text("No hay imagen disponible", color = Color.Gray)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }


            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    DrawerText(universe!!.title)
                }
            }

            item { Spacer(modifier = Modifier.height(10.dp)) }


            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth().padding(horizontal = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        SectionHeader("Descripcion")
                        DrawerText(universe!!.description)
                    }
                }
            }

//            item{
//                Spacer(modifier = Modifier.height(16.dp))
//                CustomDivider()
//                Spacer(modifier = Modifier.height(8.dp))
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 24.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Button(
//                        onClick = { Log.d("UniverseDetail", "Comprar click") },
//                        modifier = Modifier.fillMaxWidth(),
//                        shape = RoundedCornerShape(8.dp),
//                        colors = ButtonDefaults.buttonColors(containerColor = DeepRed, contentColor = Color.White)
//                    ) {
//                        Text(text = "Comprar Manual")
//                    }
//                }
//            }
        }
    } ?: Text("Cargando recurso...", color = Color.White)
}

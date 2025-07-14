package org.mireiavh.finalprojectt.ui.HomeSection

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ErrorResult
import coil.request.ImageRequest
import org.mireiavh.finalprojectt.R
import org.mireiavh.finalprojectt.infrastructure.controllers.ManualViewModel
import org.mireiavh.finalprojectt.utils.DarkBrown
import org.mireiavh.finalprojectt.utils.Peach
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun ManualDetailSection(viewModel: ManualViewModel) {
    val manual by viewModel.selectedManual.collectAsState()

    manual?.let { manual ->

        val context = LocalContext.current

        val imageRequest = ImageRequest.Builder(context)
            .data(URLDecoder.decode(manual.poster, StandardCharsets.UTF_8.name()))
            .crossfade(true)
            .listener(
                onError = { _, result ->
                    val error = (result as? ErrorResult)?.throwable
                    Log.e("mine error", "Error loading image", error)
                },
                onSuccess = { _, _ ->
                    Log.d("mine success", "Imagen cargada correctamente")
                }
            )
            .build()

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (manual.poster.isBlank()) {
                    Text("No hay imagen disponible")
                } else {
                    AsyncImage(
                        model = imageRequest,
                        contentDescription = manual.title,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(width = 100.dp, height = 140.dp)
                            .border(0.4.dp, Peach, shape = RoundedCornerShape(2.dp))
                            .clip(RoundedCornerShape(2.dp))
                    )
                }

                Log.d("mine", "JSON del manual: " + manual.poster)
                Text(text = manual.title)
                Text(text = manual.description)
            }
        }
    } ?: Text("Cargando manual...")
}
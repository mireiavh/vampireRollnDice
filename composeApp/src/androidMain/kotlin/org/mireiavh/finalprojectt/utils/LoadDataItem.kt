package org.mireiavh.finalprojectt.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun LoadDataExample() {
    var data by remember { mutableStateOf("Cargando") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(5000)
        isLoading = false
        data = "Datos cargados"
    }

    val animatedDots = getAnimatedDots(isLoading)

    Box(contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = DeepRed,
                    strokeWidth = 6.dp,
                    modifier = Modifier.scale(2.5f)
                )

            }
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = data + animatedDots,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun getAnimatedDots(isLoading: Boolean): String {
    var dots by remember { mutableStateOf("") }
    LaunchedEffect(isLoading) {
        while (isLoading) {
            dots = when (dots) {
                "" -> "."
                "." -> ".."
                ".." -> "..."
                else -> ""
            }
            delay(600)
        }
    }
    return dots
}
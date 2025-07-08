package org.mireiavh.finalproject.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.mireiavh.finalproject.R
import org.mireiavh.finalproject.infrastructure.ManualViewModel
import org.mireiavh.finalproject.utils.DarkBrown
import org.mireiavh.finalproject.utils.ManualListScreen

@Composable
fun HomeView(viewModel: ManualViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBrown)
    ) {
        Image(
            painter = painterResource(id = R.drawable.initial_cover_vampire),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    alpha = 0.08f
                },
            contentScale = ContentScale.Crop,
            colorFilter = null
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ManualListScreen(viewModel = viewModel)
        }
    }
}
package org.mireiavh.finalprojectt.ui

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
import androidx.navigation.NavController
import org.mireiavh.finalprojectt.R
import org.mireiavh.finalprojectt.infrastructure.controllers.ManualViewModel
import org.mireiavh.finalprojectt.ui.HomeSection.HomeMenuSection
import org.mireiavh.finalprojectt.domain.model.Manual
import org.mireiavh.finalprojectt.utils.DarkBrown

@Composable
fun HomeView(
    viewModel: ManualViewModel,
    onMoreInfoClick: (Manual) -> Unit,
    navController : NavController
) {

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
            HomeMenuSection(
                viewModel = viewModel,
                onMoreInfoClick = onMoreInfoClick,
                navController = navController
            )
        }
    }
}
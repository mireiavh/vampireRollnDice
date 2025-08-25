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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.mireiavh.finalprojectt.R
import org.mireiavh.finalprojectt.domain.model.Character
import org.mireiavh.finalprojectt.infrastructure.viewmodels.ManualViewModel
import org.mireiavh.finalprojectt.ui.homeSectionUI.HomeMenuSection
import org.mireiavh.finalprojectt.domain.model.Manual
import org.mireiavh.finalprojectt.domain.model.Universe
import org.mireiavh.finalprojectt.infrastructure.viewmodels.GlobalCharacterViewModel
import org.mireiavh.finalprojectt.infrastructure.viewmodels.UniverseViewModel
import org.mireiavh.finalprojectt.utils.DarkBrown

@Composable
fun HomeView(
    manualViewModel: ManualViewModel,
    characterHomeViewModel: GlobalCharacterViewModel,
    universeViewModel: UniverseViewModel,
    onMoreInfoClick: (Manual) -> Unit,
    onMoreInfoUniverseClick: (Universe) -> Unit,
    onMoreInfoCharacterClick: (Character) -> Unit,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBrown)
    ) {
        Image(
            painter = painterResource(id = R.drawable.initial_cover_vampire2),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.4f },
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            DarkBrown,
                            DarkBrown.copy(alpha = 0f),
                            DarkBrown
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HomeMenuSection(
                manualViewModel = manualViewModel,
                universeViewModel = universeViewModel,
                globalCharacterViewModel = characterHomeViewModel,
                onMoreInfoClick = onMoreInfoClick,
                onMoreInfoUniverseClick = onMoreInfoUniverseClick,
                onMoreInfoCharacterClick = onMoreInfoCharacterClick,
                navController = navController
            )
        }
    }
}
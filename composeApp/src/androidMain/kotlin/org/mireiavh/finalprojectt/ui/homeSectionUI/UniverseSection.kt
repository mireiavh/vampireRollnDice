package org.mireiavh.finalprojectt.ui.homeSectionUI

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.mireiavh.finalprojectt.domain.model.Universe
import org.mireiavh.finalprojectt.infrastructure.viewmodels.UniverseViewModel
import org.mireiavh.finalprojectt.utils.customs.UniverseListContent

@Composable
fun UniverseSection(
    universeViewModel: UniverseViewModel,
    searchQuery: String,
    onMoreInfoClick: (Universe) -> Unit
) {
    val uiState by universeViewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> CircularProgressIndicator()
        uiState.errorMessage != null -> Text("Error: ${uiState.errorMessage}")
        else -> {
            val filtered = uiState.universes.filter {
                it.title.contains(searchQuery, ignoreCase = true)
            }
            UniverseListContent(
                universes = filtered,
                onMoreInfoClick = onMoreInfoClick
            )
        }
    }
}

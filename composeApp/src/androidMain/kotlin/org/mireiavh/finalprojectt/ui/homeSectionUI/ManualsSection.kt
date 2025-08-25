package org.mireiavh.finalprojectt.ui.homeSectionUI

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.mireiavh.finalprojectt.domain.model.Manual
import org.mireiavh.finalprojectt.infrastructure.viewmodels.ManualViewModel
import org.mireiavh.finalprojectt.utils.customs.ManualListContent

@Composable
fun ManualsSection(
    viewModel: ManualViewModel,
    searchQuery: String,
    onMoreInfoClick: (Manual) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> CircularProgressIndicator()
        uiState.errorMessage != null -> Text("Error: ${uiState.errorMessage}")
        else -> {
            val filtered = uiState.manuals.filter {
                it.title.contains(searchQuery, ignoreCase = true)
            }
            ManualListContent(
                manuals = filtered,
                onMoreInfoClick = onMoreInfoClick
            )
        }
    }
}
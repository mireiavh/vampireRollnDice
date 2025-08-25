package org.mireiavh.finalprojectt.ui.homeSectionUI

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.mireiavh.finalprojectt.infrastructure.viewmodels.GlobalCharacterViewModel
import androidx.compose.material3.CircularProgressIndicator
import org.mireiavh.finalprojectt.domain.model.Character

@Composable
fun GlobalCharacterSection(
    viewModel: GlobalCharacterViewModel,
    searchQuery: String,
    onCharacterClick: (Character) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> CircularProgressIndicator()
        uiState.errorMessage != null -> Text("Error: ${uiState.errorMessage}")
        else -> {
            val filtered = uiState.characters.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
            GlobalCharacterListContent(
                globalCharacters = filtered,
                onCharacterClick = onCharacterClick
            )
        }
    }
}

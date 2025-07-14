package org.mireiavh.finalprojectt.ui.HomeSection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.mireiavh.finalprojectt.domain.model.Manual
import org.mireiavh.finalprojectt.infrastructure.controllers.ManualViewModel
import org.mireiavh.finalprojectt.utils.ManualListContent
import org.mireiavh.finalprojectt.utils.SearchAndFilterSection

@Composable
fun HomeMenuSection(
    viewModel: ManualViewModel,
    onMoreInfoClick: (Manual) -> Unit,
    navController : NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery = remember { mutableStateOf("") }
    val selectedFilter = remember { mutableStateOf("Todos") }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        uiState.errorMessage != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${uiState.errorMessage}")
            }
        }
        else -> {
            val filteredManuals = when (selectedFilter.value) {
                "Manuales" -> uiState.manuals
                "Personajes" -> uiState.manuals
                "Universo" -> uiState.manuals
                else -> emptyList()
            }.filter {
                it.title.contains(searchQuery.value, ignoreCase = true)
            }

            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                SearchAndFilterSection(
                    searchQuery = searchQuery,
                    selectedFilter = selectedFilter
                )
                ManualListContent(
                    manuals = filteredManuals,
                    searchQuery = searchQuery,
                    onMoreInfoClick = onMoreInfoClick,
                    navController = navController
                )
            }
        }
    }
}
package org.mireiavh.finalprojectt.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.dp

enum class ResourceType {
    MANUAL,
    TUTORIAL,
    VIDEO
}

data class Resource(
    val id: Int,
    val title: String,
    val type: ResourceType,
    val content: String
)

@Composable
fun HomeScreen() {
    FilteredResourceScreen(
        allResources = listOf(
            Resource(1, "Manual de seguridad", ResourceType.MANUAL, "Contenido..."),
            Resource(2, "Video introductorio", ResourceType.VIDEO, "URL..."),
            Resource(3, "Tutorial de uso", ResourceType.TUTORIAL, "Contenido...")
        )
    )
}

@Composable
fun ResourceFilterSelector(
    selectedType: ResourceType,
    onTypeSelected: (ResourceType) -> Unit
) {
    Column {
        ResourceType.entries.forEach { type ->
            Button(onClick = { onTypeSelected(type) }) {
                Text(text = type.name)
            }
        }
    }
}

@Composable
fun AnimatedResourceList(resources: List<Resource>) {
    LazyColumn {
        items(resources) { resource ->
            Text(text = resource.title, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun FilteredResourceScreen(allResources: List<Resource>) {
    val searchQuery = remember { mutableStateOf("") }
    val selectedType = remember { mutableStateOf(ResourceType.MANUAL) }

    val filteredResources = remember(searchQuery.value, selectedType.value) {
        allResources.filter {
            it.type == selectedType.value &&
                    it.title.contains(searchQuery.value, ignoreCase = true)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ResourceFilterSelector(
            selectedType = selectedType.value,
            onTypeSelected = { selectedType.value = it }
        )

        SearchTextField(searchQuery = searchQuery)

        AnimatedResourceList(resources = filteredResources)
    }
}

@Composable
fun SearchAndFilterSection(
    searchQuery: MutableState<String>,
    selectedFilter: MutableState<String>,
    filters: List<String> = listOf("Manuales", "Universo Vampiro"),
    onFilterSelected: (String) -> Unit = { selectedFilter.value = it }
) {
    Column {
        SearchTextField(searchQuery = searchQuery)

        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            filters.forEach { filter ->
                Button(
                    onClick = { onFilterSelected(filter) },
                    modifier = Modifier
                        .padding(end = 3.dp),
                    shape = RoundedCornerShape(0.3.dp),
                    colors = if (selectedFilter.value == filter) {
                        ButtonDefaults.buttonColors(containerColor = DeepRed)
                    } else {
                        ButtonDefaults.buttonColors(containerColor = LightBrown)
                    }
                ) {
                    Text(filter)
                }
            }
        }
    }
}


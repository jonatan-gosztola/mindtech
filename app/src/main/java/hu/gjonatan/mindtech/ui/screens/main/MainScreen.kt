package hu.gjonatan.mindtech.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.gjonatan.mindtech.data.models.PokemonLink
import hu.gjonatan.mindtech.ui.theme.MindtechTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    detailsButtonClicked: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainScreenContent(
        uiState = uiState,
        onTypeSelected = viewModel::onTypeSelected,
        onSearchQueryUpdate = viewModel::onSearchQueryUpdate,
        onRefreshClick = viewModel::onRefreshClick,
        detailsButtonClicked = detailsButtonClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenContent(
    uiState: MainScreenUiState,
    onTypeSelected: (String) -> Unit,
    onSearchQueryUpdate: (String) -> Unit,
    detailsButtonClicked: (String) -> Unit,
    onRefreshClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        when (uiState) {
            is MainScreenUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(150.dp)
                )
            }

            is MainScreenUiState.Error -> {
                ErrorContent(
                    onRefreshClick = onRefreshClick
                )
            }

            is MainScreenUiState.Default -> {
                DefaultContent(uiState, onTypeSelected, onSearchQueryUpdate, detailsButtonClicked)
            }
        }
    }
}

@Composable
fun ErrorContent(
    onRefreshClick: () -> Unit
) {
    Text("Something went wrong")
    Button(onClick = onRefreshClick) {
        Text("Refresh")
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DefaultContent(
    uiState: MainScreenUiState.Default,
    onTypeSelected: (String) -> Unit,
    onSearchQueryUpdate: (String) -> Unit,
    detailsButtonClicked: (String) -> Unit
) {
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = uiState.searchQuery,
                onQueryChange = onSearchQueryUpdate,
                onSearch = {},
                expanded = false,
                onExpandedChange = {},
                placeholder = { Text("Search Pokemon") },
            )
        },
        expanded = false,
        onExpandedChange = {},
        modifier = Modifier.fillMaxWidth()
    ) {}

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        OutlinedTextField(
            value = uiState.selectedType ?: "Select a type",
            onValueChange = {},
            readOnly = true,
            label = { Text("Pokemon Type") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            uiState.types.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }

    LazyColumn {
        items(uiState.pokemons) { pokemon ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(pokemon.name)

                Button(onClick = { detailsButtonClicked(pokemon.name) }) {
                    Text("Details")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenContentPreview() {
    MindtechTheme {
        MainScreenContent(
            uiState = MainScreenUiState.Default(
                types = persistentListOf("fire", "water", "grass"),
                selectedType = "fire",
                searchQuery = "",
                pokemons = persistentListOf(
                    PokemonLink("Bulbasaur", "asd"),
                    PokemonLink("Ivysaur", "asd"),
                    PokemonLink("Venusaur", "asd")
                )
            ),
            onTypeSelected = {},
            onSearchQueryUpdate = {},
            detailsButtonClicked = {},
            onRefreshClick = {},
        )
    }
}

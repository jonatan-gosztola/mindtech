package hu.gjonatan.mindtech.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import hu.gjonatan.mindtech.data.models.Pokemon
import hu.gjonatan.mindtech.ui.components.ErrorContent
import hu.gjonatan.mindtech.ui.components.LoadingIndicator
import hu.gjonatan.mindtech.ui.theme.MindtechTheme

@Composable
fun DetailsScreen(viewModel: DetailsScreenViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetailsScreenContent(
        uiState = uiState,
        onRefreshClick = viewModel::onRefreshClick
    )
}

@Composable
private fun DetailsScreenContent(
    uiState: DetailsScreenUiState,
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
            is DetailsScreenUiState.Error -> {
                ErrorContent(
                    onRefreshClick = onRefreshClick
                )
            }

            is DetailsScreenUiState.Loading -> {
                LoadingIndicator()
            }

            is DetailsScreenUiState.Default -> {
                DefaultContent(pokemon = uiState.pokemon)
            }
        }
    }
}

@Composable
private fun DefaultContent(pokemon: Pokemon) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (pokemon.imageUrl != null) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = pokemon.imageUrl,
                    contentDescription = "${pokemon.name} image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        PropertyRow(label = "Name", value = pokemon.name.replaceFirstChar { it.uppercase() })
        PropertyRow(label = "Weight", value = "${pokemon.weight}")
        PropertyRow(label = "Height", value = "${pokemon.height}")
        PropertyRow(label = "Type", value = pokemon.type.replaceFirstChar { it.uppercase() })
        PropertyRow(
            label = "Abilities",
            value = pokemon.abilities.joinToString(", ") { it.replaceFirstChar { char -> char.uppercase() } })
    }
}

@Composable
private fun PropertyRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value
        )
    }
}

class DetailsScreenUiStateProvider : PreviewParameterProvider<DetailsScreenUiState> {
    override val values = sequenceOf(
        DetailsScreenUiState.Default(
            pokemon = Pokemon(
                id = "1",
                name = "Bulbasaur",
                type = "Grass",
                weight = 69,
                height = 7,
                abilities = listOf("Overgrow", "Chlorophyll"),
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
            )
        ),
        DetailsScreenUiState.Loading,
        DetailsScreenUiState.Error
    )
}

@Preview(showBackground = true)
@Composable
private fun DetailsScreenContentPreview(
    @PreviewParameter(DetailsScreenUiStateProvider::class) uiState: DetailsScreenUiState
) {
    MindtechTheme {
        DetailsScreenContent(
            uiState = uiState,
            onRefreshClick = {}
        )
    }
}

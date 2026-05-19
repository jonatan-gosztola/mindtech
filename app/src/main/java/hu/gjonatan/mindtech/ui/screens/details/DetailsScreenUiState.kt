package hu.gjonatan.mindtech.ui.screens.details

import hu.gjonatan.mindtech.data.models.Pokemon

sealed class DetailsScreenUiState {
    data class Default(
        val pokemon: Pokemon
    ): DetailsScreenUiState()
    data object Loading: DetailsScreenUiState()

    data object Error: DetailsScreenUiState()
}
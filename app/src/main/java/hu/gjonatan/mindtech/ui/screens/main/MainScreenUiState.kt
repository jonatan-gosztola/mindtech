package hu.gjonatan.mindtech.ui.screens.main

import hu.gjonatan.mindtech.data.models.PokemonLink
import kotlinx.collections.immutable.ImmutableList

sealed class MainScreenUiState {
    data class Default(
        val types: ImmutableList<String>,
        val selectedType: String? = null,
        val searchQuery: String = "",
        val pokemons: ImmutableList<PokemonLink>
    ): MainScreenUiState()

    data object Loading : MainScreenUiState()

    data object Error:  MainScreenUiState()
}
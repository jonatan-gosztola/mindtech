package hu.gjonatan.mindtech.ui.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.gjonatan.mindtech.data.DataRepository
import hu.gjonatan.mindtech.data.models.Pokemon
import hu.gjonatan.mindtech.data.models.PokemonLink
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainScreenViewModel(dataRepository: DataRepository): ViewModel() {

    val types: StateFlow<List<String>?> = dataRepository.getTypes()
        .onEach {
            Log.d("MainScreenViewModel", "types: $it")
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val pokemonsByType: StateFlow<List<PokemonLink>?> = dataRepository.getPokemonsByType()
        .onEach {
            Log.d("MainScreenViewModel", "pokemons by type: $it")
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val selectedPokemon: StateFlow<Pokemon?> = dataRepository.getSelectedPokemonDetails()
        .onEach {
            Log.d("MainScreenViewModel", "selected pokemon: $it")
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    init {
        viewModelScope.launch {
            dataRepository.fetchTypes()
        }

        viewModelScope.launch {
            dataRepository.fetchPokemonsByType("fire")
        }

        viewModelScope.launch {
            dataRepository.selectAndFetchPokemon("1")
        }
    }
}

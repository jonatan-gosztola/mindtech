package hu.gjonatan.mindtech.ui.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.gjonatan.mindtech.data.DataRepository
import hu.gjonatan.mindtech.data.models.PokemonLink
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val types: StateFlow<List<String>?> = dataRepository.getTypes()
        .onEach {
            Log.d("MainScreenViewModel", "types: $it")
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val selectedType: MutableStateFlow<String?> = MutableStateFlow(null)

    private val searchQuery = MutableStateFlow("")

    private val pokemonsByType: StateFlow<List<PokemonLink>?> = dataRepository.getPokemonsByType()
        .onEach {
            Log.d("MainScreenViewModel", "pokemons by type: $it")
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val defaultUiState: StateFlow<MainScreenUiState> = combine(
        types,
        selectedType,
        pokemonsByType,
        searchQuery
    ) { types, selectedType, pokemonsByType, searchQuery ->
        val filteredPokemons = pokemonsByType?.filter {
            it.name.contains(searchQuery, ignoreCase = true)
        }?.toImmutableList() ?: persistentListOf()

        MainScreenUiState.Default(
            types = types?.toImmutableList() ?: persistentListOf(),
            selectedType = selectedType,
            searchQuery = searchQuery,
            pokemons = filteredPokemons
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainScreenUiState.Loading)

    private val error = MutableStateFlow(false)
    private val loading = MutableStateFlow(false)

    val uiState: StateFlow<MainScreenUiState> = combine(
        loading,
        error,
        defaultUiState
    ) { loading, error, default ->
        when {
            loading -> MainScreenUiState.Loading
            error -> MainScreenUiState.Error
            else -> default
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainScreenUiState.Loading)

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                error.value = false
                loading.value = true

                dataRepository.fetchTypes()
            } catch (exception: Exception) {
                Log.e("MainScreenViewModel", "Error fetching types", exception)
                error.value = true
            } finally {
                loading.value = false
            }
        }
    }

    fun onTypeSelected(type: String) {
        selectedType.value = type
        viewModelScope.launch {
            dataRepository.fetchPokemonsByType(type)
        }
    }

    fun onSearchQueryUpdate(query: String) {
        searchQuery.value = query
    }

    fun onRefreshClick() {
        fetchData()
    }
}

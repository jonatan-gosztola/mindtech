package hu.gjonatan.mindtech.ui.screens.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.gjonatan.mindtech.data.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailsScreenViewModel(
    private val name: String,
    private val dataRepository: DataRepository
): ViewModel() {

    val pokemon = dataRepository.getSelectedPokemonDetails()
        .onEach {
            Log.d("DetailsScreenViewModel", "pokemon: $it")
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val error = MutableStateFlow(false)

    private val loading = MutableStateFlow(false)

    val uiState = combine(
        loading,
        error,
        pokemon
    ) { loading, error, pokemon ->
        when {
            loading -> DetailsScreenUiState.Loading
            error -> DetailsScreenUiState.Error
            pokemon != null -> DetailsScreenUiState.Default(pokemon)
            else -> DetailsScreenUiState.Error
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DetailsScreenUiState.Loading)

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                loading.value = true
                error.value = false
                dataRepository.selectAndFetchPokemon(name)
            } catch (exception: Exception) {
                error.value = true
                Log.e("DetailsScreenViewModel", "Error fetching types", exception)
            } finally {
                loading.value = false
            }
        }
    }

    fun onRefreshClick() {
        fetchData()
    }
}

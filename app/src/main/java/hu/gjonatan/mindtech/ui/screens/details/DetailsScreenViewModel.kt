package hu.gjonatan.mindtech.ui.screens.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.gjonatan.mindtech.data.DataRepository
import kotlinx.coroutines.flow.SharingStarted
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

    init {
        viewModelScope.launch {
            dataRepository.selectAndFetchPokemon(name)
        }
    }
}

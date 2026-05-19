package hu.gjonatan.mindtech.data

import hu.gjonatan.mindtech.data.models.Pokemon
import hu.gjonatan.mindtech.data.models.PokemonLink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class DefaultDataRepository(
    private val apiDataSource: ApiDataSource
): DataRepository {

    private val _types: MutableStateFlow<List<String>?> = MutableStateFlow(null)

    private val _pokemon: MutableStateFlow<Pokemon?> = MutableStateFlow(null)

    private val _pokemonsByType: MutableStateFlow<List<PokemonLink>?> = MutableStateFlow(null)

    override fun getTypes(): Flow<List<String>?> = _types.asStateFlow()

    override fun getSelectedPokemonDetails(): Flow<Pokemon?> = _pokemon.asStateFlow()

    override fun getPokemonsByType(): Flow<List<PokemonLink>?> = _pokemonsByType.asStateFlow()

    override suspend fun fetchTypes() {
        withContext(Dispatchers.IO) {
            val types = apiDataSource.fetchTypes()

            _types.value = types
        }
    }

    override suspend fun selectAndFetchPokemon(id: String) {
        withContext(Dispatchers.IO) {
            _pokemon.value = apiDataSource.fetchPokemon(id)
        }
    }

    override suspend fun fetchPokemonsByType(type: String) {
        withContext(Dispatchers.IO) {
            val pokemonsByType = apiDataSource.fetchPokemonsByType(type)
            _pokemonsByType.value = pokemonsByType
        }
    }
}

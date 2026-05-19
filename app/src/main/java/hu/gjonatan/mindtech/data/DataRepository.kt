package hu.gjonatan.mindtech.data

import hu.gjonatan.mindtech.data.models.Pokemon
import hu.gjonatan.mindtech.data.models.PokemonLink
import kotlinx.coroutines.flow.Flow

interface DataRepository {

    suspend fun fetchTypes()

    suspend fun selectAndFetchPokemon(id: String)

    suspend fun fetchPokemonsByType(type: String)

    fun getTypes(): Flow<List<String>?>

    fun getSelectedPokemonDetails(): Flow<Pokemon?>

    fun getPokemonsByType(): Flow<List<PokemonLink>?>
}

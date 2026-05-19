package hu.gjonatan.mindtech.data

import hu.gjonatan.mindtech.data.models.NamedApiResourceList
import hu.gjonatan.mindtech.data.models.Pokemon
import hu.gjonatan.mindtech.data.models.PokemonLink
import hu.gjonatan.mindtech.data.models.PokemonResponse
import hu.gjonatan.mindtech.data.models.TypeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiDataSource(
    private val client: HttpClient
) {

    private val BASE_URL = "https://pokeapi.co/api/v2/"

    suspend fun fetchTypes(): List<String> {
        val response = client.get(
            urlString = BASE_URL + "type"
        ).body<NamedApiResourceList>()

        return response.results.map { it.name }
    }

    suspend fun fetchPokemonsByType(type: String): List<PokemonLink> {
        val response = client.get(
            urlString = BASE_URL + "type/$type"
        ).body<TypeResponse>()

        return response.pokemon.map { entry ->
            PokemonLink(
                name = entry.pokemon.name,
                detailsUrl = entry.pokemon.url
            )
        }
    }

    suspend fun fetchPokemon(id: String): Pokemon {
        val response = client.get(
            urlString = BASE_URL + "pokemon/$id"
        ).body<PokemonResponse>()

        return Pokemon(
            id = response.id.toString(),
            name = response.name,
            weight = response.weight,
            height = response.height,
            type = response.types.firstOrNull()?.type?.name ?: "unknown",
            abilities = response.abilities.map { it.ability.name },
            imageUrl = response.sprites.other.officialArtwork.frontDefault
        )
    }

}

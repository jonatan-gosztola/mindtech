package hu.gjonatan.mindtech.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PokemonApiModel(
    val id: String,
    val type: String,
    val name: String,
    val isCaught: Boolean = false,
    val weight: Int,
    val height: Int,
    val abilities: List<String>,
    // sprites.other["official-artwork"].front_default
    val imageUrl: String? = null
)

@Serializable
data class NamedApiResource(
    val name: String,
    val url: String
)

@Serializable
data class NamedApiResourceList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<NamedApiResource>
)

@Serializable
data class TypeResponse(
    val pokemon: List<TypePokemonEntry>
)

@Serializable
data class TypePokemonEntry(
    val pokemon: NamedApiResource,
    val slot: Int
)

@Serializable
data class PokemonResponse(
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val types: List<PokemonTypeDto>,
    val abilities: List<PokemonAbilityDto>,
    val sprites: PokemonSpritesDto
)

@Serializable
data class PokemonTypeDto(
    val type: NamedApiResource
)

@Serializable
data class PokemonAbilityDto(
    val ability: NamedApiResource
)

@Serializable
data class PokemonSpritesDto(
    val other: PokemonOtherSpritesDto
)

@Serializable
data class PokemonOtherSpritesDto(
    @SerialName("official-artwork")
    val officialArtwork: PokemonOfficialArtworkDto
)

@Serializable
data class PokemonOfficialArtworkDto(
    @SerialName("front_default")
    val frontDefault: String?
)



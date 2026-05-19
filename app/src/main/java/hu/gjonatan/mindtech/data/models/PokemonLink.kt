package hu.gjonatan.mindtech.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonLink(
    val name: String,
    @SerialName("url")
    val detailsUrl: String,
)

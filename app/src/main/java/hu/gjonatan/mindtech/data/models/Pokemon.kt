package hu.gjonatan.mindtech.data.models

data class Pokemon(
    val id: String,
    val type: String,
    val name: String,
    val isCaught: Boolean = false,
    val weight: Int,
    val height: Int,
    val abilities: List<String>,
    val imageUrl: String? = null
)
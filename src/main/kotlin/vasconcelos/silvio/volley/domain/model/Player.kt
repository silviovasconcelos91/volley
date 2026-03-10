package vasconcelos.silvio.volley.domain.model

data class Player(
    val id: Long? = null,
    val name: String,
    val surname: String,
    val email: String,
    val age: Int,
    val level: Level,
    val category: Category
)
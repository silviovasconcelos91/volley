package vasconcelos.silvio.volley.domain.model

data class Team(
    val id: Long? = null,
    val name: String,
    val captainId: Long,
    val players: List<Player> = emptyList()
)
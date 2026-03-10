package vasconcelos.silvio.volley.infrastructure.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("team_players")
data class TeamPlayerEntity(
    @Id val id: Long? = null,
    val teamId: Long,
    val playerId: Long
)
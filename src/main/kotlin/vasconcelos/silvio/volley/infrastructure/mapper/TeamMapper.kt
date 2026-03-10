package vasconcelos.silvio.volley.infrastructure.mapper

import org.springframework.stereotype.Component
import vasconcelos.silvio.volley.domain.model.Player
import vasconcelos.silvio.volley.domain.model.Team
import vasconcelos.silvio.volley.infrastructure.persistence.TeamEntity

@Component
class TeamMapper {

    fun toDomain(entity: TeamEntity, players: List<Player>) = Team(
        id = entity.id,
        name = entity.name,
        captainId = entity.captainId,
        players = players
    )

    fun toEntity(team: Team) = TeamEntity(
        id = team.id,
        name = team.name,
        captainId = team.captainId
    )
}
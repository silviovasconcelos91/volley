package vasconcelos.silvio.volley.infrastructure.persistence

import org.springframework.stereotype.Repository
import vasconcelos.silvio.volley.domain.model.Team
import vasconcelos.silvio.volley.domain.repository.TeamRepository
import vasconcelos.silvio.volley.infrastructure.mapper.PlayerMapper
import vasconcelos.silvio.volley.infrastructure.mapper.TeamMapper

@Repository
class TeamRepositoryAdapter(
    private val springDataTeamRepository: SpringDataTeamRepository,
    private val springDataTeamPlayerRepository: SpringDataTeamPlayerRepository,
    private val springDataPlayerRepository: SpringDataPlayerRepository,
    private val teamMapper: TeamMapper,
    private val playerMapper: PlayerMapper
) : TeamRepository {

    override fun findAll(): List<Team> =
        springDataTeamRepository.findAll().map { loadTeamWithPlayers(it) }

    override fun findById(id: Long): Team? =
        springDataTeamRepository.findById(id).orElse(null)?.let { loadTeamWithPlayers(it) }

    override fun save(team: Team): Team {
        val saved = springDataTeamRepository.save(teamMapper.toEntity(team))
        return loadTeamWithPlayers(saved)
    }

    override fun deleteById(id: Long) {
        springDataTeamPlayerRepository.deleteByTeamId(id)
        springDataTeamRepository.deleteById(id)
    }

    override fun addPlayer(teamId: Long, playerId: Long) {
        springDataTeamPlayerRepository.save(TeamPlayerEntity(teamId = teamId, playerId = playerId))
    }

    override fun removePlayer(teamId: Long, playerId: Long) {
        springDataTeamPlayerRepository.deleteByTeamIdAndPlayerId(teamId, playerId)
    }

    private fun loadTeamWithPlayers(entity: TeamEntity): Team {
        val playerIds = springDataTeamPlayerRepository.findPlayerIdsByTeamId(entity.id!!)
        val players = playerIds.mapNotNull { springDataPlayerRepository.findById(it).orElse(null) }
            .map { playerMapper.toDomain(it) }
        return teamMapper.toDomain(entity, players)
    }
}
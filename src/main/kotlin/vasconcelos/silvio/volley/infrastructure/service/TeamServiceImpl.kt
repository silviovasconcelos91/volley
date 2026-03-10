package vasconcelos.silvio.volley.infrastructure.service

import org.springframework.stereotype.Service
import vasconcelos.silvio.volley.domain.model.Team
import vasconcelos.silvio.volley.domain.repository.PlayerRepository
import vasconcelos.silvio.volley.domain.repository.TeamRepository
import vasconcelos.silvio.volley.domain.service.TeamService

@Service
class TeamServiceImpl(
    private val teamRepository: TeamRepository,
    private val playerRepository: PlayerRepository
) : TeamService {

    override fun findAll(): List<Team> = teamRepository.findAll()

    override fun findById(id: Long): Team? = teamRepository.findById(id)

    override fun create(team: Team): Team {
        playerRepository.findById(team.captainId)
            ?: throw NoSuchElementException("Captain with id ${team.captainId} not found")
        return teamRepository.save(team)
    }

    override fun update(id: Long, team: Team): Team {
        teamRepository.findById(id)
            ?: throw NoSuchElementException("Team with id $id not found")
        playerRepository.findById(team.captainId)
            ?: throw NoSuchElementException("Captain with id ${team.captainId} not found")
        return teamRepository.save(team.copy(id = id))
    }

    override fun delete(id: Long) {
        teamRepository.findById(id)
            ?: throw NoSuchElementException("Team with id $id not found")
        teamRepository.deleteById(id)
    }

    override fun addPlayer(teamId: Long, playerId: Long): Team {
        teamRepository.findById(teamId)
            ?: throw NoSuchElementException("Team with id $teamId not found")
        playerRepository.findById(playerId)
            ?: throw NoSuchElementException("Player with id $playerId not found")
        teamRepository.addPlayer(teamId, playerId)
        return teamRepository.findById(teamId)!!
    }

    override fun removePlayer(teamId: Long, playerId: Long): Team {
        teamRepository.findById(teamId)
            ?: throw NoSuchElementException("Team with id $teamId not found")
        teamRepository.removePlayer(teamId, playerId)
        return teamRepository.findById(teamId)!!
    }
}
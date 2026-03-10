package vasconcelos.silvio.volley.domain.service

import vasconcelos.silvio.volley.domain.model.Team

interface TeamService {
    fun findAll(): List<Team>
    fun findById(id: Long): Team?
    fun create(team: Team): Team
    fun update(id: Long, team: Team): Team
    fun delete(id: Long)
    fun addPlayer(teamId: Long, playerId: Long): Team
    fun removePlayer(teamId: Long, playerId: Long): Team
}
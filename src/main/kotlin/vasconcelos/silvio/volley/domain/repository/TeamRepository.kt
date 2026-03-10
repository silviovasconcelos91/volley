package vasconcelos.silvio.volley.domain.repository

import vasconcelos.silvio.volley.domain.model.Team

interface TeamRepository {
    fun findAll(): List<Team>
    fun findById(id: Long): Team?
    fun save(team: Team): Team
    fun deleteById(id: Long)
    fun addPlayer(teamId: Long, playerId: Long)
    fun removePlayer(teamId: Long, playerId: Long)
}
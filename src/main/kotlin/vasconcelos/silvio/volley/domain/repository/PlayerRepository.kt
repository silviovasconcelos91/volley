package vasconcelos.silvio.volley.domain.repository

import vasconcelos.silvio.volley.domain.model.Player

interface PlayerRepository {
    fun findAll(): List<Player>
    fun findById(id: Long): Player?
    fun save(player: Player): Player
    fun deleteById(id: Long)
}
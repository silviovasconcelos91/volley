package vasconcelos.silvio.volley.domain.service

import vasconcelos.silvio.volley.domain.model.Player

interface PlayerService {
    fun findAll(): List<Player>
    fun findById(id: Long): Player?
    fun create(player: Player): Player
    fun update(id: Long, player: Player): Player
    fun delete(id: Long)
}
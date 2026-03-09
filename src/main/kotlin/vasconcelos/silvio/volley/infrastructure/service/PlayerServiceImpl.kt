package vasconcelos.silvio.volley.infrastructure.service

import org.springframework.stereotype.Service
import vasconcelos.silvio.volley.domain.model.Player
import vasconcelos.silvio.volley.domain.repository.PlayerRepository
import vasconcelos.silvio.volley.domain.service.PlayerService

@Service
class PlayerServiceImpl(
    private val playerRepository: PlayerRepository
) : PlayerService {

    override fun findAll(): List<Player> = playerRepository.findAll()

    override fun findById(id: Long): Player? = playerRepository.findById(id)

    override fun create(player: Player): Player = playerRepository.save(player)

    override fun update(id: Long, player: Player): Player {
        playerRepository.findById(id) ?: throw NoSuchElementException("Player with id $id not found")
        return playerRepository.save(player.copy(id = id))
    }

    override fun delete(id: Long) {
        playerRepository.findById(id) ?: throw NoSuchElementException("Player with id $id not found")
        playerRepository.deleteById(id)
    }
}
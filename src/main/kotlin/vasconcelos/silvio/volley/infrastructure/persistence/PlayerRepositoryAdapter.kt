package vasconcelos.silvio.volley.infrastructure.persistence

import org.springframework.stereotype.Repository
import vasconcelos.silvio.volley.domain.model.Level
import vasconcelos.silvio.volley.domain.model.Player
import vasconcelos.silvio.volley.domain.repository.PlayerRepository

@Repository
class PlayerRepositoryAdapter(
    private val springDataPlayerRepository: SpringDataPlayerRepository
) : PlayerRepository {

    override fun findAll(): List<Player> =
        springDataPlayerRepository.findAll().map { it.toDomain() }

    override fun findById(id: Long): Player? =
        springDataPlayerRepository.findById(id).orElse(null)?.toDomain()

    override fun save(player: Player): Player =
        springDataPlayerRepository.save(player.toEntity()).toDomain()

    override fun deleteById(id: Long) =
        springDataPlayerRepository.deleteById(id)

    private fun PlayerEntity.toDomain() = Player(
        id = id,
        name = name,
        surname = surname,
        email = email,
        age = age,
        level = Level.valueOf(level)
    )

    private fun Player.toEntity() = PlayerEntity(
        id = id,
        name = name,
        surname = surname,
        email = email,
        age = age,
        level = level.name
    )
}
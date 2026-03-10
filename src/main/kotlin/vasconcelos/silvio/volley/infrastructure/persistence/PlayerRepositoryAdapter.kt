package vasconcelos.silvio.volley.infrastructure.persistence

import org.springframework.stereotype.Repository
import vasconcelos.silvio.volley.domain.model.Player
import vasconcelos.silvio.volley.domain.repository.PlayerRepository
import vasconcelos.silvio.volley.infrastructure.mapper.PlayerMapper

@Repository
class PlayerRepositoryAdapter(
    private val springDataPlayerRepository: SpringDataPlayerRepository,
    private val playerMapper: PlayerMapper
) : PlayerRepository {

    override fun findAll(): List<Player> =
        springDataPlayerRepository.findAll().map { playerMapper.toDomain(it) }

    override fun findById(id: Long): Player? =
        springDataPlayerRepository.findById(id).orElse(null)?.let { playerMapper.toDomain(it) }

    override fun save(player: Player): Player =
        springDataPlayerRepository.save(playerMapper.toEntity(player)).let { playerMapper.toDomain(it) }

    override fun deleteById(id: Long) =
        springDataPlayerRepository.deleteById(id)
}
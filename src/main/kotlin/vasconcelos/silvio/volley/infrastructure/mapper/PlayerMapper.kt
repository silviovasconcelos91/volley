package vasconcelos.silvio.volley.infrastructure.mapper

import org.springframework.stereotype.Component
import vasconcelos.silvio.volley.controller.dto.PlayerDto
import vasconcelos.silvio.volley.domain.model.Player

@Component
class PlayerMapper {

    fun toDto(player: Player) = PlayerDto(
        id = player.id,
        name = player.name,
        surname = player.surname,
        email = player.email,
        age = player.age,
        level = player.level
    )

    fun toDomain(dto: PlayerDto) = Player(
        name = dto.name,
        surname = dto.surname,
        email = dto.email,
        age = dto.age,
        level = dto.level
    )
}
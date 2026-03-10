package vasconcelos.silvio.volley.infrastructure.mapper

import org.springframework.stereotype.Component
import vasconcelos.silvio.volley.controller.dto.PlayerDto
import vasconcelos.silvio.volley.domain.model.Category
import vasconcelos.silvio.volley.domain.model.Level
import vasconcelos.silvio.volley.domain.model.Player
import vasconcelos.silvio.volley.infrastructure.persistence.PlayerEntity

@Component
class PlayerMapper {

    fun toDto(player: Player) = PlayerDto(
        id = player.id,
        name = player.name,
        surname = player.surname,
        email = player.email,
        age = player.age,
        level = player.level,
        category = player.category
    )

    fun toDomain(dto: PlayerDto) = Player(
        name = dto.name,
        surname = dto.surname,
        email = dto.email,
        age = dto.age,
        level = dto.level,
        category = dto.category
    )

    fun toDomain(entity: PlayerEntity) = Player(
        id = entity.id,
        name = entity.name,
        surname = entity.surname,
        email = entity.email,
        age = entity.age,
        level = Level.valueOf(entity.level),
        category = Category.valueOf(entity.category)
    )

    fun toEntity(player: Player) = PlayerEntity(
        id = player.id,
        name = player.name,
        surname = player.surname,
        email = player.email,
        age = player.age,
        level = player.level.name,
        category = player.category.name
    )
}
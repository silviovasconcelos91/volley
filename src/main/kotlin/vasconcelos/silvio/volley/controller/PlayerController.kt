package vasconcelos.silvio.volley.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vasconcelos.silvio.volley.controller.dto.PlayerDto
import vasconcelos.silvio.volley.domain.service.PlayerService
import vasconcelos.silvio.volley.infrastructure.mapper.PlayerMapper

@RestController
@RequestMapping("/api/v1/players")
class PlayerController(
    private val playerService: PlayerService,
    private val playerMapper: PlayerMapper
) {

    @GetMapping
    fun findAll(): ResponseEntity<List<PlayerDto>> {
        val players = playerService.findAll().map { playerMapper.toDto(it) }
        return ResponseEntity.ok(players)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<PlayerDto> {
        val player = playerService.findById(id)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(playerMapper.toDto(player))
    }

    @PostMapping
    fun create(@RequestBody dto: PlayerDto): ResponseEntity<PlayerDto> {
        val player = playerService.create(playerMapper.toDomain(dto))
        return ResponseEntity.status(HttpStatus.CREATED).body(playerMapper.toDto(player))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody dto: PlayerDto
    ): ResponseEntity<PlayerDto> {
        val player = playerService.update(id, playerMapper.toDomain(dto))
        return ResponseEntity.ok(playerMapper.toDto(player))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        playerService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
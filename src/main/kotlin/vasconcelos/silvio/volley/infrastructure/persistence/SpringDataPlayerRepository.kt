package vasconcelos.silvio.volley.infrastructure.persistence

import org.springframework.data.repository.CrudRepository

interface SpringDataPlayerRepository : CrudRepository<PlayerEntity, Long>
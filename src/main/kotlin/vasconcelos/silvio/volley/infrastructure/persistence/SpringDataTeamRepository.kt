package vasconcelos.silvio.volley.infrastructure.persistence

import org.springframework.data.repository.CrudRepository

interface SpringDataTeamRepository : CrudRepository<TeamEntity, Long>
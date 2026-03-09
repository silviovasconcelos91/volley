package vasconcelos.silvio.volley.infrastructure.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("players")
data class PlayerEntity(
    @Id val id: Long? = null,
    val name: String,
    val surname: String,
    val email: String,
    val age: Int,
    val level: String
)

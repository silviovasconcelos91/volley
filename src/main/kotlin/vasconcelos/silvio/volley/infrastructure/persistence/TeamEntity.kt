package vasconcelos.silvio.volley.infrastructure.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("teams")
data class TeamEntity(
    @Id val id: Long? = null,
    val name: String,
    val captainId: Long
)
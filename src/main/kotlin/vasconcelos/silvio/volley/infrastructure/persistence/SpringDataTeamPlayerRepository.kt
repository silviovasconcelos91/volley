package vasconcelos.silvio.volley.infrastructure.persistence

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface SpringDataTeamPlayerRepository : CrudRepository<TeamPlayerEntity, Long> {

    @Query("SELECT player_id FROM team_players WHERE team_id = :teamId")
    fun findPlayerIdsByTeamId(teamId: Long): List<Long>

    @Modifying
    @Query("DELETE FROM team_players WHERE team_id = :teamId AND player_id = :playerId")
    fun deleteByTeamIdAndPlayerId(teamId: Long, playerId: Long)

    @Modifying
    @Query("DELETE FROM team_players WHERE team_id = :teamId")
    fun deleteByTeamId(teamId: Long)
}
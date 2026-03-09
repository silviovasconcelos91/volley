package vasconcelos.silvio.volley.infrastructure.service

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import vasconcelos.silvio.volley.domain.model.Level
import vasconcelos.silvio.volley.domain.model.Player
import vasconcelos.silvio.volley.domain.repository.PlayerRepository
import vasconcelos.silvio.volley.infrastructure.service.PlayerServiceImpl

@ExtendWith(MockitoExtension::class)
class PlayerServiceImplTest {

    @Mock
    lateinit var playerRepository: PlayerRepository

    @InjectMocks
    lateinit var playerService: PlayerServiceImpl

    @Test
    fun should_returnAllPlayers_when_findAll() {
        val players = listOf(
            Player(id = 1, name = "John", surname = "Doe", email = "john@test.com", age = 25, level = Level.BEGINNER),
            Player(id = 2, name = "Jane", surname = "Doe", email = "jane@test.com", age = 30, level = Level.ADVANCED)
        )
        `when`(playerRepository.findAll()).thenReturn(players)

        val result = playerService.findAll()

        assertThat(result).hasSize(2)
        assertThat(result).isEqualTo(players)
    }

    @Test
    fun should_returnPlayer_when_findByIdWithExistingId() {
        val player = Player(id = 1, name = "John", surname = "Doe", email = "john@test.com", age = 25, level = Level.BEGINNER)
        `when`(playerRepository.findById(1)).thenReturn(player)

        val result = playerService.findById(1)

        assertThat(result).isEqualTo(player)
    }

    @Test
    fun should_returnNull_when_findByIdWithNonExistingId() {
        `when`(playerRepository.findById(99)).thenReturn(null)

        val result = playerService.findById(99)

        assertThat(result).isNull()
    }

    @Test
    fun should_savePlayer_when_create() {
        val player = Player(name = "John", surname = "Doe", email = "john@test.com", age = 25, level = Level.BEGINNER)
        val savedPlayer = player.copy(id = 1)
        `when`(playerRepository.save(player)).thenReturn(savedPlayer)

        val result = playerService.create(player)

        assertThat(result.id).isEqualTo(1)
        assertThat(result.name).isEqualTo("John")
    }

    @Test
    fun should_updatePlayer_when_playerExists() {
        val existingPlayer = Player(id = 1, name = "John", surname = "Doe", email = "john@test.com", age = 25, level = Level.BEGINNER)
        val updatedPlayer = Player(name = "John", surname = "Updated", email = "john@test.com", age = 26, level = Level.INTERMEDIATE)
        val savedPlayer = updatedPlayer.copy(id = 1)
        `when`(playerRepository.findById(1)).thenReturn(existingPlayer)
        `when`(playerRepository.save(updatedPlayer.copy(id = 1))).thenReturn(savedPlayer)

        val result = playerService.update(1, updatedPlayer)

        assertThat(result.id).isEqualTo(1)
        assertThat(result.surname).isEqualTo("Updated")
        assertThat(result.level).isEqualTo(Level.INTERMEDIATE)
    }

    @Test
    fun should_throwException_when_updateNonExistingPlayer() {
        val player = Player(name = "John", surname = "Doe", email = "john@test.com", age = 25, level = Level.BEGINNER)
        `when`(playerRepository.findById(99)).thenReturn(null)

        assertThatThrownBy { playerService.update(99, player) }
            .isInstanceOf(NoSuchElementException::class.java)
            .hasMessage("Player with id 99 not found")
    }

    @Test
    fun should_deletePlayer_when_playerExists() {
        val player = Player(id = 1, name = "John", surname = "Doe", email = "john@test.com", age = 25, level = Level.BEGINNER)
        `when`(playerRepository.findById(1)).thenReturn(player)

        playerService.delete(1)

        verify(playerRepository).deleteById(1)
    }

    @Test
    fun should_throwException_when_deleteNonExistingPlayer() {
        `when`(playerRepository.findById(99)).thenReturn(null)

        assertThatThrownBy { playerService.delete(99) }
            .isInstanceOf(NoSuchElementException::class.java)
            .hasMessage("Player with id 99 not found")
    }
}
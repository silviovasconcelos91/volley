package vasconcelos.silvio.volley.infrastructure.persistence

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import vasconcelos.silvio.volley.domain.model.Category
import vasconcelos.silvio.volley.domain.model.Level
import vasconcelos.silvio.volley.domain.model.Player
import vasconcelos.silvio.volley.domain.repository.PlayerRepository

@SpringBootTest
@Transactional
class PlayerRepositoryAdapterTest {

    @Autowired
    lateinit var playerRepository: PlayerRepository

    @Test
    fun should_saveAndReturnPlayerWithId_when_validPlayer() {
        val player = Player(name = "John", surname = "Doe", email = "john@test.com", age = 25, level = Level.BEGINNER, category = Category.LOISIR)

        val saved = playerRepository.save(player)

        assertThat(saved.id).isNotNull()
        assertThat(saved.name).isEqualTo("John")
        assertThat(saved.level).isEqualTo(Level.BEGINNER)
    }

    @Test
    fun should_findPlayer_when_existingId() {
        val saved = playerRepository.save(
            Player(name = "Jane", surname = "Doe", email = "jane@test.com", age = 30, level = Level.ADVANCED, category = Category.FEMININ)
        )

        val found = playerRepository.findById(saved.id!!)

        assertThat(found).isNotNull
        assertThat(found?.name).isEqualTo("Jane")
        assertThat(found?.level).isEqualTo(Level.ADVANCED)
    }

    @Test
    fun should_returnNull_when_nonExistingId() {
        val found = playerRepository.findById(99999)

        assertThat(found).isNull()
    }

    @Test
    fun should_returnAllPlayers_when_playersExist() {
        playerRepository.save(Player(name = "Player1", surname = "Test", email = "p1@test.com", age = 20, level = Level.BEGINNER, category = Category.JEUNE))
        playerRepository.save(Player(name = "Player2", surname = "Test", email = "p2@test.com", age = 25, level = Level.EXPERT, category = Category.COMPETITION_MIXTE))

        val all = playerRepository.findAll()

        assertThat(all).hasSizeGreaterThanOrEqualTo(2)
    }

    @Test
    fun should_deletePlayer_when_existingId() {
        val saved = playerRepository.save(
            Player(name = "ToDelete", surname = "Test", email = "del@test.com", age = 22, level = Level.INTERMEDIATE, category = Category.LOISIR)
        )

        playerRepository.deleteById(saved.id!!)

        val found = playerRepository.findById(saved.id!!)
        assertThat(found).isNull()
    }
}
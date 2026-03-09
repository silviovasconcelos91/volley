package vasconcelos.silvio.volley.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import vasconcelos.silvio.volley.controller.dto.PlayerDto
import vasconcelos.silvio.volley.domain.model.Level

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PlayerControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun should_returnCreatedPlayer_when_postValidPlayer() {
        val dto = PlayerDto(name = "John", surname = "Doe", email = "john@test.com", age = 25, level = Level.BEGINNER)

        mockMvc.perform(
            post("/api/v1/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").isNotEmpty)
            .andExpect(jsonPath("$.name").value("John"))
            .andExpect(jsonPath("$.surname").value("Doe"))
            .andExpect(jsonPath("$.email").value("john@test.com"))
            .andExpect(jsonPath("$.age").value(25))
            .andExpect(jsonPath("$.level").value("BEGINNER"))
    }

    @Test
    fun should_returnAllPlayers_when_getPlayers() {
        createPlayer("Alice", "Smith", "alice@test.com", 22, Level.INTERMEDIATE)
        createPlayer("Bob", "Jones", "bob@test.com", 30, Level.EXPERT)

        mockMvc.perform(get("/api/v1/players"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("Alice"))
            .andExpect(jsonPath("$[1].name").value("Bob"))
    }

    @Test
    fun should_returnPlayer_when_getExistingId() {
        val id = createPlayer("Jane", "Doe", "jane@test.com", 28, Level.ADVANCED)

        mockMvc.perform(get("/api/v1/players/$id"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.name").value("Jane"))
            .andExpect(jsonPath("$.level").value("ADVANCED"))
    }

    @Test
    fun should_return404_when_getnonExistingId() {
        mockMvc.perform(get("/api/v1/players/99999"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun should_returnUpdatedPlayer_when_putExistingPlayer() {
        val id = createPlayer("John", "Doe", "john@test.com", 25, Level.BEGINNER)
        val updated = PlayerDto(name = "John", surname = "Updated", email = "john.updated@test.com", age = 26, level = Level.INTERMEDIATE)

        mockMvc.perform(
            put("/api/v1/players/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.surname").value("Updated"))
            .andExpect(jsonPath("$.email").value("john.updated@test.com"))
            .andExpect(jsonPath("$.age").value(26))
            .andExpect(jsonPath("$.level").value("INTERMEDIATE"))
    }

    @Test
    fun should_return404_when_putNonExistingPlayer() {
        val dto = PlayerDto(name = "Ghost", surname = "Player", email = "ghost@test.com", age = 20, level = Level.BEGINNER)

        mockMvc.perform(
            put("/api/v1/players/99999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun should_return204_when_deleteExistingPlayer() {
        val id = createPlayer("ToDelete", "Player", "del@test.com", 22, Level.BEGINNER)

        mockMvc.perform(delete("/api/v1/players/$id"))
            .andExpect(status().isNoContent)

        mockMvc.perform(get("/api/v1/players/$id"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun should_return404_when_deleteNonExistingPlayer() {
        mockMvc.perform(delete("/api/v1/players/99999"))
            .andExpect(status().isNotFound)
    }

    private fun createPlayer(name: String, surname: String, email: String, age: Int, level: Level): Long {
        val dto = PlayerDto(name = name, surname = surname, email = email, age = age, level = level)
        val result = mockMvc.perform(
            post("/api/v1/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
            .andExpect(status().isCreated)
            .andReturn()

        val response = objectMapper.readValue(result.response.contentAsString, PlayerDto::class.java)
        return requireNotNull(response.id)
    }
}
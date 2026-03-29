package com.minedesso.backendapi.home;

import com.minedesso.backendapi.BaseIT;
import com.minedesso.backendapi.home.domain.dtos.in.HomeSaveCommand;
import com.minedesso.backendapi.home.domain.dtos.out.Home;
import com.minedesso.backendapi.home.persistence.HomeEntity;
import com.minedesso.backendapi.home.persistence.HomeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class HomeControllerIT extends BaseIT {
    private static final String PATH = "/home";

    @Autowired
    private HomeRepository homeRepository;

    @Test
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    void saveHome_returnsOk() {
        HomeSaveCommand command = HomeTestHelper.createHomeSaveCommand();

        HttpEntity<HomeSaveCommand> entity = new HttpEntity<>(command);
        ResponseEntity<Void> response = this.restTemplate.exchange(
                PATH,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Optional<HomeEntity> homeOpt = this.homeRepository.findById(1L);
        assertTrue(homeOpt.isPresent());

        HomeEntity home = homeOpt.get();
        assertEquals("World 1", home.getName());
        assertEquals("world", home.getWorldName());
    }

    @Test
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    @Sql(scripts = "/sql/home/createHomes.sql")
    void saveHome_homeAlreadyExists_returnsConflict() {
        HomeSaveCommand command = HomeTestHelper.createHomeSaveCommand();

        HttpEntity<HomeSaveCommand> entity = new HttpEntity<>(command);
        ResponseEntity<Void> response = this.restTemplate.exchange(
                PATH,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    @Sql(scripts = "/sql/home/createHomes.sql")
    void getHome_returnsOk() {
        String uuid = "a22e9e92-1894-4d63-993c-a09f0e1edc6f";

        ResponseEntity<Home> response = this.restTemplate.exchange(
                PATH + "/" + uuid + "/" + "World 1",
                HttpMethod.GET,
                null,
                Home.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Home home = response.getBody();
        assertNotNull(home);
        assertEquals("World 1", home.getName());
    }

    @Test
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    @Sql(scripts = "/sql/home/createHomes.sql")
    void getHome_homeNotFound_returnsNotFound() {
        String uuid = "a22e9e92-1894-4d63-993c-a09f0e1edc6f";

        ResponseEntity<Home> response = this.restTemplate.exchange(
                PATH + "/" + uuid + "/" + "World 2",
                HttpMethod.GET,
                null,
                Home.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    @Sql(scripts = "/sql/home/createHomes.sql")
    void getAllHomesOfPlayer_returnsOk() {
        String uuid = "a22e9e92-1894-4d63-993c-a09f0e1edc6f";

        ResponseEntity<List<Home>> response = this.restTemplate.exchange(
                PATH + "/" + uuid + "/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Home> homes = response.getBody();
        assertNotNull(homes);
        assertFalse(homes.isEmpty());
        assertEquals(1, homes.size());

        Home home = homes.getFirst();
        assertEquals("World 1", home.getName());
    }

    @Test
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    @Sql(scripts = "/sql/home/createHomes.sql")
    void deleteHome_returnsOk() {
        String uuid = "a22e9e92-1894-4d63-993c-a09f0e1edc6f";

        ResponseEntity<Void> response = this.restTemplate.exchange(
                PATH + "/" + uuid + "/World 1",
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Optional<HomeEntity> homeOpt = this.homeRepository.findById(1L);
        assertTrue(homeOpt.isEmpty());
    }


    @Test
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    @Sql(scripts = "/sql/home/createHomes.sql")
    void deleteHome_homeNotFound_returnsNotFound() {
        String uuid = "a22e9e92-1894-4d63-993c-a09f0e1edc6f";

        ResponseEntity<Void> response = this.restTemplate.exchange(
                PATH + "/" + uuid + "/World 2",
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

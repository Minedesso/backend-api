package com.minedesso.backendapi.ban;

import com.minedesso.backendapi.BaseIT;
import com.minedesso.backendapi.ban.domain.dtos.in.BanSaveCommand;
import com.minedesso.backendapi.ban.domain.dtos.out.Ban;
import com.minedesso.backendapi.minecraftplayer.persistence.MinecraftPlayerEntity;
import com.minedesso.backendapi.minecraftplayer.persistence.MinecraftPlayerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BanControllerIT extends BaseIT {
    private static final String PATH = "/ban";

    @Autowired
    private MinecraftPlayerRepository minecraftPlayerRepository;

    @Test
    @Transactional
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    @Sql(scripts = "/sql/ban/createReasons.sql")
    void createBan_returnsOk() {
        BanSaveCommand banSaveCommand = BanTestHelper.createBanSaveCommand();

        HttpEntity<BanSaveCommand> entity = new HttpEntity<>(banSaveCommand);
        ResponseEntity<Ban> response = this.restTemplate.exchange(
                PATH,
                HttpMethod.POST,
                entity,
                Ban.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        Optional<MinecraftPlayerEntity> targetEntityOptional = this.minecraftPlayerRepository
                .findByNameIgnoreCase(banSaveCommand.getTargetName());
        Optional<MinecraftPlayerEntity> senderEntityOptional = this.minecraftPlayerRepository
                .findById(banSaveCommand.getBannedBy());
        Ban ban = response.getBody();

        assertTrue(targetEntityOptional.isPresent());
        assertTrue(senderEntityOptional.isPresent());

        assertFalse(targetEntityOptional.get().getReceivedBans().isEmpty());
        assertFalse(senderEntityOptional.get().getAssignedBans().isEmpty());

        assertTrue(ban.getExpiresAt().isAfter(ban.getBannedAt()));
        assertEquals("Hacking", ban.getReason());
        assertEquals("_ConFace", ban.getBannedBy());
    }

    @Test
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    @Sql(scripts = "/sql/ban/createReasons.sql")
    @Sql(scripts = "/sql/ban/createBans.sql")
    void createBan_playerAlreadyBanned_returnsConflict() {
        BanSaveCommand banSaveCommand = BanTestHelper.createBanSaveCommand();

        HttpEntity<BanSaveCommand> entity = new HttpEntity<>(banSaveCommand);
        ResponseEntity<Ban> response = this.restTemplate.exchange(
                PATH,
                HttpMethod.POST,
                entity,
                Ban.class
        );

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    @Sql(scripts = "/sql/ban/createReasons.sql")
    void createBan_reasonNotFound_returnsConflict() {
        BanSaveCommand banSaveCommand = BanTestHelper.createBanSaveCommandUnvalidReasonId();

        HttpEntity<BanSaveCommand> entity = new HttpEntity<>(banSaveCommand);
        ResponseEntity<Ban> response = this.restTemplate.exchange(
                PATH,
                HttpMethod.POST,
                entity,
                Ban.class
        );

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    @Sql(scripts = "/sql/ban/createReasons.sql")
    void createBan_targetUuidNotFound_returnsConflict() {
        BanSaveCommand banSaveCommand = BanTestHelper.createBanSaveCommandUnvalidTarget();

        HttpEntity<BanSaveCommand> entity = new HttpEntity<>(banSaveCommand);
        ResponseEntity<Ban> response = this.restTemplate.exchange(
                PATH,
                HttpMethod.POST,
                entity,
                Ban.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    @Sql(scripts = "/sql/ban/createReasons.sql")
    @Sql(scripts = "/sql/ban/createBans.sql")
    void getBanDetails_returnsOk() {
        UUID uuid = UUID.fromString("a22e9e92-1894-4d63-993c-a09f0e1edc7f");

        ResponseEntity<Ban> response = this.restTemplate.exchange(
                PATH + "/" + uuid,
                HttpMethod.GET,
                null,
                Ban.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertTrue(response.hasBody());
        Ban ban = response.getBody();

        assertNotNull(ban);
        assertEquals("_ConFace", ban.getBannedBy());
        assertEquals("Hacking", ban.getReason());
    }

    @Test
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    void getBanDetails_returnsNotFound() {
        UUID uuid = UUID.fromString("a22e9e92-1894-4d63-993c-a09f0e1edc7f");

        ResponseEntity<Ban> response = this.restTemplate.exchange(
                PATH + "/" + uuid,
                HttpMethod.GET,
                null,
                Ban.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.hasBody());
    }

    @Test
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    @Sql(scripts = "/sql/ban/createReasons.sql")
    @Sql(scripts = "/sql/ban/createBans.sql")
    void checkActiveBanByUuid_returnsTrue() {
        UUID uuid = UUID.fromString("a22e9e92-1894-4d63-993c-a09f0e1edc7f");

        ResponseEntity<Boolean> response = this.restTemplate.exchange(
                PATH + "/check/" + uuid,
                HttpMethod.GET,
                null,
                Boolean.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertTrue(response.hasBody());
        assertNotNull(response.getBody());
        assertTrue(response.getBody());
    }

    @Test
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    void checkActiveBanByUuid_returnsFalse() {
        UUID uuid = UUID.fromString("a22e9e92-1894-4d63-993c-a09f0e1edc7f");

        ResponseEntity<Boolean> response = this.restTemplate.exchange(
                PATH + "/check/" + uuid,
                HttpMethod.GET,
                null,
                Boolean.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertTrue(response.hasBody());
        assertNotNull(response.getBody());
        assertFalse(response.getBody());
    }

    @Test
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    @Sql(scripts = "/sql/ban/createReasons.sql")
    @Sql(scripts = "/sql/ban/createBans.sql")
    void checkActiveBanByName_returnsTrue() {
        String playerName = "Alessio";

        ResponseEntity<Boolean> response = this.restTemplate.exchange(
                PATH + "/check?playerName=" + playerName,
                HttpMethod.GET,
                null,
                Boolean.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertTrue(response.hasBody());
        assertNotNull(response.getBody());
        assertTrue(response.getBody());
    }

    @Test
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    void checkActiveBanByName_returnsFalse() {
        String playerName = "Alessio";

        ResponseEntity<Boolean> response = this.restTemplate.exchange(
                PATH + "/check?playerName=" + playerName,
                HttpMethod.GET,
                null,
                Boolean.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertTrue(response.hasBody());
        assertNotNull(response.getBody());
        assertFalse(response.getBody());
    }

    @Test
    void checkActiveBanByName_minecraftPlayerDoesNotExist_returnsFalse() {
        String playerName = ".";

        ResponseEntity<Boolean> response = this.restTemplate.exchange(
                PATH + "/check?playerName=" + playerName,
                HttpMethod.GET,
                null,
                Boolean.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertTrue(response.hasBody());
        assertNotNull(response.getBody());
        assertFalse(response.getBody());
    }

}

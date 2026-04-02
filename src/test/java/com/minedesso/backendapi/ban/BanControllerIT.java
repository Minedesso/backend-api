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

        assertFalse(targetEntityOptional.get().getBans().isEmpty());
        assertFalse(senderEntityOptional.get().getBanned().isEmpty());

        assertTrue(ban.getExpiresAt().isAfter(ban.getBannedAt()));
        assertEquals("Hacking", ban.getReason());
        assertEquals("_ConFace", ban.getBannedBy());
    }

    @Test
    @Sql(scripts = "/sql/minecraftplayer/createMinecraftPlayers.sql")
    @Sql(scripts = "/sql/ban/createReasons.sql")
    @Sql(scripts = "/sql/ban/createBans.sql")
    void createBan_returnsConflict_playerAlreadyBanned() {
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
    void createBan_returnsConflict_reasonNotFound() {
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
    void createBan_returnsConflict_targetUuidNotFound() {
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
}

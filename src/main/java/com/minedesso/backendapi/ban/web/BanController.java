package com.minedesso.backendapi.ban.web;

import com.minedesso.backendapi.ban.domain.dtos.in.BanSaveCommand;
import com.minedesso.backendapi.ban.domain.dtos.out.Ban;
import com.minedesso.backendapi.ban.domain.services.BanUseCase;
import com.minedesso.backendapi.ban.domain.util.exceptions.PlayerAlreadyBannedException;
import com.minedesso.backendapi.ban.domain.util.exceptions.PlayerUuidNotFoundException;
import com.minedesso.backendapi.ban.domain.util.exceptions.ReasonNotFoundException;
import com.minedesso.backendapi.home.domain.utils.exceptions.ActiveBanNotFoundException;
import com.minedesso.backendapi.minecraftplayer.domain.utils.exceptions.MinecraftPlayerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/ban")
@RestController
public class BanController {

    private final BanUseCase banUseCase;

    @PostMapping
    public ResponseEntity<Ban> createBan(@RequestBody BanSaveCommand banSaveCommand) {
        try {
            Ban ban = banUseCase.saveBan(banSaveCommand);
            return ResponseEntity.ok(ban);
        } catch (PlayerAlreadyBannedException | ActiveBanNotFoundException | ReasonNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (PlayerUuidNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Ban> getBanDetails(@PathVariable UUID uuid) {
        try {
            Ban ban = banUseCase.getBanDetails(uuid);
            return ResponseEntity.ok(ban);
        } catch (ActiveBanNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/check/{uuid}")
    public ResponseEntity<Boolean> checkBan(@PathVariable UUID uuid) {
        boolean isBanned = banUseCase.checkActiveBan(uuid);
        return ResponseEntity.ok(isBanned);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkActiveBan(@RequestParam String playerName) {
        try {
            boolean isBanned = banUseCase.checkActiveBan(playerName);
            return ResponseEntity.ok(isBanned);
        } catch (MinecraftPlayerNotFoundException e) {
            return ResponseEntity.ok(false);
        }
    }

}

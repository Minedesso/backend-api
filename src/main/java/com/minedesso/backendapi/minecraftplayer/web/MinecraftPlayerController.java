package com.minedesso.backendapi.minecraftplayer.web;

import com.minedesso.backendapi.minecraftplayer.domain.dtos.in.MinecraftPlayerSaveCommand;
import com.minedesso.backendapi.minecraftplayer.domain.dtos.out.MinecraftPlayer;
import com.minedesso.backendapi.minecraftplayer.domain.services.MinecraftPlayerUseCase;
import com.minedesso.backendapi.minecraftplayer.domain.utils.exceptions.MinecraftPlayerNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/minecraft-player")
@Slf4j
@AllArgsConstructor
public class MinecraftPlayerController {
    private final MinecraftPlayerUseCase useCase;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody MinecraftPlayerSaveCommand command) {
        this.useCase.save(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MinecraftPlayer>> getAll() {
        List<MinecraftPlayer> minecraftPlayers = this.useCase.getAll();
        return new ResponseEntity<>(minecraftPlayers, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<MinecraftPlayer> getById(@PathVariable("uuid") UUID uuid) {
        try {
            MinecraftPlayer minecraftPlayer = this.useCase.getById(uuid);
            return new ResponseEntity<>(minecraftPlayer, HttpStatus.OK);
        } catch (MinecraftPlayerNotFoundException e) {
            log.info(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable("uuid") UUID uuid) {
        try {
            this.useCase.delete(uuid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MinecraftPlayerNotFoundException e) {
            log.info(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

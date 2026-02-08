package com.minedesso.backendapi.lobby.web;

import com.minedesso.backendapi.lobby.domain.dtos.in.LobbySpawnSaveCommand;
import com.minedesso.backendapi.lobby.domain.dtos.out.LobbySpawn;
import com.minedesso.backendapi.lobby.domain.services.LobbySpawnUseCase;
import com.minedesso.backendapi.lobby.domain.utils.exceptions.LobbySpawnNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lobby-spawn")
@Slf4j
@AllArgsConstructor
public class LobbySpawnController {
    private LobbySpawnUseCase useCase;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody LobbySpawnSaveCommand command) {
        try {
            this.useCase.save(command);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (LobbySpawnNotFoundException e) {
            log.debug(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<LobbySpawn> getLobbySpawn() {
        try {
            LobbySpawn lobbySpawn = this.useCase.getLobbySpawn();
            return new ResponseEntity<>(lobbySpawn, HttpStatus.OK);
        } catch (LobbySpawnNotFoundException e) {
            log.debug(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

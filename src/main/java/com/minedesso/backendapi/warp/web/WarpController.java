package com.minedesso.backendapi.warp.web;

import com.minedesso.backendapi.warp.domain.dtos.in.WarpSaveCommand;
import com.minedesso.backendapi.warp.domain.dtos.out.Warp;
import com.minedesso.backendapi.warp.domain.services.WarpUseCase;
import com.minedesso.backendapi.warp.domain.utils.exceptions.WarpNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warp")
@Slf4j
@RequiredArgsConstructor
public class WarpController {

    private final WarpUseCase warpUseCase;

    @PostMapping()
    public ResponseEntity<Void> save(@RequestBody WarpSaveCommand command) {
        warpUseCase.save(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Warp>> getAll() {
        return ResponseEntity.ok(warpUseCase.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Warp> getByName(@PathVariable String name) {
        try {
            Warp warp = warpUseCase.getByName(name);
            return ResponseEntity.ok(warp);
        } catch (WarpNotFoundException e) {
            log.debug(e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteByName(@PathVariable String name) {
        try {
            warpUseCase.deleteByName(name);
            return ResponseEntity.ok().build();
        } catch (WarpNotFoundException e) {
            log.debug(e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

}

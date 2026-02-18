package com.minedesso.backendapi.location.web;

import com.minedesso.backendapi.location.domain.dtos.in.LocationSaveCommand;
import com.minedesso.backendapi.location.domain.dtos.out.Location;
import com.minedesso.backendapi.location.domain.services.LocationUseCase;
import com.minedesso.backendapi.location.domain.utils.exceptions.LocationNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
@Slf4j
@AllArgsConstructor
public class LocationController {
    private final LocationUseCase useCase;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody LocationSaveCommand command) {
        this.useCase.save(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Location> getByName(@PathVariable String name) {
        try {
            Location location = this.useCase.getByName(name.toLowerCase());
            return new ResponseEntity<>(location, HttpStatus.OK);
        } catch (LocationNotFoundException e) {
            log.debug(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        try {
            this.useCase.delete(name.toLowerCase());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (LocationNotFoundException e) {
            log.debug(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

package com.minedesso.backendapi.home.web;

import com.minedesso.backendapi.home.domain.dtos.in.HomeSaveCommand;
import com.minedesso.backendapi.home.domain.dtos.out.Home;
import com.minedesso.backendapi.home.domain.services.HomeService;
import com.minedesso.backendapi.home.domain.services.HomeUseCase;
import com.minedesso.backendapi.home.domain.utils.exceptions.HomeAlreadyExistsException;
import com.minedesso.backendapi.home.domain.utils.exceptions.HomeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeUseCase homeUseCase;

    @PostMapping()
    public ResponseEntity<Void> saveHome(@RequestBody HomeSaveCommand homeSaveCommand) {
        try {
            homeUseCase.save(homeSaveCommand);
            return ResponseEntity.ok().build();
        } catch (HomeAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{owner-uuid}/{homeName}")
    public ResponseEntity<Home> getHome(@PathVariable(name = "owner-uuid") UUID ownerUuid, @PathVariable String homeName) {
        try {
            Home home = homeUseCase.getHome(ownerUuid, homeName);
            return ResponseEntity.ok(home);
        } catch (HomeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{owner-uuid}/all")
    public ResponseEntity<List<Home>> getAllHomesOfPlayer(@PathVariable(name = "owner-uuid") UUID ownerUuid) {
        return ResponseEntity.ok(homeUseCase.getAllOfPlayer(ownerUuid));
    }

    @DeleteMapping("/{owner-uuid}/{homeName}")
    public ResponseEntity<Void> deleteHome(@PathVariable(name = "owner-uuid") UUID ownerUuid, @PathVariable String homeName) {
        try {
            homeUseCase.deleteHome(ownerUuid, homeName);
            return ResponseEntity.ok().build();
        } catch (HomeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

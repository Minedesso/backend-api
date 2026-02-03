package com.minedesso.backendapi.home.web;

import com.minedesso.backendapi.home.domain.dtos.in.HomeSaveCommand;
import com.minedesso.backendapi.home.domain.dtos.out.Home;
import com.minedesso.backendapi.home.domain.services.HomeService;
import com.minedesso.backendapi.home.domain.utils.exceptions.HomeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @PostMapping()
    public ResponseEntity<Void> saveHome(@RequestBody HomeSaveCommand homeSaveCommand) {
        homeService.save(homeSaveCommand);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ownerUuid}/{homeName}")
    public ResponseEntity<Void> deleteHome(@PathVariable UUID ownerUuid, @PathVariable String homeName) {
        try {
            homeService.deleteHome(ownerUuid, homeName);
            return ResponseEntity.ok().build();
        } catch (HomeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{ownerUuid}/{homeName}")
    public ResponseEntity<Home> getHome(@PathVariable UUID ownerUuid, @PathVariable String homeName) {
        try {
            Home home = homeService.getHome(ownerUuid, homeName);
            return ResponseEntity.ok(home);
        } catch (HomeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{ownerUuid}/all")
    public ResponseEntity<List<Home>> getAllHomes(@PathVariable UUID ownerUuid) {
        return ResponseEntity.ok(homeService.getAll(ownerUuid));
    }

}

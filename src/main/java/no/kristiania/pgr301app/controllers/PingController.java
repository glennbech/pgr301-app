package no.kristiania.pgr301app.controllers;

import no.kristiania.pgr301app.models.Wish;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/ping")
@RestController
public class PingController {

    @GetMapping
    public ResponseEntity getWish(@PathVariable(value = "id") Long wishId) {

        return ResponseEntity.ok().body("pong");
    }
}

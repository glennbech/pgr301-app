package no.kristiania.pgr301app.controllers;

import no.kristiania.pgr301app.models.Wish;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WishController {

    @GetMapping("/wishes")
    public Wish wishes() {
        return new Wish("First", "wish.com");
    }
}

package no.kristiania.pgr301app.controllers;
import no.kristiania.pgr301app.models.Wish;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class WishController {

    private static final Logger LOG = Logger.getLogger(WishController.class.getName());

    @GetMapping("/wishes")
    public Wish wishes() {
        LOG.info("Getting wishes.");
        return new Wish("Second", "wish.com");
    }
}

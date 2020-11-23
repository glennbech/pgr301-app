package no.kristiania.pgr301app.controllers;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import no.kristiania.pgr301app.models.Wish;
import no.kristiania.pgr301app.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.logging.Logger;

@RestController
@RequestMapping("/wishes")
public class WishController {
    private static final Logger LOG = Logger.getLogger(WishController.class.getName());

    private MeterRegistry meterRegistry;

    @Autowired
    public WishController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Autowired
    private WishRepository wishRepository;



    @GetMapping
    public List<Wish> getWishes() {
        LOG.info("Getting wishes.");
        LOG.info(SecurityContextHolder.getContext().getAuthentication().getName());
        return wishRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wish> getWish(@PathVariable(value = "id") Long wishId) {

        Wish wish = wishRepository
                .findById(wishId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wish not found"));

        return ResponseEntity.ok().body(wish);
    }

    @PostMapping
    public ResponseEntity<Wish> createWish(@RequestBody Wish wish, UriComponentsBuilder b) {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        wish.setUserId(auth.getName());
        Wish newWish = meterRegistry.timer("save-wish-job").record(new Supplier<Wish>() {
            @Override
            public Wish get() {
                try {
                    //Simulate time for metrics
                    Thread.sleep(ThreadLocalRandom.current().nextLong(1, 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return wishRepository.save(wish);
            }
        });
        UriComponents uriComponents =
                b.path("/wishes/{id}").buildAndExpand(newWish.getId());
        LOG.info("Creating new wish with title" + newWish.getTitle());
        meterRegistry.counter("wishcount", "wish", wish.getTitle()).increment();
        return ResponseEntity.created(uriComponents.toUri()).build();
    }
}

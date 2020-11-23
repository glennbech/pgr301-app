package no.kristiania.pgr301app.controllers;
import io.micrometer.core.instrument.*;
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
        var user = SecurityContextHolder.getContext().getAuthentication().getName();
        LOG.info(user + " getting wishes.");
        List<Wish> wishes = wishRepository.findByGiftedFalse();
        meterRegistry.gauge("ungifted-wishes", wishes.size());
        return wishes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wish> getWish(@PathVariable(value = "id") Long wishId) {

        Wish wish = wishRepository
                .findById(wishId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wish not found"));

        return ResponseEntity.ok().body(wish);
    }

    @PutMapping("/{id}/gift")
    public ResponseEntity givePresent(@PathVariable(value = "id") Long wishId) {

        Wish wish = wishRepository
                .findById(wishId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wish not found"));

        wish.setGifted(true);
        wishRepository.save(wish);
        var user = SecurityContextHolder.getContext().getAuthentication().getName();
        LOG.info("Wish #" + wish.getId() + " gifted to user " + wish.getUserId() + " by " + user);

        return ResponseEntity.ok("ok");
    }

    @PostMapping
    public ResponseEntity<Wish> createWish(@RequestBody Wish wish, UriComponentsBuilder b) {
        meterRegistry.summary("wish-cost").record(wish.getPrice());
        var auth = SecurityContextHolder.getContext().getAuthentication();
        wish.setUserId(auth.getName());
        Wish newWish = meterRegistry.timer("wish-save-job").record(new Supplier<Wish>() {
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
        meterRegistry.counter("wishes-created").increment();
        return ResponseEntity.created(uriComponents.toUri()).build();
    }
}

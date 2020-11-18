package no.kristiania.pgr301app.repository;

import no.kristiania.pgr301app.models.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {

}

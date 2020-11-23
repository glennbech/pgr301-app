package no.kristiania.pgr301app.repository;

import no.kristiania.pgr301app.models.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    @Query
    public List<Wish> findByGiftedFalse();

}

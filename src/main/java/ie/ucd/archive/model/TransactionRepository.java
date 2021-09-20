package ie.ucd.archive.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findById(Long id);

    Optional<Transaction> findByMediaAndTypeAndActive(Media media, String type, Boolean active);

    Optional<Transaction> findByUserAndMediaAndTypeAndActive(User user, Media media, String type, Boolean active);

    List<Transaction> findAllByMediaAndTypeAndActive(Media media, String type, Boolean active);

    List<Transaction> findAllByUserAndMediaAndTypeAndActive(User user, Media media, String type, Boolean active);

    List<Transaction> findAllByUserAndActiveEquals(User user, Boolean active);
}

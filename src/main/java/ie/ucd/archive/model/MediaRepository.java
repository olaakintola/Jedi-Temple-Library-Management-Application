package ie.ucd.archive.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {

    Optional<Media> findById(Integer id);
    List<Media> findByTitleIgnoreCaseContaining(String title);
    List<Media> findByMediatextIgnoreCaseContaining(String mediatext);
    List<Media> findByMediatypeIgnoreCaseContaining(String mediatype);
    List<Media> findAllByIsviewable(Boolean state);
}


package ie.ucd.archive.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);
    Optional<User> findUserByUsername(String username);
    List<User> findByUsernameIgnoreCaseContaining(String username);
    List<User> findByRoleIgnoreCaseContaining(String role);
    List<User> findByAddressIgnoreCaseContaining(String address);
}
package by.kotik.userservice.repository;

import by.kotik.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String nickname);
    Optional<User> findByEmail(String email);
    @Query("select u from User u where u.username = :login or u.email = :login")
    Optional<User> findByLogin(String login);
}

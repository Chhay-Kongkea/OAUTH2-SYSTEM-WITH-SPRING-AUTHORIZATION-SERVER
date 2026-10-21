package co.istad.kongkea.spring_authorization_server.feature.customer.repository;

import co.istad.kongkea.spring_authorization_server.feature.customer.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, String> {

    Optional<AppUser> findByUsername(String username);
    boolean existsByUsername(String username);
}

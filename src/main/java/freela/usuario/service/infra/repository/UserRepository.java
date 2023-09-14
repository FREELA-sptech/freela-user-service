package freela.usuario.service.infra.repository;

import freela.usuario.service.domain.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String username);
}

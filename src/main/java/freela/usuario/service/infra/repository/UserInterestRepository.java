package freela.usuario.service.infra.repository;

import freela.usuario.service.domain.model.entities.SubCategory;
import freela.usuario.service.domain.model.entities.User;
import freela.usuario.service.domain.model.entities.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInterestRepository extends JpaRepository<UserInterest,Integer> {
    List<UserInterest> findAllByUser(User user);
    List<UserInterest> findAllBySubCategory(SubCategory subCategory);
}

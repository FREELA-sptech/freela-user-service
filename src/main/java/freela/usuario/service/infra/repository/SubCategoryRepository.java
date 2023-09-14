package freela.usuario.service.infra.repository;


import freela.usuario.service.domain.model.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepository extends JpaRepository<SubCategory,Integer> {

}

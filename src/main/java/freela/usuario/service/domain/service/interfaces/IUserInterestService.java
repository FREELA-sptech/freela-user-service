package freela.usuario.service.domain.service.interfaces;

import freela.usuario.service.domain.model.entities.SubCategory;
import freela.usuario.service.domain.model.entities.User;

import java.util.List;

public interface IUserInterestService {
    void createUserInterest (List<Integer> subCategories, User user);
    void updateUserInterest(List<Integer> subCategories, User user);
    List<SubCategory> getAllSubCategoriesByUser(User user);
}

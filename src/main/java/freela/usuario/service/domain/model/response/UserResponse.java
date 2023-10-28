package freela.usuario.service.domain.model.response;

import freela.usuario.service.domain.model.entities.SubCategory;
import freela.usuario.service.domain.model.entities.User;

import java.util.List;

public class UserResponse extends User {
    public List<SubCategory> subCategories;

    public UserResponse(User user, List<SubCategory> subCategories) {
        super(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoto(),
                user.getDescription(),
                user.getUf(),
                user.getCity(),
                user.getIsFreelancer(),
                user.getRate()
        );

        this.subCategories = subCategories;
    }
}

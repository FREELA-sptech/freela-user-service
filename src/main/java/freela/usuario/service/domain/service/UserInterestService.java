package freela.usuario.service.domain.service;

import freela.usuario.service.domain.model.entities.SubCategory;
import freela.usuario.service.domain.model.entities.User;
import freela.usuario.service.domain.model.entities.UserInterest;
import freela.usuario.service.domain.service.interfaces.IUserInterestService;
import freela.usuario.service.infra.repository.SubCategoryRepository;
import freela.usuario.service.infra.repository.UserInterestRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserInterestService implements IUserInterestService {
    private SubCategoryRepository subCategoryRepository;
    private UserInterestRepository userInterestRepository;

    public UserInterestService(
            SubCategoryRepository subCategoryRepository,
            UserInterestRepository userInterestRepository
    ) {
        this.subCategoryRepository = subCategoryRepository;
        this.userInterestRepository = userInterestRepository;
    }

    public void createUserInterest(List<Integer> subCategories, User user) {
        for (Integer subCategorieId : subCategories) {
            Optional<SubCategory> subCategory = this.subCategoryRepository.findById(subCategorieId);
            subCategory.ifPresent(category -> this.userInterestRepository.save(
                    new UserInterest(
                            user,
                            category,
                            category.getCategory()
                    )));
        }
    }

    public void updateUserInterest(List<Integer> subCategories, User user) {
        List<UserInterest> existingInterests = this.userInterestRepository.findAllByUser(user);

        existingInterests.forEach(userInterest -> {
            if (!subCategories.contains(userInterest.getSubCategory().getId())) {
                this.userInterestRepository.delete(userInterest);
            }
        });

        for (Integer subCategoryId : subCategories) {
            boolean interestExists = existingInterests.stream()
                    .anyMatch(userInterest -> userInterest.getSubCategory().getId().equals(subCategoryId));

            if (!interestExists) {
                Optional<SubCategory> subCategoryOptional = subCategoryRepository.findById(subCategoryId);
                subCategoryOptional.ifPresent(subCategory -> this.userInterestRepository.save(
                        new UserInterest(
                                user,
                                subCategory,
                                subCategory.getCategory()
                        )
                ));
            }
        }
    }

    public List<SubCategory> getAllSubCategoriesByUser(User user) {
        List<UserInterest> interests = this.userInterestRepository.findAllByUser(user);
        List<SubCategory> subCategories = new ArrayList<>();

        for (UserInterest interest : interests) {
            subCategories.add(interest.getSubCategory());
        }
        return subCategories;
    }
}

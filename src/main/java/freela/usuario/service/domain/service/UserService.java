package freela.usuario.service.domain.service;

import freela.usuario.service.domain.mapper.UsuarioMapper;
import freela.usuario.service.domain.model.entities.SubCategory;
import freela.usuario.service.domain.model.entities.User;
import freela.usuario.service.domain.model.entities.UserInterest;
import freela.usuario.service.domain.model.request.LoginRequest;
import freela.usuario.service.domain.model.request.RegisterRequest;
import freela.usuario.service.domain.model.request.UpdateRequest;
import freela.usuario.service.domain.model.response.UserResponse;
import freela.usuario.service.domain.service.interfaces.IUserService;
import freela.usuario.service.infra.repository.UserInterestRepository;
import freela.usuario.service.infra.repository.UserRepository;
import freela.usuario.service.web.exceptions.UserConflictsException;
import freela.usuario.service.web.exceptions.UserIncorrectPasswordException;
import freela.usuario.service.web.exceptions.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserService implements IUserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserInterestService userInterestService;
    private UserInterestRepository userInterestRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserInterestService userInterestService, UserInterestRepository userInterestRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userInterestService = userInterestService;
        this.userInterestRepository = userInterestRepository;
    }

    @Override
    public User register(RegisterRequest registerRequest) {
        String senhaCriptografada = passwordEncoder.encode(registerRequest.getPassword());

        Optional<User> userByEmail = userRepository.findByEmail(registerRequest.getEmail());

        if (userByEmail.isPresent()) throw new UserConflictsException("Email já cadastrado!");

        User user = userRepository.save(UsuarioMapper.register(registerRequest, senhaCriptografada));

        this.userInterestService.createUserInterest(registerRequest.getSubCategoriesIds(), user);
        List<SubCategory> subCategories = userInterestService.getAllSubCategoriesByUser(user);

        return user;
    }

    @Override
    public User authenticate(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new UserNotFoundException("Email não encontrado!")
        );

        Boolean isCorrectPassword = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

        if (!isCorrectPassword) throw new UserIncorrectPasswordException("Senha incorreta");

        return user;
    }

    @Override
    public User uploadPicture(Integer idUser, MultipartFile image) throws IOException {
        User user = this.userRepository.findById(idUser).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado!")
        );;

        byte[] imageData = image.getBytes();

        user.setPhoto(imageData);

        userRepository.save(user);

        return user;
    }

    @Override
    public UserResponse updateUser(Integer idUser, UpdateRequest updateRequest) {
        User user = this.userRepository.findById(idUser).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado!")
        );

        user.setName(updateRequest.getName());
        user.setUf(updateRequest.getUf());
        user.setCity(updateRequest.getCity());
        user.setDescription(updateRequest.getDescription());
        user.setDeviceId(updateRequest.getDeviceId());

        this.userInterestService.updateUserInterest(updateRequest.getSubCategoriesIds(), user);

        userRepository.save(user);

        List<SubCategory> subCategories = getSubcategoriesUser(user.getId());

        return new UserResponse(user, subCategories);
    }

    @Override
    public UserResponse getUserById(Integer idUser) {
        User user = this.userRepository.findById(idUser).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado!")
        );

        List<SubCategory> subCategories = getSubcategoriesUser(user.getId());

        return new UserResponse(user, subCategories);
    }

    @Override
    public List<UserResponse> getUsersBySubcategories(Integer idUser) {
        User userRequest = this.userRepository.findById(idUser).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado!")
        );;

        List<SubCategory> subCategories = this.userInterestService.getAllSubCategoriesByUser(userRequest);

        List<UserResponse> users = new ArrayList<>();
        Set<Integer> addedUserIds = new HashSet<>();

        for (SubCategory sub : subCategories) {
            List<UserInterest> interest = this.userInterestRepository.findAllBySubCategory(sub);

            for (UserInterest inte : interest) {
                User user = inte.getUser();
                if (user.getId() != userRequest.getId() && user.getIsFreelancer() && !addedUserIds.contains(user.getId())) {
                    List<SubCategory> subCategoriesUser = getSubcategoriesUser(user.getId());
                    users.add(new UserResponse(user, subCategoriesUser));
                    addedUserIds.add(user.getId());
                }
            }
        }

        return users;
    }

    @Override
    public List<SubCategory> getSubcategoriesUser(Integer idUser) {
        User userRequest = this.userRepository.findById(idUser).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado!")
        );

        return this.userInterestService.getAllSubCategoriesByUser(userRequest);
    }
}

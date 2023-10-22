package freela.usuario.service.domain.service.interfaces;

import freela.usuario.service.domain.model.entities.User;
import freela.usuario.service.domain.model.request.LoginRequest;
import freela.usuario.service.domain.model.request.RegisterRequest;
import freela.usuario.service.domain.model.request.UpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    User register(RegisterRequest registerRequest);
    User authenticate(LoginRequest loginRequest);
    User uploadPicture(Integer idUser, MultipartFile image) throws IOException;
    User updateUser(Integer idUser, UpdateRequest updateRequest);
    User getUserById(Integer idUser);
    List<User> getUsersBySubcategories(Integer idUser);
}

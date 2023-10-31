package freela.usuario.service.domain.mapper;


import freela.usuario.service.domain.model.request.RegisterRequest;
import freela.usuario.service.domain.model.entities.User;

public class UsuarioMapper {
    public static User register(RegisterRequest request, String senhaCriptografada) {
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(senhaCriptografada);
        user.setPhoto(new byte[0]);
        user.setUf(request.getUf());
        user.setRate(5.0);
        user.setCity(request.getCity());
        user.setIsFreelancer(request.getIsFreelancer());
        user.setDeviceId(request.getDeviceId());

        return user;
    }
}

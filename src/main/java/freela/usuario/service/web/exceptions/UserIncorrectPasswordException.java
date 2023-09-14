package freela.usuario.service.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserIncorrectPasswordException extends RuntimeException {
    public UserIncorrectPasswordException(String message) {
        super(message);
    }
}

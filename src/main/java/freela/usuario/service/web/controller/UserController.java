package freela.usuario.service.web.controller;

import freela.usuario.service.domain.model.entities.User;
import freela.usuario.service.domain.model.request.LoginRequest;
import freela.usuario.service.domain.model.request.RegisterRequest;
import freela.usuario.service.domain.model.request.UpdateRequest;
import freela.usuario.service.domain.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Parâmetros incorretos.",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "201", description = "Usuário registrado.")
    })
    @PostMapping
    public ResponseEntity<User> post(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "200", description = "Usuário encontrado.")
    })
    @GetMapping("/{idUser}")
    public ResponseEntity<Object> getById(@PathVariable Integer idUser) {
        return ResponseEntity.ok(userService.getUserById(idUser));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso.")
    })
    @PatchMapping("/{idUser}")
    public ResponseEntity<Object> update(@PathVariable Integer idUser, @RequestBody UpdateRequest request) {
        return ResponseEntity.ok(userService.updateUser(idUser, request));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Login realizado.")
    })
    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "200", description = "Imagem salva com sucesso.")
    })
    @PostMapping("/photo/{idUser}")
    public ResponseEntity<Object> uploadImage(@PathVariable Integer idUser, @RequestParam("image") MultipartFile image) throws IOException {
        return ResponseEntity.ok(userService.uploadPicture(idUser, image));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "A lista de subcategorias está vazia."),
            @ApiResponse(responseCode = "200", description = "Lista de usuários por subcategorias obtida.")
    })
    @GetMapping("/by-subcategories/{idUser}")
    public ResponseEntity<Object> getUsersBySubCategories(@PathVariable Integer idUser) {
        return ResponseEntity.ok(this.userService.getUsersBySubcategories(idUser));
    }

    @ApiResponses({
        @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
        @ApiResponse(responseCode = "200", description = "Token FCM atualizado com sucesso")
    })
    @PostMapping("/fcm-token/{idUser}")
    public ResponseEntity<Object> getUsersBySubCategories(@PathVariable Integer idUser, @RequestParam String fcmToken) {
        return ResponseEntity.ok(this.userService.updateFcmToken(idUser,fcmToken));
    }
}

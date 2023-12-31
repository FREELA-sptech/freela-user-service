package freela.usuario.service.web.controller;

import freela.usuario.service.domain.model.entities.SubCategory;
import freela.usuario.service.domain.model.entities.User;
import freela.usuario.service.domain.model.request.LoginRequest;
import freela.usuario.service.domain.model.request.RegisterRequest;
import freela.usuario.service.domain.model.request.UpdateRequest;
import freela.usuario.service.domain.model.response.UserResponse;
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
import java.util.List;

@RestController
@RequestMapping("/user")
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
    public ResponseEntity<UserResponse> getById(@PathVariable Integer idUser) {
        return ResponseEntity.ok(userService.getUserById(idUser));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso.")
    })
    @PatchMapping("/{idUser}")
    public ResponseEntity<UserResponse> update(@PathVariable Integer idUser, @RequestBody UpdateRequest request) {
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
    public ResponseEntity<List<UserResponse>> getUsersBySubCategories(@PathVariable Integer idUser) {
        return ResponseEntity.ok(this.userService.getUsersBySubcategories(idUser));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "A lista de subcategorias está vazia."),
            @ApiResponse(responseCode = "200", description = "Lista de usuários por subcategorias obtida.")
    })
    @GetMapping("/subcategories/{idUser}")
    public ResponseEntity<List<SubCategory>> getSubCategoriesUser(@PathVariable Integer idUser) {
        return ResponseEntity.ok(this.userService.getSubcategoriesUser(idUser));
    }

    @PostMapping("/fcm-token/{idUser}")
    public ResponseEntity<Object> getUsersBySubCategories(@PathVariable Integer idUser, @RequestParam String fcmToken) {
        return ResponseEntity.ok(this.userService.updateFcmToken(idUser,fcmToken));
    }
}

package freela.usuario.service.web.controller;

import freela.usuario.service.domain.model.entities.SubCategory;
import freela.usuario.service.infra.repository.SubCategoryRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sub-categories")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class SubCategoriesController {

    private final SubCategoryRepository subCategoryRepository;

    public SubCategoriesController(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Nenhuma Categoria encontrada."),
            @ApiResponse(responseCode = "200", description = "Lista de Categorias.")
    })
    @GetMapping
    public ResponseEntity<List<SubCategory>> findAll(){
        List<SubCategory> subCategories = this.subCategoryRepository.findAll();

        if(subCategories.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subCategories);
    }
}

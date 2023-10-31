package freela.usuario.service.domain.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class    RegisterRequest {
    @Size(min = 2, max = 50)
    private String name;
    @Email
    private String email;
    @Size(min = 8, max = 50)
    private String password;
    @NotNull
    @NotEmpty
    private ArrayList<Integer> subCategoriesIds;
    private String city;
    private String uf;
    private Boolean isFreelancer;
    private String deviceId;
}
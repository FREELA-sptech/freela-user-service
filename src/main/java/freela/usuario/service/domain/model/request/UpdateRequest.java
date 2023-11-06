package freela.usuario.service.domain.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest {
    private String name;
    private String city;
    private String uf;
    private String deviceId;
    private String description;
    private ArrayList<Integer> subCategoriesIds;
}

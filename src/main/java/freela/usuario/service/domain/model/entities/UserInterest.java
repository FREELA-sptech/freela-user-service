package freela.usuario.service.domain.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private User user;
    @ManyToOne
    private SubCategory subCategory;
    @ManyToOne
    private Category category;

    public UserInterest(User user, SubCategory subCategory, Category category) {
        this.user = user;
        this.subCategory = subCategory;
        this.category = category;
    }
}

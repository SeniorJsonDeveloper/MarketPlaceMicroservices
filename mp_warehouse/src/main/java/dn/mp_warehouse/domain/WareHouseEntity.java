package dn.mp_warehouse.domain;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(schema = "warehouse",name = "mp_warehouse")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WareHouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToMany(mappedBy = "warehouse")
    private List<ProductEntity> products;

    private String developerName;



}

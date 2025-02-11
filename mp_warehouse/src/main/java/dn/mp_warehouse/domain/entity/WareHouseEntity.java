package dn.mp_warehouse.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "warehouse",name = "mp_warehouse")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WareHouseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @OneToMany(mappedBy = "warehouse")
    private List<ProductEntity> products = new ArrayList<>();

    private String developerName;

    private Boolean isExists;

    private Long countOfProducts;

    @ManyToMany(mappedBy = "warehouses",fetch = FetchType.LAZY)
    private List<ShopEntity> shops;



}

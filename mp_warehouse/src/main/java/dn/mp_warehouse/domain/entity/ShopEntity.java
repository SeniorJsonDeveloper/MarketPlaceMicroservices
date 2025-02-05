package dn.mp_warehouse.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(schema = "warehouse",name = "mp_shop")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false,unique = true)
    private String name;

    private String description;

    private String countOfDeals;



    @OneToMany(mappedBy = "shop")
    private List<ProductEntity> products;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "shop_warehouse",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "warehouse_id")
    )
    private List<WareHouseEntity> warehouses;
}

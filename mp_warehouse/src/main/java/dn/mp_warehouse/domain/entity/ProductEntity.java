package dn.mp_warehouse.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "warehouse",name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity extends BaseEntity{

    private String productName;

    private BigDecimal price;

    private String description;

    private String brand;

    @Column(name = "count")
    private Long countOfProducts;

    private String buyerId;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "warehouse_id")
    private WareHouseEntity warehouse;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "shop_id")
    private ShopEntity shop;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Event> events = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private DeliveryEntity delivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id")
    private DeveloperEntity developer;


    @Override
    public String toString() {
        return "ProductEntity{" +
                "productName='" + productName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", countOfProducts=" + countOfProducts +
                ", buyerId='" + buyerId + '\'' +
                ", warehouse=" + warehouse +
                ", shop=" + shop +
                ", events=" + events +
                ", category=" + category +
                ", location=" + location +
                ", delivery=" + delivery +
                ", developer=" + developer +
                '}';
    }
}

package dn.mp_warehouse.domain.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(schema = "warehouse",name = "shops")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopEntity extends BaseEntity{

    @Column(nullable = false,unique = true)
    private String name;

    private String description;

    private Long totalCountOfDeals;

    private Double rating;

    private BigDecimal deposit;

    private String category;

    @OneToMany(mappedBy = "shop",fetch = FetchType.LAZY)
    private List<ProductEntity> products = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id")
    private DeveloperEntity developer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "shop_locations",
            joinColumns = @JoinColumn(name = "shop_id"),
    inverseJoinColumns = @JoinColumn(name = "location_id"),
            schema = "warehouse")
    private List<LocationEntity> locations = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "shop_delivery",
    joinColumns = @JoinColumn(name = "delivery_id"),
    inverseJoinColumns = @JoinColumn(name = "shop_id"),
            schema = "warehouse")
    private List<DeliveryEntity> deliveries = new ArrayList<>();

    @ManyToMany(mappedBy = "shops")
    private Set<Event> events = new HashSet<>();
}

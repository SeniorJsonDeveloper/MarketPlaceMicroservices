package dn.mp_warehouse.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(schema = "warehouse", name = "locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationEntity extends BaseEntity {

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    private String home;

    private String apartment;

    @Column(nullable = false)
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id")
    private DeveloperEntity developer;

    @ManyToMany(mappedBy = "locations", fetch = FetchType.LAZY)
    private Set<DeliveryEntity> deliveries = new HashSet<>();

    @ManyToMany(mappedBy = "locations", fetch = FetchType.LAZY)
    private List<ShopEntity> shops = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private WareHouseEntity warehouse;
}

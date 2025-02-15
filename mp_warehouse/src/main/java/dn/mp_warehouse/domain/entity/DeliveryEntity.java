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
@Table(schema = "warehouse", name = "deliveries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryEntity extends BaseEntity {

    private String description;

    private String status;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "delivery_status",schema = "orders",
            joinColumns = @JoinColumn(name = "delivery_status_id")
    )
    private Set<DeliveryStatus> deliveryStatus = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "delivery_locations",
    joinColumns = @JoinColumn(name = "delivery_id"),
    inverseJoinColumns = @JoinColumn(name = "location_id"),
            schema = "warehouse")
    private Set<LocationEntity> locations  = new HashSet<>();

    @ManyToMany(mappedBy = "deliveries")
    private Set<Event> events = new HashSet<>();

    @OneToMany(mappedBy = "delivery")
    private List<ProductEntity> products = new ArrayList<>();

    @ManyToMany(mappedBy = "deliveries")
    private List<ShopEntity> shops = new ArrayList<>();

    @ManyToMany(mappedBy = "deliveries", fetch = FetchType.LAZY)
    private List<WareHouseEntity> warehouses = new ArrayList<>();
}

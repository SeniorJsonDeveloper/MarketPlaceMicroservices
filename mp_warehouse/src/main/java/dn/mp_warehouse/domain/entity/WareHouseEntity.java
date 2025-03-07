package dn.mp_warehouse.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(schema = "warehouse",name = "warehouses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WareHouseEntity extends BaseEntity {

    @Column(nullable = false,unique = true,length = 50)
    private String name;

    @Column(nullable = false)
    private Boolean isExists;

    private Long countOfProducts;

    private Long shopId;

    @OneToMany(mappedBy = "warehouse",fetch = FetchType.LAZY,orphanRemoval = true)
    private List<ProductEntity> products = new ArrayList<>();

    @OneToMany(mappedBy = "warehouse")
    private Set<LocationEntity> locations = new HashSet<>();

    @ManyToMany(mappedBy = "warehouses")
    private Set<Event> events = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "warehouse_developers",
    joinColumns = @JoinColumn(name = "warehouse_id"),
    inverseJoinColumns = @JoinColumn(name = "developer_id"),
    schema = "warehouse")
    private List<DeveloperEntity> developers = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "delivery_warehouse",
    joinColumns = @JoinColumn(name = "warehouse_id"),
    inverseJoinColumns = @JoinColumn(name = "delivery_id"),
    schema = "warehouse")
    private List<DeliveryEntity> deliveries = new ArrayList<>();



}

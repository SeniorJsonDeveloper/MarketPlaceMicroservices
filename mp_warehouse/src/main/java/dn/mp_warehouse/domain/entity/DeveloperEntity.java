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
@Table(schema = "warehouse", name = "developers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperEntity extends BaseEntity {

    @Column(unique = true, nullable = false,length = 20)
    private String name;

    @OneToMany(mappedBy = "developer",fetch = FetchType.EAGER)
    private List<ShopEntity> shops = new ArrayList<>();

    @OneToMany(mappedBy = "developer",fetch = FetchType.LAZY)
    private Set<LocationEntity> locations = new HashSet<>();

    @ManyToMany(mappedBy = "developers",fetch = FetchType.LAZY)
    private List<WareHouseEntity> warehouses = new ArrayList<>();

    @OneToMany(mappedBy = "developer",fetch = FetchType.EAGER)
    private Set<ProductEntity> products = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "developer_category",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            schema = "warehouse")
    private Set<CategoryEntity> categories = new HashSet<>();

    @ManyToMany(mappedBy = "developers",fetch = FetchType.LAZY)
    private Set<Event> events = new HashSet<>();

}

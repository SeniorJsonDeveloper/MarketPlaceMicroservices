package dn.mp_warehouse.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@Table(schema = "warehouse",name = "mp_delivery")
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String courierId;

    private String status;

    private String orderId;

    private String warehouseId;

    private String buyerId;

    private String shopId;

    @OneToMany(mappedBy = "delivery")
    private List<ProductEntity> productEntityList;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private WareHouseEntity wareHouseEntity;
}

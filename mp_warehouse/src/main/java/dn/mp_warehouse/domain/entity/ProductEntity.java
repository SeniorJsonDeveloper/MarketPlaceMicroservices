package dn.mp_warehouse.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(schema = "warehouse",name = "mp_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private BigDecimal cost;

    private String description;

    private String category;

    private String brand;

    private String country;

    @Column(name = "count")
    private Long countOfProducts;

    @Column(unique = true)
    private String buyerId;

    @Column(unique = true)
    private String sellerId;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "warehouse_id")
    private WareHouseEntity warehouse;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "delivery_id")
    private DeliveryEntity delivery;




}

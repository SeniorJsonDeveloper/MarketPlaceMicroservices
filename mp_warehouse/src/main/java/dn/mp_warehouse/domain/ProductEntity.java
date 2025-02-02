package dn.mp_warehouse.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(schema = "warehouse",name = "mp_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    private Long count;

    @Column(unique = true)
    private String buyerId;

    @Column(unique = true)
    private String sellerId;

//    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
//    private WareHouseEntity warehouse;


}

package dn.mp_warehouse.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "warehouse", name = "schedules")
public class ScheduleEntity extends BaseEntity{

    @Column(nullable = false)
    private String description;

}

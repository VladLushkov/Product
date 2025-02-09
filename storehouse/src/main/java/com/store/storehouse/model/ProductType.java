package com.store.storehouse.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "product_type")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductType {

    public enum Const{
        FOOD,
        FURNITURE,
        CLOTH
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @CreationTimestamp
    @Column(name = "is_enable")
    private Boolean isEnable;
}

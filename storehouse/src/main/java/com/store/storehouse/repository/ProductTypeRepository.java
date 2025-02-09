package com.store.storehouse.repository;

import com.store.storehouse.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    // добавить кэши
    Optional<ProductType> getProductTypeByNameAndIsEnableTrue(String name);
}

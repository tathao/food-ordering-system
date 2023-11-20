package com.food.ordering.system.order.db.repository;

import com.food.ordering.system.order.db.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {

    Optional<List<ProductEntity>> findAllByIdIn(List<UUID> productIds);
}

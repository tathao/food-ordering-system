package com.food.ordering.system.order.db.adapter;

import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.common.domain.valueobject.ProductId;
import com.food.ordering.system.order.db.entity.ProductEntity;
import com.food.ordering.system.order.db.mapper.OrderDataAccessMapper;
import com.food.ordering.system.order.db.repository.ProductJpaRepository;
import com.food.ordering.system.order.domain.entity.Product;
import com.food.ordering.system.order.service.ports.output.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository productJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;
    @Override
    public Optional<List<Product>> findProductsByIdIn(List<UUID> productIds) {
        List<ProductEntity> productEntities = productJpaRepository.findAllByIdIn(productIds)
                .orElse(new ArrayList<>());
        if (!productEntities.isEmpty()) {
            return Optional.of(
                    productEntities.stream().map(
                            orderDataAccessMapper::productEntityToProduct
                    ).collect(Collectors.toList())
            );
        }
        return Optional.empty();
    }
}

package com.food.ordering.system.order.db.mapper;

import com.food.ordering.system.common.domain.valueobject.*;
import com.food.ordering.system.order.db.entity.AddressEntity;
import com.food.ordering.system.order.db.entity.OrderEntity;
import com.food.ordering.system.order.db.entity.OrderItemEntity;
import com.food.ordering.system.order.domain.entity.Order;
import com.food.ordering.system.order.domain.entity.OrderItem;
import com.food.ordering.system.order.domain.entity.Product;
import com.food.ordering.system.order.domain.valueobject.OrderItemId;
import com.food.ordering.system.order.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.domain.valueobject.TrackingId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.food.ordering.system.common.domain.constant.DomainConstant.FAILURE_MESSAGE_DELIMITER;

@Component
public class OrderDataAccessMapper {

    public OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .customerId(order.getCustomerId().getValue())
                .restaurantId(order.getRestaurantId().getValue())
                .totalAmount(order.getTotalAmount().getAmount())
                .trackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .address(deliveryAddressToAddressEntity(order.getDeliveryAddress()))
                .orderItems(orderItemsToOrderItemEntities(order.getItems()))
                .orderStatus(order.getOrderStatus())
                .failureMessage(order.getFailureMessages() != null ?
                        String.join(FAILURE_MESSAGE_DELIMITER, order.getFailureMessages()): "")
                .build();
        orderEntity.getAddress().setOrderEntity(orderEntity);
        orderEntity.getOrderItems().forEach(orderItemEntity -> orderItemEntity.setOrderEntity(orderEntity));
        return orderEntity;
    }

    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.builder()
                .orderId(new OrderId(orderEntity.getId()))
                .customerId(new CustomerId(orderEntity.getCustomerId()))
                .restaurantId(new RestaurantId(orderEntity.getRestaurantId()))
                .trackingId(new TrackingId(orderEntity.getTrackingId()))
                .totalAmount(new Money(orderEntity.getTotalAmount()))
                .orderStatus(orderEntity.getOrderStatus())
                .deliveryAddress(addressEntityToDeliveryAddress(orderEntity.getAddress()))
                .items(orderItemEntitiesToOrderItems(orderEntity.getOrderItems()))
                .failureMessages(orderEntity.getFailureMessage().isEmpty() ? new ArrayList<>() :
                        new ArrayList<>(Arrays.asList(orderEntity.getFailureMessage()
                                .split(FAILURE_MESSAGE_DELIMITER))))
                .build();
    }

    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> orderItems) {
        return orderItems.stream().map(
                orderItemEntity -> OrderItem.builder()
                        .orderItemId(new OrderItemId(orderItemEntity.getId()))
                        .subTotal(new Money(orderItemEntity.getSubTotal()))
                        .quantity(orderItemEntity.getQuantity())
                        .product(new Product(new ProductId(orderItemEntity.getProductId())))
                        .build()
        ).collect(Collectors.toList());
    }

    private StreetAddress addressEntityToDeliveryAddress(AddressEntity address) {
        return new StreetAddress(address.getId(), address.getStreet(), address.getPostalCode(), address.getCity());
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(List<OrderItem> items) {
        return items.stream().map(item -> OrderItemEntity.builder()
                .id(item.getId().getValue())
                .quantity(item.getQuantity())
                .subTotal(item.getSubTotal().getAmount())
                .price(item.getPrice().getAmount())
                .productId(item.getProduct().getId().getValue())
                .build()).toList();

    }

    private AddressEntity deliveryAddressToAddressEntity(StreetAddress deliveryAddress) {
        return AddressEntity.builder()
                .id(deliveryAddress.getId())
                .street(deliveryAddress.getStreet())
                .postalCode(deliveryAddress.getPostalCode())
                .city(deliveryAddress.getCity())
                .build();
    }

}

package com.food.ordering.system.order.service.mapper;

import com.food.ordering.system.common.domain.valueobject.CustomerId;
import com.food.ordering.system.common.domain.valueobject.Money;
import com.food.ordering.system.common.domain.valueobject.ProductId;
import com.food.ordering.system.common.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.domain.entity.Order;
import com.food.ordering.system.order.domain.entity.OrderItem;
import com.food.ordering.system.order.domain.entity.Product;
import com.food.ordering.system.order.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.service.dto.create.CreateOrderRequest;
import com.food.ordering.system.order.service.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.dto.create.OrderAddress;
import com.food.ordering.system.order.service.dto.track.TrackOrderResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {
    public Order createOrderCommandToOrder(CreateOrderRequest createOrderRequest) {
        return Order.builder()
                .customerId(new CustomerId(createOrderRequest.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderRequest.getRestaurantId()))
                .totalAmount(new Money(createOrderRequest.getTotal()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderRequest.getOrderAddress()))
                .items(orderItemsToOrderItemEntities(createOrderRequest.getItems()))
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(List<com.food.ordering.system.order.service.dto.create.OrderItem> items) {
        return items.stream()
                .map(orderItem -> OrderItem.builder()
                        .product(new Product(new ProductId(orderItem.getProductId()), new Money(orderItem.getPrice())))
                        .price(new Money(orderItem.getPrice()))
                        .subTotal(new Money(orderItem.getSubTotal()))
                        .quantity(orderItem.getQuantity())
                        .build()).collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress address) {
        return new StreetAddress(UUID.randomUUID(), address.getStreet(), address.getPostalCode(), address.getCity());
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return TrackOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages())
                .build();
    }
}

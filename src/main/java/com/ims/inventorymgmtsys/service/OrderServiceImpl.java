package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.Order;
import com.ims.inventorymgmtsys.input.CartInput;
import com.ims.inventorymgmtsys.input.OrderInput;
import com.ims.inventorymgmtsys.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public Order placeOrder(OrderInput orderInput, CartInput cartInput) {
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderDateTime(LocalDateTime.now());
        order.setCustomerName(orderInput.getName());
        order.setEmployeeName(orderInput.);
    }
}

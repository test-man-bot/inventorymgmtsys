package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.Employee;
import com.ims.inventorymgmtsys.entity.Order;
import com.ims.inventorymgmtsys.input.CartInput;
import com.ims.inventorymgmtsys.input.CartItemInput;
import com.ims.inventorymgmtsys.input.OrderInput;
import com.ims.inventorymgmtsys.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public Order placeOrder(OrderInput orderInput, CartInput cartInput, Employee employee) {
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderDateTime(LocalDateTime.now());
        order.setCustomerName(orderInput.getName());
        order.setEmployeeName(employee.getEmployeeName());
        order.setPaymentMethod(orderInput.getPaymentMethod());

        int totalAmount = calculateTotalAmount(cartInput.getCartItemInputs());
        int billingAmount = caluculateTax(totalAmount);

        orderRepository.insert(order);
        List<>



    }


    private int calculateTotalAmount(List<CartItemInput> cartItems) {
        int totalAmount = 0;
        for (CartItemInput cartItem : cartItems) {
            totalAmount += (cartItem.getProductPrice() * cartItem.getQuantity());
        }
        return totalAmount;
    }

    private int caluculateTax(int price) { return (int) (price * 1.1); }
}

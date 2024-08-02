package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.Employee;
import com.ims.inventorymgmtsys.entity.Order;
import com.ims.inventorymgmtsys.input.CartInput;
import com.ims.inventorymgmtsys.input.CartItemInput;
import com.ims.inventorymgmtsys.input.OrderInput;

import java.util.List;

public interface OrderService {
    int calculateTotalAmount(List<CartItemInput> cartItemInputs);
    int calculateTax(int price);
    Order placeOrder(OrderInput orderInput, CartInput cartInput, Employee employee);

    Order findById(String orderId);

    List<Order> findAll();
}

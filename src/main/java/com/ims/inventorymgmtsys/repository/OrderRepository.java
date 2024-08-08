package com.ims.inventorymgmtsys.repository;

import com.ims.inventorymgmtsys.entity.Employee;
import com.ims.inventorymgmtsys.entity.Order;

import java.util.List;

public interface OrderRepository {
    void save(Order order, Employee employee);

    List<Order> findAll();

    Order findById(String orderId);
}

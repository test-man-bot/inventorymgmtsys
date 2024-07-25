package com.ims.inventorymgmtsys.repository;

import com.ims.inventorymgmtsys.entity.Employee;
import com.ims.inventorymgmtsys.entity.Order;

public interface OrderRepository {
    void insert(Order order, Employee employee);
}

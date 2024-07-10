package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.Employee;
import com.ims.inventorymgmtsys.entity.Order;
import com.ims.inventorymgmtsys.input.CartInput;
import com.ims.inventorymgmtsys.input.OrderInput;

public interface OrderService {
    Order placeOrder(OrderInput orderInput, CartInput cartInput, Employee employee);
}

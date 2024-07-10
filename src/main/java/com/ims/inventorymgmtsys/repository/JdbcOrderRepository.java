package com.ims.inventorymgmtsys.repository;

import com.ims.inventorymgmtsys.entity.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcOrderRepository implements OrderRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}

    @Override
    public void insert(Order order) {
        jdbcTemplate.update("INSERT INTO t_order VALUES (?,?,?,?,?)",
                order.getOrderId(),
                order.getOrderDateTime(),
                order.getCustomerName(),
                order.getEmployeeName(),
                order.getPaymentMethod()
                );
    }

}

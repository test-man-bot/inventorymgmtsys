package com.ims.inventorymgmtsys.repository;

import com.ims.inventorymgmtsys.entity.Employee;
import com.ims.inventorymgmtsys.entity.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}

    @Override
    public void insert(Order order, Employee employee) {
        jdbcTemplate.update("INSERT INTO t_order VALUES (?,?,?,?,?)",
                order.getOrderId(),
                order.getOrderDateTime(),
                order.getCustomerName(),
                order.getEmployeeName(),
                order.getPaymentMethod().toString()
                );
    }

    @Override
    public List<Order> findAll(){
        return jdbcTemplate.query("SELECT * FROM t_order", new DataClassRowMapper<>(Order.class));
    }

    @Override
    public Order findById(String orderId) {
        List<Order> orders = jdbcTemplate.query("SELECT * FROM t_order WHERE id = ?", new BeanPropertyRowMapper<>(Order.class), orderId);
        return orders.isEmpty() ? null : orders.get(0);
    }

}

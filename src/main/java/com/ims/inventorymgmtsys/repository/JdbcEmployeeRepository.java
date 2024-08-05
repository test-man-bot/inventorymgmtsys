package com.ims.inventorymgmtsys.repository;

import com.ims.inventorymgmtsys.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcEmployeeRepository implements EmployeeRepository{

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public JdbcEmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Employee findById(String employeeid) {
        return jdbcTemplate.queryForObject("SELECT * FROM employee WHERE employeeid = ?", new DataClassRowMapper<>(Employee.class), employeeid);
    }


    @Override
    public List<Employee> findAll() {
        return jdbcTemplate.query("SELECT * FROM employee", new DataClassRowMapper<>(Employee.class));
    }
}

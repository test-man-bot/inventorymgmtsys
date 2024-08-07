package com.ims.inventorymgmtsys.repository;

import com.ims.inventorymgmtsys.entity.Employee;

import java.util.List;

public interface EmployeeRepository {

    Employee findById(String id);

    List<Employee> findAll();

    boolean update(Employee employee);
    void save(Employee employee);
}

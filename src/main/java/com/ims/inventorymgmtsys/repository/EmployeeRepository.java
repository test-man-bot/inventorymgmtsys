package com.ims.inventorymgmtsys.repository;

import com.ims.inventorymgmtsys.entity.Employee;

import java.util.List;

public interface EmployeeRepository {

    Employee selectById(String id);

    List<Employee> selectAll();
}

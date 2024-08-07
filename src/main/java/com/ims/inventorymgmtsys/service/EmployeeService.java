package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.Employee;
import com.ims.inventorymgmtsys.entity.EmployeeWrapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface EmployeeService {
    List<Employee> findAll();
    Employee findById(String id);
    boolean update(Employee employee);
    void save(Employee employee);
    EmployeeWrapper getEmployeeWrapper();
    boolean updateEmployees(List<Employee> employees);
}

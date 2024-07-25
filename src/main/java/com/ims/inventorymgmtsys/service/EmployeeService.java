package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.Employee;
import com.ims.inventorymgmtsys.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    List<Employee> selectAll();
    Employee selectById(String id);
}

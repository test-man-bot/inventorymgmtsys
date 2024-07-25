package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.Employee;
import com.ims.inventorymgmtsys.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Override
    public List<Employee> selectAll() {
//        List<Employee> employees = employeeRepository.selectAll();
        // Employeeリストの内容をログに出力
//        for (Employee employee : employees) {
//            System.out.println("Employee ID: " + employee.getEmployeeId() + ", Employee Name: " + employee.getEmployeeName());
//        }
//        return employees;
        return employeeRepository.selectAll();
    }

    @Override
    public Employee selectById(String employeeId) {
        return employeeRepository.selectById(employeeId);
    }
}

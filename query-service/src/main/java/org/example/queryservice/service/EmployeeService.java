package org.example.queryservice.service;

import lombok.RequiredArgsConstructor;
import org.example.sharedlib.model.Employee;
import org.example.sharedlib.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}

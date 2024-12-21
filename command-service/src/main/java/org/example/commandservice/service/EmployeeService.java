package org.example.commandservice.service;

import lombok.RequiredArgsConstructor;
import org.example.commandservice.event.KafkaProducer;
import org.example.sharedlib.model.Employee;
import org.example.sharedlib.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final KafkaProducer kafkaProducer;

    public Employee saveEmployee(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        kafkaProducer.sendMessage("Employee created with ID: " + savedEmployee.getId());
        return savedEmployee;
    }
}

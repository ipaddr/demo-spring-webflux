package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository
        extends ReactiveCrudRepository<Employee, Integer> {
}

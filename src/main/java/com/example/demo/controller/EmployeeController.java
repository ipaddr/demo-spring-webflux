package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/hello")
    public Mono<String> hello(){
        return Mono.just("Hello world");
    }

    @GetMapping("/employees/{id}")
    public Mono<Employee> getEmployee(@PathVariable("id") int id){
        return employeeRepository.findById(id);
    }

    @GetMapping("/employees")
    public Flux<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Mono<Employee> createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @PutMapping("/employees/{id}")
    public Mono<Employee> updateEmployee(@PathVariable("id") int id
            , @RequestBody Employee employee){
        return employeeRepository.findById(id).map(e->{
           e.setFirstName(employee.getFirstName());
           e.setLastName(employee.getLastName());
           e.setEmail(employee.getEmail());
           return e;
        }).flatMap(e -> employeeRepository.save(e));
    }

    @DeleteMapping("/employees/{id}")
    public Mono<Void> deleteEmployee(@PathVariable("id") int id){
        return employeeRepository.deleteById(id);
    }
}
















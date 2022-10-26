package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

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

    @GetMapping(path = "/employees")
    public Flux<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    @GetMapping(path = "/employees-r", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Employee> getAllEmployeeReactive(){
        return employeeRepository.findAll().delayElements(Duration.ofSeconds(1L));
    }

    @PostMapping("/employees")
    public Mono<Employee> createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @PutMapping("/employees/{id}")
    public Mono<Employee> updateEmployee(@PathVariable("id") int id
            , @RequestBody Employee employee){
        return
                // ini mengambil data dari database
                employeeRepository.findById(id)
                // ini mentransformasi content datanya
                .map(e->{
                   e.setFirstName(employee.getFirstName());
                   e.setLastName(employee.getLastName());
                   e.setEmail(employee.getEmail());
                   return e;
                })
                // menyimpan data ke database
                .flatMap(e -> employeeRepository.save(e));
    }

    @DeleteMapping("/employees/{id}")
    public Mono<Void> deleteEmployee(@PathVariable("id") int id){
        return employeeRepository.deleteById(id);
    }
}
















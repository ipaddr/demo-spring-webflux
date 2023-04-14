package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeMongoDB;
import com.example.demo.repository.EmployeeMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.Flow;

@RestController
@RequestMapping("api-mongo")
public class EmployeeMongoDbController {

    @Autowired
    EmployeeMongoRepository employeeMongoRepository;

    @GetMapping("/hello")
    public Mono<String> hello(){
        return Mono.just("Hello world");
    }

//    @GetMapping("/employees/{id}")
//    public Flux<EmployeeMongoDB> getEmployee(@PathVariable("id") int id){
//        return employeeMongoRepository.findEmployeeByObjectId(String.valueOf(id));
//    }

    @GetMapping(path = "/employees")
    public Flux<EmployeeMongoDB> getAllEmployee(){
        return Flux.just((EmployeeMongoDB) employeeMongoRepository.findAll());
    }

//    @GetMapping(path = "/employees-r", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<Employee> getAllEmployeeReactive(){
//        return employeeMongoRepository.findAll().delayElements(Duration.ofSeconds(1L));
//    }
//
//    @PostMapping("/employees")
//    public Mono<Employee> createEmployee(@RequestBody Employee employee){
//        return employeeMongoRepository.save(employee);
//    }
//
//    @PutMapping("/employees/{id}")
//    public Mono<Employee> updateEmployee(@PathVariable("id") int id
//            , @RequestBody Employee employee){
//        return
//                // ini mengambil data dari database
//                employeeMongoRepository.findById(id)
//                // ini mentransformasi content datanya
//                .map(e->{
//                   e.setFirstName(employee.getFirstName());
//                   e.setLastName(employee.getLastName());
//                   e.setEmail(employee.getEmail());
//                   return e;
//                })
//                // menyimpan data ke database
//                .flatMap(e -> employeeMongoRepository.save(e));
//    }
//
//    @DeleteMapping("/employees/{id}")
//    public Mono<Void> deleteEmployee(@PathVariable("id") int id){
//        return employeeMongoRepository.deleteById(id);
//    }
}
















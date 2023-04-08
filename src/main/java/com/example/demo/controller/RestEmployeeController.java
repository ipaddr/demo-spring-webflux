package com.example.demo.controller;

import com.example.demo.kafka.Producer;
import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeMongoDB;
import com.example.demo.repository.EmployeeMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kafka")
public class RestEmployeeController {

    @Autowired
    private  final EmployeeMongoRepository employeeMongoRepository;
    private final Producer producer;

    RestEmployeeController(EmployeeMongoRepository employeeMongoRepository, Producer producer){
        this.employeeMongoRepository = employeeMongoRepository;
        this.producer = producer;
    }

    @RequestMapping(value = "employees", method = RequestMethod.GET)
    public List<EmployeeMongoDB> getEmployeeMongoDB(){
        return  employeeMongoRepository.findAll();
    }

    @RequestMapping(value = "employee-get", method = RequestMethod.GET)
    public void addEmployeeMongoDB(@RequestParam("msg") String message){
        producer.sendMessage(message);
    }

    @RequestMapping(value = "add-employee", method = RequestMethod.POST)
    public EmployeeMongoDB createEmployee(@RequestBody EmployeeMongoDB employee){
        return employeeMongoRepository.save(employee);
    }

}

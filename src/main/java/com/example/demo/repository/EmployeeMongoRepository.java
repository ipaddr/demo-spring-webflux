package com.example.demo.repository;

import com.example.demo.model.EmployeeMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EmployeeMongoRepository extends MongoRepository<EmployeeMongoDB, String> { }

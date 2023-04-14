package com.example.demo.kafka;

import com.example.demo.model.EmployeeMongoDB;
import com.example.demo.repository.EmployeeMongoRepository;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    @Autowired
    private EmployeeMongoRepository employeeMongoRepository;

    @Autowired
    NewTopic newTopic;

    public ConsumerService (EmployeeMongoRepository employeeMongoRepository){
        this.employeeMongoRepository = employeeMongoRepository;
    }

    @KafkaListener(topics = "newtopic", groupId = "spring-boot-api-consumer"
            , containerFactory = "kafkaListenerContainerFactory")
    public void consume(String message){
        System.out.println(message);
    }
}

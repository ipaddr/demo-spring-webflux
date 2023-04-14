package com.example.demo.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public final class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private  NewTopic newTopic;

    Producer(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message){
//        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(newTopic.name(), message);

//        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                logger.error("faild to send message", ex);
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, String> result) {
//                logger.info("sent message successfully");
//            }
//        });
    }

}

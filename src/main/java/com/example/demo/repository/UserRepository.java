package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository
        extends ReactiveCrudRepository<User, Integer> {
    Mono<User> findByUsername(String username);
}

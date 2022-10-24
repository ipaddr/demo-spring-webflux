package com.example.demo.client;

import com.example.demo.model.Employee;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("client")
public class EmployeeClient {

    private final WebClient webClient;

    public EmployeeClient(){
        // add timeout
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                })
                ;
        // add webclient with timeout
        webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @GetMapping(value = "/employees")
    @ResponseBody
    public Mono<ResponseEntity<Flux<Employee>>> allEmployees(){
        return webClient.get()
                .uri("/api/employees")
                .retrieve()
                .toEntityFlux(Employee.class)
                ;
    }

    @GetMapping(value = "/employees/id")
    @ResponseBody
    public Mono<ResponseEntity<Employee>> getEmployee(@RequestParam("id") int id){
        return webClient.get()
                .uri("/api/employees/{id}", id)
                .retrieve()
                .toEntity(Employee.class)
                ;
    }

    @GetMapping(value = "/employees/add")
    @ResponseBody
    public Mono<ResponseEntity<Employee>> addEmployee(@RequestParam("firstname") String firstName
            ,@RequestParam("lastname") String lastName
            ,@RequestParam("email") String email){

        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);

        return webClient
                .post()
                .uri("/api/employees")
                .bodyValue(employee)
                .retrieve()
                .toEntity(Employee.class)
        ;

    }

    @GetMapping(value = "/employees/update")
    @ResponseBody
    public Mono<ResponseEntity<Employee>> updateEmployee(@RequestParam("id") int id
            ,@RequestParam("firstname") String firstName
            ,@RequestParam("lastname") String lastName
            ,@RequestParam("email") String email){

        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);

        return webClient
                .put()
                .uri("/api/employees/{id}", id)
                .bodyValue(employee)
                .retrieve()
                .toEntity(Employee.class)
                ;

    }

    @GetMapping(value = "/employees/delete")
    @ResponseBody
    public Mono<ResponseEntity<Void>> deleteEmployee(@RequestParam("id") int id){
        return webClient
                .delete()
                .uri("/api/employees/{id}", id)
                .retrieve()
                .toEntity(Void.class)
                ;

    }

}




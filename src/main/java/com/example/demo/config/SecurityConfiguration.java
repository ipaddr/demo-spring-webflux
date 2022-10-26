package com.example.demo.config;

import com.example.demo.service.MyUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    @Primary
    public MyUserDetailService userDetailService(){
        return new MyUserDetailService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(){
        return authentication -> userDetailService().findByUsername(authentication.getPrincipal().toString())
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("Username not found!")))
                .flatMap(user -> {
                    final String username = authentication.getPrincipal().toString();
                    final String password = authentication.getCredentials().toString();

                    if (passwordEncoder().matches(password, user.getPassword())) {
                        return Mono.just(new UsernamePasswordAuthenticationToken(username, user.getPassword(), user.getAuthorities()));
                    }
                    return Mono.just(new UsernamePasswordAuthenticationToken(username, authentication.getCredentials()));
                });
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic(Customizer.withDefaults())
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/**").hasAnyAuthority("admin", "employee")
                .pathMatchers(HttpMethod.PUT, "/**").hasAnyAuthority("admin")
                .anyExchange()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .logout();
        return http.build();
    }

}

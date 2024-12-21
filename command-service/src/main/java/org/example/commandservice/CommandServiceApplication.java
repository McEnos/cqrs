package org.example.commandservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
        "org.example.sharedlib.*"
})
@EnableJpaRepositories(basePackages = {
        "org.example.sharedlib.*"
})
//https://medium.com/@vino7tech/implementing-cqrs-architecture-in-spring-boot-with-kafka-zookeeper-docker-and-mysql-11623f61ad68
public class CommandServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommandServiceApplication.class, args);
    }

}

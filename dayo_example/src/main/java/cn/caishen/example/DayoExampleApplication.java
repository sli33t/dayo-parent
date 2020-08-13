package cn.caishen.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DayoExampleApplication {
    public static void main( String[] args ) {
    	SpringApplication.run(DayoExampleApplication.class, args);
    }
}


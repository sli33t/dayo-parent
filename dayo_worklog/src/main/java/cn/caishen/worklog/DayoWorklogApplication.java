package cn.caishen.worklog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableRedisHttpSession
public class DayoWorklogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DayoWorklogApplication.class, args);
    }
}

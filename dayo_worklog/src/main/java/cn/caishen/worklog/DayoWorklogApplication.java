package cn.caishen.worklog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
//@EnableRedisHttpSession
public class DayoWorklogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DayoWorklogApplication.class, args);
    }
}

package cn.caishen.auth;

import cn.caishen.auth.config.JwtProperties;
import cn.caishen.service.DayoAuthService;
import cn.caishen.service.DayoRoleService;
import cn.caishen.service.DayoUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(clients = {DayoUserService.class, DayoRoleService.class, DayoAuthService.class})
@EnableConfigurationProperties(JwtProperties.class)
public class DayoAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(DayoAuthApplication.class, args);
    }
}

package cn.caishen.system;

import cn.caishen.service.DayoAuthService;
import cn.caishen.service.DayoRoleService;
import cn.caishen.service.DayoUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
//@EnableRedisHttpSession
@EnableFeignClients(clients = {DayoUserService.class, DayoRoleService.class, DayoAuthService.class})
public class DayoSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DayoSystemApplication.class, args);
    }
}

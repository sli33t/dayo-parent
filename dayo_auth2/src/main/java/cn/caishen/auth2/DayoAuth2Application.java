package cn.caishen.auth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DayoAuth2Application 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(DayoAuth2Application.class, args);
    }
}

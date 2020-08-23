package com.example.iotmqtt;

import com.example.iotmqtt.subclint.MqttPushClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.UUID;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@MapperScan("com.example.iotmqtt.mapper")
@Configuration
@EnableSwagger2
public class IotMqttApplication {

    public static void main(String[] args) {
        try{
            SpringApplication.run(IotMqttApplication.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}

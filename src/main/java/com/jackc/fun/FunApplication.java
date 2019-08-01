package com.jackc.fun;

import com.jackc.fun.listener.CloseListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Set;

@SpringBootApplication
@EnableScheduling
public class FunApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(FunApplication.class);
        app.addListeners(new CloseListener());
        app.run(args);
    }

}

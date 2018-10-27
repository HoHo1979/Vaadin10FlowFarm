package com.iotarch.farm;

import com.iotarch.farm.entity.User;
import com.iotarch.farm.repository.UserRepository;
import com.iotarch.farm.security.WebSecurityConfig;
import com.iotarch.farm.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackageClasses = { WebSecurityConfig.class, MainView.class, FarmApplication.class,
        UserService.class }, exclude = ErrorMvcAutoConfiguration.class)
@EntityScan(basePackageClasses = { User.class })
public class FarmApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FarmApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FarmApplication.class);
    }
}

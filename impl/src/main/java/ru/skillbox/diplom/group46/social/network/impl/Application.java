package ru.skillbox.diplom.group46.social.network.impl;

import org.springframework.boot.SpringApplication;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


import org.springframework.cloud.openfeign.EnableFeignClients;


import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepositoryImpl;
import ru.skillbox.diplom.group46.social.network.impl.service.geo.GeoClient;

/**
 * Application
 *
 * @author author_name
 */

@EnableScheduling
@SpringBootApplication
@EntityScan("ru.skillbox.diplom.group46.social.network.domain")
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
@EnableFeignClients(basePackageClasses = GeoClient.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

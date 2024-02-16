package ru.skillbox.diplom.group46.social.network.impl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepositoryImpl;

/**
 * Application
 *
 * @author author_name
 */
@SpringBootApplication
@EntityScan("ru.skillbox.diplom.group46.social.network.domain.*")
@EnableJpaRepositories(basePackages = "ru.skillbox.diplom.group46.social.network.impl.repository.*")

@SpringBootApplication()
@EntityScan(basePackages = {"ru.skillbox.diplom.group46.social.network.*"})
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
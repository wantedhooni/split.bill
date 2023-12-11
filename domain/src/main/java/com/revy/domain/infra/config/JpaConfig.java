package com.revy.domain.infra.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Revy on 2023.12.08
 */
@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = {"com.revy.domain"})
@EnableJpaRepositories(basePackages = {"com.revy.domain"})
public class JpaConfig {
}

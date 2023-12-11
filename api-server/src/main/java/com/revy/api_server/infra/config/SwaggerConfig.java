package com.revy.api_server.infra.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Revy on 2023.12.05
 */
@OpenAPIDefinition(
        info = @Info(title = "더치페이 정산하기 API",
                description = "더치페이 정산하기 API",
                version = "v1"))
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/**"};


        return GroupedOpenApi.builder()
                .group("더치페이 정산하기 API")
                .pathsToMatch(paths)
                .build();
    }

}

package com.doittogether.platform.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("springdoc-openapi")
                        .version("1.0")
                        .description("doto")
                )
                .components(new Components()
                        .addSecuritySchemes("Authorization",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("Authorization"));
    }

    @Bean
    public OpenApiCustomizer customisePaginationParameters() {
        return openApi -> openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations()
                .forEach(operation -> {
                    if (operation.getParameters() != null) {
                        operation.getParameters().forEach(parameter -> {
                            switch (parameter.getName()) {
                                case "page" -> parameter.setDescription("페이지 번호 (0부터 시작)");
                                case "size" -> parameter.setDescription("페이지 크기 (한 페이지의 항목 수)");
                                case "sort" -> parameter.setDescription("정렬 기준 (예: userId,asc)");
                            }
                        });
                    }
                }));
    }
}

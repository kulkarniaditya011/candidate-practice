package com.employee.app.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class OpenApiConfig {
    public static final String SECURITY_SCHEME_NAME = "Bearer Authentication";

    public OpenAPI openAPIConfig(){
        return new OpenAPI()
                .info(new Info()
                        .title("Practice Apis")
                        .description("Rest API for practice")
                        .version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Provide JWT token for authentication")));
    }
}

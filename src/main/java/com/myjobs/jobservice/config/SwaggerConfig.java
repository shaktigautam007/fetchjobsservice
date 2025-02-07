package com.myjobs.jobservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(
        info = @Info(title="JOBS API",description="This API is developed to fetch SAVED,ARCHIVED,APPLIED,IN PROGRESS JOBS"),
        servers = {
                @Server(description = "Dev",url = "http://localhost:8080/"),
                @Server(description = "Test",url = "http://localhost:8080/")
        }
)
@SecurityScheme(
        name = "x-csrf-token",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "X-CSRF-TOKEN"
)

public class SwaggerConfig {
}

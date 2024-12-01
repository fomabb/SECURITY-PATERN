package com.iase24.crazy_task_tracker_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Nikolay Kirilyuk",
                        email = "chaosbb@uandex.by",
                        url = "https://test.iase24.com/"
                ), description = "Open Api documentation",
                title = "Open Api specification - org.iase24.com",
                version = "1.0",
                license = @License(
                        name = "Backend-developer",
                        url = "https://www.linkedin.com/feed/"
                ),
                termsOfService = "https://test.iase24.com/"
        ),
        servers = {
                @Server(
                        description = "LOCAL",
                        url = "http://localhost:8076"
                ),
                @Server(
                        description = "DEV",
                        url = "http://localhost:8087"
                )
        }
)
public class SwaggerConfig {
}

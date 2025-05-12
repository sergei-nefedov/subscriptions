package pers.nefedov.subscriptions.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;


@OpenAPIDefinition(
        info = @Info(
                title = "Subscription service",
                description = "API documentation", version = "1.0.0",
                contact = @Contact(
                        name = "Sergei Nefedov",
                        email = "post.fvyf6@slmail.me"
                )
        )
)
public class OpenApiConfig {
}
package co.istad.kongkea.spring_authorization_server.feature.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank
        @Size(min = 3, max = 100)
        String username,

        @NotBlank
        @Size(min = 8, max = 200)
        String password)
{ }

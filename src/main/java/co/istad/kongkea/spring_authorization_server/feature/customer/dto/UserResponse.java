package co.istad.kongkea.spring_authorization_server.feature.customer.dto;

public record UserResponse(
        String username,
        String role,
        boolean enabled) {
}

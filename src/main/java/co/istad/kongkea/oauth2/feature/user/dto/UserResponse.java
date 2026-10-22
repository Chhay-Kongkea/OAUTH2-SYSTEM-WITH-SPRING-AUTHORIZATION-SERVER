package co.istad.kongkea.oauth2.feature.user.dto;

public record UserResponse(
        String username,
        String email,
        String role,
        boolean enabled) {
}

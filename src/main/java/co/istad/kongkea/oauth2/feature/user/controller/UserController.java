package co.istad.kongkea.oauth2.feature.user.controller;

import co.istad.kongkea.oauth2.feature.user.dto.CreateUserRequest;
import co.istad.kongkea.oauth2.feature.user.dto.UserResponse;
import co.istad.kongkea.oauth2.feature.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {

        return userService.create(request);

    }

    @GetMapping("/users/me")
    public UserResponse me(Authentication authentication) {

        return userService.getResponse(authentication.getName());

    }
}

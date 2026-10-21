package co.istad.kongkea.spring_authorization_server.feature.customer.controller;

import co.istad.kongkea.spring_authorization_server.feature.customer.dto.CreateUserRequest;
import co.istad.kongkea.spring_authorization_server.feature.customer.dto.UserResponse;
import co.istad.kongkea.spring_authorization_server.feature.customer.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {

        return userService.create(request);

    }
}

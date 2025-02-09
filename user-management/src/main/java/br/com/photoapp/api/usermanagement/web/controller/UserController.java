package br.com.photoapp.api.usermanagement.web.controller;

import br.com.photoapp.api.usermanagement.mapper.UserMapper;
import br.com.photoapp.api.usermanagement.service.UserService;
import br.com.photoapp.api.usermanagement.web.representation.request.CreateUserRequest;
import br.com.photoapp.api.usermanagement.web.representation.response.UserResponse;
import br.com.photoapp.eureka.photoappcommons.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable("userId") Long userId) {
        User user = userService.getUserById(userId);

        return UserMapper.fromDomainToResponse(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest userRequest) {
        User user = userService.createUser(userRequest);

        return UserMapper.fromDomainToResponse(user);
    }
}

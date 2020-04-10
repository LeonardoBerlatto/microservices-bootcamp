package br.com.photoapp.api.usermanagement.web.controller;

import br.com.photoapp.api.usermanagement.domain.User;
import br.com.photoapp.api.usermanagement.mapper.UserMapper;
import br.com.photoapp.api.usermanagement.service.UserService;
import br.com.photoapp.api.usermanagement.web.representation.user.request.CreateUserRequest;
import br.com.photoapp.api.usermanagement.web.representation.user.response.UserResponse;
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
        User user = userService.create(userRequest);

        return UserMapper.fromDomainToResponse(user);
    }
}

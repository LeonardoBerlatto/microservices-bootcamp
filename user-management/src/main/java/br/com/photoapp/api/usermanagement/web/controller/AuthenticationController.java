package br.com.photoapp.api.usermanagement.web.controller;


import br.com.photoapp.api.usermanagement.security.AuthenticationService;
import br.com.photoapp.api.usermanagement.web.representation.request.LoginRequest;
import br.com.photoapp.api.usermanagement.web.representation.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return this.authenticationService.authenticate(loginRequest);
    }
}

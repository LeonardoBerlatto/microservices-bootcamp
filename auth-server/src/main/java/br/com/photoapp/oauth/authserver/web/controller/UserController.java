package br.com.photoapp.oauth.authserver.web.controller;

import br.com.photoapp.oauth.authserver.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public UserPrincipal getUser(@AuthenticationPrincipal UserPrincipal user) {
        return user;
    }
}

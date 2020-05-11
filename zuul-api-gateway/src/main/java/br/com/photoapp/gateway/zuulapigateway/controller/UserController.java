package br.com.photoapp.gateway.zuulapigateway.controller;

import br.com.photoapp.gateway.zuulapigateway.controller.representation.response.CurrentUserResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class UserController {

    @GetMapping("/me")
    public CurrentUserResponse getUser(OAuth2Authentication user) {
        return CurrentUserResponse.builder()
                .username(user.getName())
                .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .build();
    }
}

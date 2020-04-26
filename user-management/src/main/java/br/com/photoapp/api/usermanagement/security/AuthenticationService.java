package br.com.photoapp.api.usermanagement.security;


import br.com.photoapp.api.usermanagement.web.representation.request.LoginRequest;
import br.com.photoapp.api.usermanagement.web.representation.response.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class AuthenticationService {

    @Value("${security.jwt.prefix}")
    private String headerPrefix;

    @Value("${security.jwt.expiration}")
    private Integer tokenExpirationTime;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponse authenticate(final LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = format("%s %s", headerPrefix, jwtTokenProvider.generateToken(authentication));

        return LoginResponse.builder().token(token).expirationTime(tokenExpirationTime).build();
    }
}

package br.com.photoapp.api.usermanagement.security;

import br.com.photoapp.api.usermanagement.service.impl.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.springframework.security.core.context.SecurityContextHolder.*;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    @Value("${security.jwt.header}")
    private String tokenHeader;

    @Value("${security.jwt.prefix}")
    private String tokenPrefix;

    @Value("${security.jwt.secret}")
    private String tokenSecret;

    private final JwtTokenProvider jwtTokenProvider;

    private final CustomUserDetailsService customUserDetailsService;

    public JwtTokenAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 1. get the authentication header. Tokens are supposed to be passed in the authentication header
        String header = request.getHeader(tokenHeader);

        // 2. validate the header and check the prefix
        if (header == null || !header.startsWith(tokenPrefix)) {
            chain.doFilter(request, response);        // If not valid, go to the next filter.
            return;
        }

        // 3. Get the token
        String token = header.replace(format("%s ", tokenPrefix), "");

        try {    // exceptions might be thrown in creating the claims if for example the token is expired

            // 4. Validate the token
            jwtTokenProvider.getUserId(token).ifPresent(id -> {
                UserDetails user = customUserDetailsService.loadUserById(id);

                // 5. Create auth object
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        user, null, Collections.emptyList());

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. Authenticate the user
                getContext().setAuthentication(auth);
            });

        } catch (Exception e) {
            // In case of failure. Make sure it's clear; so guarantee user won't be authenticated
            clearContext();
        }

        // go to the next filter in the filter chain
        chain.doFilter(request, response);
    }

}
package br.com.photoapp.gateway.zuulapigateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static java.util.Objects.nonNull;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    @Value("${security.jwt.header}")
    private String tokenHeader;

    @Value("${security.jwt.prefix}")
    private String tokenPrefix;

    @Value("${security.jwt.secret}")
    private String tokenSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 1. get the authentication header. Tokens are supposed to be passed in the authentication header
        final String header = request.getHeader(tokenHeader);

        // 2. validate the header and check the prefix
        if(header == null || !header.startsWith(tokenPrefix)) {
            chain.doFilter(request, response);  		// If not valid, go to the next filter.
            return;
        }

        // 3. Get the token
        final String token = header.replace(String.format("%s ",tokenPrefix), "");

        try {	// exceptions might be thrown in creating the claims if for example the token is expired

            // 4. Validate the token

           Claims claims = parse(token).getBody();

            final String username = claims.getSubject();
            if(nonNull(username)) {

                // 5. Create auth object
                // UsernamePasswordAuthenticationToken: A built-in object, used by spring to represent the current authenticated / being authenticated user.
                final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.emptyList());

                // 6. Authenticate the user
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            // In case of failure. Make sure it's clear; so guarantee user won't be authenticated
            SecurityContextHolder.clearContext();
        }

        // go to the next filter in the filter chain
        chain.doFilter(request, response);
    }

    private Jws<Claims> parse(String jwt) {
        return Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(jwt);
    }

}
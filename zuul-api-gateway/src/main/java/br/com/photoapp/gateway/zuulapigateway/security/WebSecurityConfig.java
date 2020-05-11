package br.com.photoapp.gateway.zuulapigateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${zuul.routes.user-management-microservice.signup.path}")
    private String userSignupUrl;

    @Value("${zuul.routes.user-management-microservice.login.path}")
    private String userLoginUrl;

    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    public WebSecurityConfig(JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter) {
        this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // handle an authorized attempts
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                // Add a filter to validate the tokens with every request
                .addFilterAfter(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // Authorization requests config
                .authorizeRequests()

                // Allow all who are accessing users service
                .antMatchers(HttpMethod.POST, userSignupUrl, userLoginUrl)
                .permitAll()

                // Any other request must be authenticated
                .anyRequest().authenticated();
    }
}

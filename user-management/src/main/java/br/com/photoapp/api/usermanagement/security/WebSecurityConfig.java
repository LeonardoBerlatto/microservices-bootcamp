package br.com.photoapp.api.usermanagement.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Value("api.user.singup.path")
    private String userSignupUrl;

    @Value("api.gateway.ip")
    private String apiGatewayIpAdress;

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .csrf().and()
                .headers().frameOptions()
                .disable()
                .and()
                .exceptionHandling()
                .and()
                .authorizeRequests()

                // Swagger
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()

                // Login and Signup
                .antMatchers(HttpMethod.POST, userSignupUrl)
                .hasIpAddress(apiGatewayIpAdress).antMatchers()
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

package br.com.photoapp.gateway.zuulapigateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableResourceServer
public class WebSecurityConfig extends ResourceServerConfigurerAdapter {

    @Value("${zuul.routes.user-management-microservice.signup.path}")
    private String userSignupUrl;

    @Value("${zuul.routes.authorization-service.login.path}")
    private String userLoginUrl;

    @Override
    public void configure(HttpSecurity security) throws Exception {
        security
                .csrf().disable()
                // handle an authorized attempts
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                // Add a filter to validate the tokens with every request
                //.addFilterAfter(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // Authorization requests config
                .authorizeRequests()

                // Allow all who are accessing users service
                .antMatchers(HttpMethod.POST, userSignupUrl, userLoginUrl)
                .permitAll()

                // Any other request must be authenticated
                .anyRequest().authenticated();
    }
}

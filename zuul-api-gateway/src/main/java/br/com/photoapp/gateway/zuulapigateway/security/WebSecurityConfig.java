package br.com.photoapp.gateway.zuulapigateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@EnableResourceServer
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends ResourceServerConfigurerAdapter {

    @Value("${zuul.routes.user-management-microservice.signup.path}")
    private String userSignupUrl;

    @Value("${zuul.routes.authorization-service.login.path}")
    private String userLoginUrl;

    @Override
    public void configure(HttpSecurity security) throws Exception {
        security
                .csrf().disable()
                .authorizeRequests()

                // Allow all who are accessing users service
                .antMatchers(HttpMethod.POST, userSignupUrl, userLoginUrl)
                .permitAll()
                .anyRequest().authenticated();
    }
}

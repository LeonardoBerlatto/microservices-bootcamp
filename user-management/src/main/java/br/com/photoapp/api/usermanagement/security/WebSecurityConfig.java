package br.com.photoapp.api.usermanagement.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@EnableResourceServer
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends ResourceServerConfigurerAdapter {

    @Value("${api.signup.path}")
    private String userSignupUrl;

    @Override
    public void configure(HttpSecurity security) throws Exception {
        security
                .csrf().disable()
                .authorizeRequests()

                // Allow all who are accessing users service
                .antMatchers(HttpMethod.POST, userSignupUrl)
                .permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

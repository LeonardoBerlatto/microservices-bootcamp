package br.com.photoapp.oauth.authserver.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
@EqualsAndHashCode(of = "username")
public class UserPrincipal implements UserDetails {
    private static String ROLE_PREFIX = "ROLE_";

    private String username;
    @JsonIgnore
    private String passsword;
    private Collection<? extends GrantedAuthority> authorities;


    @Autowired
    public UserPrincipal(String username, String passsword, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.passsword = passsword;
        this.authorities = authorities;
    }

    public static UserPrincipal create(String passsword) {

        List<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority(ROLE_PREFIX+"USER")
        );

        return new UserPrincipal(
                "user",
                passsword,
                authorities
        );
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.passsword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}

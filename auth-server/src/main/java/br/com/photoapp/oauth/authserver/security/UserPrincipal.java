package br.com.photoapp.oauth.authserver.security;

import br.com.photoapp.eureka.commonservice.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private static String ROLE_PREFIX = "ROLE_";

    private Long id;

    private String username;

    @JsonIgnore
    private String passsword;

    private Collection<? extends GrantedAuthority> authorities;


    public static UserPrincipal create(User user) {

        List<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority(ROLE_PREFIX+"USER")
        );

        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
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

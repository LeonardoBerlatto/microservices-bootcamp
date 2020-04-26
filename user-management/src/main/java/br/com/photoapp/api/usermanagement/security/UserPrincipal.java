package br.com.photoapp.api.usermanagement.security;

import br.com.photoapp.api.usermanagement.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor()
public class UserPrincipal implements UserDetails {

	private User user;

	private Collection<? extends GrantedAuthority> authorities;

	public static UserPrincipal create(User user) {

		return new UserPrincipal(user, Collections.emptyList());
	}

	@Override
	@JsonIgnore
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
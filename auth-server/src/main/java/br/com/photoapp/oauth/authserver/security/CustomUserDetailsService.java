package br.com.photoapp.oauth.authserver.security;

import br.com.photoapp.eureka.commonservice.domain.User;
import br.com.photoapp.oauth.authserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = getUser(() -> repository.findByEmailOrUsername(username));
        return UserPrincipal.create(user);
    }

    private User getUser(Supplier<Optional<User>> supplier) {
        return supplier.get().orElseThrow(() ->
                new UsernameNotFoundException("Usuário não cadastrado")
        );
    }
}

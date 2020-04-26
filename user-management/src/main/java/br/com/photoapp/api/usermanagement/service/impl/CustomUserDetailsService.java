package br.com.photoapp.api.usermanagement.service.impl;

import br.com.photoapp.api.usermanagement.domain.User;
import br.com.photoapp.api.usermanagement.repository.UserRepository;
import br.com.photoapp.api.usermanagement.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = getUser(() -> userRepository.findByEmail(username));
        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = getUser(() -> userRepository.findById(id));
        return UserPrincipal.create(user);
    }


    private User getUser(Supplier<Optional<User>> supplier) {
        return supplier.get().orElseThrow(() ->
                new UsernameNotFoundException("Usuário não cadastrado")
        );
    }
}

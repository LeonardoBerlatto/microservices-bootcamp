package br.com.photoapp.api.usermanagement.service.impl;

import br.com.photoapp.api.usermanagement.exception.UserNotFoundException;
import br.com.photoapp.api.usermanagement.exception.UserWithThisEmailExistsException;
import br.com.photoapp.api.usermanagement.exception.UserWithThisUsernameExistsException;
import br.com.photoapp.api.usermanagement.mapper.UserMapper;
import br.com.photoapp.api.usermanagement.repository.UserRepository;
import br.com.photoapp.api.usermanagement.service.UserService;
import br.com.photoapp.api.usermanagement.web.representation.request.CreateUserRequest;
import br.com.photoapp.eureka.commonservice.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static br.com.photoapp.api.usermanagement.utils.JdbiUtils.validateInsert;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(final CreateUserRequest userRequest) {
        if (this.existsByEmail(userRequest.getEmail())) {
            throw new UserWithThisEmailExistsException();
        }

        if (this.existsByUsername(userRequest.getUsername())) {
            throw new UserWithThisUsernameExistsException();
        }

        final User user = UserMapper.fromRequestToDomain(userRequest);

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        final Long id = userRepository.createUser(user);

        validateInsert(id);

        user.setId(id);
        return user;
    }

    @Override
    public User getUserById(final Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    private boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean existsByUsername(final String username) {
        return userRepository.existsByUsername(username);
    }

}

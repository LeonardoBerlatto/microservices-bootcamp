package br.com.photoapp.api.usermanagement.service.impl;

import br.com.photoapp.api.usermanagement.domain.User;
import br.com.photoapp.api.usermanagement.exception.UserNotFoundException;
import br.com.photoapp.api.usermanagement.mapper.UserMapper;
import br.com.photoapp.api.usermanagement.repository.UserRepository;
import br.com.photoapp.api.usermanagement.service.UserService;
import br.com.photoapp.api.usermanagement.web.representation.request.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static br.com.photoapp.api.usermanagement.utils.JdbiUtils.validateInsert;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(final CreateUserRequest userRequest) {
        final User user = UserMapper.fromRequestToDomain(userRequest);

        user.setPassword(getEncryptedPassword(userRequest.getPassword()));

        final Long id = userRepository.createUser(user);

        validateInsert(id);

        user.setId(id);
        return user;
    }

    @Override
    public User getUserById(final Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public String getEncryptedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}

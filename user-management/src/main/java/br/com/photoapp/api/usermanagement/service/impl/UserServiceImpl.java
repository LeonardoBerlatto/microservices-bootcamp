package br.com.photoapp.api.usermanagement.service.impl;

import br.com.photoapp.api.usermanagement.mapper.UserMapper;
import br.com.photoapp.api.usermanagement.repository.UserRepository;
import br.com.photoapp.api.usermanagement.service.UserService;
import br.com.photoapp.api.usermanagement.web.domain.User;
import br.com.photoapp.api.usermanagement.web.representation.user.request.CreateUserRequest;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(final CreateUserRequest userRequest) {

        final User user = UserMapper.fromRequestToDomain(userRequest);

        final Long id = userRepository.createUser(user);

        if (id <= 0 ) {
            throw new RuntimeException("Something when wrong, review the data you are trying to submit.");
        }

        user.setId(id);

        return user;
    }

    @Override
    public User getUserById(final Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("aaaaa"));
    }
}

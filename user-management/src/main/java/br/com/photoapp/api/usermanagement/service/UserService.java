package br.com.photoapp.api.usermanagement.service;

import br.com.photoapp.api.usermanagement.domain.User;
import br.com.photoapp.api.usermanagement.web.representation.user.request.CreateUserRequest;

public interface UserService {

    User create(CreateUserRequest userRequest);

    User getUserById(Long id);

}

package br.com.photoapp.api.usermanagement.service;

import br.com.photoapp.api.usermanagement.web.representation.request.CreateUserRequest;
import br.com.photoapp.eureka.commonservice.domain.User;

public interface UserService {

    User createUser(CreateUserRequest userRequest);

    User getUserById(Long id);

}

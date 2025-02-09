package br.com.photoapp.api.usermanagement.mapper;

import br.com.photoapp.api.usermanagement.web.representation.request.CreateUserRequest;
import br.com.photoapp.api.usermanagement.web.representation.response.UserResponse;
import br.com.photoapp.eureka.photoappcommons.domain.User;

public class UserMapper {

    public static UserResponse fromDomainToResponse(final User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .build();
    }

    public static User fromRequestToDomain(final CreateUserRequest userDetails) {
        return User.builder()
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .password(userDetails.getPassword())
                .birthDate(userDetails.getBirthDate())
                .build();
    }
}

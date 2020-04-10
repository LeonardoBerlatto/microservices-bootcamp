package br.com.photoapp.api.usermanagement.mapper;

import br.com.photoapp.api.usermanagement.domain.User;
import br.com.photoapp.api.usermanagement.web.representation.user.request.CreateUserRequest;
import br.com.photoapp.api.usermanagement.web.representation.user.response.UserResponse;

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

package br.com.photoapp.api.usermanagement.service.impl;

import br.com.photoapp.api.usermanagement.exception.UserNotFoundException;
import br.com.photoapp.api.usermanagement.exception.UserWithThisEmailExistsException;
import br.com.photoapp.api.usermanagement.exception.UserWithThisUsernameExistsException;
import br.com.photoapp.api.usermanagement.repository.UserRepository;
import br.com.photoapp.api.usermanagement.web.representation.request.CreateUserRequest;
import br.com.photoapp.eureka.commonservice.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    UserRepository repository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl tested;


    @Test
    public void shouldCreateUser() {
        // arrange
        final Long userId = Long.valueOf("1");

        final CreateUserRequest userRequest = CreateUserRequest.builder()
                .firstName("First")
                .lastName("Last")
                .username("last1234")
                .email("nice@email.com")
                .password("12345678")
                .birthDate(LocalDate.now())
                .build();

        when(repository.existsByEmail(anyString()))
                .thenReturn(false);

        when(repository.existsByUsername(anyString()))
                .thenReturn(false);

        when(repository.createUser(Mockito.any(User.class)))
                .thenReturn(userId);

        // act
        var result = tested.createUser(userRequest);

        // assert
        verify(repository)
                .createUser(Mockito.any(User.class));
        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeException_whenNoRowsInserted() {
        // arrange
        final Long noRowsInserted = Long.valueOf("0");

        final CreateUserRequest userRequest = CreateUserRequest.builder()
                .firstName("First")
                .lastName("Last")
                .username("last1234")
                .email("nice@email.com")
                .password("12345678")
                .birthDate(LocalDate.now())
                .build();

        when(repository.createUser(Mockito.any(User.class)))
                .thenReturn(noRowsInserted);

        // act
        tested.createUser(userRequest);
    }

    @Test(expected = UserWithThisEmailExistsException.class)
    public void shouldThrowUserWithThisEmailExistsException_whenEmailAlreadyExistsInTheDB() {
        // arrange

        final String email = "nice@email.com";

        final CreateUserRequest userRequest = CreateUserRequest.builder()
                .email(email)
                .build();

        when(repository.existsByEmail(email))
                .thenReturn(true);

        // act
        tested.createUser(userRequest);
    }

    @Test(expected = UserWithThisUsernameExistsException.class)
    public void shouldThrowUserWithThisUsernameExistsException_whenUsernameAlreadyExistsInTheDB() {
        // arrange

        final String email = "nice@email.com";
        final String username = "xxxx_my-username_xxxx";

        final CreateUserRequest userRequest = CreateUserRequest.builder()
                .email(email)
                .username(username)
                .build();

        when(repository.existsByEmail(email))
                .thenReturn(false);

        when(repository.existsByUsername(username))
                .thenReturn(true);

        // act
        tested.createUser(userRequest);
    }

    @Test
    public void shouldGetUserById() {
        // arrange
        final Long userId = Long.valueOf("1");

        final User user = User.builder()
                .id(userId)
                .build();

        when(repository.findById(userId))
                .thenReturn(Optional.of(user));

        // act
        var result = tested.getUserById(userId);

        // assert
        verify(repository).findById(userId);
        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowUserNotFoundException_whenUserDoesNotExists() {
        // arrange
        when(repository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        // act
        var result = tested.getUserById(Long.valueOf("1"));
    }
}
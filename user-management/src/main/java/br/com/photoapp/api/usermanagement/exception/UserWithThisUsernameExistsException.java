package br.com.photoapp.api.usermanagement.exception;

public class UserWithThisUsernameExistsException extends BadRequestException {

    public UserWithThisUsernameExistsException() {
        super("A user with this username already exits.");
    }
}

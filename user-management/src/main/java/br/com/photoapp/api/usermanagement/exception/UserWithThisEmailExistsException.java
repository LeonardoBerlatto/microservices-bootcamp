package br.com.photoapp.api.usermanagement.exception;

public class UserWithThisEmailExistsException extends BadRequestException {

    public UserWithThisEmailExistsException() {
        super("A user with this email already exits.");
    }
}

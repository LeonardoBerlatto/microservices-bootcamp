package br.com.photoapp.api.usermanagement.exception;

public class UserNotFoundException extends NotFoundExeception{

	public UserNotFoundException() {
		super("User");
	}
}

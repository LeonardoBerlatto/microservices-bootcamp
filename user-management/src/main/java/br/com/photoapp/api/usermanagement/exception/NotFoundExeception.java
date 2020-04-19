package br.com.photoapp.api.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExeception extends RuntimeException{

	public NotFoundExeception(String resource) {
		super(String.format("%s not found.", resource));
	}
}

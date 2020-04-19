package br.com.photoapp.api.usermanagement.exception.model;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

	private Date timestamp;
	private int status;
	private String message;
	private String uriPath;

}

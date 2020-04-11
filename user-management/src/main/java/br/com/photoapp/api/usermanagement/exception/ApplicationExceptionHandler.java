package br.com.photoapp.api.usermanagement.exception;

import br.com.photoapp.api.usermanagement.exception.model.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorMessage> handleUnexpectedException(Exception exception, WebRequest request) {
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        final ErrorMessage errorMessage = buildMessage(exception, request, httpStatus.value());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), httpStatus);
    }

    @ExceptionHandler(value = NotFoundExeception.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundExeception exception, WebRequest request) {
        final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        final ErrorMessage errorMessage = buildMessage(exception, request, httpStatus.value());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), httpStatus);
    }

    private ErrorMessage buildMessage(final Exception exception, final WebRequest request, int statusCode) {
        final String context = ((ServletWebRequest) request).getRequest().getServletPath();

        return ErrorMessage.builder()
                .timestamp(new Date())
                .message(exception.getMessage())
                .status(statusCode)
                .uriPath(context)
                .build();
    }
}

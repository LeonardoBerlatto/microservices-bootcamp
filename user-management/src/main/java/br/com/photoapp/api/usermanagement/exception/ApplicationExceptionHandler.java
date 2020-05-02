package br.com.photoapp.api.usermanagement.exception;

import br.com.photoapp.api.usermanagement.exception.model.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Date;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorMessage> handleUnexpectedException(Exception exception, WebRequest request) {
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        final ErrorMessage errorMessage = buildMessage(exception.getMessage(), request, httpStatus.value());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), httpStatus);
    }

    @ExceptionHandler(value = NotFoundExeception.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundExeception exception, WebRequest request) {
        final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        final ErrorMessage errorMessage = buildMessage(exception.getMessage(), request, httpStatus.value());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), httpStatus);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolationException(MethodArgumentNotValidException exception, WebRequest request) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        String exceptionMessage = exception.getMessage();

        if (!exception.getBindingResult().getAllErrors().isEmpty()) {
            exceptionMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }

        final ErrorMessage errorMessage = buildMessage(exceptionMessage, request, httpStatus.value());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), httpStatus);
    }

    private ErrorMessage buildMessage(final String message, final WebRequest request, int statusCode) {
        final String context = ((ServletWebRequest) request).getRequest().getServletPath();

        return ErrorMessage.builder()
                .timestamp(new Date())
                .message(message)
                .status(statusCode)
                .uriPath(context)
                .build();
    }
}

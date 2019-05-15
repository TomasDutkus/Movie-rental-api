package com.tomasdutkus.movierental.controller.response;

import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MovieRentalResponseBodyAdvice extends ResponseEntityExceptionHandler
        implements ResponseBodyAdvice<Object> {

    private static final HashMap<String, HttpStatus> exceptionStatuses = new HashMap<>();

    static {
        exceptionStatuses.put("com.tomasdutkus.movierental.exception.FilmAlreadyExistException", HttpStatus.CONFLICT);
        exceptionStatuses.put("com.tomasdutkus.movierental.exception.FilmNotFoundException", HttpStatus.NOT_FOUND);
        exceptionStatuses.put("com.tomasdutkus.movierental.exception.RentalNotFoundException", HttpStatus.NOT_FOUND);
        exceptionStatuses.put("com.tomasdutkus.movierental.exception.UserAlreadyExistException", HttpStatus.CONFLICT);
        exceptionStatuses.put("com.tomasdutkus.movierental.exception.UserNotFoundException", HttpStatus.NOT_FOUND);
        exceptionStatuses.put("com.tomasdutkus.movierental.exception.UserException", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request) {
        String exceptionName = ex.getClass().getName();
        HttpStatus status = exceptionStatuses.get(exceptionName);

        if (status == null) {
            status = HttpStatus.BAD_REQUEST;
        }

        MovieRentalResponseEnvelope envelope =
                new MovieRentalResponseEnvelope(false).addError(new ApiErrorBody(status, ex.getMessage()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(envelope, status);
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        return body instanceof MovieRentalResponseEnvelope ? body : new MovieRentalResponseEnvelope(true).addData(body);
    }

}

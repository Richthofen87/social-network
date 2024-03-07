package ru.skillbox.diplom.group46.social.network.impl.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.skillbox.diplom.group46.social.network.api.exception.auth.AuthenticationError;
import ru.skillbox.diplom.group46.social.network.domain.exception.captcha.CaptchaException;

@Slf4j
@ResponseBody
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({EntityNotFoundException.class, EntityExistsException.class})
    public ErrorResponse handleException(Throwable ex) {
        log.warn("Method handleException(%s) started with param: \"%s\", cause: \"%s\""
                .formatted(Throwable.class, ex, ex.getCause()));
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({CaptchaException.class, AuthenticationCredentialsNotFoundException.class, AuthenticationError.class})
    public ErrorResponse handleAuthException(Throwable ex) {
        log.warn("Method handleAuthException(%s) started with param: \"%s\", cause: \"%s\""
                .formatted(Throwable.class, ex, ex.getCause()));
        if (ex instanceof AuthenticationError) {
            AuthenticationError authError = (AuthenticationError) ex;
            log.warn("AuthenticationError: status={}, message={}", authError.getStatus(), authError.getMessage());
        }
        return ErrorResponse.create(ex, HttpStatus.UNAUTHORIZED, ex.getMessage());
    }
}
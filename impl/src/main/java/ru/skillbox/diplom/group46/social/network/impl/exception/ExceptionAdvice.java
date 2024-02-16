package ru.skillbox.diplom.group46.social.network.impl.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.skillbox.diplom.group46.social.network.domain.exception.captcha.CaptchaException;

@Slf4j
@ResponseBody
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({EntityNotFoundException.class, EntityExistsException.class, UsernameNotFoundException.class, })
    public ErrorResponse handleException(Throwable ex) {
        log.warn("Method handleException(%s) started with param: \"%s\", cause: \"%s\""
                .formatted(Throwable.class, ex, ex.getCause()));
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({CaptchaException.class, AuthenticationCredentialsNotFoundException.class})
    public ErrorResponse handleException(CaptchaException ex) {
        log.warn("Method handleException(%s) started with param: \"%s\", cause: \"%s\""
                .formatted(CaptchaException.class, ex, ex.getMessage()));
        return ErrorResponse.create(ex, HttpStatus.UNAUTHORIZED, ex.getMessage());
    }
}
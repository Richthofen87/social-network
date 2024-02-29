package ru.skillbox.diplom.group46.social.network.api.exception.auth;

import lombok.Data;
import java.util.Date;
@Data
public class AuthenticationError extends RuntimeException{
    private int status;
    private String message;
    private Date timestamp;

    public AuthenticationError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}

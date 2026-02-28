package com.nivishay.nmp.users.service.exception;

public class InvalidCredentialsException extends Throwable {
    public InvalidCredentialsException(String invalidCredentials) {
        super(invalidCredentials);
    }
}

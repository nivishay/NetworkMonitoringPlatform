package com.nivishay.nmp.users.service.exception;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException(String userNotFound) {
        super(userNotFound);
    }
}

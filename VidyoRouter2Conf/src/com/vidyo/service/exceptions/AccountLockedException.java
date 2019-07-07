package com.vidyo.service.exceptions;

public class AccountLockedException extends Exception {

    public AccountLockedException(String msg) {
        super(msg);
    }
}

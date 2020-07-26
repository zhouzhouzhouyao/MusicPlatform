package com.exception;

/**
 * @author Liu
 * Created on 2020/7/22.
 */
public class UserInfoNotValidException extends TransactionException {
    
    public UserInfoNotValidException (String message) {
        super(message);
    }
    
    public UserInfoNotValidException (Exception e) {
        super(e.getMessage());
    }
    
}

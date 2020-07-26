package com.exception;

/**
 * @author Liu
 * Created on 2020/7/22.
 */
public class UserPasswordNotValidException extends TransactionException {
    
    public UserPasswordNotValidException (String message) {
        super(message);
    }
    
    public UserPasswordNotValidException (Exception e) {
        super(e.getMessage());
    }
    
}

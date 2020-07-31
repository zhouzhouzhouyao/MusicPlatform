package com.exception;

/**
 * @author Liu
 * Created on 2020/7/22.
 */
public class UserPhoneNotValidException extends TransactionException {
    
    public UserPhoneNotValidException (String message) {
        super(message);
    }
    
    public UserPhoneNotValidException (Exception e) {
        super(e.getMessage());
    }
    
}

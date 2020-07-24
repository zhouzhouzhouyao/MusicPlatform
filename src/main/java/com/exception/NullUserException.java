package com.exception;

/**
 * @author Liu
 * Created on 2020/7/22.
 */
public class NullUserException extends TransactionException {
    
    public NullUserException (String message) {
        super(message);
    }
    
    public NullUserException (Exception e) {
        super(e.getMessage());
    }
    
}

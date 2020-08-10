package com.exception;

/**
 * @author Liu
 * Created on 2020/7/25.
 */
public class UnknownActionException extends TransactionException {
    
    public UnknownActionException (String message) {
        super(message);
    }
    
    public UnknownActionException (Exception e) {
        super(e.getMessage());
    }
    
}

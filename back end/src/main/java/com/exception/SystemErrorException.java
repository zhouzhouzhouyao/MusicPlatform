package com.exception;

/**
 * @author Liu
 * Created on 2020/7/26.
 */
public class SystemErrorException extends TransactionException {
    
    public SystemErrorException (String message) {
        super(message);
    }
    
    public SystemErrorException (Exception e) {
        super(e.getMessage());
    }
    
}

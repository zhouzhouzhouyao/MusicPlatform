package com.exception;

/**
 * @author Liu
 * Created on 2020/7/22.
 */
public class UidErrorException extends TransactionException {
    
    public UidErrorException (String message) {
        super(message);
    }
    
    public UidErrorException (Exception e) {
        super(e.getMessage());
    }
    
}

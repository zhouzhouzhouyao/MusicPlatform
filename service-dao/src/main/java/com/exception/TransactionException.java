package com.exception;

/**
 * @author Liu
 * Created on 2020/7/22.
 */
public class TransactionException extends RuntimeException {
    
    public TransactionException (String message) {
        super(message);
    }
    
}

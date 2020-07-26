package com.exception;

/**
 * @author Liu
 * Created on 2020/7/26.
 */
public class NewsErrorException extends TransactionException {
    
    public NewsErrorException (String message) {
        super(message);
    }
    
    public NewsErrorException (Exception e) {
        super(e.getMessage());
    }
    
}

package com.exception;

/**
 * @author Liu
 * Created on 2020/7/22.
 */
public class AccountNotValidException extends TransactionException {
    
    public AccountNotValidException (String message) {
        super(message);
    }
    
    public AccountNotValidException (Exception e) {
        super(e.getMessage());
    }
    
}

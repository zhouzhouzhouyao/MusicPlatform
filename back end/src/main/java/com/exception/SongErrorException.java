package com.exception;

/**
 * @author Liu
 * Created on 2020/7/25.
 */
public class SongErrorException extends TransactionException {
    
    public SongErrorException (String message) {
        super(message);
    }
    
    public SongErrorException (Exception e) {
        super(e.getMessage());
    }
    
}

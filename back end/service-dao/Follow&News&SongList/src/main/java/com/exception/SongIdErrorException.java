package com.exception;

/**
 * @author Liu
 * Created on 2020/7/25.
 */
public class SongIdErrorException extends TransactionException {
    
    public SongIdErrorException (String message) {
        super(message);
    }
    
    public SongIdErrorException (Exception e) {
        super(e.getMessage());
    }
    
}

package com.exception;

/**
 * @author Liu
 * Created on 2020/7/25.
 */
public class SongListErrorException extends TransactionException {
    
    public SongListErrorException (String message) {
        super(message);
    }
    
    public SongListErrorException (Exception e) {
        super(e.getMessage());
    }
    
}

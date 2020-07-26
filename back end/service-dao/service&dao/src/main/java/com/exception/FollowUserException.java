package com.exception;

/**
 * @author Liu
 * Created on 2020/7/25.
 */
public class FollowUserException extends TransactionException {
    
    public FollowUserException (String message) {
        super(message);
    }
    
    public FollowUserException (Exception e) {
        super(e.getMessage());
    }
    
}

package com.example.socialnewsspring.exception;

public class SubredditNotFoundException extends RuntimeException {
    public SubredditNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}

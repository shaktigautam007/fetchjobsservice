package com.myjobs.jobservice.exception;

public class JobServiceException extends RuntimeException {
    public JobServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
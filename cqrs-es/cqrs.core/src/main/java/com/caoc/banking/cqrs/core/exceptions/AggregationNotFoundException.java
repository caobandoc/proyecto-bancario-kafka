package com.caoc.banking.cqrs.core.exceptions;

public class AggregationNotFoundException extends RuntimeException{
    public AggregationNotFoundException(String message) {
        super(message);
    }
}

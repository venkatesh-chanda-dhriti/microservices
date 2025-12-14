package com.eazybytes.accounts.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resource, String resourceType, String field)  {
        super(String.format("%s  not found with given  %s : %s", resource, resourceType, field));
    }
}

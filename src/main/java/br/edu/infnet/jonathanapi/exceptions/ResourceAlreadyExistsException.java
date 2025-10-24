package br.edu.infnet.jonathanapi.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s com %s '%s' já existe", resourceName, fieldName, fieldValue));
    }
    
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
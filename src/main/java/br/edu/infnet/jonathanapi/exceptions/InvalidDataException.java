package br.edu.infnet.jonathanapi.exceptions;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String fieldName, String reason) {
        super(String.format("Campo '%s' inválido: %s", fieldName, reason));
    }
    
    public InvalidDataException(String message) {
        super(message);
    }
}
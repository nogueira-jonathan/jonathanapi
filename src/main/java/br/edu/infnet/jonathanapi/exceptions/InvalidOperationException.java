package br.edu.infnet.jonathanapi.exceptions;

public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String message) {
        super(message);
    }
    
    public InvalidOperationException(String operation, String reason) {
        super(String.format("Operação '%s' inválida: %s", operation, reason));
    }
}
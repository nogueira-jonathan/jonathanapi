package br.edu.infnet.jonathanapi.exceptions;

public class InvalidStateException extends RuntimeException {
    public InvalidStateException(String entityName, String currentState, String expectedState) {
        super(String.format("%s está em estado inválido. Estado atual: %s, Esperado: %s", 
            entityName, currentState, expectedState));
    }
    
    public InvalidStateException(String message) {
        super(message);
    }
}
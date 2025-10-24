package br.edu.infnet.jonathanapi.exceptions;

public class InsufficientResourceException extends RuntimeException {
    public InsufficientResourceException(String resourceName, int required, int available) {
        super(String.format("%s insuficiente. Necessário: %d, Disponível: %d", 
            resourceName, required, available));
    }
    
    public InsufficientResourceException(String message) {
        super(message);
    }
}
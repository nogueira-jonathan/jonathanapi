package br.edu.infnet.jonathanapi.exceptions;

public class ItemAlreadyEquippedException extends RuntimeException {
    public ItemAlreadyEquippedException(String itemType, String itemName) {
        super(String.format("%s '%s' já está equipado(a)", itemType, itemName));
    }
    
    public ItemAlreadyEquippedException(String message) {
        super(message);
    }
}
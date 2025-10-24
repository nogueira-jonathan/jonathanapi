package br.edu.infnet.jonathanapi.exceptions;

public class ItemNotEquippedException extends RuntimeException {
    public ItemNotEquippedException(String itemType) {
        super(String.format("Nenhum(a) %s equipado(a)", itemType));
    }
}
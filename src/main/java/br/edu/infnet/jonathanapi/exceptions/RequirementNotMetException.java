package br.edu.infnet.jonathanapi.exceptions;

public class RequirementNotMetException extends RuntimeException {
    public RequirementNotMetException(String requirement, Object expected, Object actual) {
        super(String.format("Requisito não atendido. %s: esperado %s, atual %s", 
            requirement, expected, actual));
    }
    
    public RequirementNotMetException(String message) {
        super(message);
    }
}
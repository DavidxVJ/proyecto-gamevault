//tener ResourceNotFoundException como una clase distinta de DuplicateResourceException nos va a permitir decirle a
// Spring: "cuando veas específicamente un ResourceNotFoundException, responde con 404; cuando veas un
// DuplicateResourceException, responde con 409". Si todo fuera RuntimeException genérica, no tendríamos forma de
//  diferenciar un caso del otro para dar la respuesta HTTP correcta.
package com.gamevault.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
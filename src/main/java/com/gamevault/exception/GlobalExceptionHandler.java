//En lugar de que cada controller tenga su propio try/catch, Spring nos permite definir un solo lugar que intercepta
//las excepciones de toda la aplicación
package com.gamevault.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

//CA: le dice a Spring "esta clase intercepta cosas relacionadas con controllers en toda la aplicación, no solo uno"
@ControllerAdvice
public class GlobalExceptionHandler {

    //le dice a Spring "cuando cualquier controller de la aplicación lance esta excepción específica (o se le escape
    //sin capturar), ejecuta este método en su lugar"
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage()); //404 Not Found
    }

    @ExceptionHandler(DuplicateResourceException.class)
    //ResponseEntity te da control total sobre la respuesta HTTP — no solo el cuerpo, también el código de estado.
    public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateResourceException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage()); //409 Conflict
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }
}
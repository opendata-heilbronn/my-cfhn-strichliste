package de.codeforheilbronn.mycfhn.strichliste.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Resource already exists")
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String resource) {
        super("Resource already exists " + resource);
    }
}

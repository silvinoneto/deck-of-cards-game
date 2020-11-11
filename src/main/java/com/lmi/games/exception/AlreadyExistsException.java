package com.lmi.games.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception class is used when a resource already exists.
 * 
 * @author silvinoneto
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}

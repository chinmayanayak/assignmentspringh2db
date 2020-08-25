package com.marsassignment.exception;

public class PersonNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersonNotFoundException(Long id) {
        super("Person id not found : " + id);
    }

}

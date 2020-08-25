package com.marsassignment.exception;

public class AddressNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public AddressNotFoundException(Long id) {
        super("Address id not found : " + id);
    }


}

package com.example.demo;

import org.springframework.http.HttpStatus;


/***
 * Root exception for domain bussines , infracstructure and other componentes in the api.
 * @author jnoh
 *
 */

public class GenericException   extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final String code;
	
	private final String message;
	
	private final HttpStatus httpStatus;
	
	/***
	 * Method to define new exception more especific
	 * @param exceptionCode
	 * @param httpStatus
	 */
	public GenericException(ExceptionCode exceptionCode, HttpStatus httpStatus) {
		super();
		this.code = exceptionCode.getCode();
		this.message = exceptionCode.getMessage();
		this.httpStatus = httpStatus;
	}
	
	

	/***
	 * get http status to display
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	/**
	 * Get source code from exception 
	 * @return
	 */
	
	public String getCode() {
		return this.code;
	}
	
	/**
	 * Get message to show 
	 * @return
	 */
	@Override
	public String getMessage() {
		return this.message;
	}
	
	

}

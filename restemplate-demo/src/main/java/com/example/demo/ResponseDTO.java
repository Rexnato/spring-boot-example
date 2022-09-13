package com.example.demo;

import org.springframework.http.HttpStatus;



/***
 * Class used for responses in services iwith  information from the operation executed. Additonal this dto can return a generic content dto
 * This dto  is able to use with any service from any layer
 * @author jnoh
 *
 * @param <T>
 */
public class ResponseDTO<T> {
	
	private T content;
	
	private boolean isValid;
	
	private ExceptionCode exceptionCode = null;
	
	private HttpStatus httpStatus;
	
	/**
	 * Constructor to generate step by step response
	 */
	public ResponseDTO() {
		super();
	}
	
	/**
	 * Constructor to generate response succesful
	 * @param content
	 * @param httpStatus
	 */
	
	public ResponseDTO(T content) {
		super();
		this.content = content;
		this.isValid = true;
	}
	
	/**
	 * Constructor to generate response succesful
	 * @param content
	 * @param httpStatus
	 */
	
	public ResponseDTO(T content, HttpStatus httpStatus) {
		super();
		
		this.httpStatus = httpStatus;
		this.isValid = true;
		this.content = content;
		
		this.exceptionCode = null;
		
	}
	/**
	 * Constructor to generate response failure
	 * @param exceptionCode
	 * @param httpStatus
	 * content default value is null
	 */
	
	public ResponseDTO(ExceptionCode exceptionCode, HttpStatus httpStatus) {
		this.exceptionCode = exceptionCode;
		this.httpStatus = httpStatus;
		this.isValid = false;
		this.content = null;
	}
	
	
	/**
	 * In case valid true return valid value
	 * if failure throw exception to exception handler
	 * @return
	 */
	public T getContentIfValid() {
		
		if(this.isError()) {
			this.throwIfNotValid();
		}
		
		return content;
	}
	
	/**
	 * Thrown an exception if not valid , this exception will catch by exception handler
	 * 
	 * 
	 * @param code = this value reference code api client service
	 */
	public void throwIfNotValid() {
		
		if(this.isError()) 
		{
			throw new GenericException(exceptionCode == null ? ExceptionCode.COD_SE001 : exceptionCode, httpStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR  : httpStatus );
		}
	}
	
	
	/***
	 * true if the operation is failure
	 * @return
	 */
	public boolean isError() {
		return !this.isValid();
	}
	
	
	
	
	

	/**
	 * In case succesful object response from the method is here
	 * is  valid false the value is null
	 * @return
	 */
	public T getContent() {
		return content;
	}
	
	
	
	public void setContent(T content) {
		this.content = content;
	}
	
	/**
	 * if is succesful or fail the operation 
	 * @return
	 */
	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	
	
	/**
	 * In case service http , the http status is here (optional)
	 * @return
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public ExceptionCode getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(ExceptionCode exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	
	
	
}






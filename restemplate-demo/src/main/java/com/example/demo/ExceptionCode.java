package com.example.demo;

public enum ExceptionCode {
		
	//Client error
	
	COD_BR001("BR001", "One or many fields request are incorrects."),
	
	//client error manangements by spring boot 
	COD_BR002("BR002", "The request method is not supported."),
	COD_BR003("BR003", "Content type  not supported."),
	COD_BR004("BR004", "Media  type not supported."),
	COD_BR005("BR005", "The field %s is invalid."),
	
	//401 unauthorizad message for example in security filters
	COD_UAT001("UAT001", "Unauthorized."),
	COD_UAT002("UAT002", "%s"),
	
	
	//500 code
	COD_SE001("SE001", "Occurred error, please try again."),
	
	//Code app
	COD_OAUTH001("COD_OAUTH001", "%s"),
	COD_OAUTH002("COD_OAUTH002", "Occurred error while invoking Auth Sevice."),
	
	
	;
	
	private String code;
    
	private String message;
	
    private ExceptionCode(String code, String description) {
        this.code = code;
        this.message = description;
    }
    
    

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    
    /**
     * replace %s in dinamic message from exception code
     */
    public ExceptionCode formatMessage(Object... args ) {
    	
    	this.message = String.format(this.message, args);
    	
    	return this;
    }

}

package com.example.demo;

import java.util.List;

public interface IGodzillaClient {
	
	String API_GODZILLA ="api.godzilla";
	
	//gets
	
	ResponseDTO<Godzilla> getGodzilla(String nombre);
	
	ResponseDTO<List<Godzilla>> getFamiliGodzilla();
	
	ResponseDTO<Godzilla> postGodzilla(Godzilla godzilla);
	
	ResponseDTO<Void> putGodzilla(Godzilla godzilla);
	
	
	

}

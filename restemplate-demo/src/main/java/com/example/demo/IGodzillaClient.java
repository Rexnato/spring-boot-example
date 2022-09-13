package com.example.demo;

import java.util.List;

public interface IGodzillaClient {
	
	//gets
	
	ResponseDTO<Godzilla> getGodzilla(String nombre);
	
	ResponseDTO<List<Godzilla>> getFamiliGodzilla();
	
	Godzilla postGodzilla(Godzilla godzilla);
	
	Godzilla putGodzilla(Godzilla godzilla);
	
	
	

}

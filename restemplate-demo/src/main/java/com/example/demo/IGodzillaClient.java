package com.example.demo;

import java.util.List;

public interface IGodzillaClient {
	
	//gets
	
	Godzilla getGodzilla(String nombre);
	
	List<Godzilla> getFamiliGodzilla();
	
	void postGodzilla(Godzilla godzilla);
	
	Godzilla putGodzilla(Godzilla godzilla);
	
	
	

}

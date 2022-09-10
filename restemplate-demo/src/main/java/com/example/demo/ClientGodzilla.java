package com.example.demo;

import java.util.List;

public interface ClientGodzilla {
	
	//gets
	
	Godzilla getGodzilla(String nombre);
	
	List<Godzilla> getFamiliGodzilla();
	
	void postGodzilla(Godzilla godzilla);
	
	Godzilla putGodzilla(Godzilla godzilla);
	
	
	

}

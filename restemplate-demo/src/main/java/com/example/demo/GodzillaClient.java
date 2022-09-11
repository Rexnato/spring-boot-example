package com.example.demo;

import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;


@Service
public class GodzillaClient  extends ClientGenericBase implements IGodzillaClient{

	
	
	
	@Override
	public Godzilla getGodzilla(String nombre) {
		
		
		return this.execute("http://localhost:23703/api/godzilla", HttpMethod.GET, Godzilla.class).getBody();
	}

	@Override
	public List<Godzilla> getFamiliGodzilla() {
	
		return null;
	}

	@Override
	public void postGodzilla(Godzilla godzilla) {
	
		
	}

	@Override
	public Godzilla putGodzilla(Godzilla godzilla) {
	
		return null;
	}

}

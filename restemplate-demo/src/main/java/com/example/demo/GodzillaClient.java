package com.example.demo;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class GodzillaClient  extends ClientGenericBase implements IGodzillaClient{

	public GodzillaClient() {
		
		super();
		this.globalHeaders.setContentType(MediaType.APPLICATION_JSON);
		this.globalHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	}

	@Override
	public Godzilla getGodzilla(String nombre) {
		
		return this.execute(String.format("api.godzilla?nombre=%s", nombre), HttpMethod.GET, Godzilla.class).getBody();
	}

	@Override
	public List<Godzilla> getFamiliGodzilla() {
		Godzilla[] array  = this.execute("api.godzilla", HttpMethod.GET, Godzilla[].class).getBody();
		return Arrays.asList(array);
	}

	@Override
	public Godzilla postGodzilla(Godzilla godzilla) {
		
		ResponseEntity<Godzilla> s =  this.execute("api.godzilla", HttpMethod.POST,godzilla, Godzilla.class);
		
		return s.getBody();
	}

	@Override
	public Godzilla putGodzilla(Godzilla godzilla) {
	
		
		return null;
	}

}

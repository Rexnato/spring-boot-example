package com.example.demo;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;


@Service
public class GodzillaClient  extends ClientGenericBase implements IGodzillaClient{

	public GodzillaClient() {
		super();
		this.globalHeaders.setContentType(MediaType.APPLICATION_JSON);
		this.globalHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	}

	@Override
	public ResponseDTO<Godzilla> getGodzilla(String nombre) {
		return this.execute(String.format(IGodzillaClient.API_GODZILLA+"?nombre=%s", nombre), HttpMethod.GET, new ParameterizedTypeReference<Godzilla>() {});
	}

	@Override
	public ResponseDTO<List<Godzilla>> getFamiliGodzilla() {
		
		return this.execute(IGodzillaClient.API_GODZILLA, HttpMethod.GET, new ParameterizedTypeReference<List<Godzilla>>() {});
	}

	@Override
	public ResponseDTO<Godzilla> postGodzilla(Godzilla godzilla) {
		
		return this.execute(IGodzillaClient.API_GODZILLA, HttpMethod.POST,godzilla, new ParameterizedTypeReference<Godzilla>() {});
	}

	@Override
	public ResponseDTO<Void> putGodzilla(Godzilla godzilla) {
		return this.execute(IGodzillaClient.API_GODZILLA, HttpMethod.PUT,godzilla, new ParameterizedTypeReference<Void>() {});
	}
	
	
	
	

}

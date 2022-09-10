package com.example.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/***
 * Client generic 
 * all clases than use must be a @service (herencia)
 * 
 * @author jnoh
 *
 */
public class ClientGenericBase {
	
	@Autowired
	RestTemplate restTemplate;
	
	/***
	 * Method to consume http method ,withoud body in send petion
	 * @param <T>
	 * @param url
	 * @return 
	 * @return 
	 */
	public  <T> ResponseEntity<T> execute(String url,HttpMethod httpMetod,Class<T> responseTypeClass) {
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		
		HttpEntity<String> httpEntity = new HttpEntity<>(header);
		
		return this.restTemplate.exchange(url, httpMetod, httpEntity, responseTypeClass);
		
		
		
		
	}

}

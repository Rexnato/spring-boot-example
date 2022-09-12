package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
	private Environment enviroment;
	
	@Autowired
	RestTemplate restTemplate;
	
	protected HttpHeaders globalHeaders = new HttpHeaders();
	
	
	


	/***
	 * Method to consume http method ,withoud body in send petion
	 * @param <T>
	 * @param url
	 * @return 
	 * @return 
	 */
	public  <T> ResponseEntity<T> execute(String urlProperties,HttpMethod httpMetod,Class<T> responseTypeClass) {
		
		String url = enviroment.getProperty(urlProperties);
		
		HttpEntity<String> httpEntity = new HttpEntity<>(globalHeaders);
		
		return this.restTemplate.exchange(url, httpMetod, httpEntity, responseTypeClass);
	}


	


}

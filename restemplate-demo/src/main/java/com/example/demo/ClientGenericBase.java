package com.example.demo;



import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	@Autowired
	ObjectMapper mapper;
	
	protected HttpHeaders globalHeaders = new HttpHeaders();
	
	protected boolean useApplicationToken = false;
	
	

	/***
	 * Method to consume http method ,withoud body in send petion
	 * @param <T>
	 * @param url
	 * @return 
	 * @return 
	 */
	public  <T> ResponseEntity<T> execute(String urlProperties,HttpMethod httpMetod,Class<T> responseTypeClass) {
		
		String url =  this.resolveUrlProperties(urlProperties);
		
		HttpEntity<String> httpEntity = new HttpEntity<>(globalHeaders);
		
		
		return this.restTemplate.exchange(url, httpMetod, httpEntity, responseTypeClass);
	}

	/***
	 * Method to consume http method ,with body in send petion.the body will converth to json object
	 * @param <T>
	 * @param <K>
	 * @param url
	 * @return 
	 * @return 
	 */
	public  <T, K> ResponseEntity<T> execute(String urlProperties,HttpMethod httpMetod,K body,Class<T> responseTypeClass) {
		
		String url =  this.resolveUrlProperties(urlProperties);
		
		String jsonBody;
		
		try {
			
			jsonBody = this.mapper.writeValueAsString(body);
			
		} catch (JsonProcessingException e) {
			jsonBody = " "; //this must be an exception catc by handler
		}
		
		HttpEntity<String> httpEntity = new HttpEntity<>(jsonBody, this.globalHeaders);
		
		return this.restTemplate.exchange(url, httpMetod, httpEntity, responseTypeClass);
	}
	
	/***
	 * Resolve url from properties
	 * @return
	 */
	protected String resolveUrlProperties(String urlProperties) {
		
		String url =  String.format("%s", enviroment.getProperty(StringUtils.substringBefore(urlProperties, "?")));
		
		String queryParams = StringUtils.substringAfter(urlProperties, "?");
		if(StringUtils.isNotBlank(queryParams.trim())) {
			url = String.format("%s?%s", url,queryParams);
		}
		
		return url;
	}


}

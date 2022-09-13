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


	/***
	 * Method to consume http method ,withoud body in send petion
	 * @param <T>
	 * @param <T>
	 * @param url
	 * @return 
	 * @return 
	 */
	public  <T> ResponseDTO<T> execute(String urlProperties,HttpMethod httpMetod,Class<T> responseTypeClass) {
		
		String url =  this.resolveUrlProperties(urlProperties);
		
		HttpEntity<String> httpEntity = new HttpEntity<>(addHeaders());
		
		ResponseEntity<T> responseEntity = null;
		
		try {
			responseEntity = this.restTemplate.exchange(url, httpMetod, httpEntity, responseTypeClass);
			
		} catch (Exception e) {
			 return getResponseErrorClient().doResolve(e);
		}
		
		return new ResponseDTO<>(responseEntity.getBody(),responseEntity.getStatusCode());
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
		
		HttpEntity<String> httpEntity = new HttpEntity<>(jsonBody, addHeaders());
		
		return this.restTemplate.exchange(url, httpMetod, httpEntity, responseTypeClass);
	}
	
	/**
	 * Add headers to httpentity request
	 */
	private HttpHeaders addHeaders() {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		
		globalHeaders.forEach(httpHeaders::put);
		
		return httpHeaders;
	}
	
	/***
	 * method that Resolve url from properties
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
	
	/***
	 * if this method is not override resolve the error from exception with values from IResponse error client generic
	 * @return
	 */
	protected IResponseErrorClient getResponseErrorClient() {
		
		return new IResponseErrorClient() {
			
			@Override
			public <T> ResponseDTO<T> doResolve(Exception exception) {
				
				return null;
			}
		};
	}
	
	
	
	/**
	 * Method that resolve authorizathiontoken 
	 * this method will be overwrite 
	 * 
	 * the param arg must be the access token or acces key to create the header autorizathion 
	 * for example 
	 * arg = Bearer 1234 ------> Authorization Bearer 1234
	 * arg = Basic 1234 -------->Authorization Basic 1234
	 * 
	 * 
	 */
	protected String resolveAuthorizathionToken(String arg) {
		
		return String.format("Authorization: %s", arg);
	}
	
	
	/**
	 * Interface to custumise error response
	 * @author jnoh
	 *
	 */
	public interface IResponseErrorClient {
		
		/**
		 * Metodo to resolve response from exception
		 * @param <T>
		 * @param exception
		 * @return
		 */
		<T> ResponseDTO<T> doResolve(Exception exception);
	}


}

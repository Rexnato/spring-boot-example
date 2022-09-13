package com.example.demo;



import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
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
	
	private IResponseErrorClient responseErrorClient;


	public ClientGenericBase() {
		super();
		this.responseErrorClient = this.defaultResponseErrorClient();
	}

	/***
	 * Method to consume http method ,withoud body in send petion
	 * @param <T>
	 * @param <T>
	 * @param url
	 * @return 
	 * @return 
	 */
	public  <T> ResponseDTO<T> execute(String urlProperties,HttpMethod httpMetod,ParameterizedTypeReference<T> responseType) {
		
		String url =  this.resolveUrlProperties(urlProperties);
		
		HttpEntity<String> httpEntity = new HttpEntity<>(addHeaders());
		
		ResponseEntity<T> responseEntity = null;
		
		try {
			responseEntity = this.restTemplate.exchange(url, httpMetod, httpEntity, responseType);
			
		} catch (HttpStatusCodeException e) {
			 return this.getResponseErrorClient().resolveResponseError(e.getStatusCode(), e.getResponseBodyAsString());
		}catch (Exception e) {
			return new ResponseDTO<>(ExceptionCode.COD_SE001, HttpStatus.INTERNAL_SERVER_ERROR);
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
	public  <T, K> ResponseDTO<T> execute(String urlProperties,HttpMethod httpMetod,K body,ParameterizedTypeReference<T> responseType) {
		
		String url =  this.resolveUrlProperties(urlProperties);
		
		String jsonBody;
		
		try {
			
			jsonBody = this.mapper.writeValueAsString(body);
			
		} catch (JsonProcessingException e) {
			jsonBody = " "; //this must be an exception catc by handler because is runtime and is error from programmer
		}
		
		HttpEntity<String> httpEntity = new HttpEntity<>(jsonBody, addHeaders());
		
		ResponseEntity<T> responseEntity = null;
		
		try {
			responseEntity = this.restTemplate.exchange(url, httpMetod, httpEntity, responseType);
			
		} catch (HttpStatusCodeException e) {
			 return this.getResponseErrorClient().resolveResponseError(e.getStatusCode(), e.getResponseBodyAsString());
		}catch (Exception e) {
			return new ResponseDTO<>(ExceptionCode.COD_SE001, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseDTO<>(responseEntity.getBody(),responseEntity.getStatusCode());
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
	private String resolveUrlProperties(String urlProperties) {
		
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
	private IResponseErrorClient defaultResponseErrorClient() {
		
		return new IResponseErrorClient() {
			
			@Override
			public <T> ResponseDTO<T> resolveResponseError(HttpStatus httpStatus, String body) {
				
				return new ResponseDTO<>(ExceptionCode.COD_CG001.formatMessage(httpStatus.getReasonPhrase()), httpStatus);
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
	
	
	public IResponseErrorClient getResponseErrorClient() {
		return responseErrorClient;
	}

	public void setResponseErrorClient(IResponseErrorClient responseErrorClient) {
		this.responseErrorClient = responseErrorClient;
	}


	/**
	 * Interface to custumise error response
	 * @author jnoh
	 *
	 */
	public interface IResponseErrorClient {
		
		/**
		 * Metodo to resolve response from exception from restemplate
		 * @param <T>
		 * @param exception
		 * @return
		 */
		<T> ResponseDTO<T> resolveResponseError(HttpStatus error, @Nullable String body);
	}


}

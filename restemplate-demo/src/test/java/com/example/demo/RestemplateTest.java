package com.example.demo;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@RestClientTest
@AutoConfigureWebClient(registerRestTemplate = true)
public class RestemplateTest {
	
	@Autowired
	private MockRestServiceServer mockServer;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Test
	public void testUno() {
		
		String url = "http://localhost:23703/bachesito/";
		
		this.mockServer.expect(requestTo(url))
		.andExpect(method(HttpMethod.GET))
		.andRespond(withSuccess());
		
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> request = new HttpEntity<>(header);
		
		ResponseEntity<Void> s = this.restTemplate.exchange(url,HttpMethod.GET,request, Void.class);
		
		
		Assertions.assertTrue(true);
	}

}

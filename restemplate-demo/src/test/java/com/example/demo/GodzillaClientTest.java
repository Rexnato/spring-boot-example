package com.example.demo;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestClientTest(value = {IGodzillaClient.class,GodzillaClient.class})
@AutoConfigureWebClient(registerRestTemplate = true)
class GodzillaClientTest {
	
	@Autowired
	IGodzillaClient client;
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	private MockRestServiceServer mockServer;
	
	@Test
	void testUno() throws JsonProcessingException {
		
		Godzilla godzillaMock = new Godzilla();
		godzillaMock.setEdad(4000);
		godzillaMock.setNombre("Godzilla");
		
		String url = "http://localhost:23703/api/godzilla";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		this.mockServer.expect(requestTo(url))
		.andExpect(method(HttpMethod.GET))
		.andRespond(withSuccess()
				.body(mapper.writeValueAsString(godzillaMock))
				.headers(headers));
		
		
		Godzilla godzilla = this.client.getGodzilla("papoGodzilla");
		
		Assertions.assertTrue(true);
	}
	

}

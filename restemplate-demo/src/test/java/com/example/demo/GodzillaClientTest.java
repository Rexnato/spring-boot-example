package com.example.demo;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
	private Environment enviroment;
	
	@Autowired
	private MockRestServiceServer mockServer;
	
	@Test
	void godzillaByName() throws JsonProcessingException {
		
		Godzilla godzillaMock = getGodzillaMock();
		
		String url = enviroment.getProperty("api.godzilla?nombre="+godzillaMock.getNombre());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		this.mockServer.expect(requestTo(url))
		.andExpect(method(HttpMethod.GET))
		.andRespond(withSuccess()
				.body(mapper.writeValueAsString(godzillaMock))
				.headers(headers));
		
		
		Godzilla response = this.client.getGodzilla(godzillaMock.getNombre());
		
		Assertions.assertEquals(godzillaMock.getId(), response.getId());
		Assertions.assertEquals(godzillaMock.getEdad(), response.getEdad());
		Assertions.assertEquals(godzillaMock.getNombre(), response.getNombre());
	}
	
	@Test
	void godzillaList() throws JsonProcessingException {
		
		List<Godzilla> familyPapo = getGodzillasMock(3);
		
		String url = enviroment.getProperty("api.godzilla");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		this.mockServer.expect(requestTo(url))
		.andExpect(method(HttpMethod.GET))
		.andRespond(withSuccess()
				.body(mapper.writeValueAsString(familyPapo))
				.headers(headers));
		
		
		List<Godzilla> response = this.client.getFamiliGodzilla();
		
		Assertions.assertEquals(familyPapo.size(),response.size());
	}
	
	/**
	 * Generate godzilla mock
	 * @return
	 */
	private Godzilla getGodzillaMock() {
		
		return new Godzilla(ThreadLocalRandom.current().nextInt(1, 1000000), ThreadLocalRandom.current().nextInt(1, 1000000), UUID.randomUUID().toString());
	}
	
	/**
	 * Generate list godzilla mock
	 * @return
	 */
	private List<Godzilla> getGodzillasMock(int cantidad) {
		
		List<Godzilla> list = new ArrayList<>();
		
		for (int i = 0; i < cantidad; i++) {
			list.add(getGodzillaMock());
		}
		
		return list;
	}
	

}

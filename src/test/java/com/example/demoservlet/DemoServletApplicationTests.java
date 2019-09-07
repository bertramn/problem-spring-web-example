package com.example.demoservlet;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DemoServletApplicationTests {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void itShouldGetMary() throws JSONException {
    ResponseEntity<String> response = restTemplate.getForEntity("/persons/Mary", String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    JSONAssert.assertEquals("{\"name\":\"Mary\"}", response.getBody(), true);
  }

	@Test
	public void itShouldGetNotGetFred() throws JSONException {
		ResponseEntity<String> response = restTemplate.getForEntity("/persons/Fred", String.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		JSONAssert.assertEquals("{\"title\":\"Not Found\",\"status\":404,\"detail\":\"Fred not found\"}", response.getBody(), true);
	}

  @Test
  public void itShouldDenyAccessToPost() throws JSONException {
    ResponseEntity<String> response = restTemplate.postForEntity("/persons", null, String.class);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    JSONAssert.assertEquals("{\"title\":\"Unauthorized\",\"status\":401,\"detail\":\"Full authentication is required to access this resource.\"}", response.getBody(), true);
  }

}

package com.carros.api.services.carros;

import com.carros.CarrosApplication;
import com.carros.api.infra.security.jwt.JwtUtil;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static junit.framework.TestCase.assertNotNull;
import static org.springframework.http.HttpMethod.*;

@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseAPIIntegrationTest {

  @Autowired
  protected TestRestTemplate rest;

  @Autowired
  @Qualifier("userDetailsService")
  protected UserDetailsService userDetailsService;

  private String jwtToken = "";

  public HttpHeaders getHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
    return headers;
  }

  @Before
  public void setupTest() {
    // Le usuário
    UserDetails user = userDetailsService.loadUserByUsername("admin");
    assertNotNull(user);

    // Gera token
    jwtToken = JwtUtil.createToken(user);
    System.out.println(jwtToken);
    assertNotNull(jwtToken);
  }

  public <T> ResponseEntity<T> post(String url, Object body, Class<T> responseType) {
    HttpHeaders headers = getHeaders();
    return rest.exchange(url, POST, new HttpEntity<>(body, headers), responseType);
  }

  public <T> ResponseEntity<T> get(String url, Class<T> responseType) {
    HttpHeaders headers = getHeaders();
    return rest.exchange(url, GET, new HttpEntity<>(headers), responseType);
  }

  public <T> ResponseEntity<T> delete(String url, Class<T> responseType) {
    HttpHeaders headers = getHeaders();
    return rest.exchange(url, DELETE, new HttpEntity<>(headers), responseType);
  }
}
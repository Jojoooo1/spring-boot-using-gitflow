package com.carros.api.services.carros.integration;

import com.carros.CarrosApplication;
import com.carros.api.services.carros.BaseAPIIntegrationTest;
import com.carros.api.services.carros.Carro;
import com.carros.api.services.carros.CarroDTO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarrosControllerIntegrationTest extends BaseAPIIntegrationTest {

  // private ResponseEntity<CarroDTO> getCarro(String url) {
  // return get(url, CarroDTO.class);
  // }

  private ResponseEntity<List<CarroDTO>> getAll(String url) {
    HttpHeaders headers = getHeaders();

    return rest.exchange(url, HttpMethod.GET, new HttpEntity<>(headers),
        new ParameterizedTypeReference<List<CarroDTO>>() {
        });
  }

  @Test
  public void shouldAddAndDeleteCarro() {
    Carro data = new Carro("nome", "tipo");
    // Call controller
    ResponseEntity<String> response = post("/api/v1/carros", data, null);
    // Verify
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    String location = response.getHeaders().get("location").get(0);
    CarroDTO expected = get(location, CarroDTO.class).getBody();

    data.setId(expected.getId());
    assertEquals(expected, CarroDTO.create(data));

    // Delete & verify
    delete(location, null);
    assertEquals(HttpStatus.NOT_FOUND, get(location, CarroDTO.class).getStatusCode());
  }

  @Test
  public void shouldGetAll() {
    List<CarroDTO> carros = getAll("/api/v1/carros").getBody();
    assertEquals(10, carros.size()); // 10 default size

    carros = getAll("/api/v1/carros?page=0&size=30").getBody();
    assertEquals(30, carros.size());
  }

  @Test
  public void shouldGetAllByTipo() {

    assertEquals(10, getAll("/api/v1/carros/tipo/classicos").getBody().size());
    assertEquals(10, getAll("/api/v1/carros/tipo/esportivos").getBody().size());
    assertEquals(10, getAll("/api/v1/carros/tipo/luxo").getBody().size());

    assertEquals(HttpStatus.NO_CONTENT, getAll("/api/v1/carros/tipo/xxx").getStatusCode());
  }

  @Test
  public void ShouldGetById() {
    ResponseEntity<CarroDTO> response = get("/api/v1/carros/11", CarroDTO.class);
    assertEquals(response.getStatusCode(), HttpStatus.OK);

    CarroDTO c = response.getBody();
    assertNotNull(c);
    assertEquals("Ferrari FF", c.getNome());
  }

  @Test
  public void shouldReturnNotFoundIfDoesNotExist() {
    ResponseEntity<CarroDTO> response = get("/api/v1/carros/1100", CarroDTO.class);
    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
  }
}
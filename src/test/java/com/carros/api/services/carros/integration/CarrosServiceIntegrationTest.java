package com.carros.api.services.carros.integration;

import com.carros.CarrosApplication;
import com.carros.api.infra.exception.ObjectNotFoundException;
import com.carros.api.services.carros.Carro;
import com.carros.api.services.carros.CarroDTO;
import com.carros.api.services.carros.CarroService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarrosServiceIntegrationTest {

  @Autowired
  private CarroService service;

  @Test
  public void shouldGetCarroById() {
    CarroDTO c = service.getCarroById(11L);
    assertNotNull(c);
    assertEquals("Ferrari FF", c.getNome());
  }

  @Test
  public void ShouldGetAll() {
    List<CarroDTO> carros = service.getAll(PageRequest.of(0, 30));
    assertEquals(30, carros.size()); // 30 created by sql file
  }

  @Test
  public void ShouldAddAndDeleteCarro() {
    Carro data = new Carro("nome", "tipo");
    // Call service
    CarroDTO expected = service.add(data);
    Long id = expected.getId();
    data.setId(id);
    // Verify
    expected = service.getCarroById(id);
    assertEquals(expected, CarroDTO.create(data));

    // Delete
    service.delete(id);
    // Verify
    try {
      service.getCarroById(id);
      fail("O carro não foi excluído");
    } catch (ObjectNotFoundException e) {
      // OK
    }
  }

  @Test
  public void ShouldGetPorTipo() {
    assertEquals(10, service.getAllByTipo("classicos", PageRequest.of(0, 10)).size());
    assertEquals(10, service.getAllByTipo("esportivos", PageRequest.of(0, 10)).size());
    assertEquals(10, service.getAllByTipo("luxo", PageRequest.of(0, 10)).size());
    assertEquals(0, service.getAllByTipo("x", PageRequest.of(0, 10)).size());
  }
  
}

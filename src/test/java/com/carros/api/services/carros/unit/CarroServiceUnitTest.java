package com.carros.api.services.carros.unit;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.carros.api.services.carros.Carro;
import com.carros.api.services.carros.CarroDTO;
import com.carros.api.services.carros.CarroRepository;
import com.carros.api.services.carros.CarroService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

// Dont need to declare MockitoAnnotations.initMocks(this)
@ExtendWith(MockitoExtension.class)
class CarroServiceUnitTest {

  // Creates mock
  @Mock
  private CarroRepository carroRepository;

  // Creates mock and injects the dependent @Mock into it
  @InjectMocks
  private CarroService carroService;

  @Test
  void shouldGetAll() {
    // <Carro> because findAll type is Page of Carro
    List<Carro> datas = Arrays.asList(new Carro("nome", "tipo"), new Carro("nome", "tipo"));
    // Mock service dependency
    Page<Carro> carroPage = new PageImpl<Carro>(datas, PageRequest.of(0, 10), datas.size());
    when(carroRepository.findAll(PageRequest.of(0, 10, Sort.unsorted()))).thenReturn(carroPage);
    // Call service
    List<CarroDTO> expected = carroService.getAll(PageRequest.of(0, 10));
    // Verify
    assertEquals(expected, datas.stream().map(CarroDTO::create).collect(Collectors.toList()));
  }

  @Test
  void shouldGetCarroById() {
    Carro data = new Carro("nome", "tipo");
    // Mock service dependency
    when(carroRepository.findById(1L)).thenReturn(Optional.of(data));
    // Call service
    CarroDTO expected = carroService.getCarroById(1L);
    // Verify
    assertEquals(expected, CarroDTO.create(data));
  }

  @Test
  void shouldSearchCarro() {
    List<Carro> datas = Arrays.asList(new Carro("nome", "tipo"), new Carro("nome", "tipo"));
    // Mock service dependency
    when(carroRepository.findByNomeContaining("query")).thenReturn(datas);
    // Call service
    List<CarroDTO> expected = carroService.search("query");
    // Verify
    assertEquals(expected, datas.stream().map(CarroDTO::create).collect(Collectors.toList()));
  }

  @Test
  void shouldAddCarro() {
    Carro data = new Carro("nome", "tipo");
    // Mock service dependency
    when(carroRepository.save(data)).thenReturn(data);
    // Call service
    CarroDTO expected = carroService.add(data);
    // Verify
    assertEquals(expected, CarroDTO.create(data));
  }

  @Test
  void shouldThrowErrorWhenAddUserWithAnId() {
    Carro data = new Carro("nome", "tipo");
    data.setId(1L);
    // Call service & assert errors
    assertThrows(IllegalArgumentException.class, () -> {
      carroService.add(data);
    });
    // Verify
    verify(carroRepository, never()).save(any(Carro.class));
  }

  @Test
  void shouldUpdateCarro() {
    Carro data = new Carro("nome", "tipo");
    data.setId(1L);
    // Mock service dependency
    when(carroRepository.findById(1L)).thenReturn(Optional.of(data));
    when(carroRepository.save(data)).thenReturn(data);
    // Call service
    CarroDTO expected = carroService.update(data);
    // Verify
    assertEquals(expected, CarroDTO.create(data));
    verify(carroRepository).save(any(Carro.class));
  }

  @Test
  void shouldNotUpdateCarroThatDoesNotHaveAnId() {
    Carro data = new Carro("nome", "tipo");
    // Call service & assert errors
    assertThrows(IllegalArgumentException.class, () -> {
      carroService.update(data);
    });
    // Verify
    verify(carroRepository, never()).save(any(Carro.class));
  }

  @Test
  void shouldNotUpdateCarroThatDoesNotExist() {
    Carro data = new Carro("nome", "tipo");
    data.setId(1L);
    // Mock repo response
    when(carroRepository.findById(1L)).thenReturn(Optional.empty());
    // Call service
    CarroDTO updatedCarroDTO = carroService.update(data);
    // Verify
    assertEquals(updatedCarroDTO, null);
  }

  @Test
  void shouldDeleteCarro() {
    // Call service
    carroService.delete(1L);
    // Verify
    verify(carroRepository, times(1)).deleteById(eq(1L));
  }

}
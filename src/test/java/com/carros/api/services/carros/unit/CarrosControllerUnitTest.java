package com.carros.api.services.carros.unit;

import java.util.Arrays;
import java.util.List;

import com.carros.api.infra.exception.ObjectNotFoundException;
import com.carros.api.services.carros.CarroDTO;
import com.carros.api.services.carros.CarroRepository;
import com.carros.api.services.carros.CarroService;
import com.carros.api.services.carros.CarrosController;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.data.domain.PageRequest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

// Dont need to declare MockitoAnnotations.initMocks(this)
@ExtendWith(MockitoExtension.class)
public class CarrosControllerUnitTest {

  private MockMvc mockMvc;

  // Creates mock
  @Mock
  private CarroRepository carroRepository;

  @Mock
  private CarroService carroService;

  // Creates mock and injects the dependent @Mock into it
  @InjectMocks
  private CarrosController carroController;

  private List<CarroDTO> carroList;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(carroController).build();
    this.carroList = Arrays.asList(new CarroDTO(1L, "ferrari", "tipo_2"), new CarroDTO(1L, "test", "John Snow"));
    // .addFilters(webConfigurer.corsFilter())
    // .addFilters(new CORSFilter())
  }

  // Get All Carros
  @Test
  public void shouldGetAll() throws Exception {
    when(carroService.getAll(PageRequest.of(0, 10))).thenReturn(this.carroList);

    mockMvc.perform(get("/api/v1/carros")).andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(carroList.size())).andReturn();
    // .andExpect(content().contentType("application/json"))
    // .andExpect(jsonPath("$[0].nome").value("ferrari"))
    // .andExpect(jsonPath("$[0].tipo").value("tipo_2")).andReturn();

    verify(carroService, times(1)).getAll(PageRequest.of(0, 10));
    verifyNoMoreInteractions(carroService);
  }

  @Test
  public void shouldGetById() throws Exception {
    CarroDTO carro = new CarroDTO(1L, "ferrari", "tipo_2");
    when(carroService.getCarroById(1L)).thenReturn(carro);

    mockMvc.perform(get("/api/v1/carros/{id}", 1)).andExpect(status().isOk())
        .andExpect(content().contentType("application/json")).andExpect(jsonPath("$.nome").value("ferrari"))
        .andExpect(jsonPath("$.tipo").value("tipo_2")).andReturn();

    verify(carroService, times(1)).getCarroById(1L);
    verifyNoMoreInteractions(carroService);
  }

  @Test
  void shouldReturn404WhenFindUserById() throws Exception {
    when(carroService.getCarroById(1L)).thenThrow(new ObjectNotFoundException("Carro não encontrado"));
    mockMvc.perform(get("/api/v1/carros/{id}", 1L)).andExpect(status().isNotFound()).andReturn();
    verify(carroService, times(1)).getCarroById(1L);
    verifyNoMoreInteractions(carroService);
  }

}
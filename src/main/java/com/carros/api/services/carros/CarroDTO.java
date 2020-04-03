package com.carros.api.services.carros;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarroDTO {
  private Long id;
  private String nome;
  private String tipo;

  /*
   * public CarroDTO(Carro carro) { this.id = carro.getId(); this.nome =
   * carro.getNome(); this.tipo = carro.getTipo(); }
   */

  public static CarroDTO create(Carro carro) {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(carro, CarroDTO.class);
  }
}

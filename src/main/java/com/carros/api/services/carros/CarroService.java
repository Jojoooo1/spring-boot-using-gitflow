package com.carros.api.services.carros;

import com.carros.api.infra.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

  @Autowired
  private CarroRepository rep;

  public List<CarroDTO> getAll(Pageable pageable) {
    return rep.findAll(pageable).stream().map(CarroDTO::create).collect(Collectors.toList());
  }

  public CarroDTO getCarroById(Long id) {
    return rep.findById(id).map(CarroDTO::create)
        .orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
  }

  public List<CarroDTO> search(String query) {
    return rep.findByNomeContaining(query).stream().map(CarroDTO::create).collect(Collectors.toList());
  }

  public List<CarroDTO> getAllByTipo(String tipo, Pageable pageable) {
    return rep.findByTipo(tipo, pageable).stream().map(CarroDTO::create).collect(Collectors.toList());
  }

  public CarroDTO add(Carro carro) {
    Assert.isNull(carro.getId(), "Não foi possível inserir o registro");
    return CarroDTO.create(rep.save(carro));
  }

  public CarroDTO update(Carro carro) {
    Assert.notNull(carro.getId(), "Não foi possível atualizar o registro");

    // Busca o carro no banco de dados
    Optional<Carro> optional = rep.findById(carro.getId());
    if (optional.isPresent()) {
      Carro db = optional.get();
      // Copiar as propriedades
      db.setNome(carro.getNome());
      db.setTipo(carro.getTipo());

      // Atualiza o carro
      rep.save(db);

      return CarroDTO.create(db);
    } else {
      return null;
      // throw new RuntimeException("Não foi possível atualizar o registro");
    }
  }

  public void delete(Long id) {
    rep.deleteById(id);
  }
}

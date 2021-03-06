package com.carros.api.services.carros;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CarroRepository{
  List<Carro> findByTipo(String tipo, Pageable pageable);
  List<Carro> findAllByTipo(String tipo, Pageable pageable);
  List<Carro> findByNomeContaining(String query);
}

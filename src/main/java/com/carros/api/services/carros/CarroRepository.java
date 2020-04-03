package com.carros.api.services.carros;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarroRepository extends JpaRepository<Carro, Long> {
  List<Carro> findByTipo(String tipo, Pageable pageable);
  List<Carro> findAllByTipo(String tipo, Pageable pageable);
  List<Carro> findByNomeContaining(String query);
}

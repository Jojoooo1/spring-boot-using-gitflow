package com.carros.api.services.carros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

// IMPORTANT => Errors are treated in exception catcher
@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {

  @Autowired
  private CarroService service;

  @GetMapping()
  public ResponseEntity<List<CarroDTO>> getAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size) {
    List<CarroDTO> carros = service.getAll(PageRequest.of(page, size));
    return ResponseEntity.ok(carros);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CarroDTO> getById(@PathVariable("id") Long id) {
    CarroDTO carro = service.getCarroById(id);
    return ResponseEntity.ok(carro);
  }

  @GetMapping("/search")
  public ResponseEntity<Object> search(@RequestParam("query") String query) {
    List<CarroDTO> carros = service.search(query);
    return carros.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(carros);
  }

  @GetMapping("/tipo/{tipo}")
  public ResponseEntity<Object> getAllByTipo(@PathVariable("tipo") String tipo,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size) {
    List<CarroDTO> carros = service.getAllByTipo(tipo, PageRequest.of(page, size));
    // noContent => 204 no content
    return carros.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(carros);
  }

  @PostMapping
  // @Secured({ "ROLE_ADMIN" })
  public ResponseEntity<Object> post(@RequestBody Carro carro) {
    // You can merge with DTO object
    CarroDTO c = service.add(carro);
    // import static net.logstash.logback.argument.StructuredArguments.kv;
    // Logs structured args
    // log.info("Order saved", kv("orderId", orderId), kv("status", status));
    URI location = getUri(c.getId());
    return ResponseEntity.created(location).build();
  }

  private URI getUri(Long id) {
    return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> put(@PathVariable("id") Long id, @RequestBody Carro carro) {
    carro.setId(id);
    CarroDTO carroUpdated = service.update(carro);
    return carroUpdated != null ? ResponseEntity.ok(carroUpdated) : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
    service.delete(id);
    // by default spring boot will throw a EmptyDataAccessException (status 500)
    // **** THREATED in Exception config ****
    return ResponseEntity.ok().build();
  }
}

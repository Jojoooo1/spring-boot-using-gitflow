package com.carros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

// import com.carros.utils.PropertyLogger;


@Slf4j
@SpringBootApplication
public class CarrosApplication {
  public static void main(String[] args) {
    for (String arg : args) {
      log.info(arg); // Reading Command-Line arguments
    }
    SpringApplication.run(CarrosApplication.class, args);
  }

}

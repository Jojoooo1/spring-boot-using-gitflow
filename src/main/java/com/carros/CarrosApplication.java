package com.carros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import com.carros.utils.PropertyLogger;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

@SpringBootApplication
public class CarrosApplication {

  // private static final Logger LOGGER = LoggerFactory.getLogger(PropertyLogger.class);

  public static void main(String[] args) {
    // for (String arg : args) {
    //   LOGGER.info(arg); // Reading Command-Line arguments
    // }
    SpringApplication.run(CarrosApplication.class, args);
  }

}

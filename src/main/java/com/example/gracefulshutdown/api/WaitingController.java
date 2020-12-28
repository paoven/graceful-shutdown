package com.example.gracefulshutdown.api;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WaitingController {


  @Value("${com.example.http.waiting.ms}")
  Integer defaultWaitMs;

  @PostMapping("/wait")
  ResponseEntity<Integer> wait(@RequestBody Integer body,
      @RequestParam(required = false) Integer waitMs) {
    final Integer computedWaitMs = Optional.ofNullable(waitMs).orElse(defaultWaitMs);
    log.info("HTTP: Handling request payload: {}", body);
    try {
      Thread.sleep(computedWaitMs);
    } catch (InterruptedException ie) {
      log.warn("HTTP: Cannot wait here: {}", body);
    }
    log.info("HTTP: Handled request payload: {}", body);

    return ResponseEntity.ok(body);
  }

}
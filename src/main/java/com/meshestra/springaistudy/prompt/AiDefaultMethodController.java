package com.meshestra.springaistudy.prompt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/ai-default-method")
public class AiDefaultMethodController {
  private AiDefaultMethodService service;

  @PostMapping(
    value = "/default-method",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    produces =MediaType.APPLICATION_NDJSON_VALUE
  )
  public Flux<String> defaultMethod(@RequestParam("question") String question) {
    return service.defaultMethod(question);
  }
}

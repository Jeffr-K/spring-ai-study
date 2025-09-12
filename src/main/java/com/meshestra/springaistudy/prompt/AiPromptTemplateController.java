package com.meshestra.springaistudy.prompt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

@Controller
@RequestMapping("/ai")
@Slf4j
public class AiPromptTemplateController {

  private AiServicePromptTemplate service;

  @PostMapping(
    value = "/prompt-template",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Flux<String> promptTemplate(
    @RequestParam("statement") String statement,
    @RequestParam("language") String language
  ) {
    return service.promptTemplate(statement, language);
  }

}

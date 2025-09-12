package com.meshestra.springaistudy.prompt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
public class AiStepBackPromptController {
  private AiStepBackPromptService service;

  @PostMapping(
    value = "/step-back",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    produces = MediaType.APPLICATION_NDJSON_VALUE
  )
  public String stepBackPrompt(@RequestParam("question") String question) throws Exception {
    String answer = service.stepBackPrompt(question);
    return answer;
  }

}

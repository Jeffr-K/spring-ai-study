package com.meshestra.springaistudy.prompt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai-zero-shot-prompt")
@Slf4j
public class AiZeroShotPromptController {
  private AiZeroShotPromptService service;

  @PostMapping(
    value = "/zero-shot",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    produces = MediaType.APPLICATION_NDJSON_VALUE)
  public String zeroShotPrompt(String review) {
    return service.zeroShotPrompt(review);
  }
}

package com.meshestra.springaistudy.prompt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
@Slf4j
public class AiRoleAssignmentController {
  private AiRoleAssignmentService service;

  @PostMapping(
    value = "/role-assignment",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    produces = MediaType.APPLICATION_NDJSON_VALUE
  )
  public Flux<String> roleAssignment(
    @RequestParam("requirements") String requirements
  ) {
    Flux<String> travelSuggestions = service.roleAssignmentPrompt(requirements);
    return travelSuggestions;
  }
}

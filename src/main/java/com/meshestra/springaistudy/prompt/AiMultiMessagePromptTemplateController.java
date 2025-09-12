package com.meshestra.springaistudy.prompt;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ai-multi-message-prompt-template")
@Slf4j
public class AiMultiMessagePromptTemplateController {
  private AiMultiMessagePromptTemplateService service;

  @PostMapping(
    value = "/multi-message",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    produces = MediaType.TEXT_PLAIN_VALUE
  )
  public String multiMessage(
    @RequestParam String question,
    HttpSession session
  ) {
    List<Message> chatMemory = (List<Message>) session.getAttribute("chatMemory");
    if(chatMemory == null) {
      chatMemory = new ArrayList<Message>();
      session.setAttribute("chatMemory", chatMemory);
    }

    String answer = service.multiMessage(question, chatMemory);
    return answer;
  }
}

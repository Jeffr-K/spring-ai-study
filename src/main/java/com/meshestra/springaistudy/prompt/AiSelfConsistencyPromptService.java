package com.meshestra.springaistudy.prompt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class AiSelfConsistencyPromptService {
  private ChatClient chatClient;

  private PromptTemplate promptTemplate = PromptTemplate
    .builder()
    .template(
      """
      다음 내용을 [IMPORTANT, NOT_IMPORTANT] 둘 중 하나로 분류해 주세요.
      레이블만 반환하세요.
      내용: {context}
      """
    )
    .build();

  public AiSelfConsistencyPromptService(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
  }

  public String selfConsistencyPrompt(String content) {
    int importantCount = 0;
    int notImportantCount = 0;

    String userText = promptTemplate.render(Map.of("content", content));

    for (int i = 0; i < 5; i++) {
      String output = chatClient.prompt()
        .user(userText)
        .options(
          ChatOptions.builder()
            .temperature(1.0)
            .build()
        )
        .call()
        .content();

      log.info("{}: {}", i, output.toString());

      if(output.equals("IMPORTANT")) {
        importantCount++;
      } else if(output.equals("NOT_IMPORTANT")) {
        notImportantCount++;
      }
    }

    String finalClassification = importantCount > notImportantCount ? "중요함" : "중요하지 않음";

    return finalClassification;
  }
}

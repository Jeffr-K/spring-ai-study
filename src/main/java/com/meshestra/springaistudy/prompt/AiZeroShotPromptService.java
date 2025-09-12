package com.meshestra.springaistudy.prompt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class AiZeroShotPromptService {

  private ChatClient chatClient;
  private PromptTemplate promptTemplate = PromptTemplate.builder()
    .template("""
        영화 리뷰를 [긍정적, 중립적, 부정적] 중에서 하나로 분류하세요.
        레이블만 반환하세요.
        리뷰: {review}
      """)
    .build();



  public AiZeroShotPromptService(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder
      .defaultOptions(
        ChatOptions
          .builder()
          .temperature(0.0) // 3가지만 분류하면 되므로 다양성이 필요없음
          .maxTokens(4) // 3가지 레이블은 최대 4토큰을 넘을수 없음 (긍정적, 부정적, 중립적) <- 확정은 아니므로 정확한 응답을 유도하는것임.
          .build())
      .build();
  }

  public String zeroShotPrompt(String review) {
    String sentiment = chatClient.prompt()
      .user(promptTemplate.render(Map.of("review", review)))
      .call()
      .content();

    return sentiment;
  }
}

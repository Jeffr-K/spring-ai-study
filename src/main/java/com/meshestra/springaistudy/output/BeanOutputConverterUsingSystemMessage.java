package com.meshestra.springaistudy.output;

import com.meshestra.springaistudy.dto.ReviewClassification;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

@Service
public class BeanOutputConverterUsingSystemMessage {

  private ChatClient chatClient;

  public ReviewClassification classifyReview(String review) {
    ReviewClassification reviewClassification = chatClient.prompt()
      .system("""
        영화 리뷰를 [POSITIVE, NEGATIVE, NEUTRAL] 중에서 하나로 분류하고 유효한 JSON 객체로 반환하세요.
      """)
      .user("%s".formatted(review))
      .options(ChatOptions.builder().temperature(0.0).build())
      .call()
      .entity(ReviewClassification.class);

    return reviewClassification;
  }

}

package com.meshestra.springaistudy.output;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.ListOutputConverter;

import java.util.List;
import java.util.Map;

public class CustomListOutputConverter {

  private ChatClient chatClient;

  /**
   * 저수준 컨버터 사용 예시
   * @param city
   * @return
   */
  public List<String> listOutputConverterLowLevel(String city) {
    ListOutputConverter converter = new ListOutputConverter();

    PromptTemplate promptTemplate = PromptTemplate
      .builder()
      .template("{city}에서 유명한 호텔 목록 5개를 출력하세요. {format}")
      .build();

    Prompt prompt = promptTemplate.create(Map.of("city", city, "format", converter.getFormat()));

    String commaSperatedString = chatClient.prompt(prompt).call().content();

    List<String> hotelList = converter.convert(commaSperatedString);

    return hotelList;
  }

  /**
   * 고수준 컨버터 사용 예시
   * @param city
   * @return
   */
  public List<String> listOutputConverterHighLevel(String city) {
    List<String> hotelList = chatClient.prompt()
      .user("%s에서 유명한 호텔 목록 5개를 출력하세요.".formatted(city))
      .call()
      .entity(new ListOutputConverter());

    return hotelList;
  }
}

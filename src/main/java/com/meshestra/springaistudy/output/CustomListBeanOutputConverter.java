package com.meshestra.springaistudy.output;

import com.meshestra.springaistudy.dto.Hotel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomListBeanOutputConverter {

  private ChatClient chatClient;

  public List<Hotel> genericBeanOutputConverterLowLevel(String cities) {
    BeanOutputConverter<List<Hotel>> beanOutputConverter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<Hotel>>() {});

    PromptTemplate promptTemplate = new PromptTemplate("""
    다음 도시들에서 유명한 호텔 3개를 출력하세요.
    {cities}
    {format}
    """);

    Prompt prompt = promptTemplate.create(Map.of(
      "cities", List.of("서울", "뉴욕", "도쿄"),
      "format", beanOutputConverter.getFormat()
    ));

    String json = chatClient.prompt(prompt).call().content();

    List<Hotel> hotelList = beanOutputConverter.convert(json);

    return hotelList;
  }


  public List<Hotel> beanOutputConverterHighLevel(String cities) {
    List<Hotel> hotelList = chatClient.prompt()
      .user("""
        다음 도시들에서 유명한 호텔 3개를 출력하세요.
        %s
        """.formatted(cities))
      .call()
      .entity(new BeanOutputConverter<>(new ParameterizedTypeReference<List<Hotel>>() {}));

    return hotelList;
  }
}

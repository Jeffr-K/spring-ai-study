package com.meshestra.springaistudy.output;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomMapOutputConverter {

  private ChatClient chatClient;



  public Map<String, Object> mapOutputConverterLowLevel(String city) {
    MapOutputConverter mapOutputConverter = new MapOutputConverter();

    PromptTemplate promptTemplate = new PromptTemplate(
      "호텔 {hotel}에 대해 정보를 알려주세요 {format}"
    );

    Prompt prompt = promptTemplate.create(Map.of(
      "hotel", city,
      "format", mapOutputConverter.getFormat()
    ));

    String json = chatClient.prompt(prompt).call().content();

    Map<String, Object> hotelInfo = mapOutputConverter.convert(json);

    return hotelInfo;
  }

  public Map<String, Object> mapOutputConverterHighLevel(String city) {
    Map<String, Object> hotelInfo = chatClient.prompt()
      .user("호텔 %s에 대해 정보를 알려주세요".formatted(city))
      .call()
      .entity(new MapOutputConverter());
    return hotelInfo;
  }
}

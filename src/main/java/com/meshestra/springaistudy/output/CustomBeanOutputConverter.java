package com.meshestra.springaistudy.output;

import com.meshestra.springaistudy.dto.Hotel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CustomBeanOutputConverter {

  public ChatClient chatClient;

  public Hotel beanOutputConverterLowLevel(String city) {
    BeanOutputConverter<Hotel> beanOutputConverter = new BeanOutputConverter<>(Hotel.class);

    Prompt prompt = PromptTemplate
      .builder()
      .template("{city}에서 유명한 호텔 목록 5개를 출력하세요. {format}")
      .build()
      .create(Map.of("city", city, "format", beanOutputConverter.getFormat()));

    String json = chatClient.prompt(prompt).call().content();

    Hotel hotel = beanOutputConverter.convert(json);

    return hotel;
  }

  public Hotel beanOutputConverterHighLevel(String city) {
    Hotel hotel = chatClient.prompt()
      .user("%s에서 유명한 호텔 목록 5개를 출력하세요.".formatted(city))
      .call()
      .entity(new BeanOutputConverter<>(Hotel.class));

    return hotel;
  }

}


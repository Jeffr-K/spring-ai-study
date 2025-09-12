package com.meshestra.springaistudy.prompt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
@Slf4j
public class AiServicePromptTemplate {
  private ChatClient chatClient;

  private PromptTemplate systemTemplate = SystemPromptTemplate.builder().template("""
      답변을 생성할 때 HTML와 CSS 를 사용해서 파란 글자로 출력하세요.
      <span> 태그 안에 들어갈 내용만 출력하세요.
    """).build();

  private PromptTemplate userTemplate = PromptTemplate.builder().template("다음 한국어 문장을 {language}로 번역해 주세요. \n 문장: {statement}").build();

  public AiServicePromptTemplate(ChatClient.Builder chatClientBuilder) {
    chatClient = chatClientBuilder.build();
  }

  public Flux<String> promptTemplate(String statement, String language) {
    Prompt prompt = userTemplate.create(
      Map.of("statement", statement, "language", language));

    Flux<String> response = chatClient.prompt(prompt).stream().content();
    return response;
  }

  /**
   * SystemMessage 와 함께 UserMessage 가 포함된 프롬프트를 LLM 에 전달하고 싶다면?
   */
  public Flux<String> promptTemplateWithSystemMessage(String statement, String language) {
    return chatClient.prompt()
      .messages(
        systemTemplate.createMessage(),
        userTemplate.createMessage(Map.of("statement", statement, "language", language))
      )
      .stream()
      .content();
  }

  /**
   * 각각의 프롬프트 템플릿에서 render() 를 호출해서 메세지 텍스트를 얻고 system() 과 user() 에 각각 제공하는 방식
   */
  public Flux<String> promptTemplateWithSystemMessageVersion2(String statement, String language) {
    return chatClient.prompt()
      .system(systemTemplate.render())
      .user(userTemplate.render(Map.of("statement", statement, "language", language)))
      .stream()
      .content();
  }

  /**
   * 자리 표시자를 넣고 formatted() 메서드를 이용한 데이터 바인딩 방식
   * 재사용의 목적이 없고 메소드 내에서 빠르게 데이터를 바인딩해서 사용할 경우 추천한다.
   */
  public Flux<String> promptTemplateWithStringFormatter(String statement, String language) {
    String systemText = """
      답변을 생성할 때 HTML와 CSS 를 사용해서 파란 글자로 출력하세요.
      <span> 태그 안에 들어갈 내용만 출력하세요.
    """;

    String userText = "다음 한국어 문장을 %s로 번역해 주세요. \n 문장: %s"
      .formatted(language, statement);

    return chatClient.prompt()
      .system(systemText)
      .user(userText)
      .stream()
      .content();
  }
}

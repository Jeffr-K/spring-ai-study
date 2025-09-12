package com.meshestra.springaistudy.prompt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
class AiMultiMessagePromptTemplateService {

  private ChatClient chatClient;


  /**
   *
   * @param question 사용자의 질문
   * @param chatMemory 이전 대화 내용이 저장된 List<Message>
   * @return
   */
  public String multiMessage(String question, List<Message> chatMemory) {
    SystemMessage systemMessage = SystemMessage.builder().text("""
      당신은 AI 비서 입니다.
      제공되는 지난 대화 내용을 보고 우선적으로 답변해주세요.
      """).build();

    // 대화 내용이 비어있다면(첫대화일경우)
    if(chatMemory.isEmpty()) {
      chatMemory.add(systemMessage);
    }

    log.info(chatMemory.toString());

    ChatResponse chatResponse = chatClient.prompt()
      .messages(chatMemory) // 이전 메세지
      .user(question) // 사용자 질문
      .call()// 요청
      .chatResponse(); // 응답, 완전한 응답이 올떄까지 블로킹

    UserMessage userMessage = UserMessage.builder().text(question).build();
    chatMemory.add(userMessage);

    AssistantMessage assistantMessage = chatResponse.getResult().getOutput();
    chatMemory.add(assistantMessage);

    String text = assistantMessage.getText();
    return text;
  }
}
package com.jpdr.apps.home.ollama.stream.service.impl;

import com.jpdr.apps.home.ollama.stream.exception.OllamaRepositoryException;
import com.jpdr.apps.home.ollama.stream.service.AppService;
import com.jpdr.apps.home.ollama.stream.service.dto.AppRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.jpdr.apps.home.ollama.stream.util.PromptUtils.HEALTH_ASSISTANT_MESSAGE;
import static com.jpdr.apps.home.ollama.stream.util.PromptUtils.HEALTH_SYSTEM_MESSAGE;
import static com.jpdr.apps.home.ollama.stream.util.PromptUtils.HEALTH_USER_MESSAGE;
import static com.jpdr.apps.home.ollama.stream.util.PromptUtils.MOOD_SYSTEM_MESSAGE;
import static com.jpdr.apps.home.ollama.stream.util.PromptUtils.MOOD_USER_MESSAGE_PREFIX;

@Slf4j
@Service
public class AppServiceImpl implements AppService {
  
  private final OllamaChatModel chatModel;
  private final OllamaOptions options;
  
    public AppServiceImpl(OllamaChatModel chatModel,
    @Qualifier(value = "appOllamaOptions") OllamaOptions options){
    this.chatModel = chatModel;
    this.options = options;
  }
  
  @Override
  public Flux<String> checkHealth() {
      return Mono.defer(() -> Mono.just(new ArrayList(List.of(
          new SystemMessage(HEALTH_SYSTEM_MESSAGE),
          new UserMessage(HEALTH_USER_MESSAGE),
          new AssistantMessage(HEALTH_ASSISTANT_MESSAGE),
          new UserMessage(HEALTH_USER_MESSAGE)
        ))))
        .map(messages -> new Prompt(messages, this.options))
        .flatMapMany(this::callModel);
  }
  
  @Override
  public Flux<String> detectMood(AppRequest request) {
      return Mono.defer(() -> Mono.just(request.getSentence()))
        .map(sentence -> new ArrayList(List.of(
          new SystemMessage(MOOD_SYSTEM_MESSAGE),
          new UserMessage(MOOD_USER_MESSAGE_PREFIX + sentence)
        )))
        .map(messages -> new Prompt(messages, this.options))
        .flatMapMany(this::callModel);
  }
  
  @Override
  public Flux<String> streamText() {
     return Flux.defer(() -> Mono.just("text ")).repeat(20).delayElements(Duration.ofSeconds(1));
  }
  
  private Flux<String> callModel(Prompt prompt){
      return this.chatModel.stream(prompt)
        .onErrorResume(Exception.class, ex -> Mono.error(new OllamaRepositoryException(ex)))
        .map(ChatResponse::getResult)
        .map(Generation::getOutput)
        .map(AssistantMessage::getText)
        .map(this::cleanAnswer);
  }
  
  private String cleanAnswer(String message){
    return message.replace("<think>","")
      .replace("</think>","")
      .replace("\n"," ");
  }
  
}

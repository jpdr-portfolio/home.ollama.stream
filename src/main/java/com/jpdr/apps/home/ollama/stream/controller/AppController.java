package com.jpdr.apps.home.ollama.stream.controller;

import com.jpdr.apps.home.ollama.stream.service.AppService;
import com.jpdr.apps.home.ollama.stream.service.dto.AppRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class AppController {
  
  private final AppService appService;
  
  @GetMapping(value = "/stream/stream-text", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<String> streamText(){
    return appService.streamText();
  }
  
  @GetMapping(value = "/stream/check-health", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<String> checkHealth(){
    return appService.checkHealth();
  }
  
  @PostMapping(value = "/stream/detect-mood", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<String> detectMood(@RequestBody AppRequest appRequest){
    return appService.detectMood(appRequest);
  }
  
}

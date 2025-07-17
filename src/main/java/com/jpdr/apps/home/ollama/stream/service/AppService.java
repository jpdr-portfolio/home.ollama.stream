package com.jpdr.apps.home.ollama.stream.service;

import com.jpdr.apps.home.ollama.stream.service.dto.AppRequest;
import reactor.core.publisher.Flux;

public interface AppService {
  
    Flux<String> checkHealth();
    Flux<String> detectMood(AppRequest request);
    
    Flux<String> streamText();
  
}

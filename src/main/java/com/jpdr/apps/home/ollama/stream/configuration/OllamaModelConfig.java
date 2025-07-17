package com.jpdr.apps.home.ollama.stream.configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OllamaModelConfig {
  
  private final OllamaOptionsConfigProperties optionsConfig;

  @Bean(name = "appOllamaOptions")
  OllamaOptions ollamaOptions(){
    return OllamaOptions.builder()
        .model(optionsConfig.getModel())
        .temperature(optionsConfig.getTemperature())
        .keepAlive(optionsConfig.getKeepAlive())
        .numCtx(optionsConfig.getNumCtx())
        .build();
  }

}

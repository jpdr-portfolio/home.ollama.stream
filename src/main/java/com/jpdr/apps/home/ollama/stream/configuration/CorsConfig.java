package com.jpdr.apps.home.ollama.stream.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {
  
  private final CorsConfigProperties corsConfigProperties;
  
  @Bean
  public WebFluxConfigurer webFluxConfigurer(){
    return new WebFluxConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/stream/**")
          .allowedOrigins(corsConfigProperties.getUrl())
          .allowedMethods(corsConfigProperties.getMethod());
      }
    };
  }
  
}

package com.jpdr.apps.home.ollama.stream.configuration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.ollama.options")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OllamaOptionsConfigProperties {
  
  String model;
  Double temperature;
  String keepAlive;
  Integer numCtx;
  
}

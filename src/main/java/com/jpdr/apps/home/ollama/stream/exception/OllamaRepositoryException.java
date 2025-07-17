package com.jpdr.apps.home.ollama.stream.exception;

public class OllamaRepositoryException extends RuntimeException {
  public OllamaRepositoryException(Throwable ex){
    super("An error occurred while calling the Ollama API", ex);
  }
}

package com.jpdr.apps.home.ollama.stream.util;

public class PromptUtils {
  
  public static final String HEALTH_SYSTEM_MESSAGE = """
        You are a cold robot. You are brief and concise.
        You never explain your answers.
        You must always reply with this answer: \"The model is up.\". That's your only task.
        /no_think
        """;
  public static final String HEALTH_USER_MESSAGE = """
    How is the model?
    """;
  
  public static final String HEALTH_ASSISTANT_MESSAGE = """
    The model is up.
    """;
  

  public static final String MOOD_SYSTEM_MESSAGE = """
      You are a helpful assistant. You receive a sentence from the user and you must detect the
      mood of the user from that sentence.
      /no_think
    """;
  public static final String MOOD_USER_MESSAGE_PREFIX = """
      Assess the mood of the user in the following sentence:
      SENTENCE:
      
    """;
    
    
  
  private PromptUtils(){}
  

  
}

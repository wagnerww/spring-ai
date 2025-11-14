package com.wagnerww.springai.ai_demo.services;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenAiService {
    
    private ChatClient chatClient;


    @Autowired
    public OpenAiService(ChatClient.Builder builder) {
        ChatMemoryRepository chatMemoryRepository = new InMemoryChatMemoryRepository();
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
            .chatMemoryRepository(chatMemoryRepository)
            .maxMessages(10)
            .build();

        this.chatClient = builder.defaultAdvisors(
                MessageChatMemoryAdvisor.builder(chatMemory).build()
                ).build();
    }


    public ChatResponse generateAnswer(String question) {
        OpenAiChatOptions options = new OpenAiChatOptions();
        options.setModel("gpt-4o");
        options.setTemperature(0.7);
        options.setMaxTokens(1000);
        Prompt prompt  = new Prompt(question,   options);
        return chatClient.prompt(prompt).call().chatResponse();
    }

    public String getTravelGuidance(String city, String month, String language, String budget) {
        PromptTemplate promptTemplate =  new PromptTemplate("Welcome to the {city} travel guide!\n"
				+ "If you're visiting in {month}, here's what you can do:\n" 
                + "1. Must-visit attractions.\n"
				+ "2. Local cuisine you must try.\n" 
                + "3. Useful phrases in {language}.\n"
				+ "4. Tips for traveling on a {budget} budget.\n"
                + "Enjoy your trip!");

            Prompt prompt = promptTemplate.create(Map.of(
                "city", city, 
                "month", month, 
                "language", language, 
                "budget", budget)
            );

        return chatClient.prompt(prompt).call().chatResponse().getResult().getOutput().getText();
    }


}

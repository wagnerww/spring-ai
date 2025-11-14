package com.wagnerww.springai.ai_demo.controllers;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wagnerww.springai.ai_demo.services.OpenAiService;


@Controller
public class AswerAnyThingController {
    @Autowired
    private OpenAiService service;

    @GetMapping("/showAskAnything")
    public String getMethodName() {
        return "askAnything";
    }

    @PostMapping("/askAnything")
    public String askAnything(
        @RequestParam("question") String question,
        Model model
    ) {
        ChatResponse response = service.generateAnswer(question);
        System.out.println("Answer: " + response);
        model.addAttribute("question", question);
        model.addAttribute("answer", response.getResult().getOutput().getText());
        return "askAnything";
    }

    
}

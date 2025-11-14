package com.wagnerww.springai.ai_demo.controllers.pompts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wagnerww.springai.ai_demo.services.OpenAiService;


@Controller
public class TravelGuideController {

    @Autowired
    private OpenAiService service;

    @GetMapping("/showTravelGuide")
    public String showTravelGuide() {
        return "travelGuide";
    }

    @PostMapping("/travelGuide")
    public String getChatResponse(
        @RequestParam("city") String city,
        @RequestParam("month") String month,
        @RequestParam("language") String language,
        @RequestParam("budget") String budget,
        Model model
    ) {

        String response = service.getTravelGuidance(city, month, language, budget);
        model.addAttribute("response", response);
        model.addAttribute("city", city);

        return "travelGuide";
            
    }
    
    
    
}

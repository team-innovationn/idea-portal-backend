package com.ecobank.idea.controller;

import com.ecobank.idea.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static com.ecobank.idea.constants.AppConstants.API_BASE_URL;

@RestController
@RequestMapping(API_BASE_URL + "/sse")
@RequiredArgsConstructor
public class SseController {
    private final SseService sseService;

    // Endpoint for clients to subscribe to SSE
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        // Add a new emitter using the SseService and return it
        return sseService.addEmitter();
    }
}

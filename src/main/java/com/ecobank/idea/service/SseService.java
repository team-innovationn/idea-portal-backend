package com.ecobank.idea.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseService {
    // Method to add a new emitter
    SseEmitter addEmitter();

    // Method to send events to all emitters
    void emitEvent(String eventType, Object data);
}

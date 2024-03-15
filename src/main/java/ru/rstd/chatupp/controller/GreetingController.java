package ru.rstd.chatupp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.rstd.chatupp.entity.Message;

@Controller
@RequiredArgsConstructor
public class GreetingController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Hello from server!");
    }

    @MessageMapping("/greeting")
    public void handle(@Payload Message message) {
        simpMessagingTemplate.setSendTimeout(10);
        simpMessagingTemplate.convertAndSendToUser("aboba", "/queue/private-chat", message);
    }
}

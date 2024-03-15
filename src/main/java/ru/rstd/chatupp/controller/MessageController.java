package ru.rstd.chatupp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.rstd.chatupp.dto.CreateMessageDto;
import ru.rstd.chatupp.dto.ReadMessageDto;
import ru.rstd.chatupp.service.MessageService;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/private-room")
    public void saveAndSendToQueue(@Payload @RequestBody CreateMessageDto createMessageDto) {
        var savedMessage = messageService.save(createMessageDto);
        simpMessagingTemplate.convertAndSendToUser(savedMessage.recipient().username(),
                "/queue/private-chat", savedMessage);
        if (createMessageDto.privateRoomId() == null) {
            simpMessagingTemplate.convertAndSendToUser(savedMessage.sender().username(), "/queue/private-chat", savedMessage.privateRoomId());
        }
    }

    @GetMapping("/api/v1/messages/{privateRoomId}")
    public ResponseEntity<List<ReadMessageDto>> findMessagesByPrivateRoomId(@PathVariable("privateRoomId") Long id) {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(messageService.findAllByPrivateRoomId(id));
    }
}

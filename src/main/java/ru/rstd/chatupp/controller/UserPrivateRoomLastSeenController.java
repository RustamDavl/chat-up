package ru.rstd.chatupp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import ru.rstd.chatupp.dto.UserPrivateRoomLastSeenEvent;
import ru.rstd.chatupp.service.UserPrivateRoomLastSeenService;

@Controller
@RequiredArgsConstructor
public class UserPrivateRoomLastSeenController {

    private final UserPrivateRoomLastSeenService userPrivateRoomLastSeenService;

    //only for existed chat rooms
    @MessageMapping("/last-seen-event")
    public void saveEvent(@Payload @RequestBody UserPrivateRoomLastSeenEvent event) {
        userPrivateRoomLastSeenService.update(event);
    }
}

package ru.rstd.chatupp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rstd.chatupp.dto.ReadPrivateRoomDto;
import ru.rstd.chatupp.service.PrivateRoomService;

import java.util.List;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/api/v1/private-rooms")
@RequiredArgsConstructor
public class PrivateRoomController {

    private final PrivateRoomService privateRoomService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ReadPrivateRoomDto>> findAllPrivateRoomsByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(privateRoomService.findAllBySenderIdOrRecipientId(userId));
    }
}

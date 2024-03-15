package ru.rstd.chatupp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rstd.chatupp.dto.ReadUserDto;
import ru.rstd.chatupp.dto.UserAuthenticationDto;
import ru.rstd.chatupp.dto.UserRegistrationDto;
import ru.rstd.chatupp.service.UserService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRegistrationDto userRegistrationDto) {
        userService.register(userRegistrationDto);
        return ResponseEntity.status(CREATED)
                .build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ReadUserDto> authenticate(@RequestBody UserAuthenticationDto userAuthenticationDto) {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(userService.authenticate(userAuthenticationDto.username(), userAuthenticationDto.password()));
    }

    @GetMapping("/{username}")
    public ResponseEntity<ReadUserDto> findByUsername(@PathVariable("username") String username) {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(userService.findByUsername(username));
    }

    @GetMapping
    public ResponseEntity<List<ReadUserDto>> findAll() {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(userService.findAll());
    }

}

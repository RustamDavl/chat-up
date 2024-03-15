package ru.rstd.chatupp.dto;

public record ReadMessageDto(String id,
                             String privateRoomId,
                             String payload,
                             ReadUserDto sender,
                             ReadUserDto recipient) {
}

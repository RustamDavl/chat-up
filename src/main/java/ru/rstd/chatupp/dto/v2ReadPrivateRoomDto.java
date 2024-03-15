package ru.rstd.chatupp.dto;

public record v2ReadPrivateRoomDto(String id, ReadUserDto sender, ReadUserDto recipient) {
}

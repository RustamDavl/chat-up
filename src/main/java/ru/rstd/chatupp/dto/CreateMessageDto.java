package ru.rstd.chatupp.dto;

public record CreateMessageDto(String privateRoomId, String senderId, String recipientId, String payload, String sentAt) {
}

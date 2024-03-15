package ru.rstd.chatupp.dto;

public record UserPrivateRoomLastSeenEvent(String userId, String privateRoomId, String inRoom) {
}

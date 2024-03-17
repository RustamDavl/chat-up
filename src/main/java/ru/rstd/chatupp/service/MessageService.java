package ru.rstd.chatupp.service;

import ru.rstd.chatupp.dto.CreateMessageDto;
import ru.rstd.chatupp.dto.ReadMessageDto;

import java.util.List;

public interface MessageService {
    ReadMessageDto save(CreateMessageDto createMessageDto);
    List<ReadMessageDto> findAllByPrivateRoomId(Long id);
    void updateStatusAsRead(Long privateRoomId, Long recipientId);
    Long getCountOfUnreadMessages(Long myId, Long privateRoomId);
}

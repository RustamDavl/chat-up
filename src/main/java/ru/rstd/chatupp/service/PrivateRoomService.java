package ru.rstd.chatupp.service;

import ru.rstd.chatupp.dto.CreatePrivateRoomDto;
import ru.rstd.chatupp.dto.v2ReadPrivateRoomDto;
import ru.rstd.chatupp.dto.ReadPrivateRoomDto;
import ru.rstd.chatupp.entity.PrivateRoom;

import java.util.List;
import java.util.Optional;

public interface PrivateRoomService {
    List<ReadPrivateRoomDto> findAllBySenderIdOrRecipientId(Long userId);

    PrivateRoom create(Long senderId, Long recipientId);

    PrivateRoom findById(Long id);
}

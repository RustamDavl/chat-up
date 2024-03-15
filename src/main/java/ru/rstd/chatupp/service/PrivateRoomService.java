package ru.rstd.chatupp.service;

import ru.rstd.chatupp.dto.CreatePrivateRoomDto;
import ru.rstd.chatupp.dto.v2ReadPrivateRoomDto;
import ru.rstd.chatupp.dto.ReadPrivateRoomDto;

import java.util.List;

public interface PrivateRoomService {
    List<ReadPrivateRoomDto> findAllBySenderIdOrRecipientId(Long userId);

    v2ReadPrivateRoomDto create(CreatePrivateRoomDto createPrivateRoomDto);
}

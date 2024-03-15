package ru.rstd.chatupp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rstd.chatupp.dto.CreatePrivateRoomDto;
import ru.rstd.chatupp.dto.v2ReadPrivateRoomDto;
import ru.rstd.chatupp.dto.ReadPrivateRoomDto;
import ru.rstd.chatupp.entity.PrivateRoom;
import ru.rstd.chatupp.exception.UserNotFoundException;
import ru.rstd.chatupp.mapper.PrivateRoomMapper;
import ru.rstd.chatupp.mapper.UserMapper;
import ru.rstd.chatupp.repository.PrivateRoomRepository;
import ru.rstd.chatupp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateRoomServiceImpl implements PrivateRoomService {

    private final PrivateRoomRepository privateRoomRepository;
    private final PrivateRoomMapper privateRoomMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<ReadPrivateRoomDto> findAllBySenderIdOrRecipientId(Long userId) {
        var list = privateRoomRepository.findAllBySenderIdOrRecipientId(userId);
        var returnList = new ArrayList<ReadPrivateRoomDto>();
        for (PrivateRoom pr : list) {
            if (pr.getRecipient().getId().equals(userId)) {
                returnList.add(privateRoomMapper.toReadPrivateRoomDtoV2(pr.getId(), pr.getSender(), userMapper));
            } else {
                returnList.add(privateRoomMapper.toReadPrivateRoomDtoV2(pr.getId(), pr.getRecipient(), userMapper));
            }
        }
        return returnList;
    }

    @Override
    public v2ReadPrivateRoomDto create(CreatePrivateRoomDto createPrivateRoomDto) {
        var maybeSender = userRepository.findById(Long.parseLong(createPrivateRoomDto.senderId()))
                .orElseThrow(() -> new UserNotFoundException("there is no user with such id"));
        var maybeRecipient = userRepository.findById(Long.parseLong(createPrivateRoomDto.recipientId()))
                .orElseThrow(() -> new UserNotFoundException("there is no user with such id"));
        var privateRoomToSave = PrivateRoom.builder()
                .sender(maybeSender)
                .recipient(maybeRecipient)
                .build();

        return privateRoomMapper.toReadPrivateRoomDto(
                privateRoomRepository.save(privateRoomToSave)
        );
    }
}

package ru.rstd.chatupp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rstd.chatupp.dto.ReadPrivateRoomDto;
import ru.rstd.chatupp.entity.PrivateRoom;
import ru.rstd.chatupp.exception.PrivateRoomNotFoundException;
import ru.rstd.chatupp.exception.UserNotFoundException;
import ru.rstd.chatupp.mapper.PrivateRoomMapper;
import ru.rstd.chatupp.mapper.UserMapper;
import ru.rstd.chatupp.repository.PrivateRoomRepository;
import ru.rstd.chatupp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PrivateRoomServiceImpl implements PrivateRoomService {

    private final PrivateRoomRepository privateRoomRepository;
    private final PrivateRoomMapper privateRoomMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public PrivateRoom findById(Long id) {
        return privateRoomRepository.findById(id)
                .orElseThrow(() -> new PrivateRoomNotFoundException("there is no private room by such id"));
    }

    @Override
    public PrivateRoom create(Long senderId, Long recipientId) {
        var maybeSender = userRepository.findById(senderId)
                .orElseThrow(() -> new UserNotFoundException("there is no user with such id"));
        var maybeRecipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new UserNotFoundException("there is no user with such id"));
        return privateRoomRepository.saveAndFlush(PrivateRoom.builder()
                .sender(maybeSender)
                .recipient(maybeRecipient)
                .build());
    }
}

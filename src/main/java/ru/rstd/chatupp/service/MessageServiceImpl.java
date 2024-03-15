package ru.rstd.chatupp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rstd.chatupp.dto.CreateMessageDto;
import ru.rstd.chatupp.dto.ReadMessageDto;
import ru.rstd.chatupp.entity.Message;
import ru.rstd.chatupp.entity.PrivateRoom;
import ru.rstd.chatupp.exception.PrivateRoomNotFoundException;
import ru.rstd.chatupp.exception.UserNotFoundException;
import ru.rstd.chatupp.mapper.MessageMapper;
import ru.rstd.chatupp.repository.MessageRepository;
import ru.rstd.chatupp.repository.PrivateRoomRepository;
import ru.rstd.chatupp.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserRepository userRepository;
    private final PrivateRoomRepository privateRoomRepository;

    @Override
    public ReadMessageDto save(CreateMessageDto createMessageDto) {
        var maybeSender = userRepository.findById(Long.parseLong(createMessageDto.senderId()))
                .orElseThrow(() -> new UserNotFoundException("there is no user with such id"));
        var maybeRecipient = userRepository.findById(Long.parseLong(createMessageDto.recipientId()))
                .orElseThrow(() -> new UserNotFoundException("there is no user with such id"));

        PrivateRoom privateRoom = null;
        if (createMessageDto.privateRoomId() == null) {
            var privateRoomToSave = PrivateRoom.builder()
                    .sender(maybeSender)
                    .recipient(maybeRecipient)
                    .build();
            privateRoom = privateRoomRepository.save(privateRoomToSave);// TODO: 11.03.2024 check if id will be assigned
        } else {
            privateRoom = privateRoomRepository.findById(Long.parseLong(createMessageDto.privateRoomId()))
                    .orElseThrow(() -> new PrivateRoomNotFoundException("there is no private room by such id"));
        }
        var messageToSave = Message.builder()
                .sender(maybeSender)
                .recipient(maybeRecipient)
                .privateRoom(privateRoom)
                .payload(createMessageDto.payload())
                .build();
        return messageMapper.toReadMessageDto(
                messageRepository.save(messageToSave)
        );
    }
    @Override
    public List<ReadMessageDto> findAllByPrivateRoomId(Long id) {
        return messageRepository.findAllByPrivateRoomId(id)
                .stream()
                .map(messageMapper::toReadMessageDto)
                .toList();
    }
}

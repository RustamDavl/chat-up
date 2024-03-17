package ru.rstd.chatupp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rstd.chatupp.dto.CreateMessageDto;
import ru.rstd.chatupp.dto.ReadMessageDto;
import ru.rstd.chatupp.entity.Message;
import ru.rstd.chatupp.entity.MessageStatus;
import ru.rstd.chatupp.entity.PrivateRoom;
import ru.rstd.chatupp.entity.UserPrivateRoomLastSeen;
import ru.rstd.chatupp.exception.PrivateRoomNotFoundException;
import ru.rstd.chatupp.exception.UserNotFoundException;
import ru.rstd.chatupp.mapper.MessageMapper;
import ru.rstd.chatupp.repository.MessageRepository;
import ru.rstd.chatupp.repository.PrivateRoomRepository;
import ru.rstd.chatupp.repository.UserPrivateRoomLastSeenRepository;
import ru.rstd.chatupp.repository.UserRepository;

import java.util.List;

import static ru.rstd.chatupp.entity.MessageStatus.READ;
import static ru.rstd.chatupp.entity.MessageStatus.UNREAD;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserRepository userRepository;
    private final UserPrivateRoomLastSeenRepository userPrivateRoomLastSeenRepository;
    private final PrivateRoomService privateRoomService;

    // TODO: 17.03.2024 change save method because of ... omg i want to cry..

    @Override
    public ReadMessageDto save(CreateMessageDto createMessageDto) {
        var maybeSender = userRepository.findById(Long.parseLong(createMessageDto.senderId()))
                .orElseThrow(() -> new UserNotFoundException("there is no user with such id"));
        var maybeRecipient = userRepository.findById(Long.parseLong(createMessageDto.recipientId()))
                .orElseThrow(() -> new UserNotFoundException("there is no user with such id"));

        PrivateRoom privateRoom;
        if (createMessageDto.privateRoomId() == null) {
            privateRoom = privateRoomService.create(maybeSender.getId(), maybeRecipient.getId());
            userPrivateRoomLastSeenRepository.save(UserPrivateRoomLastSeen.builder()
                    .user(maybeRecipient)
                    .inRoom(false)
                    .privateRoom(privateRoom)
                    .build());
            userPrivateRoomLastSeenRepository.save(UserPrivateRoomLastSeen.builder()
                    .user(maybeSender)
                    .inRoom(true)
                    .privateRoom(privateRoom)
                    .build());
        } else {
            privateRoom = privateRoomService.findById(Long.parseLong(createMessageDto.privateRoomId()));
        }

        var maybeUserPrivateRoomLastSeen = userPrivateRoomLastSeenRepository.findByUserIdAndPrivateRoomId(
                        maybeRecipient.getId(), privateRoom.getId())
                .orElseThrow(() -> new RuntimeException(""));

        Message messageToSave;
        if (!maybeUserPrivateRoomLastSeen.getInRoom()) {
            messageToSave = Message.builder()
                    .sender(maybeSender)
                    .recipient(maybeRecipient)
                    .privateRoom(privateRoom)
                    .payload(createMessageDto.payload())
                    .messageStatus(UNREAD)
                    .build();
        } else {
            messageToSave = Message.builder()
                    .sender(maybeSender)
                    .recipient(maybeRecipient)
                    .privateRoom(privateRoom)
                    .payload(createMessageDto.payload())
                    .messageStatus(READ)
                    .build();
        }
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

    @Override
    public void updateStatusAsRead(Long privateRoomId, Long recipientId) {
        messageRepository.findAllByPrivateRoomIdAndRecipientId(privateRoomId, recipientId)
                .forEach(message -> {
                    message.setMessageStatus(READ);
                    messageRepository.save(message);
                });
    }

    @Override
    public Long getCountOfUnreadMessages(Long myId, Long privateRoomId) {
        return messageRepository.getCountOfUnreadMessages(myId, privateRoomId);
    }


}

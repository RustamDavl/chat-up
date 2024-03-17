package ru.rstd.chatupp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rstd.chatupp.dto.UserPrivateRoomLastSeenEvent;
import ru.rstd.chatupp.entity.UserPrivateRoomLastSeen;
import ru.rstd.chatupp.exception.PrivateRoomNotFoundException;
import ru.rstd.chatupp.exception.UserNotFoundException;
import ru.rstd.chatupp.repository.PrivateRoomRepository;
import ru.rstd.chatupp.repository.UserPrivateRoomLastSeenRepository;
import ru.rstd.chatupp.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UserPrivateRoomLastSeenServiceImpl implements UserPrivateRoomLastSeenService {

    private final UserPrivateRoomLastSeenRepository userPrivateRoomLastSeenRepository;
    private final UserRepository userRepository;
    private final PrivateRoomRepository privateRoomRepository;
    private final MessageService messageService;

    @Override
    public void update(UserPrivateRoomLastSeenEvent event) {
        var maybeUser = userRepository.findById(Long.parseLong(event.userId()))
                .orElseThrow(() -> new UserNotFoundException("there is no user with such id"));
        var maybePrivateRoom = privateRoomRepository.findById(Long.parseLong(event.privateRoomId()))
                .orElseThrow(() -> new PrivateRoomNotFoundException("there is no private room with such id"));
        var privateRoomLastSeen = userPrivateRoomLastSeenRepository.findByUserIdAndPrivateRoomId(
                        maybeUser.getId(), maybePrivateRoom.getId())
                .orElseThrow(() -> new RuntimeException(""));

        privateRoomLastSeen.setInRoom(Boolean.valueOf(event.inRoom()));
        if (Boolean.parseBoolean(event.inRoom())) {
            messageService.updateStatusAsRead(maybePrivateRoom.getId(), maybeUser.getId());
        }
        userPrivateRoomLastSeenRepository.save(privateRoomLastSeen);
    }

    @Override
    public void create(UserPrivateRoomLastSeen userPrivateRoomLastSeen) {
        userPrivateRoomLastSeenRepository.save(userPrivateRoomLastSeen);
    }
}

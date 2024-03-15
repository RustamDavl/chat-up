package ru.rstd.chatupp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rstd.chatupp.dto.UserPrivateRoomLastSeenEvent;
import ru.rstd.chatupp.entity.UserPrivateRoomLastSeen;
import ru.rstd.chatupp.exception.PrivateRoomNotFoundException;
import ru.rstd.chatupp.exception.UserNotFoundException;
import ru.rstd.chatupp.repository.PrivateRoomRepository;
import ru.rstd.chatupp.repository.UserPrivateRoomLastSeenRepository;
import ru.rstd.chatupp.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserPrivateRoomLastSeenServiceImpl implements UserPrivateRoomLastSeenService {

    private final UserPrivateRoomLastSeenRepository userPrivateRoomLastSeenRepository;
    private final UserRepository userRepository;
    private final PrivateRoomRepository privateRoomRepository;

    @Override
    public UserPrivateRoomLastSeen create(UserPrivateRoomLastSeenEvent event) {
        var maybeUser = userRepository.findById(Long.parseLong(event.userId()))
                .orElseThrow(() -> new UserNotFoundException("there is no user with such id"));
        var maybePrivateRoom = privateRoomRepository.findById(Long.parseLong(event.privateRoomId()))
                .orElseThrow(() -> new PrivateRoomNotFoundException("there is no private room with such id"));

        return userPrivateRoomLastSeenRepository.save(
                UserPrivateRoomLastSeen.builder()
                        .user(maybeUser)
                        .privateRoom(maybePrivateRoom)
                        .inRoom(Boolean.valueOf(event.inRoom()))
                        .build()
        );
    }
}

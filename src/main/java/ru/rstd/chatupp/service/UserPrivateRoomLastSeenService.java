package ru.rstd.chatupp.service;

import ru.rstd.chatupp.dto.UserPrivateRoomLastSeenEvent;
import ru.rstd.chatupp.entity.UserPrivateRoomLastSeen;

public interface UserPrivateRoomLastSeenService {
    void update(UserPrivateRoomLastSeenEvent event);
    void create(UserPrivateRoomLastSeen userPrivateRoomLastSeen);
}

package ru.rstd.chatupp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rstd.chatupp.entity.UserPrivateRoomLastSeen;

import java.util.Optional;

@Repository
public interface UserPrivateRoomLastSeenRepository extends JpaRepository<UserPrivateRoomLastSeen, Long> {
    Optional<UserPrivateRoomLastSeen> findByUserIdAndPrivateRoomId(Long userId, Long privateRoomId);
}

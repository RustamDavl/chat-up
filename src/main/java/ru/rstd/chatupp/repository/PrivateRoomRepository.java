package ru.rstd.chatupp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.rstd.chatupp.entity.PrivateRoom;

import java.util.List;

@Repository
public interface PrivateRoomRepository extends JpaRepository<PrivateRoom, Long> {

    @Query(
            """
                    SELECT pr FROM PrivateRoom pr
                    WHERE pr.sender.id = :userId OR pr.recipient.id = :userId
                    """
    )
    List<PrivateRoom> findAllBySenderIdOrRecipientId(Long userId);
}

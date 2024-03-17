package ru.rstd.chatupp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.rstd.chatupp.entity.Message;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByPrivateRoomId(Long id);
    @Query("""
            SELECT m from Message m
            where m.privateRoom.id = :privateRoomId AND m.recipient.id =:recipientId and m.messageStatus = 'UNREAD'
            """)
    List<Message> findAllByPrivateRoomIdAndRecipientId(Long privateRoomId, Long recipientId);

    @Query("""
            SELECT count (m) FROM Message m
            where m.recipient.id = :myId and m.privateRoom.id =: privateRoomId and m.messageStatus = 'UNREAD'
            """)
    Long getCountOfUnreadMessages(Long myId, Long privateRoomId);

    @Query("""
            SELECT m from Message m
            where m.privateRoom.id =:privateRoomId
            order by m.id desc 
            limit 1
            """)
    Optional<Message> findLastByPrivateRoomId(Long privateRoomId);
}

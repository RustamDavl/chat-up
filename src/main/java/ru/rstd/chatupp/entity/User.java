package ru.rstd.chatupp.entity;


import jakarta.persistence.*;
import lombok.*;
import ru.rstd.chatupp.entity.embeddable.PersonalInfo;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Embedded
    private PersonalInfo personalInfo;

//    @OneToMany(mappedBy = "senderId")
//    @Builder.Default
//    private List<PrivateRoom> privateRoomSenders = new ArrayList<>();
//
//    @OneToMany(mappedBy = "recipientId")
//    @Builder.Default
//    private List<PrivateRoom> privateRoomRecipients = new ArrayList<>();
//
//    @OneToMany(mappedBy = "senderId")
//    @Builder.Default
//    private List<Message> senderMessages = new ArrayList<>();
//
//    @OneToMany(mappedBy = "recipientId")
//    @Builder.Default
//    private List<Message> recipientMessages = new ArrayList<>();

}

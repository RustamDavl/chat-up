--liquibase formatted sql

--changeset rstd:1
create table message
(
    id              bigserial primary key,
    private_room_id bigint references private_room (id) not null,
    payload         text                                not null,
    sender_id       bigint references users (id)        not null,
    recipient_id    bigint references users (id)        not null
);
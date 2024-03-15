--liquibase formatted sql

--changeset rstd:1
create table user_private_room_last_seen
(
    id              bigserial primary key,
    user_id         bigint references users (id)        not null,
    private_room_id bigint references private_room (id) not null,
    in_room         bool                                not null,
    unique (user_id, private_room_id)
);
--liquibase formatted sql

--changeset rstd:1
create table private_room
(
    id           bigserial primary key,
    sender_id    bigint references users (id) not null,
    recipient_id bigint references users (id) not null
);
--liquibase formatted sql

--changeset rstd:1
create table users
(
    id       bigserial primary key,
    username varchar(128) unique not null,
    password varchar(256)        not null,
    bio      varchar(512),
    photo    bytea
);
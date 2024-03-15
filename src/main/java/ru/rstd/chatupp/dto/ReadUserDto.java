package ru.rstd.chatupp.dto;

import java.util.List;

public record ReadUserDto(
        String id,
        String username,
        String bio
) {
}

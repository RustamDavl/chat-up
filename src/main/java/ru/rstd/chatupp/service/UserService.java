package ru.rstd.chatupp.service;

import ru.rstd.chatupp.dto.ReadUserDto;
import ru.rstd.chatupp.dto.UserRegistrationDto;
import ru.rstd.chatupp.entity.User;

import java.util.List;

public interface UserService {

    ReadUserDto authenticate(String username, String password);

    void register(UserRegistrationDto userRegistrationDto);

    ReadUserDto findByUsername(String username);

    List<ReadUserDto> findAll();
}

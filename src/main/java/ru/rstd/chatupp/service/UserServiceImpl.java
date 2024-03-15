package ru.rstd.chatupp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rstd.chatupp.dto.ReadUserDto;
import ru.rstd.chatupp.dto.UserRegistrationDto;
import ru.rstd.chatupp.entity.User;
import ru.rstd.chatupp.exception.UserNotFoundException;
import ru.rstd.chatupp.exception.UsernameAlreadyExistsException;
import ru.rstd.chatupp.mapper.UserMapper;
import ru.rstd.chatupp.repository.UserRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public ReadUserDto authenticate(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .map(userMapper::toReadUserDto)
                .orElseThrow(() -> new UserNotFoundException("incorrect credentials"));
    }

    @Override
    public void register(UserRegistrationDto userRegistrationDto) {
        var maybeUser = userRepository.findByUsername(userRegistrationDto.username());
        if (maybeUser.isPresent())
            throw new UsernameAlreadyExistsException("user with such username already exists");
        userRepository.save(userMapper.toUser(userRegistrationDto));
    }

    @Override
    public ReadUserDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toReadUserDto)
                .orElseThrow(() -> new UserNotFoundException("there is no user with such username"));
    }

    @Override
    public List<ReadUserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toReadUserDto)
                .toList();
    }
}

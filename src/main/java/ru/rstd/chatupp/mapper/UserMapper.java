package ru.rstd.chatupp.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.rstd.chatupp.dto.ReadUserDto;
import ru.rstd.chatupp.dto.UserRegistrationDto;
import ru.rstd.chatupp.entity.User;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    User toUser(UserRegistrationDto userRegistrationDto);

    ReadUserDto toReadUserDto(User user);
}

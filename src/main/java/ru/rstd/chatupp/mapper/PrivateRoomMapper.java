package ru.rstd.chatupp.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.rstd.chatupp.dto.v2ReadPrivateRoomDto;
import ru.rstd.chatupp.dto.ReadPrivateRoomDto;
import ru.rstd.chatupp.entity.PrivateRoom;
import ru.rstd.chatupp.entity.User;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PrivateRoomMapper {

    v2ReadPrivateRoomDto toReadPrivateRoomDto(PrivateRoom privateRoom);

    default ReadPrivateRoomDto toReadPrivateRoomDtoV2(Long id, User recipient, UserMapper userMapper) {
        return new ReadPrivateRoomDto(id.toString(), userMapper.toReadUserDto(recipient));
    }
}

package ru.rstd.chatupp.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.rstd.chatupp.dto.ReadMessageDto;
import ru.rstd.chatupp.entity.Message;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MessageMapper {

    @Mapping(target = "privateRoomId", source = "privateRoom.id")
    ReadMessageDto toReadMessageDto(Message message);
}

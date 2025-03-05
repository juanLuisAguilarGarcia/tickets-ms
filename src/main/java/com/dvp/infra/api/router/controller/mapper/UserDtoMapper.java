package com.dvp.infra.api.router.controller.mapper;

import com.dvp.infra.api.router.controller.dto.request.CreateAndUpdateUserDto;
import com.dvp.domain.entities.User;
import org.mapstruct.Mapper;

import java.util.Objects;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    static User toEntity(CreateAndUpdateUserDto createAndUpdateUserDto){
        return updateToEntity(createAndUpdateUserDto, null);
    }

    static User updateToEntity(CreateAndUpdateUserDto createAndUpdateUserDto, Long userId){
        if(Objects.isNull(createAndUpdateUserDto)){
            return User.builder().build();
        }

        return User.builder()
                .userId(userId)
                .firstName(createAndUpdateUserDto.getFirstName())
                .lastName(createAndUpdateUserDto.getLastName()).build();
    }
}

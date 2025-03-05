package com.dvp.app.mapper;

import com.dvp.infra.adapter.db.entites.UsersDto;
import com.dvp.infra.api.router.controller.dto.response.user.UserDto;
import com.dvp.domain.entities.User;
import com.dvp.infra.api.router.controller.dto.response.user.UserDataDto;
import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface UserMapper {

    static List<UserDataDto> toDto(List<User> user){
        if (user.isEmpty()) {
            new ArrayList<>();
        }
        return user.stream().map(u -> UserDataDto.builder().userId(u.getUserId())
                .createAt(u.getCreateAt())
                .updateAt(u.getUpdateAt())
                .firstName(u.getFirstName())
                .lastName(u.getLastName()).build()).toList();
    }

    static UserDto toUserDto(List<UserDataDto> userData, String msg){
        UserDto userDto =  UserDto.builder().data(userData).build();
        userDto.setCode(String.valueOf(HttpStatus.OK.value()));
        userDto.setMessage(msg);

        return userDto;
    }

    static UsersDto toUsersEntityDto(User user) {
        if (Objects.isNull(user) ){
            return UsersDto.builder().build();
        }

        return  UsersDto.builder()
                .userId(user.getUserId())
                .createAt(Objects.isNull(user.getCreateAt()) ? Timestamp.valueOf(LocalDateTime.now()) : user.getCreateAt())
                .updateAt(Timestamp.valueOf(LocalDateTime.now()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName()).build();
    }
}

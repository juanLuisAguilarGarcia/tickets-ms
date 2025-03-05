package com.dvp.infra.adapter.db.mapper;

import com.dvp.domain.entities.User;
import com.dvp.infra.adapter.db.entites.UsersDto;
import com.dvp.infra.api.router.controller.dto.response.user.UserDataDto;
import org.mapstruct.Mapper;

import java.util.*;

@Mapper(componentModel = "spring")
public interface MapperUsersEntity {

    static User toUser(UsersDto user){
        if (Objects.isNull(user)) {
            return User.builder().build();
        }

        return User.builder()
                .userId(user.getUserId())
                .createAt(user.getCreateAt())
                .updateAt(user.getUpdateAt())
                .firstName(user.getFirstName())
                .lastName(user.getLastName()).build();
    }

    static User toUserFromOptional(Optional<UsersDto> user){
        if (user.isEmpty()) {
            return User.builder().build();
        }

        UsersDto u = user.get();

        return User.builder()
                .userId(u.getUserId())
                .createAt(u.getCreateAt())
                .updateAt(u.getUpdateAt())
                .firstName(u.getFirstName())
                .lastName(u.getLastName()).build();
    }

    static List<User> toUserList(Iterator<UsersDto> usersDto){
        List<User> users = new ArrayList<>();

        while(usersDto.hasNext()) {
            UsersDto userDto = usersDto.next();
            users.add(User.builder().userId(userDto.getUserId())
                    .createAt(userDto.getCreateAt())
                    .updateAt(userDto.getUpdateAt())
                    .firstName(userDto.getFirstName())
                    .lastName(userDto.getLastName()).build());
        }

        return users;
    }
}

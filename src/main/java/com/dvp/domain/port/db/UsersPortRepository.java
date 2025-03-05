package com.dvp.domain.port.db;

import com.dvp.domain.entities.User;
import com.dvp.infra.adapter.db.entites.UsersDto;

import java.util.List;

public interface UsersPortRepository {
    User save(UsersDto user);
    User getUserById(Long userId);
    List<User> getUsers();
    User getUserByName(String fistName, String LastName);
    User updateUser(UsersDto user );
}

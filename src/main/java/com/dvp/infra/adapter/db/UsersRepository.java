package com.dvp.infra.adapter.db;

import com.dvp.domain.port.db.UsersPortRepository;
import com.dvp.infra.adapter.db.entites.UsersDto;
import com.dvp.infra.adapter.db.jpa.UsersJpaRepository;
import com.dvp.infra.adapter.db.mapper.MapperUsersEntity;
import com.dvp.domain.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsersRepository implements UsersPortRepository {

    @Autowired
    private UsersJpaRepository usersJpaRepository;

    @Transactional
    public User save(UsersDto userToSave){
        return MapperUsersEntity.toUser(usersJpaRepository.save(userToSave));
    }

    public User getUserById(Long userId){
        return MapperUsersEntity.toUserFromOptional(usersJpaRepository.findById(userId));
    }

    public List<User> getUsers() {
        return MapperUsersEntity.toUserList(usersJpaRepository.findAll().iterator());
    }

    @Transactional
    public User updateUser(UsersDto userToUpdate){
        return MapperUsersEntity.toUser(usersJpaRepository.save(userToUpdate));
    }

    public User getUserByName(String fistName, String lastName){
        return MapperUsersEntity.toUser(usersJpaRepository.findByFirstNameAndLastName(fistName, lastName));
    }
}

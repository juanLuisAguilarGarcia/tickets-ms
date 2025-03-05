package com.dvp.app;

import com.dvp.app.mapper.UserMapper;
import com.dvp.domain.entities.User;
import com.dvp.domain.port.db.UsersPortRepository;
import com.dvp.infra.api.router.controller.dto.response.user.UserDto;
import com.dvp.infra.api.router.controller.error.exception.UserException;
import com.dvp.infra.api.router.facade.UsersFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.dvp.app.ServiceConsts.*;

@Slf4j
@Service
public class UsersService implements UsersFacade {

    @Autowired
    private UsersPortRepository usersPortRepository;

    public UserDto createUser(User userToSave) throws UserException {
        try{
            validUserBeforeCreate(userToSave);

            User user = usersPortRepository.save(UserMapper.toUsersEntityDto(userToSave));

            log.info(String.format(MSG_PROCESS_SERVICE, "created", "name: ", userToSave.getFirstName()));
            return UserMapper.toUserDto(UserMapper.toDto(Collections.singletonList(user)), MSG_USER_CREATED);
        } catch(DataAccessException ex){
            log.error(String.format(MSG_ERROR_PROCESS_SERVICE, "created",  "name: ", userToSave.getFirstName(),
                    ex.getMessage()));
            throw new UserException("422-1", ex.getMessage());
        }
    }

    public UserDto getUserById(Long userId) throws UserException {
        try{
            User user = existsUser(userId);
            log.info(String.format(MSG_PROCESS_SERVICE, "getById", "userId: ", userId));
            return UserMapper.toUserDto(UserMapper.toDto(Collections.singletonList(user)), MSG_USER_GET);
        } catch(DataAccessException ex){
            log.error(String.format(MSG_ERROR_PROCESS_SERVICE, "getById",  "userId: ", userId,
                    ex.getMessage()));
            throw new UserException("422-2", ex.getMessage());
        }

    }

    public UserDto getUsers() throws UserException {
        try{
            List<User> user = usersPortRepository.getUsers();
            log.info("get all user service");
            return UserMapper.toUserDto(UserMapper.toDto(user), MSG_USER_GET);
        } catch(DataAccessException ex){
            log.error(String.format("Error to get all users: %s",
                    ex.getMessage()));
            throw new UserException("422-3", ex.getMessage());
        }

    }

    public UserDto updateUser(User userToUpdate) throws UserException {
        try{
            User userExists = existsUser(userToUpdate.getUserId());

            userToUpdate.setCreateAt(userExists.getCreateAt());
            User user = usersPortRepository.updateUser(UserMapper.toUsersEntityDto(userToUpdate));
            log.info(String.format(MSG_PROCESS_SERVICE, "update", "userId: ", userToUpdate.getUserId()));
            return UserMapper.toUserDto(UserMapper.toDto(Collections.singletonList(user)), MSG_USER_UPDATE);
        } catch(DataAccessException ex){
            log.error(String.format(MSG_ERROR_PROCESS_SERVICE, "update",  "userId: ", userToUpdate.getUserId(),
                    ex.getMessage()));
            throw new UserException("422-4", ex.getMessage());
        }
    }

    private User existsUser(Long userId) throws UserException {
        User user = usersPortRepository.getUserById(userId);

        if(Objects.isNull(user.getUserId())  || user.getUserId() < 1L) {
            log.error(String.format(MSG_ERROR_PROCESS_SERVICE, "exists",  "userId: ", userId,
                    "User not found"));
            throw new UserException("422-5", "User not found");
        }

        return user;
    }

    private void validUserBeforeCreate(User userToCreate) throws UserException {
        User user = usersPortRepository.getUserByName(
                userToCreate.getFirstName(), userToCreate.getLastName());

        if(!Objects.isNull(user.getUserId()) && user.getUserId() >= 1L ) {
            log.error(String.format(MSG_ERROR_PROCESS_SERVICE, "exists",  "name: ", userToCreate.getFirstName(),
                        "User exists in system."));
                throw new UserException("422-6", "User exists in system.");
        }
    }
}

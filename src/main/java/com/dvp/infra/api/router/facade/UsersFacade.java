package com.dvp.infra.api.router.facade;

import com.dvp.infra.api.router.controller.dto.response.user.UserDto;
import com.dvp.infra.api.router.controller.error.exception.UserException;
import com.dvp.domain.entities.User;

public interface UsersFacade {

    public UserDto createUser(User user) throws UserException;

    public UserDto getUserById(Long userId) throws UserException;

    public UserDto getUsers() throws UserException;

    public UserDto updateUser(User user) throws UserException;
}

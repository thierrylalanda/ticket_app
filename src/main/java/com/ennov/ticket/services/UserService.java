/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ennov.ticket.services;

import com.ennov.ticket.exceptions.UserNotFoundException;
import dtos.TicketDto;
import dtos.UserDto;
import java.util.List;

/**
 *
 * @author LALANDA
 */
public interface UserService {

    UserDto createUser(UserDto user) throws UserNotFoundException;

    UserDto updateUser(UserDto user);

    int deleteUser(int userId);

    UserDto getUserById(int userId) throws UserNotFoundException;

    UserDto getUserByEmail(String userEmail) throws UserNotFoundException;

    List<UserDto> getAllUser();

    List<TicketDto> getAllTicketByUser(int userId);
}

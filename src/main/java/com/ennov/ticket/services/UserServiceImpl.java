/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ennov.ticket.services;

import com.ennov.ticket.Entity.User;
import com.ennov.ticket.dao.UserRepository;
import com.ennov.ticket.exceptions.UserNotFoundException;
import dtos.TicketDto;
import dtos.UserDto;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LALANDA
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public UserDto createUser(UserDto user) throws UserNotFoundException {
        //verifier si l'utilisateur existe deja 
        boolean userExists = userRepository
                .findByEmail(user.getEmail())
                .isPresent();

        if (userExists) {
            throw new UserNotFoundException("Adresse mail non disponible");
        }
        User userCreate = userRepository.save(ticketMapper.fromUserDto(user));
        return ticketMapper.fromUser(userCreate);
    }

    @Override
    public UserDto updateUser(UserDto user) {
        User userUpdate = userRepository.save(ticketMapper.fromUserDto(user));
        return ticketMapper.fromUser(userUpdate);
    }

    @Override
    public int deleteUser(int userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UserDto getUserById(int userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return ticketMapper.fromUser(user.get());
        }
        throw new UserNotFoundException("Utilisateur non existant");
    }

    @Override
    public UserDto getUserByEmail(String userEmail) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if (user.isPresent()) {
            return ticketMapper.fromUser(user.get());
        }
        throw new UserNotFoundException("Utilisateur non existant");
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDTOS = users.stream()
                .map(user -> ticketMapper.fromUser(user))
                .collect(Collectors.toList());
        return userDTOS;

    }

    @Override
    public List<TicketDto> getAllTicketByUser(int userId) {
        return ticketService.getAllTicketUser(userId);
    }
}

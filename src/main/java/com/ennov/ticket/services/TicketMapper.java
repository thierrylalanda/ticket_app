/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ennov.ticket.services;

import com.ennov.ticket.Entity.Ticket;
import com.ennov.ticket.Entity.User;
import dtos.TicketDto;
import dtos.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author LALANDA
 */
@Service
public class TicketMapper {

    public Ticket fromTicketDto(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        BeanUtils.copyProperties(ticketDto, ticket);
        if (ticketDto.getUserAssigned() != null) {
            ticket.setUserAssigned(fromUserDto(ticketDto.getUserAssigned()));
        }
        return ticket;
    }

    public TicketDto fromTicket(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        BeanUtils.copyProperties(ticket, ticketDto);
        if (ticket.getUserAssigned() != null) {
            ticketDto.setUserAssigned(fromUser(ticket.getUserAssigned()));
        }
        return ticketDto;
    }

    public User fromUserDto(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }

    public UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }
}

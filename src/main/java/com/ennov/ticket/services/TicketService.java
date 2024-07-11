/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ennov.ticket.services;

import com.ennov.ticket.exceptions.TicketNotFoundException;
import dtos.TicketDto;
import java.util.List;

/**
 *
 * @author LALANDA
 */
public interface TicketService {

    TicketDto createTicket(TicketDto ticketDto);

    TicketDto updateTicket(TicketDto ticketDto);

    boolean deleteTicket(int ticketId) throws TicketNotFoundException;

    TicketDto getTicketById(int ticketId) throws TicketNotFoundException;

    List<TicketDto> getAllTicket();

    List<TicketDto> getAllTicketUser(int userId);

    TicketDto assignTicketToUser(int userId, int ticketId) throws TicketNotFoundException;
}

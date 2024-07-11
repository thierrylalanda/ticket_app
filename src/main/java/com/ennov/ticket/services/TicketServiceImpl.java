/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ennov.ticket.services;

import com.ennov.ticket.Entity.Ticket;
import com.ennov.ticket.dao.TicketRepository;
import com.ennov.ticket.enums.Statut;
import com.ennov.ticket.exceptions.TicketNotFoundException;
import dtos.TicketDto;
import jakarta.transaction.Transactional;
import java.util.List;
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
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;
    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public TicketDto createTicket(TicketDto ticketDto) {
        Ticket ticket = ticketRepository.save(ticketMapper.fromTicketDto(ticketDto));
        return ticketMapper.fromTicket(ticket);
    }

    @Override
    public TicketDto updateTicket(TicketDto ticketDto) {
        Ticket ticket = ticketRepository.save(ticketMapper.fromTicketDto(ticketDto));

        return ticketMapper.fromTicket(ticket);
    }

    @Override
    public boolean deleteTicket(int ticketId) throws TicketNotFoundException {
        try {
            TicketDto ticket = getTicketById(ticketId);
            if (ticket.getStatut() == Statut.PENDING) {
                ticketRepository.deleteById(ticketId);
                return true;
            }

        } catch (TicketNotFoundException ex) {
            throw new TicketNotFoundException(ex.getMessage());
        }
        return false;
    }

    @Override
    public TicketDto getTicketById(int ticketId) throws TicketNotFoundException {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException("Ticket not found"));;
        return ticketMapper.fromTicket(ticket);

    }

    @Override
    public List<TicketDto> getAllTicket() {
        List<Ticket> tickets = ticketRepository.findAll();
        List<TicketDto> ticketDTOS = tickets.stream()
                .map(ticket -> ticketMapper.fromTicket(ticket))
                .collect(Collectors.toList());
        return ticketDTOS;
    }

    @Override
    public List<TicketDto> getAllTicketUser(int userId) {
        List<Ticket> tickets = ticketRepository.getAllTickeByUser(userId);
        List<TicketDto> ticketDTOS = tickets.stream()
                .map(ticket -> ticketMapper.fromTicket(ticket))
                .collect(Collectors.toList());
        return ticketDTOS;

    }

    @Override
    public TicketDto assignTicketToUser(int userId, int ticketId) throws TicketNotFoundException {

        try {
            ticketRepository.assigneTicketToUser(userId, ticketId);
            return getTicketById(ticketId);
        } catch (TicketNotFoundException ex) {
            throw new TicketNotFoundException(ex.getMessage());
        }
    }
}

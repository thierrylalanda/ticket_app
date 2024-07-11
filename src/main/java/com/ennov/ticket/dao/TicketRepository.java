/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ennov.ticket.dao;

import com.ennov.ticket.Entity.Ticket;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author LALANDA
 */
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("select t from Ticket t where t.userAssigned.id = :userId")
    List<Ticket> getAllTickeByUser(@Param("userId") int userId);
    
    @Modifying
    @Transactional
    @Query("UPDATE Ticket t SET t.userAssigned = (SELECT u FROM User u WHERE u.id = :userId) WHERE t.id = :ticketId")
    void assigneTicketToUser(@Param("userId") int userId, @Param("ticketId") int ticketId);
    
    
}

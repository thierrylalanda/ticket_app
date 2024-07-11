/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ennov.ticket.controllers;

import com.ennov.ticket.exceptions.TicketNotFoundException;
import com.ennov.ticket.services.TicketService;
import dtos.TicketDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import payload.ResponseData;

/**
 *
 * @author LALANDA
 */
@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketServiceImpl;

    @Operation(summary = "Récupérer tous les tickets.")
    @ApiResponse(responseCode = "200",description = "Liste des tickets trouvéés et retournées")
    @GetMapping()
    public ResponseEntity<ResponseData> list() {
        ResponseData response = ResponseData.builder().success(true).data(ticketServiceImpl.getAllTicket()).build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Récupérer un ticket par son ID")
    @ApiResponse(responseCode = "200",description = "Tickets trouvé et retourné")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        ResponseData response;
        try {
            response = ResponseData.builder().success(true).data(ticketServiceImpl.getTicketById(id)).build();

        } catch (TicketNotFoundException ex) {
            response = ResponseData.builder().success(false).message(ex.getMessage()).build();
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Mettre à jour un ticket existant.")
    @ApiResponse(responseCode = "200",description = "Tickets mis à jour avec success")
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable int id, @RequestBody TicketDto ticketRequest) {
        TicketDto user = ticketServiceImpl.updateTicket(ticketRequest);
        ResponseData response = ResponseData.builder().success(true).data(user).build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Créer un nouveau ticket.")
    @ApiResponse(responseCode = "200",description = "Tickets créé avec success")
    @PostMapping
    public ResponseEntity<?> post(@RequestBody TicketDto ticketRequest) {
        TicketDto user;
        ResponseData response;
        user = ticketServiceImpl.createTicket(ticketRequest);
        response = ResponseData.builder().success(true).data(user).build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Supprimer un ticket par son ID.")
    @ApiResponse(responseCode = "200",description = "Tickets supprimé avec success")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        ResponseData response;
        try {
            boolean isDeleted = ticketServiceImpl.deleteTicket(id);
            response = ResponseData.builder().success(isDeleted).build();
        } catch (TicketNotFoundException ex) {
            response = ResponseData.builder().success(false).message(ex.getMessage()).build();
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Assigner un ticket à un utilisateur.")
    @ApiResponse(responseCode = "200",description = "Tickets assigné avec success")
    @PutMapping("/{id}/assign/{userId}")
    public ResponseEntity<?> assignTicket(@PathVariable int id, @PathVariable int userId, @RequestBody TicketDto ticketRequest) {
        TicketDto user;
        ResponseData response;
        try {
            user = ticketServiceImpl.assignTicketToUser(id, userId);
            response = ResponseData.builder().success(true).data(user).build();
        } catch (TicketNotFoundException ex) {
            response = ResponseData.builder().success(false).message(ex.getMessage()).build();
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public Object handleError(HttpServletRequest req, Exception ex) {
        Object errorObject = new Object();
        return errorObject;
    }

}

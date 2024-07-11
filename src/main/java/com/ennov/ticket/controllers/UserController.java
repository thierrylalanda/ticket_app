/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ennov.ticket.controllers;

import com.ennov.ticket.exceptions.UserNotFoundException;
import com.ennov.ticket.services.UserService;
import dtos.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import payload.ResponseData;

/**
 *
 * @author LALANDA
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userServiceImpl;
    
    @Operation(summary = "Récupérer tous les utilisateurs")
    @ApiResponse(responseCode = "200",description = "Utilisateur correctement retournés")
    @GetMapping()
    public ResponseEntity<ResponseData> list() {
        ResponseData response = ResponseData.builder().success(true).data(userServiceImpl.getAllUser()).build();
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Rechercher un utilisateur en fonction de son identifiant")
    @ApiResponse(responseCode = "200",description = "Utilisateur trouvé et retourne")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> get(@PathVariable Integer id) {
        ResponseData response;
        try {
            response = ResponseData.builder().success(true).data(userServiceImpl.getUserById(id)).build();
            
        } catch (UserNotFoundException ex) {
            response = ResponseData.builder().success(false).message(ex.getMessage()).build();
        }
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Modifier un utilisateur")
    @ApiResponse(responseCode = "200",description = "Mis à jour effectuée avec success",useReturnTypeSchema = true)
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData> put(@PathVariable Integer id, @RequestBody UserDto userRequest) {
        UserDto user = userServiceImpl.updateUser(userRequest);
        ResponseData response = ResponseData.builder().success(true).data(user).build();
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Créer un utilisateur")
    @ApiResponse(responseCode = "200",description = "Utilisateur correctement crée")
    @PostMapping
    public ResponseEntity<ResponseData> post(@RequestBody UserDto userRequest) {
        UserDto user;
        ResponseData response;
        try {
            user = userServiceImpl.createUser(userRequest);
            response = ResponseData.builder().success(true).data(user).build();
        } catch (UserNotFoundException ex) {
            response = ResponseData.builder().success(false).message(ex.getMessage()).build();
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Récupérer les tickets assignés à l'utilisateur")
    @ApiResponse(responseCode = "200",description = "Liste correctement retournées")
   @GetMapping("/{id}/ticket")
    public ResponseEntity<ResponseData> getTicketUser(@PathVariable int id) {
        ResponseData response = ResponseData.builder().success(true).data(userServiceImpl.getAllTicketByUser(id)).build();
        return ResponseEntity.ok(response);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public Object handleError(HttpServletRequest req, Exception ex) {
        Object errorObject = new Object();
        return errorObject;
    }
    
}

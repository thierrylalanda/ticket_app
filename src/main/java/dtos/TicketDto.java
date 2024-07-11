/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import com.ennov.ticket.enums.Statut;
import lombok.Data;

/**
 *
 * @author LALANDA
 */
@Data
public class TicketDto {
    private Integer id;
    private String title;
    private String description;
    private UserDto userAssigned;
    private Statut statut;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ennov.ticket.dao;

import com.ennov.ticket.Entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author LALANDA
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}

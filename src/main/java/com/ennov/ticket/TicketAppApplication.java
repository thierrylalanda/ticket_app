package com.ennov.ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.ennov.ticket.Entity")
public class TicketAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketAppApplication.class, args);
	}

}

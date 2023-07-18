package com.currencies.mainpackage;

import com.currencies.mainpackage.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, String> {
}

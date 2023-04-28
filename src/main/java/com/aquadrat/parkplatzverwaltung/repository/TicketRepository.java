package com.aquadrat.parkplatzverwaltung.repository;

import com.aquadrat.parkplatzverwaltung.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}

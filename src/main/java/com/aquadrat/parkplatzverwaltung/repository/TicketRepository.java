package com.aquadrat.parkplatzverwaltung.repository;

import com.aquadrat.parkplatzverwaltung.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findAll();
}

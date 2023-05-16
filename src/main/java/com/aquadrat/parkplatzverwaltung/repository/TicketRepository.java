package com.aquadrat.parkplatzverwaltung.repository;

import com.aquadrat.parkplatzverwaltung.model.ParkSlot;
import com.aquadrat.parkplatzverwaltung.model.Ticket;
import com.aquadrat.parkplatzverwaltung.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findAll();
    Ticket findTicketByVehicleAndIsValid(Vehicle vehicle, boolean isValid);

    List<Ticket> findTicketsByParkSlot(ParkSlot parkSlot);
    List<Ticket> findAllByIsValid(boolean valid);
}

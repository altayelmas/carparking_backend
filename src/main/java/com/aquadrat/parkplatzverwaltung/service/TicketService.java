package com.aquadrat.parkplatzverwaltung.service;

import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.Ticket;
import com.aquadrat.parkplatzverwaltung.model.Vehicle;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketCreateRequest;
import com.aquadrat.parkplatzverwaltung.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
    public Ticket createTicket(TicketCreateRequest request) {
        /*Ticket ticket = new Ticket();
        ticket.setEntryDate(new Date());
        Vehicle vehicle = new Vehicle(request.getLicencePlate(), request.getVehicleType(), null);
        ticket.setVehicle(vehicle);
        ParkingLot parkingLot = new RestTemplate().getForObject("http://localhost:8080/parking-lot/getAll/" + request.getLotID(), ParkingLot.class);
        ticket.setParkinglot(parkingLot);
        ticket.setParkSlot(parkingLot.getParkSlots().get(request.getSlotID()));
        return ticketRepository.save(ticket);*/
        return new Ticket();
    }
}

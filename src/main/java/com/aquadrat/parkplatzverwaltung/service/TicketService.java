package com.aquadrat.parkplatzverwaltung.service;

import com.aquadrat.parkplatzverwaltung.exception.NotAvailableException;
import com.aquadrat.parkplatzverwaltung.exception.NotFoundException;
import com.aquadrat.parkplatzverwaltung.mapper.TicketMapper;
import com.aquadrat.parkplatzverwaltung.model.ParkSlot;
import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.Ticket;
import com.aquadrat.parkplatzverwaltung.model.Vehicle;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketCreateRequest;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketDto;
import com.aquadrat.parkplatzverwaltung.repository.ParkingLotRepository;
import com.aquadrat.parkplatzverwaltung.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final TicketMapper ticketMapper;

    public TicketService(TicketRepository ticketRepository, ParkingLotRepository parkingLotRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.ticketMapper = ticketMapper;
    }

    private ParkSlot findParkSlot(List<ParkSlot> parkSlotList, Integer slotID) {
        for (ParkSlot parkSlot: parkSlotList) {
            if (parkSlot.getSlotID().equals(slotID)) {
                return parkSlot;
            }
        }
        return null;
    }
    public TicketDto createTicket(TicketCreateRequest request) {
        Ticket ticket = new Ticket();
        ticket.setEntryDate(new Date());
        Vehicle vehicle = new Vehicle(request.getLicencePlate(), request.getVehicleType(), ticket);
        ticket.setVehicle(vehicle);
        Optional<ParkingLot> parkingLot = parkingLotRepository.findById(request.getLotID());
        if (parkingLot.isEmpty()) {
            throw new NotFoundException("Parking Lot not found");
        }
        ParkingLot p = parkingLot.get();
        ticket.setParkinglot(p);
        ParkSlot parkSlot = findParkSlot(p.getParkSlots(), request.getSlotID());
        if (!parkSlot.isAvailable()) {
            throw new NotAvailableException("Park Slot not available");
        }
        ticket.setParkSlot(parkSlot);
        parkSlot.setAvailable(false);
        return ticketMapper.convertToDto(ticketRepository.save(ticket));
    }
}

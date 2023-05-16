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
import com.aquadrat.parkplatzverwaltung.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final VehicleRepository vehicleRepository;
    private final TicketMapper ticketMapper;

    public TicketService(TicketRepository ticketRepository, ParkingLotRepository parkingLotRepository, VehicleRepository vehicleRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.vehicleRepository = vehicleRepository;
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
        Optional<ParkingLot> parkingLot = parkingLotRepository.findById(request.getLotID());
        if (parkingLot.isEmpty()) {
            throw new NotFoundException("Parking Lot not found");
        }
        ParkingLot p = parkingLot.get();
        ticket.setParkinglot(p);
        ParkSlot parkSlot = findParkSlot(p.getParkSlots(), request.getSlotID());

        if (parkSlot == null) {
            throw new NotFoundException("Park Slot not found");
        }

        if (!parkSlot.isAvailable()) {
            throw new NotAvailableException("Park Slot not available");
        }

        Optional<Vehicle> maybeVehicle = vehicleRepository.findById(request.getLicencePlate());
        Vehicle vehicle;
        if (maybeVehicle.isPresent()) {
            vehicle = maybeVehicle.get();
        } else {
            vehicle = new Vehicle(request.getLicencePlate(), request.getVehicleType(), new ArrayList<>());
        }

        Ticket ticketOfVehicle = ticketRepository.findTicketByVehicleAndIsValid(vehicle, true);

        if (ticketOfVehicle != null) {
            throw new NotAvailableException("This vehicle is already in Parking Lot");
        }

        ticket.setParkSlot(parkSlot);
        ticket.setValid(true);
        vehicle.getTicketList().add(ticket);
        ticket.setVehicle(vehicle);
        parkSlot.setAvailable(false);
        p.getTickets().add(ticket);
        TicketDto ticketDto = ticketMapper.convertToDto(ticketRepository.save(ticket));
        vehicleRepository.save(vehicle);
        return ticketDto;
    }

    public List<TicketDto> getAll() {
        List<TicketDto> ticketDtoList = ticketMapper.ticketListToTicketDtoList(ticketRepository.findAll());
        return ticketDtoList;
    }

    public TicketDto parkOut(Integer ticketID) {
        Optional<Ticket> ticket = ticketRepository.findById(ticketID);

        if (ticket.isEmpty()) {
            throw new NotFoundException("Ticket not found");
        }
        Ticket oldTicket = ticket.get();

        if (!oldTicket.isValid()) {
            throw new NotAvailableException("This ticket is not valid.");
        }

        Ticket newTicket = Ticket.builder()
                .ticketID(oldTicket.getTicketID())
                .entryDate(oldTicket.getEntryDate())
                .exitDate(new Date())
                .isValid(false)
                .vehicle(oldTicket.getVehicle())
                .parkSlot(oldTicket.getParkSlot())
                .parkinglot(oldTicket.getParkinglot())
                .build();

        newTicket.getParkSlot().setAvailable(true);

        // TODO - Control the codes that uses the vehicle class
        newTicket.getVehicle().getTicketList().remove(oldTicket);
        newTicket.getVehicle().getTicketList().add(newTicket);

        return ticketMapper.convertToDto(ticketRepository.save(newTicket));
    }
}

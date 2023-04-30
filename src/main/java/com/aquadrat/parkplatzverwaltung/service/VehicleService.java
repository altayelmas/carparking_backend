package com.aquadrat.parkplatzverwaltung.service;

import com.aquadrat.parkplatzverwaltung.controller.VehicleController;
import com.aquadrat.parkplatzverwaltung.mapper.VehicleMapper;
import com.aquadrat.parkplatzverwaltung.model.Ticket;
import com.aquadrat.parkplatzverwaltung.model.Vehicle;
import com.aquadrat.parkplatzverwaltung.model.dto.VehicleDto;
import com.aquadrat.parkplatzverwaltung.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {
    private final TicketRepository ticketRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleService(TicketRepository ticketRepository, VehicleMapper vehicleMapper) {
        this.ticketRepository = ticketRepository;
        this.vehicleMapper = vehicleMapper;
    }

    public List<VehicleDto> getAll() {
        List<Ticket> ticketList = ticketRepository.findAll();
        List<VehicleDto> vehicleDtoList = new ArrayList<>();

        for (Ticket ticket: ticketList) {
            Vehicle vehicle = ticket.getVehicle();
            vehicleDtoList.add(vehicleMapper.convertToDto(vehicle));
        }
        return vehicleDtoList;

    }
}


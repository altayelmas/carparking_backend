package com.aquadrat.parkplatzverwaltung.mapper;

import com.aquadrat.parkplatzverwaltung.model.Ticket;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketDto;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public TicketDto convertToDto(Ticket ticket) {
        return TicketDto.builder()
                .ticketID(ticket.getTicketID())
                .slotID(ticket.getParkSlot().getSlotID())
                .entryDate(ticket.getEntryDate())
                .licencePlate(ticket.getVehicle().getLicencePlate())
                .lotID(ticket.getParkinglot().getLotID())
                .build();
    }
}

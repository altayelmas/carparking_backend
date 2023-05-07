package com.aquadrat.parkplatzverwaltung.mapper;

import com.aquadrat.parkplatzverwaltung.model.Ticket;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TicketMapper {
    public TicketDto convertToDto(Ticket ticket) {
        return TicketDto.builder()
                .ticketID(ticket.getTicketID())
                .slotID(ticket.getParkSlot().getSlotID())
                .entryDate(ticket.getEntryDate())
                .exitDate(ticket.getExitDate())
                .isValid(ticket.isValid())
                .licencePlate(ticket.getVehicle().getLicencePlate())
                .lotID(ticket.getParkinglot().getLotID())
                .build();
    }

    public List<TicketDto> ticketListToTicketDtoList(List<Ticket> ticketList) {
        List<TicketDto> ticketDtoList = new ArrayList<>();
        for (Ticket ticket: ticketList) {
            ticketDtoList.add(convertToDto(ticket));
        }
        return ticketDtoList;
    }
}

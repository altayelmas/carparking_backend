package com.aquadrat.parkplatzverwaltung.mapper;

import com.aquadrat.parkplatzverwaltung.model.Ticket;
import com.aquadrat.parkplatzverwaltung.model.dto.TicketDto;
import com.aquadrat.parkplatzverwaltung.support.TicketTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketMapperTest {

    private TicketMapper ticketMapper;

    @BeforeEach
    public void setUp() {
        ticketMapper = new TicketMapper();
    }

    @Test
    public void convertToDtoTest() {
        Ticket ticket = TicketTestSupport.generateTicket(1);
        TicketDto ticketDto = TicketTestSupport.generateTicketDto(1);

        TicketDto result = ticketMapper.convertToDto(ticket);
        assertEquals(ticketDto, result);
    }

    @Test
    public void ticketListToTicketDtoListTest() {
        List<Ticket> ticketList = TicketTestSupport.generateTicketList(3);
        List<TicketDto> ticketDtoList = TicketTestSupport.generateTicketDtoList(3);

        List<TicketDto> result = ticketMapper.ticketListToTicketDtoList(ticketList);
        assertEquals(ticketDtoList, result);
    }
}

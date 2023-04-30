package com.aquadrat.parkplatzverwaltung.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketResponse {
    private TicketDto ticketDto;
    private boolean isSuccess;
    private String message;

}

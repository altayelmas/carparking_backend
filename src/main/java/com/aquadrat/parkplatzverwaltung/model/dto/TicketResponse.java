package com.aquadrat.parkplatzverwaltung.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private TicketDto ticketDto;
    private boolean isSuccess;
    private String message;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        TicketResponse ticketResponse = (TicketResponse) obj;
        if ((this.ticketDto == null && ticketResponse.getTicketDto() == null) && this.isSuccess == ticketResponse.isSuccess() && this.message.equals(ticketResponse.getMessage())) {
            return true;
        }

        if (this.ticketDto.equals(ticketResponse.getTicketDto()) && this.isSuccess == ticketResponse.isSuccess() && this.message.equals(ticketResponse.getMessage())) {
            return true;
        }
        return false;
    }
}

package com.aquadrat.parkplatzverwaltung.model.dto;

import com.aquadrat.parkplatzverwaltung.model.Vehicle;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class TicketDto {

    private Integer ticketID;
    private Date entryDate;
    private Date exitDate;
    private boolean isValid;
    private String licencePlate;
    private Integer slotID;
    private Integer lotID;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        TicketDto ticketDto = (TicketDto) obj;
        if (this.ticketID == ticketDto.getTicketID() && this.isValid == ticketDto.isValid() && this.licencePlate.equals(ticketDto.getLicencePlate()) && this.slotID == ticketDto.getSlotID() && this.lotID == ticketDto.getLotID()) {
            return true;
        }
        return false;
    }
}

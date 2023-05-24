package com.aquadrat.parkplatzverwaltung.model;

import com.aquadrat.parkplatzverwaltung.repository.TicketRepository;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer slotID;
    private boolean isAvailable;
    @ManyToOne
    @JoinColumn(name = "parkinglot_id", referencedColumnName = "lotID")
    private ParkingLot parkinglot;
    @OneToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "ticketID")
    private Ticket ticket;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        if (this.getSlotID() == ((ParkSlot) obj).getSlotID()) {
            return true;
        }
        return false;
    }

}

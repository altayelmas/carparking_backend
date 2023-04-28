package com.aquadrat.parkplatzverwaltung.model;

import com.aquadrat.parkplatzverwaltung.repository.TicketRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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

}

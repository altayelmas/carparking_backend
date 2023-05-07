package com.aquadrat.parkplatzverwaltung.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketID;
    private Date entryDate;
    private Date exitDate;
    private boolean isValid;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "licence_plate", referencedColumnName = "licencePlate")
    private Vehicle vehicle;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ParkSlot parkSlot;
    @ManyToOne
    @JoinColumn(name = "parkinglot_id", referencedColumnName = "lotID")
    private ParkingLot parkinglot;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        // TODO - Obje castına gerek var mı?
        if (this.getTicketID().equals(((Ticket) obj).getTicketID())) {
            return true;
        }
        return false;
    }
}

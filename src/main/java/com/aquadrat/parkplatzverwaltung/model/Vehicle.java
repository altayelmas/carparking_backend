package com.aquadrat.parkplatzverwaltung.model;

import com.aquadrat.parkplatzverwaltung.model.enums.VehicleType;
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
public class Vehicle {

    @Id
    private String licencePlate;
    @Enumerated
    private VehicleType vehicleType;
    @OneToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "ticketID")
    private Ticket ticket;

}

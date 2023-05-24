package com.aquadrat.parkplatzverwaltung.model;

import com.aquadrat.parkplatzverwaltung.model.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    @Id
    private String licencePlate;
    @Enumerated
    private VehicleType vehicleType;
    @OneToMany
    private List<Ticket> ticketList;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        if (this.getLicencePlate().equals(((Vehicle) obj).getLicencePlate())) {
            return true;
        }
        return false;
    }

    public Vehicle(String licencePlate) {
        this.licencePlate = licencePlate;
    }
}

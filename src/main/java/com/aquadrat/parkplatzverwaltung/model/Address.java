package com.aquadrat.parkplatzverwaltung.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressID;
    private String street;
    private String city;
    private String postCode;
    private String country;
    @OneToOne(mappedBy = "address")
    @JoinColumn(name = "parkinglot_id", referencedColumnName = "lotID")
    private ParkingLot parkingLot;

}

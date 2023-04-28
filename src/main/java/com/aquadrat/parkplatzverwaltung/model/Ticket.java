package com.aquadrat.parkplatzverwaltung.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketID;
    private Date entryDate;
    private Date exitDate;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Vehicle vehicle;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ParkSlot parkSlot;
    @ManyToOne
    @JoinColumn(name = "parkinglot_id", referencedColumnName = "lotID")
    private ParkingLot parkinglot;
}

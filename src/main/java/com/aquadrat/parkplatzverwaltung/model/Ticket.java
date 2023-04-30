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
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Vehicle vehicle;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ParkSlot parkSlot;
    @ManyToOne
    @JoinColumn(name = "parkinglot_id", referencedColumnName = "lotID")
    private ParkingLot parkinglot;
}

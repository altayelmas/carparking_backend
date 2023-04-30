package com.aquadrat.parkplatzverwaltung.repository;

import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Integer> {
    List<ParkingLot> findAll();
}

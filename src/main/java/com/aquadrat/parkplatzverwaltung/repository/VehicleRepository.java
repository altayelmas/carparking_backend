package com.aquadrat.parkplatzverwaltung.repository;

import com.aquadrat.parkplatzverwaltung.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    List<Vehicle> findAll();
}

package com.aquadrat.parkplatzverwaltung.service;

import com.aquadrat.parkplatzverwaltung.mapper.VehicleMapper;
import com.aquadrat.parkplatzverwaltung.model.Vehicle;
import com.aquadrat.parkplatzverwaltung.model.dto.VehicleDto;
import com.aquadrat.parkplatzverwaltung.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    public List<VehicleDto> getAll() {
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        List<VehicleDto> vehicleDtoList = new ArrayList<>();

        for (Vehicle vehicle: vehicleList) {

            if (vehicle.getTicketList().get(vehicle.getTicketList().size() - 1).isValid()) {
                vehicleDtoList.add(vehicleMapper.convertToDto(vehicle));
            }
        }
        return vehicleDtoList;
    }
}


package com.aquadrat.parkplatzverwaltung.mapper;

import com.aquadrat.parkplatzverwaltung.model.Vehicle;
import com.aquadrat.parkplatzverwaltung.model.dto.VehicleDto;
import com.aquadrat.parkplatzverwaltung.support.VehicleTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleMapperTest {

    private VehicleMapper vehicleMapper;

    @BeforeEach
    public void setUp() {
        vehicleMapper = new VehicleMapper();
    }

    @Test
    public void convertToDtoTest() {
        Vehicle vehicle = VehicleTestSupport.generateVehicle();
        VehicleDto vehicleDto = VehicleTestSupport.generateVehicleDto();

        VehicleDto result = vehicleMapper.convertToDto(vehicle);
        assertEquals(vehicleDto, result);
    }
}

package com.aquadrat.parkplatzverwaltung.service;

import com.aquadrat.parkplatzverwaltung.mapper.VehicleMapper;
import com.aquadrat.parkplatzverwaltung.model.*;
import com.aquadrat.parkplatzverwaltung.model.dto.VehicleDto;
import com.aquadrat.parkplatzverwaltung.repository.VehicleRepository;
import com.aquadrat.parkplatzverwaltung.support.VehicleTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class VehicleServiceTest {

    private VehicleService vehicleService;
    private VehicleRepository vehicleRepository;
    private VehicleMapper vehicleMapper;

    @BeforeEach
    public void setUp() {
        vehicleRepository = mock(VehicleRepository.class);
        vehicleMapper = mock(VehicleMapper.class);

        vehicleService = new VehicleService(vehicleRepository, vehicleMapper);
    }

    @Test
    public void getAllTest() {
        List<Vehicle> vehicleList = VehicleTestSupport.generateVehicleList();
        List<VehicleDto> vehicleDtoList = VehicleTestSupport.generateVehicleDtoList();
        when(vehicleRepository.findAll()).thenReturn(vehicleList);
        for (int i = 0; i < 5; i++) {
            when(vehicleMapper.convertToDto(vehicleList.get(i))).thenReturn(vehicleDtoList.get(i));
        }

        List<VehicleDto> result = vehicleService.getAll();
        assertEquals(vehicleDtoList, result);
        verify(vehicleRepository).findAll();

        for (int i = 0; i < 5; i++) {
            verify(vehicleMapper).convertToDto(vehicleList.get(i));
        }
    }
}

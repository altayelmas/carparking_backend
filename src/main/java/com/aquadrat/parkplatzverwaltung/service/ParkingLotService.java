package com.aquadrat.parkplatzverwaltung.service;

import com.aquadrat.parkplatzverwaltung.model.Address;
import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.ParkSlot;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotCreateRequest;
import com.aquadrat.parkplatzverwaltung.repository.ParkingLotRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotService {

    private final ParkingLotRepository lotRepository;

    public ParkingLotService(ParkingLotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }
    public ParkingLot createParkingLot(ParkingLotCreateRequest request) {
        ParkingLot parkingLot = new ParkingLot();
        // TODO - Discuss the Address
        Address address = new Address(null, request.getStreet(), request.getCity(), request.getPostCode(), request.getCountry(), null);
        parkingLot.setName(request.getName());

        List<ParkSlot> parkSlotList = new ArrayList<>();
        for (int i = 0; i < request.getNumberOfSlots(); i++) {
            // TODO - Discuss the parkSlotList
            parkSlotList.add(new ParkSlot(null, true, null, null));
        }
        parkingLot.setParkSlots(parkSlotList);
        parkingLot.setAddress(address);

        return lotRepository.save(parkingLot);
    }

    public List<ParkingLot> getAll() {
        return lotRepository.findAll();
    }

    public ParkingLot getParkingLotById(Integer lotID) {
        ParkingLot parkingLot = lotRepository.findByLotID(lotID);
        return parkingLot;
    }

}

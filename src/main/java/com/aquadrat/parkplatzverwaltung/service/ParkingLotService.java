package com.aquadrat.parkplatzverwaltung.service;

import com.aquadrat.parkplatzverwaltung.exception.NotFoundException;
import com.aquadrat.parkplatzverwaltung.exception.SlotsNotEmptyException;
import com.aquadrat.parkplatzverwaltung.mapper.ParkingLotMapper;
import com.aquadrat.parkplatzverwaltung.model.Address;
import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.ParkSlot;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotCreateRequest;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotDto;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotUpdateRequest;
import com.aquadrat.parkplatzverwaltung.repository.ParkingLotRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotService {

    private final ParkingLotRepository lotRepository;
    private final ParkingLotMapper parkingLotMapper;

    public ParkingLotService(ParkingLotRepository lotRepository, ParkingLotMapper parkingLotMapper) {
        this.lotRepository = lotRepository;
        this.parkingLotMapper = parkingLotMapper;
    }
    public ParkingLotDto createParkingLot(ParkingLotCreateRequest request) {
        ParkingLot parkingLot = new ParkingLot();
        Address address = new Address(null, request.getStreet(), request.getCity(), request.getPostCode(), request.getCountry(), parkingLot);
        parkingLot.setName(request.getName());

        List<ParkSlot> parkSlotList = new ArrayList<>();
        for (int i = 0; i < request.getNumberOfSlots(); i++) {
            parkSlotList.add(new ParkSlot(null, true, parkingLot, null));
        }
        parkingLot.setParkSlots(parkSlotList);
        parkingLot.setAddress(address);

        return parkingLotMapper.convertToDto(lotRepository.save(parkingLot));
    }

    public List<ParkingLotDto> getAll() {
        List<ParkingLot> parkingLotList = lotRepository.findAll();
        return parkingLotMapper.lotListToLotDtoList(parkingLotList);
    }

    public ParkingLotDto getParkingLotById(Integer lotID) {
        Optional<ParkingLot> parkingLot = lotRepository.findById(lotID);
        if (parkingLot.isEmpty()) {
            throw new NotFoundException("Parking Lot not found");
        }
        return parkingLotMapper.convertToDto(parkingLot.get());
    }

    public boolean deleteParkingLot(Integer lotID) {
        Optional<ParkingLot> parkingLot = lotRepository.findById(lotID);
        if (parkingLot.isEmpty()) {
            return false;
        }
        lotRepository.deleteById(lotID);
        return true;
    }

    public ParkingLotDto updateParkingLot(Integer lotID, ParkingLotUpdateRequest lotUpdateRequest) {
        Optional<ParkingLot> parkingLot = lotRepository.findById(lotID);

        if (parkingLot.isEmpty()) {
            throw new NotFoundException("Parking Lot not found");
        }
        ParkingLot updatedParkingLot = parkingLot.get();
        Address newAddress = Address.builder()
                .addressID(updatedParkingLot.getAddress().getAddressID())
                .street(lotUpdateRequest.getStreet())
                .city(lotUpdateRequest.getCity())
                .postCode(lotUpdateRequest.getPostCode())
                .parkingLot(updatedParkingLot)
                .build();

        updatedParkingLot.setAddress(newAddress);
        updatedParkingLot.setName(lotUpdateRequest.getName());
        Integer currentSize = updatedParkingLot.getParkSlots().size();

        if (currentSize.equals(lotUpdateRequest.getNumberOfSlots())) {
            return parkingLotMapper.convertToDto(lotRepository.save(updatedParkingLot));
        } else if (lotUpdateRequest.getNumberOfSlots() > currentSize) {

            for (int i = 0 ; i < lotUpdateRequest.getNumberOfSlots() - currentSize; i++) {
                updatedParkingLot.getParkSlots().add(new ParkSlot(null, true, updatedParkingLot, null));
            }
            return parkingLotMapper.convertToDto(lotRepository.save(updatedParkingLot));
        } else {
            for (int i = currentSize - 1; i >= lotUpdateRequest.getNumberOfSlots(); i--) {
                if (!updatedParkingLot.getParkSlots().get(i).isAvailable()) {
                    throw new SlotsNotEmptyException("Park Slots are not empty");
                }
            }

            for (int i = currentSize - 1; i >= lotUpdateRequest.getNumberOfSlots(); i--) {
                updatedParkingLot.getParkSlots().remove(i);
            }

            return parkingLotMapper.convertToDto(lotRepository.save(updatedParkingLot));

        }
    }

}

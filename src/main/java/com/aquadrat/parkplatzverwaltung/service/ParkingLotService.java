package com.aquadrat.parkplatzverwaltung.service;

import com.aquadrat.parkplatzverwaltung.exception.NotAvailableException;
import com.aquadrat.parkplatzverwaltung.exception.NotFoundException;
import com.aquadrat.parkplatzverwaltung.exception.SlotsNotEmptyException;
import com.aquadrat.parkplatzverwaltung.mapper.ParkingLotMapper;
import com.aquadrat.parkplatzverwaltung.model.Address;
import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.ParkSlot;
import com.aquadrat.parkplatzverwaltung.model.Ticket;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotCreateRequest;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotDto;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotUpdateRequest;
import com.aquadrat.parkplatzverwaltung.repository.ParkingLotRepository;
import com.aquadrat.parkplatzverwaltung.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotService {

    private final ParkingLotRepository lotRepository;
    private final ParkingLotMapper parkingLotMapper;
    private final TicketRepository ticketRepository;

    public ParkingLotService(ParkingLotRepository lotRepository, ParkingLotMapper parkingLotMapper, TicketRepository ticketRepository) {
        this.lotRepository = lotRepository;
        this.parkingLotMapper = parkingLotMapper;
        this.ticketRepository = ticketRepository;
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

    public ParkingLotDto deleteParkingLot(Integer lotID) {
        Optional<ParkingLot> parkingLot = lotRepository.findById(lotID);
        if (parkingLot.isEmpty()) {
            throw new NotFoundException("Parking Lot not found");
        }

        ParkingLot lot = parkingLot.get();

        for (ParkSlot parkSlot: lot.getParkSlots()) {
            if (!parkSlot.isAvailable()) {
                throw new NotAvailableException("Parking Lot is not empty");
            }
        }

        if (!lot.getTickets().isEmpty()) {
            throw new NotAvailableException("This Parking Lot has tickets");
        }
        lotRepository.deleteById(lotID);
        return parkingLotMapper.convertToDto(parkingLot.get());
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
                .country(lotUpdateRequest.getCountry())
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
                List<Ticket> ticketList = ticketRepository.findTicketsByParkSlot(updatedParkingLot.getParkSlots().get(i));
                if (!ticketList.isEmpty()) {
                    throw new NotAvailableException("There are tickets associated with this slots.");
                }
            }

            for (int i = currentSize - 1; i >= lotUpdateRequest.getNumberOfSlots(); i--) {
                updatedParkingLot.getParkSlots().remove(i);
            }

            return parkingLotMapper.convertToDto(lotRepository.save(updatedParkingLot));

        }
    }

}

package com.aquadrat.parkplatzverwaltung.support;

import com.aquadrat.parkplatzverwaltung.model.Address;
import com.aquadrat.parkplatzverwaltung.model.ParkSlot;
import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.dto.*;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotTestSupport {

    public static ParkingLot generateParkingLot(int lotID) {
        Address address = Address.builder()
                .addressID(1)
                .street("TestStreet")
                .city("TestCity")
                .postCode("TestPostCode")
                .country("TestCountry").build();

        ParkingLot parkingLot = ParkingLot.builder()
                .lotID(lotID)
                .name("TestParkingLot")
                .address(address)
                .build();

        List<ParkSlot> parkSlotList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            parkSlotList.add(ParkSlot.builder()
                    .slotID(i)
                    .isAvailable(true)
                    .parkinglot(parkingLot).build());
        }
        parkingLot.setParkSlots(parkSlotList);
        parkingLot.getAddress().setParkingLot(parkingLot);
        parkingLot.setTickets(new ArrayList<>());
        return parkingLot;
    }

    public static ParkingLotDto generateParkingLotDto(int lotID) {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(lotID);

        AddressDto addressDto = AddressDto.builder()
                .addressID(parkingLot.getAddress().getAddressID())
                .street(parkingLot.getAddress().getStreet())
                .city(parkingLot.getAddress().getCity())
                .postCode(parkingLot.getAddress().getPostCode())
                .country(parkingLot.getAddress().getCountry())
                .lotID(parkingLot.getLotID())
                .build();

        List<ParkSlotDto> parkSlotDtoList = generateParkSlotDtoList();

        return ParkingLotDto.builder()
                .lotID(parkingLot.getLotID())
                .name(parkingLot.getName())
                .addressDto(addressDto)
                .parkSlotDtoList(parkSlotDtoList)
                .build();
    }

    public static List<ParkingLot> generateParkingLotList(int count) {
        List<ParkingLot> parkingLotList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            parkingLotList.add(ParkingLotTestSupport.generateParkingLot(i));
        }
        return parkingLotList;
    }

    public static List<ParkingLotDto> generateParkingLotDtoList(int count) {
        List<ParkingLotDto> parkingLotDtoList = new ArrayList<>();
        List<ParkingLot> parkingLotList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            parkingLotList.add(ParkingLotTestSupport.generateParkingLot(i));
        }

        for (int i = 0; i < count; i++) {

            AddressDto addressDto = AddressDto.builder()
                    .addressID(parkingLotList.get(i).getAddress().getAddressID())
                    .street(parkingLotList.get(i).getAddress().getStreet())
                    .city(parkingLotList.get(i).getAddress().getCity())
                    .postCode(parkingLotList.get(i).getAddress().getPostCode())
                    .country(parkingLotList.get(i).getAddress().getCountry())
                    .lotID(1)
                    .build();

            List<ParkSlotDto> parkSlotDtoList = new ArrayList<>();
            for (ParkSlot parkSlot : parkingLotList.get(i).getParkSlots()) {
                parkSlotDtoList.add(ParkSlotDto.builder()
                        .slotID(parkSlot.getSlotID())
                        .isAvailable(true)
                        .lotID(i)
                        .build());
            }

            parkingLotDtoList.add(ParkingLotDto.builder()
                    .lotID(parkingLotList.get(i).getLotID())
                    .name(parkingLotList.get(i).getName())
                    .addressDto(addressDto)
                    .parkSlotDtoList(parkSlotDtoList)
                    .build());
        }
        return parkingLotDtoList;
    }

    public static ParkSlot generateParkSlot() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        return parkingLot.getParkSlots().get(5);
    }

    public static ParkSlotDto generateParkSlotDto() {
        ParkSlotDto parkSlotDto = ParkSlotDto.builder()
                .slotID(5)
                .isAvailable(true)
                .lotID(1)
                .build();
        return parkSlotDto;
    }

    public static List<ParkSlot> generateParkSlotList() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        return parkingLot.getParkSlots();
    }

    public static List<ParkSlotDto> generateParkSlotDtoList() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        List<ParkSlot> parkSlotList = parkingLot.getParkSlots();
        List<ParkSlotDto> parkSlotDtoList = new ArrayList<>();

        for (ParkSlot parkSlot : parkSlotList) {
            parkSlotDtoList.add(ParkSlotDto.builder()
                    .slotID(parkSlot.getSlotID())
                    .isAvailable(true)
                    .lotID(1)
                    .build());
        }
        return parkSlotDtoList;
    }

    public static AddressDto generateAddressDto() {
        return AddressDto.builder()
                .addressID(1)
                .street("TestStreet")
                .city("TestCity")
                .postCode("TestPostCode")
                .country("TestCountry")
                .lotID(1)
                .build();
    }

    public static ParkingLotCreateRequest generateParkingLotCreateRequest() {
        return ParkingLotCreateRequest.builder()
                .name("TestParkingLot")
                .street("TestStreet")
                .city("TestCity")
                .postCode("TestPostCode")
                .country("TestCountry")
                .numberOfSlots(50)
                .build();
    }

    public static ParkingLotUpdateRequest generateParkingLotUpdateRequest(int numberOfSlots) {
        return ParkingLotUpdateRequest.builder()
                .name("TestParkingLot2")
                .street("TestStreet2")
                .city("TestCity2")
                .postCode("TestPostCode2")
                .country("TestCountry2")
                .numberOfSlots(numberOfSlots)
                .build();
    }

    public static ParkingLot updateParkingLotWithUpdateRequest(ParkingLot parkingLot, ParkingLotUpdateRequest parkingLotUpdateRequest) {
        Address address = Address.builder()
                .addressID(parkingLot.getAddress().getAddressID())
                .street(parkingLotUpdateRequest.getStreet())
                .city(parkingLotUpdateRequest.getCity())
                .postCode(parkingLotUpdateRequest.getPostCode())
                .parkingLot(parkingLot)
                .country(parkingLotUpdateRequest.getCountry())
                .build();

        parkingLot.setAddress(address);
        parkingLot.setName(parkingLotUpdateRequest.getName());
        Integer size = parkingLot.getParkSlots().size();

        if (size.equals(parkingLotUpdateRequest.getNumberOfSlots())) {
            return parkingLot;

        } else if (parkingLotUpdateRequest.getNumberOfSlots() > size) {

            for (int i = 0; i < parkingLotUpdateRequest.getNumberOfSlots() - size; i++) {
                parkingLot.getParkSlots().add(new ParkSlot(50 + i, true, parkingLot, null));
            }
            return parkingLot;

        } else {

            for (int i = size - 1; i >= parkingLotUpdateRequest.getNumberOfSlots(); i--) {
                parkingLot.getParkSlots().remove(i);
            }
            return parkingLot;
        }
    }

    public static ParkingLotDto generateUpdatedParkingLotDto(ParkingLot parkingLot) {
        List<ParkSlotDto> parkSlotDtoList = new ArrayList<>();

        for (ParkSlot parkSlot : parkingLot.getParkSlots()) {
            parkSlotDtoList.add(ParkSlotDto.builder()
                    .slotID(parkSlot.getSlotID())
                    .isAvailable(parkSlot.isAvailable())
                    .lotID(parkSlot.getParkinglot().getLotID())
                    .build());
        }

        AddressDto addressDto = AddressDto.builder()
                .addressID(parkingLot.getAddress().getAddressID())
                .street(parkingLot.getAddress().getStreet())
                .city(parkingLot.getAddress().getCity())
                .postCode(parkingLot.getAddress().getPostCode())
                .country(parkingLot.getAddress().getCountry())
                .lotID(parkingLot.getLotID())
                .build();

        return ParkingLotDto.builder()
                .lotID(parkingLot.getLotID())
                .name(parkingLot.getName())
                .addressDto(addressDto)
                .parkSlotDtoList(parkSlotDtoList)
                .build();
    }
}

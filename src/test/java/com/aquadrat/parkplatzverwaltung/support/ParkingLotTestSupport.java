package com.aquadrat.parkplatzverwaltung.support;

import com.aquadrat.parkplatzverwaltung.model.Address;
import com.aquadrat.parkplatzverwaltung.model.ParkSlot;
import com.aquadrat.parkplatzverwaltung.model.ParkingLot;

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
}

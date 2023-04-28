package com.aquadrat.parkplatzverwaltung.controller;

import com.aquadrat.parkplatzverwaltung.exception.NotFoundException;
import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotCreateRequest;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotResponse;
import com.aquadrat.parkplatzverwaltung.service.ParkingLotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/parking-lot")
public class ParkingLotController {
    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @PostMapping("/create")
    public ResponseEntity<ParkingLot> createParkingLot(@RequestBody ParkingLotCreateRequest request) {
        return ResponseEntity.ok(parkingLotService.createParkingLot(request));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ParkingLot>> getAll() {
        return new ResponseEntity<>(parkingLotService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{lotID}")
    public ResponseEntity<ParkingLotResponse> getParkingLotById(@PathVariable("lotID") Integer lotID) {
        ParkingLotResponse parkingLotResponse = new ParkingLotResponse();
        try {
            ParkingLot parkingLot = parkingLotService.getParkingLotById(lotID);
            // TODO - parkingLotResponse.set(DTO);
            parkingLotResponse.setMessage(HttpStatus.OK.toString());
            parkingLotResponse.setSuccess(true);
            return ResponseEntity.ok(parkingLotResponse);
        } catch (NotFoundException notFoundException) {
            parkingLotResponse.setMessage(notFoundException.getMessage());
            parkingLotResponse.setSuccess(false);
            return new ResponseEntity<>(parkingLotResponse, HttpStatus.NOT_FOUND);
        }

    }
}

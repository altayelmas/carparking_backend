package com.aquadrat.parkplatzverwaltung.controller;

import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotCreateRequest;
import com.aquadrat.parkplatzverwaltung.service.ParkingLotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("/getAll/{lotID}")
    public ResponseEntity<ParkingLot> getParkingLotById(@PathVariable("lotID") Integer lotID) {
        ParkingLot parkingLot = parkingLotService.getParkingLotById(lotID);
        return new ResponseEntity<>(parkingLot, HttpStatus.OK);
    }
}

package com.aquadrat.parkplatzverwaltung.controller;

import com.aquadrat.parkplatzverwaltung.exception.NotAvailableException;
import com.aquadrat.parkplatzverwaltung.exception.NotFoundException;
import com.aquadrat.parkplatzverwaltung.exception.SlotsNotEmptyException;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotCreateRequest;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotDto;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotResponse;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotUpdateRequest;
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
    public ResponseEntity<ParkingLotDto> createParkingLot(@RequestBody ParkingLotCreateRequest request) {
        return ResponseEntity.ok(parkingLotService.createParkingLot(request));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ParkingLotDto>> getAll() {
        return new ResponseEntity<>(parkingLotService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{lotID}")
    public ResponseEntity<ParkingLotResponse> getParkingLotById(@PathVariable("lotID") Integer lotID) {
        ParkingLotResponse parkingLotResponse = new ParkingLotResponse();
        try {
            ParkingLotDto parkingLotDto = parkingLotService.getParkingLotById(lotID);
            parkingLotResponse.setParkingLotDto(parkingLotDto);
            parkingLotResponse.setMessage(HttpStatus.OK.toString());
            parkingLotResponse.setSuccess(true);
            return ResponseEntity.ok(parkingLotResponse);
        } catch (NotFoundException notFoundException) {
            parkingLotResponse.setMessage(notFoundException.getMessage());
            parkingLotResponse.setSuccess(false);
            return new ResponseEntity<>(parkingLotResponse, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{lotID}")
    public ResponseEntity<ParkingLotResponse> deleteParkingLot(@PathVariable Integer lotID) {
        ParkingLotResponse parkingLotResponse = new ParkingLotResponse();
        try {
            ParkingLotDto parkingLotDto = parkingLotService.deleteParkingLot(lotID);
            parkingLotResponse.setParkingLotDto(parkingLotDto);
            parkingLotResponse.setMessage(HttpStatus.OK.toString());
            parkingLotResponse.setSuccess(true);
            return ResponseEntity.ok(parkingLotResponse);
        } catch (NotFoundException notFoundException){
            parkingLotResponse.setMessage(notFoundException.getMessage());
            parkingLotResponse.setSuccess(false);
            return new ResponseEntity<>(parkingLotResponse, HttpStatus.NOT_FOUND);
        } catch (NotAvailableException notAvailableException) {
            parkingLotResponse.setMessage(notAvailableException.getMessage());
            parkingLotResponse.setSuccess(false);
            return new ResponseEntity<>(parkingLotResponse, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PatchMapping("/update/{lotID}")
    public ResponseEntity<ParkingLotResponse> updateParkingLot(@PathVariable Integer lotID, @RequestBody ParkingLotUpdateRequest lotUpdateRequest) {
        ParkingLotResponse parkingLotResponse = new ParkingLotResponse();
        try {
            ParkingLotDto parkingLotDto = parkingLotService.updateParkingLot(lotID, lotUpdateRequest);
            parkingLotResponse.setParkingLotDto(parkingLotDto);
            parkingLotResponse.setMessage(HttpStatus.OK.toString());
            parkingLotResponse.setSuccess(true);
            return ResponseEntity.ok(parkingLotResponse);
        } catch (NotFoundException notFoundException) {
            parkingLotResponse.setMessage(notFoundException.getMessage());
            parkingLotResponse.setSuccess(false);
            return new ResponseEntity<>(parkingLotResponse, HttpStatus.NOT_FOUND);
        } catch (SlotsNotEmptyException slotsNotEmptyException) {
            parkingLotResponse.setMessage(slotsNotEmptyException.getMessage());
            parkingLotResponse.setSuccess(false);
            return new ResponseEntity<>(parkingLotResponse, HttpStatus.NOT_ACCEPTABLE);
        } catch (NotAvailableException notAvailableException) {
            parkingLotResponse.setMessage(notAvailableException.getMessage());
            parkingLotResponse.setSuccess(false);
            return new ResponseEntity<>(parkingLotResponse, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}

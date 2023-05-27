package com.aquadrat.parkplatzverwaltung.service;

import com.aquadrat.parkplatzverwaltung.exception.NotAvailableException;
import com.aquadrat.parkplatzverwaltung.exception.NotFoundException;
import com.aquadrat.parkplatzverwaltung.exception.SlotsNotEmptyException;
import com.aquadrat.parkplatzverwaltung.mapper.ParkingLotMapper;
import com.aquadrat.parkplatzverwaltung.model.ParkingLot;
import com.aquadrat.parkplatzverwaltung.model.Ticket;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotCreateRequest;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotDto;
import com.aquadrat.parkplatzverwaltung.model.dto.ParkingLotUpdateRequest;
import com.aquadrat.parkplatzverwaltung.repository.ParkingLotRepository;
import com.aquadrat.parkplatzverwaltung.repository.TicketRepository;
import com.aquadrat.parkplatzverwaltung.support.ParkingLotTestSupport;
import com.aquadrat.parkplatzverwaltung.support.TicketTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ParkingLotServiceTest {

    private ParkingLotService parkingLotService;
    private ParkingLotRepository parkingLotRepository;
    private ParkingLotMapper parkingLotMapper;
    private TicketRepository ticketRepository;

    @BeforeEach
    public void setUp() {
        parkingLotRepository = mock(ParkingLotRepository.class);
        parkingLotMapper = mock(ParkingLotMapper.class);
        ticketRepository = mock(TicketRepository.class);

        parkingLotService = new ParkingLotService(parkingLotRepository, parkingLotMapper, ticketRepository);
    }

    @Test
    public void createParkingLotTest() {
        ParkingLotCreateRequest parkingLotCreateRequest = ParkingLotTestSupport.generateParkingLotCreateRequest();
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        ParkingLotDto parkingLotDto = ParkingLotTestSupport.generateParkingLotDto(1);
        when(parkingLotMapper.convertToDto(parkingLot)).thenReturn(parkingLotDto);
        when(parkingLotRepository.save(any())).thenReturn(parkingLot);

        ParkingLotDto result = parkingLotService.createParkingLot(parkingLotCreateRequest);
        assertEquals(parkingLotDto, result);
        verify(parkingLotMapper).convertToDto(parkingLot);
    }

    @Test
    public void getAllTest() {
        List<ParkingLot> parkingLotList = ParkingLotTestSupport.generateParkingLotList(5);
        List<ParkingLotDto> parkingLotDtoList = ParkingLotTestSupport.generateParkingLotDtoList(5);
        when(parkingLotRepository.findAll()).thenReturn(parkingLotList);
        when(parkingLotMapper.lotListToLotDtoList(parkingLotList)).thenReturn(parkingLotDtoList);

        List<ParkingLotDto> result = parkingLotService.getAll();
        assertEquals(parkingLotDtoList, result);
        verify(parkingLotRepository).findAll();
        verify(parkingLotMapper).lotListToLotDtoList(parkingLotList);
    }

    @Test
    public void deleteParkingLotTest() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        when(parkingLotRepository.findById(1)).thenReturn(Optional.of(parkingLot));

        parkingLotService.deleteParkingLot(1);
        verify(parkingLotRepository).findById(1);
        verify(parkingLotRepository).deleteById(1);
    }

    @Test
    public void deleteParkingLotTest_whenParkingLotDoesNotExist_shouldThrowNotFoundException() {
        when(parkingLotRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> parkingLotService.deleteParkingLot(1));
        verify(parkingLotRepository).findById(1);
        verifyNoInteractions(parkingLotMapper);
    }

    @Test
    public void deleteParkingLotTest_whenParkingLotIsNotEmpty_shouldThrowNotAvailableException() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        parkingLot.getParkSlots().get(2).setAvailable(false);
        when(parkingLotRepository.findById(1)).thenReturn(Optional.of(parkingLot));
        assertThrows(NotAvailableException.class, () -> parkingLotService.deleteParkingLot(1));
        verify(parkingLotRepository).findById(1);
        verifyNoInteractions(parkingLotMapper);
    }

    @Test
    public void deleteParkingLotTest_whenParkingLotHasTickets_shouldThrowNotAvailableException() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        List<Ticket> ticketList = TicketTestSupport.generateTicketList(3);
        parkingLot.setTickets(ticketList);
        when(parkingLotRepository.findById(1)).thenReturn(Optional.of(parkingLot));
        assertThrows(NotAvailableException.class, () -> parkingLotService.deleteParkingLot(1));
        verify(parkingLotRepository).findById(1);
        verifyNoInteractions(parkingLotMapper);
    }


    @Test
    public void updateParkingLotTest_whenParkingLotDoesNotExist() {
        when(parkingLotRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> parkingLotService.deleteParkingLot(1));
        verify(parkingLotRepository).findById(1);
        verifyNoInteractions(parkingLotMapper);
    }
    @Test
    public void updateParkingLotTest_whenNumberOfSlotsAreEqual() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        ParkingLotUpdateRequest parkingLotUpdateRequest = ParkingLotTestSupport.generateParkingLotUpdateRequest(50);
        ParkingLot updatedParkingLot = ParkingLotTestSupport.updateParkingLotWithUpdateRequest(parkingLot, parkingLotUpdateRequest);
        ParkingLotDto parkingLotDto = ParkingLotTestSupport.generateUpdatedParkingLotDto(updatedParkingLot);
        when(parkingLotRepository.findById(1)).thenReturn(Optional.of(parkingLot));
        when(parkingLotRepository.save(updatedParkingLot)).thenReturn(updatedParkingLot);
        when(parkingLotMapper.convertToDto(updatedParkingLot)).thenReturn(parkingLotDto);

        ParkingLotDto result = parkingLotService.updateParkingLot(1, parkingLotUpdateRequest);
        assertEquals(parkingLotDto, result);
        verify(parkingLotRepository).findById(1);
        verify(parkingLotRepository).save(updatedParkingLot);
        verify(parkingLotMapper).convertToDto(updatedParkingLot);
    }

    @Test
    public void updateParkingLotTest_whenRequestSlotsAreHigher() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        ParkingLotUpdateRequest parkingLotUpdateRequest = ParkingLotTestSupport.generateParkingLotUpdateRequest(60);
        ParkingLot updatedParkingLot = ParkingLotTestSupport.updateParkingLotWithUpdateRequest(parkingLot, parkingLotUpdateRequest);
        ParkingLotDto parkingLotDto = ParkingLotTestSupport.generateUpdatedParkingLotDto(updatedParkingLot);
        when(parkingLotRepository.findById(1)).thenReturn(Optional.of(parkingLot));
        when(parkingLotRepository.save(updatedParkingLot)).thenReturn(updatedParkingLot);
        when(parkingLotMapper.convertToDto(updatedParkingLot)).thenReturn(parkingLotDto);

        ParkingLotDto result = parkingLotService.updateParkingLot(1, parkingLotUpdateRequest);
        assertEquals(parkingLotDto, result);
        verify(parkingLotRepository).findById(1);
        verify(parkingLotRepository).save(updatedParkingLot);
        verify(parkingLotMapper).convertToDto(updatedParkingLot);
    }

    @Test
    public void updateParkingLotTest_whenRequestSlotsAreLower() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        ParkingLotUpdateRequest parkingLotUpdateRequest = ParkingLotTestSupport.generateParkingLotUpdateRequest(30);
        ParkingLot updatedParkingLot = ParkingLotTestSupport.updateParkingLotWithUpdateRequest(parkingLot, parkingLotUpdateRequest);
        ParkingLotDto parkingLotDto = ParkingLotTestSupport.generateUpdatedParkingLotDto(updatedParkingLot);
        when(parkingLotRepository.findById(1)).thenReturn(Optional.of(parkingLot));
        when(parkingLotRepository.save(updatedParkingLot)).thenReturn(updatedParkingLot);
        when(parkingLotMapper.convertToDto(updatedParkingLot)).thenReturn(parkingLotDto);

        ParkingLotDto result = parkingLotService.updateParkingLot(1, parkingLotUpdateRequest);
        assertEquals(parkingLotDto, result);
        verify(parkingLotRepository).findById(1);
        verify(parkingLotRepository).save(updatedParkingLot);
        verify(parkingLotMapper).convertToDto(updatedParkingLot);
    }

    @Test
    public void updateParkingLotTest_whenRequestSlotsAreLowerAndTheSlotsAreNotEmpty_shouldThrowSlotsNotEmptyException() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        parkingLot.getParkSlots().get(35).setAvailable(false);
        ParkingLotUpdateRequest parkingLotUpdateRequest = ParkingLotTestSupport.generateParkingLotUpdateRequest(30);
        when(parkingLotRepository.findById(1)).thenReturn(Optional.of(parkingLot));
        assertThrows(SlotsNotEmptyException.class, () -> parkingLotService.updateParkingLot(1, parkingLotUpdateRequest));
        verify(parkingLotRepository).findById(1);
        verifyNoInteractions(parkingLotMapper);
    }

    @Test
    public void updateParkingLotTest_whenRequestSlotsAreLowerAndThereAreTickets_shouldThrowNotAvailableException() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        ParkingLotUpdateRequest parkingLotUpdateRequest = ParkingLotTestSupport.generateParkingLotUpdateRequest(30);
        List<Ticket> ticketList = TicketTestSupport.generateTicketList(3);
        when(parkingLotRepository.findById(1)).thenReturn(Optional.of(parkingLot));
        when(ticketRepository.findTicketsByParkSlot(parkingLot.getParkSlots().get(35))).thenReturn(ticketList);
        assertThrows(NotAvailableException.class, () -> parkingLotService.updateParkingLot(1, parkingLotUpdateRequest));
        verify(parkingLotRepository).findById(1);
        verifyNoInteractions(parkingLotMapper);
    }


    @Test
    public void getParkingLotByIdTest() {
        ParkingLot parkingLot = ParkingLotTestSupport.generateParkingLot(1);
        ParkingLotDto parkingLotDto = ParkingLotTestSupport.generateParkingLotDto(1);
        when(parkingLotRepository.findById(1)).thenReturn(Optional.of(parkingLot));
        when(parkingLotMapper.convertToDto(parkingLot)).thenReturn(parkingLotDto);

        ParkingLotDto result = parkingLotService.getParkingLotById(1);
        assertEquals(parkingLotDto, result);
        verify(parkingLotRepository).findById(1);
        verify(parkingLotMapper).convertToDto(parkingLot);
    }

    @Test
    public void getParkingLotByIdTest_whenParkingLotDoesNotExist_shouldThrowNotFoundException() {
        when(parkingLotRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> parkingLotService.getParkingLotById(1));
        verify(parkingLotRepository).findById(1);
        verifyNoInteractions(parkingLotMapper);
    }

}

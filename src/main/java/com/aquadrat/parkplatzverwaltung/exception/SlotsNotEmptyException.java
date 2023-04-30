package com.aquadrat.parkplatzverwaltung.exception;

public class SlotsNotEmptyException extends RuntimeException{
    public SlotsNotEmptyException(String message) {
        super(message);
    }
}

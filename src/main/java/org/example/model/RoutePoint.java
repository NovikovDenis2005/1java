package org.example.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RoutePoint {
    private String stationName;
    private LocalTime arrivalTime;
    private LocalTime departureTime;

    public RoutePoint(String stationName, LocalTime arrivalTime, LocalTime departureTime) {
        this.stationName = stationName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
        String arrival = arrivalTime != null ? arrivalTime.format(fmt) : "--:--";
        String departure = departureTime != null ? departureTime.format(fmt) : "--:--";
        return stationName + " (прибытие: " + arrival + ", отправление: " + departure + ")";
    }
}

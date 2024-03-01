package by.itacademy.airport.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Route {
    private Integer id;
    private City arrivalCity;
    private City departureCity;
    private Plane plane;
    private String number;
    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;
    private Double routeTime;
}

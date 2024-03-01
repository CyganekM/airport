package by.itacademy.airport.entity;

import lombok.Data;

@Data
public class Ticket {
    private Integer id;
    private User user;
    private Route route;
    private String status;
    private String seat;
}

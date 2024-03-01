package by.itacademy.airport.entity;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private Integer id;
    private String name;
    private String role;
    private String job;
    private List<Plane> planes;
}
